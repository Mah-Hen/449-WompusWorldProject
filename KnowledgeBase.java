import java.beans.PropertyEditor;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class KnowledgeBase {
    Set<PropositionalSentence> KnowledgeBase; // a knowledge base
    int time; // a counter indicating time
    Board brd;

    public KnowledgeBase(Board brd) {
        KnowledgeBase = new LinkedHashSet<PropositionalSentence>(); // When using a normal hashSet, the order of the values in the set would change for every run
        this.brd = brd;
    }

    public String KB_Agent(String[] percepts) {
        String action = " ";
        for(int i=0; i<percepts.length; i++){
            Tell(Make_Percept_Sentence(percepts, i));}
        action = Ask();
        //Tell(Make_Action_Sentence(action));

        // this.time += 1;
        return action;
    }

    private Literal Make_Percept_Sentence(String[] percepts, int inc) {
        PropositionalSentence perceptSentence = new PropositionalSentence(null, "", null);
        int row = brd.getPlayer().getRow();
        int col = brd.getPlayer().getCol();
        String percept = "";

        
        if(percepts[inc] == "None"){
           if(inc == 0){
            percept = "Stench";
           }
           else if(inc == 1){
            percept = "Breeze";
           }
           else{
            percept = "Glitter";
           }
            Literal perceptClause = new Literal(percept, row, col);
            perceptClause.setNot();;
            return perceptClause;
        }
        else
            return null;
    }

    private PropositionalSentence Make_Action_Query() {
        Literal query = new Literal("", -1, -1);
        Literal alpha = new Literal("", -1, -1);
        boolean queried = false;
        Random rand = new Random();
        Player agent = this.brd.getPlayer();
        int row = 0;
        int col = 0;

        // We're trying to figure out how to turn information from knowledge base into one sentence about our next move. Whether this move is safe or not. Use if statements
        for(PropositionalSentence info: KnowledgeBase){
            if(queried){
                break;
            }
            if(!info.hasPropConnective()){
                Literal infoClause = info.getBackConnectee();
                alpha = new Literal(infoClause.getPercept(), infoClause.getRow(), infoClause.getCol());
                if(infoClause.isNot()){
                    alpha.setNot();
                }
            }
            if(info.hasPropConnective()){
                String connective = info.getPropConnective();
                    if(connective.equals("<->")){ 
                        PropositionalSentence secondPropSentence = info.getPropSentence();
                        if(secondPropSentence.getLiteralConnective().equals("V")){ // if a conjuction
                                Literal  firstConjuctee = secondPropSentence.getBackConnectee();
                                Literal secondConjuctee = secondPropSentence.getFrontConnectee();
                                
                                int firstPropRow = firstConjuctee.getRow();
                                int firstPropCol = firstConjuctee.getCol();
                                String firstProp = firstConjuctee.getPercept();
                                
                                String secondProp = secondConjuctee.getPercept();
                                int secondPropRow = secondConjuctee.getRow();
                                int secondPropCol = secondConjuctee.getCol();
    
                            if(alpha.isNot()){ // !Breeze(row, col)
                                if(firstProp.equals("Wumpus")|| firstProp.equals("Pit")){
                                    int rng = rand.nextInt(2)+1;
                                    if(rng%2==0){
                                        query = new Literal(firstProp, firstPropRow, firstPropCol);
                                        queried = true;
                                        break;
                                    }
                                    else{
                                        query = new Literal(secondProp, secondPropRow, secondPropCol);
                                        queried = true;
                                        break;
                                    }
                                }
                                else if(firstProp.equals("Gold")){
                                    int rng = rand.nextInt(2)+1;
                                    if(rng%2==0){
                                        query = new Literal(firstProp, firstPropRow, firstPropCol);
                                        queried = true;
                                        break;
                                    }
                                    else{
                                        query = new Literal(secondProp, secondPropRow, secondPropCol);
                                        queried = true;
                                        break;
                                    }

                                }   
                            }
                            else{
                                if(firstProp.equals("Wumpus")|| firstProp.equals("Pit")){
                                    int rng = rand.nextInt(2)+1;
                                    if(rng%2==0){
                                        query = new Literal(firstProp, firstPropRow, firstPropCol);
                                        query.setNot();
                                        queried = true;
                                        break;
                                    }
                                    else{
                                        query = new Literal(secondProp, secondPropRow, secondPropCol);
                                        query.setNot();
                                        queried = true;
                                        break;
                                    }
                                }
                                else if(firstProp.equals("Gold")){
                                    int rng = rand.nextInt(2)+1;
                                    if(rng%2==0){
                                        query = new Literal(firstProp, firstPropRow, firstPropCol);
                                        query.setNot();
                                        queried = true;
                                        break;
                                    }
                                    else{
                                        query = new Literal(secondProp, secondPropRow, secondPropCol);
                                        query.setNot();
                                        queried = true;
                                        break;
                                    }
                                }  
                            }
                        }
    
                    }
            }
        }
        
        return new PropositionalSentence(query, "", null);
    }

    private PropositionalSentence Make_Action_Sentence(String action, int time) {
        int len = action.length();

        PropositionalSentence perceptSentence = new PropositionalSentence(null, "", null);
        return perceptSentence;
    }

    private void Tell(Literal perceptClause){
        int row = brd.getPlayer().getRow();
        int col = brd.getPlayer().getCol();
        PropositionalSentence perceptSequence = new PropositionalSentence(perceptClause, "", null);
        KnowledgeBase.add(perceptSequence);
        String percept = perceptClause.getPercept();
        ArrayList <Literal>  newPropSentence = new <Literal> ArrayList();

        for(int incRow=-1; incRow<=1; incRow++){
            for(int incCol=-1; incCol<=1; incCol++){
                int adjacentRow = incRow + row;
                int adjacentCol = incCol + col;
                if(brd.validCell(adjacentRow, adjacentCol)){
                    if(brd.adjacentCell(row, col, adjacentRow, adjacentCol)){
                        if(perceptSequence.getBackConnectee().isNot()){
                            if(percept.equals("Stench")){
                                newPropSentence.add(new Literal("Wumpus", adjacentRow, adjacentCol));
                            }
                            else if(percept.equals("Breeze")){
                                newPropSentence.add(new Literal("Pit", adjacentRow, adjacentCol));
                            }
                            else{
                                newPropSentence.add(new Literal("Gold", adjacentRow, adjacentCol));
                            }
                        }
                        else{
                            if(percept.equals("Stench")){
                                Literal propClause = new Literal("Wumpus", adjacentRow, adjacentCol);
                                propClause.setNot();;
                                newPropSentence.add(propClause);
                            }
                            else if(percept.equals("Breeze")){
                                Literal propClause = new Literal("Pit", adjacentRow, adjacentCol);
                                propClause.setNot();
                                newPropSentence.add(propClause);
                            }
                            else{
                                Literal propClause = new Literal("Gold", adjacentRow, adjacentCol);
                                propClause.setNot();
                                newPropSentence.add(propClause);
                            } 
                        }
                    }
                }
            }
        }
        PropositionalSentence disjunctiveSentence = createDisjunction(newPropSentence);
        Literal newPerceptClause = new Literal(percept, row, col);
        PropositionalSentence newPerceptSequence = new PropositionalSentence(newPerceptClause, "", null);
        newPerceptSequence.iff(disjunctiveSentence);
        if(!KnowledgeBase.contains(newPerceptSequence)){ // no duplicated information in KB
            KnowledgeBase.add(newPerceptSequence);
        }

    }




    private String Ask(){
        ArrayList<PropositionalSentence> actions = new ArrayList<PropositionalSentence>();
        String action = "";
        int cnt = 0;

        for(int i=0; i<3; i++){
            PropositionalSentence action_Query = Make_Action_Query();
            Boolean entailed = PL_Resolution(action_Query);
            if(entailed){
              actions.add(action_Query);  
            }

        }
        action = getAction(actions);
        return action;
    } 

    private Boolean PL_Resolution(PropositionalSentence action_Query) {
        // O(N^2)
        ArrayList<PropositionalSentence> resolvents = new ArrayList<>();
        PropositionalSentence query = new PropositionalSentence(action_Query.getBackConnectee(), action_Query.getPropConnective(), action_Query.getFrontConnectee()); 
        ArrayList<PropositionalSentence> cnfKB = convertKBintoCNF(); // has to be a KB of separate clauses, list of ORs  
        query.not(); 
        cnfKB.add(query);

        ArrayList<PropositionalSentence> newClauses = new ArrayList<>();

        while(true){
            for(int i=0; i<cnfKB.size()-1; i++){
                for(int j=0; j<cnfKB.size(); j++){
                    if(i!=j & complementary(cnfKB.get(i), cnfKB.get(j))){
                        resolvents = PL_Resolve(cnfKB.get(i), cnfKB.get(j));

                        if(resolvents.isEmpty()){
                            return true;
                        }
                        for(PropositionalSentence clause: resolvents){
                            newClauses.add(clause); 
                        }
                    }
                }
            }
            for(int i=0; i<cnfKB.size()-1; i++){
                for(int j=0; j<cnfKB.size(); j++){
                    if(newClauses.equals(cnfKB)){
                        return false;
                    }
                }
            }
            for(PropositionalSentence newClause: newClauses){
                cnfKB.add(newClause);
            }
        }
    }

    private ArrayList<PropositionalSentence> PL_Resolve(PropositionalSentence propFirstSentence, PropositionalSentence propSecondSentence) {
        boolean resoluted = false;
        ArrayList<PropositionalSentence> resolvents = new ArrayList<PropositionalSentence>();
        // Still trying to figure out how to resolve these two strings, next attempt could be a grab everything in one sentence and check to see for its opposite. 
        while(!resoluted){
            Literal firstPropFirstConjuctee = propFirstSentence.getBackConnectee();
            Literal secondPropFirstConjuctee = propSecondSentence.getBackConnectee();
            if(firstPropFirstConjuctee.getPercept().equals(secondPropFirstConjuctee.getPercept()) & 
            (firstPropFirstConjuctee.isNot() || secondPropFirstConjuctee.isNot()) & 
            (firstPropFirstConjuctee.getRow()==secondPropFirstConjuctee.getRow() & 
            firstPropFirstConjuctee.getCol() == secondPropFirstConjuctee.getCol())){
                if(propFirstSentence.hasPropConnective()){
                    PropositionalSentence firstProp = new PropositionalSentence(propFirstSentence.getFrontConnectee(), "", null);
                    firstProp.or(propFirstSentence.getPropSentence());
                    propFirstSentence = firstProp;

                }
                else if(propFirstSentence.hasLiteralConnective()){
                    propFirstSentence = new PropositionalSentence(propFirstSentence.getFrontConnectee(), "", null);
                }
                else{
                    propFirstSentence = null;
                    resoluted = true;
                }

                if(propSecondSentence.hasPropConnective()){
                    PropositionalSentence secondProp = new PropositionalSentence(propSecondSentence.getFrontConnectee(), "", null);
                    secondProp.or(propSecondSentence.getPropSentence());
                    propSecondSentence = secondProp;
                }
                else if(propSecondSentence.hasLiteralConnective()){
                    propSecondSentence = new PropositionalSentence(propSecondSentence.getFrontConnectee(), "", null);
                }
                else{
                    propSecondSentence = null;
                    resoluted = true;
                }
            }

            else if(!firstPropFirstConjuctee.getPercept().equals(secondPropFirstConjuctee.getPercept())){
                if(!propSecondSentence.hasLiteralConnective()){
                    Literal firstPropSecondConjuctee = propFirstSentence.getFrontConnectee();
                    if(firstPropSecondConjuctee.getPercept().equals(secondPropFirstConjuctee.getPercept()) &
                        (firstPropSecondConjuctee.isNot() || secondPropFirstConjuctee.isNot()) & 
                        (firstPropSecondConjuctee.getRow()==secondPropFirstConjuctee.getRow() & 
                        firstPropSecondConjuctee.getCol() == secondPropFirstConjuctee.getCol())){
                            if(propFirstSentence.hasPropConnective()){ // here
                                if(!propFirstSentence.getPropSentence().hasLiteralConnective()){
                                    PropositionalSentence firstProp = new PropositionalSentence(propFirstSentence.getBackConnectee(), "V", propFirstSentence.getPropSentence().getBackConnectee());
                                    propFirstSentence = firstProp;
                                }
                                else{
                                    PropositionalSentence firstProp = new PropositionalSentence(propFirstSentence.getBackConnectee(), "", null);
                                    firstProp.or(propFirstSentence.getPropSentence());
                                    propFirstSentence = firstProp;
                                }
                            }
                            else if(propFirstSentence.hasLiteralConnective()){
                                propFirstSentence = new PropositionalSentence(propFirstSentence.getBackConnectee(), "", null);
                            }
                            else{
                                propFirstSentence = null;
                                resoluted = true;
                            }

                            if(propSecondSentence.hasPropConnective()){
                                if(!propFirstSentence.getPropSentence().hasLiteralConnective()){
                                    PropositionalSentence secondProp = new PropositionalSentence(propSecondSentence.getFrontConnectee(), "V", propSecondSentence.getPropSentence().getBackConnectee());
                                    propFirstSentence = secondProp;
                                }
                                else{
                                    PropositionalSentence secondProp = new PropositionalSentence(propSecondSentence.getFrontConnectee(), "", null);
                                    secondProp.or(propSecondSentence.getPropSentence());
                                    propSecondSentence = secondProp;
                                    }
                            }
                            
                            else if(propSecondSentence.hasLiteralConnective()){
                                propSecondSentence = new PropositionalSentence(propSecondSentence.getFrontConnectee(), "", null);
                            }
                            else{
                                propSecondSentence = null;
                                resoluted = true;
                            }  
                    }
                    else{
                    Literal firstPropThirdConjuctee = propFirstSentence.getPropSentence().getBackConnectee();
                    if(firstPropThirdConjuctee.getPercept().equals(secondPropFirstConjuctee.getPercept()) &
                        (firstPropThirdConjuctee.isNot() || secondPropFirstConjuctee.isNot()) & 
                        (firstPropThirdConjuctee.getRow()==secondPropFirstConjuctee.getRow() & 
                        firstPropThirdConjuctee.getCol() == secondPropFirstConjuctee.getCol())){
                            if(propFirstSentence.getPropSentence().hasLiteralConnective()){ 
                                    PropositionalSentence firstProp = new PropositionalSentence(propFirstSentence.getBackConnectee(), "V", propFirstSentence.getFrontConnectee());
                                    PropositionalSentence secondProp = new PropositionalSentence(propFirstSentence.getPropSentence().getFrontConnectee(), "", null);
                                    firstProp.or(secondProp);
                                    propFirstSentence = firstProp;
                            }
                            else if(propFirstSentence.hasLiteralConnective()){
                                propFirstSentence = new PropositionalSentence(propFirstSentence.getBackConnectee(), "V", propFirstSentence.getFrontConnectee());
                            }
                            else{
                                propFirstSentence = null;
                                resoluted = true;
                            }

                            if(propSecondSentence.hasPropConnective()){
                                PropositionalSentence firstProp = new PropositionalSentence(propSecondSentence.getBackConnectee(), "V", propSecondSentence.getFrontConnectee());
                                PropositionalSentence secondProp = new PropositionalSentence(propSecondSentence.getPropSentence().getFrontConnectee(), "", null);
                                firstProp.or(secondProp);
                                propFirstSentence = firstProp;
                            }
                            
                            else if(propSecondSentence.hasLiteralConnective()){
                                propSecondSentence = new PropositionalSentence(propSecondSentence.getBackConnectee(), "V", propSecondSentence.getFrontConnectee());
                            }
                            else{
                                propSecondSentence = null;
                                resoluted = true;
                            }  
                        }
                    }
                }
                else{
                    Literal secondPropSecondConjuctee = propSecondSentence.getFrontConnectee(); // next/second literal in the second Propositional Sentence
                    if(firstPropFirstConjuctee.getPercept().equals(secondPropSecondConjuctee.getPercept())){
                        if((firstPropFirstConjuctee.isNot() || secondPropSecondConjuctee.isNot()) & (firstPropFirstConjuctee.getRow()==secondPropSecondConjuctee.getRow() & firstPropFirstConjuctee.getCol() == secondPropSecondConjuctee.getCol())){
                            if(propFirstSentence.hasPropConnective()){
                                if(!propFirstSentence.getPropSentence().hasLiteralConnective()){ // if the connected prop sentence doesn't have an extra literal
                                    PropositionalSentence firstProp = new PropositionalSentence(propFirstSentence.getFrontConnectee(), "V", propFirstSentence.getPropSentence().getBackConnectee()); // since we're removing the back/first literal,  we're combining the two literal left in the sentence.
                                    propFirstSentence = firstProp;
                                }
                                else{
                                    PropositionalSentence firstProp = new PropositionalSentence(propFirstSentence.getFrontConnectee(), "", null);
                                    firstProp.or(propFirstSentence.getPropSentence());
                                    propFirstSentence = firstProp;
                                }
                            }
                            else if(propFirstSentence.hasLiteralConnective()){
                                propFirstSentence = new PropositionalSentence(propFirstSentence.getFrontConnectee(), "", null);
                            }
                            else{
                                propFirstSentence = null;
                                resoluted = true;
                            }

                            if(propSecondSentence.hasPropConnective()){ // if second prop sentence has another prop sentence connected
                                if(!propSecondSentence.getPropSentence().hasLiteralConnective()){ // // if the first prop sentence's "connected prop sentence" does not have an extra literal
                                    PropositionalSentence secondProp = new PropositionalSentence(propSecondSentence.getBackConnectee(), "V", propSecondSentence.getPropSentence().getBackConnectee());
                                    propSecondSentence = secondProp;
                                }
                                else{
                                    PropositionalSentence secondProp = new PropositionalSentence(propSecondSentence.getBackConnectee(), "", null);
                                    secondProp.or(propSecondSentence.getPropSentence());
                                    propSecondSentence = secondProp;
                                    }
                            }
                            
                            else if(propSecondSentence.hasLiteralConnective()){ // if the second prop sentence has an extra/second literal
                                propSecondSentence = new PropositionalSentence(propSecondSentence.getBackConnectee(), "", null); // since we're removing the second literal, we'll create a new prop sentence with the first literal of the sentence
                            }
                            else{
                                propSecondSentence = null;
                                resoluted = true;
                            }
                        }
                    }
                }
            }
            else{  // the two sentences have the same percept just not the same row/col
                if(propFirstSentence.hasLiteralConnective()){
                   Literal firstPropSecondConjuctee = propFirstSentence.getFrontConnectee(); // select the next literal in the first prop sentence
                   if(firstPropSecondConjuctee.getPercept().equals(secondPropFirstConjuctee.getPercept()) & 
                    (firstPropSecondConjuctee.isNot() || secondPropFirstConjuctee.isNot()) & 
                    (firstPropSecondConjuctee.getRow()==secondPropFirstConjuctee.getRow() & 
                    firstPropSecondConjuctee.getCol() == secondPropFirstConjuctee.getCol())){ // that way we can check for opposing literal again
                        if(propFirstSentence.hasPropConnective()){
                            PropositionalSentence firstProp = new PropositionalSentence(propFirstSentence.getBackConnectee(), "", null);
                            firstProp.or(propFirstSentence.getPropSentence());
                            propFirstSentence = firstProp;
                        }
                        else if(propFirstSentence.hasLiteralConnective()){
                            propFirstSentence = new PropositionalSentence(propFirstSentence.getBackConnectee(), "", null);
                        }
                        else{
                            propFirstSentence = null;
                            resoluted = true;
                        }

                        if(propSecondSentence.hasPropConnective()){
                            PropositionalSentence secondProp = new PropositionalSentence(propSecondSentence.getFrontConnectee(), "", null);
                            secondProp.or(propSecondSentence.getPropSentence());
                            propSecondSentence = secondProp;
                        }
                        else if(propSecondSentence.hasLiteralConnective()){
                            propSecondSentence = new PropositionalSentence(propSecondSentence.getFrontConnectee(), "", null);
                        }
                        else{
                            propSecondSentence = null;
                            resoluted = true;
                        }
                    }

                    else if(propSecondSentence.hasLiteralConnective()){
                        Literal secondPropSecondConjuctee = propSecondSentence.getFrontConnectee(); // select the next literal in the first prop sentence
                        if(firstPropFirstConjuctee.getPercept().equals(secondPropSecondConjuctee.getPercept()) & 
                        (firstPropFirstConjuctee.isNot() || secondPropSecondConjuctee.isNot()) & 
                        (firstPropFirstConjuctee.getRow()==secondPropSecondConjuctee.getRow() & 
                        firstPropFirstConjuctee.getCol() == secondPropSecondConjuctee.getCol())){ // that way we can check for opposing literal again
                            if(propFirstSentence.hasPropConnective()){
                                PropositionalSentence firstProp = new PropositionalSentence(propFirstSentence.getBackConnectee(), "", null);
                                firstProp.or(propFirstSentence.getPropSentence());
                                propFirstSentence = firstProp;
                            }
                            else if(propFirstSentence.hasLiteralConnective()){
                                propFirstSentence = new PropositionalSentence(propFirstSentence.getBackConnectee(), "", null);
                            }
                            else{
                                propFirstSentence = null;
                                resoluted = true;
                            }

                            if(propSecondSentence.hasPropConnective()){
                                PropositionalSentence secondProp = new PropositionalSentence(propSecondSentence.getFrontConnectee(), "", null);
                                secondProp.or(propSecondSentence.getPropSentence());
                                propSecondSentence = secondProp;
                            }
                            else if(propSecondSentence.hasLiteralConnective()){
                                propSecondSentence = new PropositionalSentence(propSecondSentence.getFrontConnectee(), "", null);
                            }
                            else{
                                propSecondSentence = null;
                                resoluted = true;
                            }

                        }
                    }
                }
            }
        }
    
        if(propFirstSentence != null)
            resolvents.add(propFirstSentence);
        if(propSecondSentence != null)
            resolvents.add(propSecondSentence);
        return resolvents;         
    }


    private boolean complementary(PropositionalSentence propFirstSentence, PropositionalSentence propSecondSentence){
        Literal firstPropFirstConjunctee = propFirstSentence.getBackConnectee();
        Literal secondPropFirstConjunctee = propSecondSentence.getBackConnectee();
        
        // Check if the back connectees of the two sentences are complementary
        if (firstPropFirstConjunctee.getPercept().equals(secondPropFirstConjunctee.getPercept())) {
            return true;
        }
        
        // Check if the first sentence has a literal connective
        if (propFirstSentence.hasLiteralConnective()) {
            Literal firstPropSecondConjunctee = propFirstSentence.getFrontConnectee();
            // Check if the forward connectee of the first sentence is complementary to the back connectee of the second sentence
            if (firstPropSecondConjunctee.getPercept().equals(secondPropFirstConjunctee.getPercept())) {
                return true;
            }
            
            // Check if the first sentence has a propositional connective
            if (propFirstSentence.hasPropConnective()) {
                PropositionalSentence firstPropSecondSentence = propFirstSentence.getPropSentence();
                // Check if the back connectee of the second sentence is complementary to the back connectee of the second sentence
                if (firstPropSecondSentence.getBackConnectee().getPercept().equals(secondPropFirstConjunctee.getPercept())) {
                    return true;
                }
            }
        }
        
        // Check if the second sentence has a literal connective
        if (propSecondSentence.hasLiteralConnective()) {
            Literal secondPropSecondConjunctee = propSecondSentence.getFrontConnectee();
            // Check if the back connectee of the first sentence is complementary to the forward connectee of the second sentence
            if (firstPropFirstConjunctee.getPercept().equals(secondPropSecondConjunctee.getPercept())) {
                return true;
            }
            
            // Check if the second sentence has a propositional connective
            if (propSecondSentence.hasPropConnective()) {
                PropositionalSentence secondPropSecondSentence = propSecondSentence.getPropSentence();
                // Check if the back connectee of the first sentence is complementary to the back connectee of the second sentence
                if (firstPropFirstConjunctee.getPercept().equals(secondPropSecondSentence.getBackConnectee().getPercept())) {
                    return true;
                }
            }
        }
        
        // If no complementary pairs are found, return false
        return false;
}




    private String getAction(ArrayList<PropositionalSentence> actions){
       // Literal literalQuery = query.getBackConnectee();
        
        return "";
    }
    

    private PropositionalSentence createDisjunction(ArrayList<Literal> propSentence){ 
        PropositionalSentence firstSentence = new PropositionalSentence(null, "", null);
        for(int i=0; i<propSentence.size(); i+=2){
            if(i==0){
                firstSentence = new PropositionalSentence(propSentence.get(i), "V", propSentence.get(i+1));
            }
            if(i%2==0 & i!=0){
                PropositionalSentence secondSentence = new PropositionalSentence(propSentence.get(i), "V", propSentence.get(i+1));
                firstSentence.or(secondSentence);
            }
            
        }

        return firstSentence;
    }



    private ArrayList<PropositionalSentence> convertKBintoCNF(){
        PropositionalSentence separateClause = new PropositionalSentence(null, "", null);
        ArrayList<PropositionalSentence> cnf = new ArrayList<>();
        
        for(PropositionalSentence info: this.KnowledgeBase){
            if(info.hasPropConnective()){
                if(info.getPropConnective().equals("<->")){
                    separateClause = eliminateBiconditional(info); // create a propositional sentence and separate conjuctions into seprate clauses

                }
                else if(info.getPropConnective().equals("->")){
                    separateClause = eliminateImplication(info);
                }
                for(PropositionalSentence clause: separateClause.separateConjuction()){
                    cnf.add(clause);
                }                     
            }
        }
     
        return cnf;
    }


    private PropositionalSentence eliminateBiconditional(PropositionalSentence info){
        PropositionalSentence elimBiSentence = new PropositionalSentence(null, "", null);

        Literal infoClause = info.getBackConnectee();
        String propSentence = infoClause.getPercept();
        int row = infoClause.getRow();
        int col =  infoClause.getCol(); 
        PropositionalSentence secondPropSentence = info.getPropSentence();
        
        if(secondPropSentence.getLiteralConnective().equals("V")){ // if a disjunction
            Literal  firstConjuctee = secondPropSentence.getBackConnectee();
            Literal secondConjuctee = secondPropSentence.getFrontConnectee();
            
            int firstPropRow = firstConjuctee.getRow();
            int firstPropCol = firstConjuctee.getCol(); // plus 2 for the space between
            String firstProp = firstConjuctee.getPercept();
            
            String secondProp = secondConjuctee.getPercept();
            int secondPropRow = secondConjuctee.getRow();
            int secondPropCol = secondConjuctee.getCol();

            Literal newFirstConjuctee = new Literal(firstProp, firstPropRow, firstPropCol);
            Literal newSecondConjuctee = new Literal(secondProp, secondPropRow, secondPropCol);
            Literal newthirdConjuctee = new Literal(propSentence, row, col);
            
            
            PropositionalSentence newFirstPropSentence = new PropositionalSentence(newFirstConjuctee, "V", newSecondConjuctee);
            PropositionalSentence newSecondPropSentence = new PropositionalSentence(newthirdConjuctee, "", null);
            newSecondPropSentence.implies(newFirstPropSentence);
            PropositionalSentence firstNoneImpliedSentence = eliminateImplication(newSecondPropSentence);

            
            // Other side of the elimination
            newFirstPropSentence = new PropositionalSentence(newFirstConjuctee, "V", newSecondConjuctee);
            newSecondPropSentence = new PropositionalSentence(newthirdConjuctee, "", null);
            newFirstPropSentence.implies(newSecondPropSentence);
            PropositionalSentence secondNoneImpliedSentence = eliminateImplication(newFirstPropSentence);

            firstNoneImpliedSentence.and(secondNoneImpliedSentence);
            elimBiSentence = firstNoneImpliedSentence;
        }


    return elimBiSentence;

    }

    private PropositionalSentence eliminateImplication(PropositionalSentence impliedSentence){
        PropositionalSentence elimImpSentence = new PropositionalSentence(null, "", null);
        Literal propLiteral = impliedSentence.getBackConnectee();
        int row = propLiteral.getRow();
        int col = propLiteral.getCol();

        PropositionalSentence secondPropSentence = impliedSentence.getPropSentence();
        if(secondPropSentence.getLiteralConnective().equals("V")){ // if a disjunction
            Literal  secondConjuctee = secondPropSentence.getBackConnectee();
            Literal thirdConjuctee = secondPropSentence.getFrontConnectee();
            
            String secondProp = secondConjuctee.getPercept();
            int secondPropRow = secondConjuctee.getRow();
            int secondPropCol = secondConjuctee.getCol();

            int thirdPropRow = thirdConjuctee.getRow();
            int thirdPropCol = thirdConjuctee.getCol();
            String thirdProp = thirdConjuctee.getPercept();

            Literal newFirstConjuctee = new Literal(propLiteral.getPercept(), row, col);
            Literal newSecondConjuctee = new Literal(secondProp, secondPropRow, secondPropCol);
            Literal newThirdConjuctee = new Literal(thirdProp, thirdPropRow, thirdPropCol);
            

            newFirstConjuctee.setNot();
            elimImpSentence = new PropositionalSentence(newFirstConjuctee, "V", newSecondConjuctee);
            PropositionalSentence newSecondPropSentence = new PropositionalSentence(newThirdConjuctee, "", null);
            elimImpSentence.or(newSecondPropSentence);
            //moveNotInwards(elimImpSentence);

            } 
        else{
            Literal secondConjuctee = impliedSentence.getFrontConnectee();
            String secondProp = secondConjuctee.getPercept();
            int secondPropRow = secondConjuctee.getRow();
            int secondPropCol = secondConjuctee.getCol();

            Literal thirdConjuctee = impliedSentence.getPropSentence().getBackConnectee();
            int thirdPropRow = thirdConjuctee.getRow();
            int thirdPropCol = thirdConjuctee.getCol();
            String thirdProp = thirdConjuctee.getPercept();

            PropositionalSentence newFirstPropSentence = new PropositionalSentence(propLiteral, "V", secondConjuctee);
            newFirstPropSentence.not();
            PropositionalSentence newSecondPropSentence = new PropositionalSentence(thirdConjuctee, "", null);
            newFirstPropSentence.or(newSecondPropSentence);
            elimImpSentence = newFirstPropSentence;          
        }

           


    return checkConjuction(elimImpSentence);
}


    private PropositionalSentence checkConjuction(PropositionalSentence propSentence){
        if(propSentence.getLiteralConnective().equals("^") || propSentence.getPropConnective().equals("^")){ // Has a conjuction
            Literal firstConjuctee = propSentence.getBackConnectee();
            Literal secondConjuctee = propSentence.getFrontConnectee();
            Literal thirdConjuctee = propSentence.getPropSentence().getBackConnectee();

            int firstPropRow = firstConjuctee.getRow();
            int firstPropCol = firstConjuctee.getCol(); // plus 2 for the space between
            String firstProp = firstConjuctee.getPercept();
            
            String secondProp = secondConjuctee.getPercept();
            int secondPropRow = secondConjuctee.getRow();
            int secondPropCol = secondConjuctee.getCol();

            String thirdProp = thirdConjuctee.getPercept();
            int thirdPropRow = thirdConjuctee.getRow();
            int thirdPropCol = thirdConjuctee.getCol();

            Literal newFirstConjuctee = new Literal(firstProp, firstPropRow, firstPropCol);
            Literal newSecondConjuctee = new Literal(secondProp, secondPropRow, secondPropCol);
            Literal newThirdConjuctee = new Literal(thirdProp, thirdPropRow, thirdPropCol);

            if(propSentence.isNot()){
                newFirstConjuctee.setNot();
            }
            if(secondConjuctee.isNot()){
                newSecondConjuctee.setNot();
            }
            if(thirdConjuctee.isNot()){
                newThirdConjuctee.setNot();
            }
            // String representation is funky.
            PropositionalSentence newFirstPropSentence = new PropositionalSentence(newFirstConjuctee, "V", newThirdConjuctee);
            PropositionalSentence newSecondPropSentence = new PropositionalSentence(newSecondConjuctee, "V", newThirdConjuctee);
            newFirstPropSentence.and(newSecondPropSentence);
            propSentence = newFirstPropSentence;
        }
    return propSentence;
    }

}

