public class Player {
    String name = "";
    boolean hasArrow;
    boolean gotGold = false;
    int row;
    int col;
    int score = 0;

    public Player(String name){
        this.name = name;
        hasArrow = true;
    }

    public String getName(){
        return this.name;
    }

    public boolean hasArrow(){
        return hasArrow;
    }

    public void shootArrow(){
        hasArrow = false;
        this.takeFromScore(10);
    }

    public void grab(){ 
        gotGold = true;
    }

    public boolean gotGold(){
        return gotGold;
    }

    public void addToScore(int s){
        score += s;
    }

    public void takeFromScore(int s){
        score -= s;
    }

    public int getScore(){
        return score;
    }

    public void setPosition(int r, int c){
        this.row = r;
        this.col = c;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

    public String toString(){ 
        String str = "";
        return str + this.name;
    }
}
