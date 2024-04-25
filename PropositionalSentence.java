public class PropositionalSentence {
    boolean not = false;
    String logicConnective = "";
    String sentence = "";
    int row;
    int col;

    public PropositionalSentence(String sentence, int row, int col){
        this.sentence = sentence;
        this.row = row;
        this.col = col;
    }

    public PropositionalSentence(String sentence, String connect){
        this.sentence = sentence;
        this.logicConnective = connect;
    }


    public String getsentence(){
        return this.sentence;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

    public PropositionalSentence not(){
        this.not = true;
        return new PropositionalSentence("!"+this.getsentence(), "not");
    }

    private PropositionalSentence and(PropositionalSentence oSentence){
        return new PropositionalSentence("(" + this.sentence + ")" + "^" + "("+ oSentence + ")", "and");
    }

    private PropositionalSentence or(PropositionalSentence oSentence){
        return new PropositionalSentence("(" + this.sentence + ")" + "V" + "(" + oSentence + ")", "or");
    }

    private PropositionalSentence iff(PropositionalSentence oSentence){
        return new PropositionalSentence("(" + this.sentence + ")" + "<->" + "("+ oSentence + ")", "iff");
    }

    private PropositionalSentence implies(PropositionalSentence oSentence){
        return new PropositionalSentence("("+ this.sentence + ")" + "->" + "(" + oSentence + ")", "implies");
    }

    @Override
    public String toString(){
        return String.format("%s(%d, %d)", this.sentence, this.row, this.col);
    }
}
