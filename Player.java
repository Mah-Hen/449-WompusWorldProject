public class Player {
    String name = ""; // name of the player
    boolean hasArrow; // indicates if the player has an arrow
    boolean gotGold = false; // indicates if the player has collected gold
    int row; // row coordinate of the player's position on the board
    int col; // column coordinate of the player's position on the board
    int score = 0; // player's score

    /**
     * Constructs a new player with the specified name.
     * 
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        hasArrow = true; // player starts with an arrow
    }

    /**
     * Gets the name of the player.
     * 
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if the player has an arrow.
     * 
     * @return true if the player has an arrow, false otherwise
     */
    public boolean hasArrow() {
        return hasArrow;
    }

    /**
     * Simulates the player shooting an arrow.
     * The player loses the arrow and incurs a score penalty.
     */
    public void shootArrow() {
        hasArrow = false;
        this.takeFromScore(10); // penalty for shooting an arrow
    }

    /**
     * Marks that the player has grabbed the gold.
     */
    public void grab() {
        gotGold = true;
    }

    /**
     * Checks if the player has grabbed the gold.
     * 
     * @return true if the player has grabbed the gold, false otherwise
     */
    public boolean gotGold() {
        return gotGold;
    }

    /**
     * Adds points to the player's score.
     * 
     * @param s the points to add
     */
    public void addToScore(int s) {
        score += s;
    }

    /**
     * Deducts points from the player's score.
     * 
     * @param s the points to deduct
     */
    public void takeFromScore(int s) {
        score -= s;
    }

    /**
     * Gets the player's current score.
     * 
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the position of the player on the board.
     * 
     * @param r the row coordinate
     * @param c the column coordinate
     */
    public void setPosition(int r, int c) {
        this.row = r;
        this.col = c;
    }

    /**
     * Gets the row coordinate of the player's position.
     * 
     * @return the row coordinate
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Gets the column coordinate of the player's position.
     * 
     * @return the column coordinate
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Overrides the toString method to represent the player as a string.
     * 
     * @return a string representation of the player
     */
    public String toString() {
        String str = "";
        return str + this.name; // returns the player's name
    }
}
