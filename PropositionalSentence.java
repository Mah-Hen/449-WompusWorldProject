import java.util.ArrayList;

public class PropositionalSentence {
    boolean not = false; // indicates negation of the sentence
    String literalConnective = ""; // connective between literals
    Literal backConnectee = null; // first literal in the sentence
    Literal frontConnectee = null; // second literal in the sentence
    String propConnective = ""; // connective between propositional sentences
    PropositionalSentence connectedPropSentence = null; // connected propositional sentence
    String sentence = ""; // string representation of the sentence
    

    /**
     * Constructs a new propositional sentence with the specified literals and connective.
     * @param backConnectee the first literal in the sentence
     * @param connective the connective between literals
     * @param connectee the second literal in the sentence
     */
    public PropositionalSentence(Literal backConnectee, String connective, Literal connectee){
        this.backConnectee = backConnectee;
        this.literalConnective = connective;
        this.frontConnectee = connectee;
        if(literalConnective.equals("")){
            if(this.backConnectee==null){
                this.sentence = "";
            }
            else{
            this.sentence = this.backConnectee.toString();
            }
        }
        else{
        this.sentence = String.format("%s%s%s", this.backConnectee, this.literalConnective, this.frontConnectee);
        }
    }


    
    /**
     * Checks if the sentence is negated.
     * @return true if the sentence is negated, false otherwise
     */
    public boolean isNot(){
        return this.not;
    }


     /**
     * Toggles the negation status of the sentence.
     */
    public void setNot(){
        if(this.not==false){
            this.not = true;
        }
        else{
            this.not=false;
        }
    }

    /* Checks if the sentence has a literal connective.
     * @return true if the sentence has a literal connective, false otherwise
     */
    public boolean hasLiteralConnective(){
        return !this.literalConnective.equals("");
    }

     /**
     * Gets the literal connective of the sentence.
     * @return the literal connective
     */
    public String getLiteralConnective(){
        return this.literalConnective;
    }

    /**
     * Sets the literal connective of the sentence.
     * @param connective the literal connective to set
     */
    public void setLiteralConnective(String connective){
        this.literalConnective = connective;
    }

    /**
     * Checks if the sentence has a propositional connective.
     * @return true if the sentence has a propositional connective, false otherwise
     */
    public boolean hasPropConnective(){
        return !this.propConnective.equals("");
    }

    /**
     * Gets the propositional connective of the sentence.
     * @return the propositional connective
     */
    public String getPropConnective(){
        return this.propConnective;
    }

     /**
     * Gets the first literal (back connectee) of the sentence.
     * @return the first literal
     */
    public Literal getBackConnectee(){
        return this.backConnectee;
    }

    /**
     * Sets the first literal (back connectee) of the sentence.
     * @param c the literal to set
     */
    public void setBackConnectee(Literal c){
        this.backConnectee = c;
    }

    /**
     * Gets the second literal (front connectee) of the sentence.
     * @return the second literal
     */
    public Literal getFrontConnectee(){
        return this.frontConnectee;
    }

    /**
     * Sets the second literal (front connectee) of the sentence.
     * @param c the literal to set
     */
    public void setFrontConnectee(Literal c){
        this.frontConnectee = c;
    }

    /**
     * Gets the connected propositional sentence.
     * @return the connected propositional sentence
     */
    public PropositionalSentence getPropSentence() {
        return connectedPropSentence;
    }

    /**
     * Gets the string representation of the sentence.
     * @return the string representation
     */
    public String getSentence(){
        return this.sentence;
    }

    /**
     * Negates the sentence.
     * This method changes the sentence to its negation form.
     */
    public void not(){
        this.setNot();
        this.backConnectee.setNot();
        if(this.frontConnectee!=null){
            this.frontConnectee.setNot();
        }
        if(this.hasLiteralConnective()){ // basically saying if this propSentence was a connective sentence prior to you making it a 'not'
            if(this.literalConnective.equals("V")){
                this.setLiteralConnective("^");
            }
            else if(this.literalConnective.equals("^")){
                this.setLiteralConnective("V");
            }
        }
        if(this.frontConnectee != null){
            this.sentence = String.format("%s%s%s", this.backConnectee, this.literalConnective, this.frontConnectee);
        }
        else{
            this.sentence = String.format("%s%s%s", this.backConnectee, "", "");
        }
    } 

    /**
     * Connects the sentence with another sentence using the disjunction operator (OR).
     * @param propSentence the sentence to connect with
     */
    public void or(PropositionalSentence propSentence){
        this.propConnective = "V";
        this.connectedPropSentence = propSentence;
    }

    /**
     * Connects the sentence with another sentence using the conjunction operator (AND).
     * @param propSentence the sentence to connect with
     */
    public void and(PropositionalSentence propSentence){
        if(this.connectedPropSentence != null){
            this.connectedPropSentence.propConnective = "^";
            this.connectedPropSentence.connectedPropSentence = propSentence;
        }
        else{
            this.propConnective = "^";
            this.connectedPropSentence = propSentence;
        }
    }

    /**
     * Connects the sentence with another sentence using the biconditional operator (IFF).
     * @param propSentence the sentence to connect with
     */
    public void iff(PropositionalSentence propSentence){
        this.propConnective = "<->";
        this.connectedPropSentence = propSentence;
    }
    
    /**
     * Connects the sentence with another sentence using the implication operator (IMPLIES).
     * @param propSentence the sentence to connect with
     */
    public void implies(PropositionalSentence propSentence){
        this.propConnective = "->";
        this.connectedPropSentence = propSentence;
    }

    /**
     * Separates a conjunction sentence into individual sentences.
     * @return an ArrayList of separated PropositionalSentences
     */
    public ArrayList<PropositionalSentence> separateConjuction(){
        ArrayList <PropositionalSentence> separated = new ArrayList<>();
        PropositionalSentence current = this;
    
        // Keep separating until there are no more conjunctions
        while (current != null) {
            // If the current sentence is a conjunction, split it
            if (current.literalConnective.equals("^") ) {
                // Add the left side of the conjunction to the separated list
                separated.add(new PropositionalSentence(backConnectee, "", null));
                separated.add(new PropositionalSentence(frontConnectee, "", null));
                // Move to the right side of the conjunction for the next iteration
                current = current.connectedPropSentence;
            } 
            else if(current.propConnective.equals("^")){
                if(current.frontConnectee != null)
                    separated.add(new PropositionalSentence(current.backConnectee, current.literalConnective, current.frontConnectee));
                else{
                    separated.add(new PropositionalSentence(current.backConnectee, "", null));
                }
                current = current.connectedPropSentence;
            }
            else {
                PropositionalSentence propSentence = new PropositionalSentence(current.backConnectee, current.literalConnective, current.frontConnectee);
                if(current.connectedPropSentence == null){
                    separated.add(propSentence); // 
                    current = null;
                    break;
                }
                else{
                    if(current.connectedPropSentence.literalConnective.equals("^")){
                        propSentence.or(new PropositionalSentence(current.connectedPropSentence.backConnectee, "", null));
                        
                    }
                    else if(current.connectedPropSentence.propConnective.equals("^")){
                        if(current.connectedPropSentence.frontConnectee==null){
                            propSentence.or(new PropositionalSentence(current.connectedPropSentence.backConnectee, "", null));
                        }
                        else{
                            propSentence.or(new PropositionalSentence(current.connectedPropSentence.backConnectee, "V", current.connectedPropSentence.frontConnectee));
                        }
                        
                    }
                separated.add(propSentence);
                }
                
            
                if(current.connectedPropSentence.propConnective.equals("^"))
                    current = current.connectedPropSentence.connectedPropSentence; // traverse over the prop Sentence
                else{
                    current = current.connectedPropSentence;
                }
            }
        }
        return separated;
    }
        

        
    // Helper method for toString
    private String toStringHelper(PropositionalSentence propSentence, String propConnective){
        String s = "";
        if(propSentence.connectedPropSentence == null){
            return propConnective + propSentence.sentence;
        }
        s = toStringHelper(propSentence.connectedPropSentence, propSentence.propConnective);
        s = propConnective+propSentence.sentence + s;
        return s;
    }

     /**
     * Checks if this propositional sentence is equal to another propositional sentence.
     * @param oPropSentence the other propositional sentence to compare
     * @return true if the sentences are equal, false otherwise
     */
    public boolean equals(PropositionalSentence oPropSentence){
        return this.not == oPropSentence.not & this.literalConnective.equals(oPropSentence.literalConnective) & 
        this.backConnectee.equals(oPropSentence.backConnectee) & this.frontConnectee.equals(oPropSentence.frontConnectee) 
        & this.propConnective.equals(oPropSentence.propConnective) & this.connectedPropSentence.equals(oPropSentence.connectedPropSentence);
    }

    /**
     * Overrides the toString method to represent the propositional sentence as a string.
     * @return a string representation of the propositional sentence
     */
    @Override
    public String toString(){
        if(this.connectedPropSentence!=null){
            return this.sentence + toStringHelper(this.connectedPropSentence, this.propConnective);
        }
        return this.sentence;
    }
}
    




