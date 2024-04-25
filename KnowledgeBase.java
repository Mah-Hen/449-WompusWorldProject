import java.util.HashSet;
import java.util.Set;

public class KnowledgeBase {
    Set<PropositionalSentence> KnowledgeBase; // a knowledge base
    int time; // a counter indicating time
    Board brd;

    public KnowledgeBase(Board brd) {
        KnowledgeBase = new HashSet<PropositionalSentence>();
        this.brd = brd;
    }

    public String KB_Agent(String[] percepts) {
        for(int i=0; i<percepts.length; i++){
            Tell(Make_Percept_Sentence(percepts, i));}
        String action = Ask(Make_Action_Query());
        // Tell(Make_Action_Sentence(action));

        // this.time += 1;
        return action;
    }

    private PropositionalSentence Make_Percept_Sentence(String[] percepts, int inc) {
        PropositionalSentence perceptSentence = new PropositionalSentence(null, null);
        int row = brd.getPlayer().getRow();
        int col = brd.getPlayer().getCol();
        String percept = "";

        if(percepts[inc].equals("None")){
           if(inc == 0){
            percept = "Stench";
           }
           else if(inc == 1){
            percept = "Breeze";
           }
           else{
            percept = "Glitter";
           }
            perceptSentence = new PropositionalSentence(percept, row, col).not();
        }
        return perceptSentence;
    }

    private String Make_Action_Query() {
        Player agent = this.brd.getPlayer();
        int row = 0;
        int col = 0;

        for(PropositionalSentence info: KnowledgeBase){
            
        }   

        return "";
    }

    private PropositionalSentence Make_Action_Sentence(String action, int time) {
        int len = action.length();

        PropositionalSentence perceptSentence = new PropositionalSentence("", 1, 1);
        return perceptSentence;
    }

    private void Tell(PropositionalSentence perceptSequence){
        
        KnowledgeBase.add(perceptSequence);

}

    private String Ask(String action_Query){
        return "";
    } 

    

    
}
