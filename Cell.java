
public class Cell<E> {
    E element = null; // the hazard or gold in the cell
    Boolean wumpusDead = false; // indicates if the wumpus in this cell is dead
    String[] perceptions = { "None", "None", "None" }; // perceptions sensed from this cell
    boolean visited = false; // indicates if the cell has been visited
    int row; // row coordinate of the cell on the board
    int col; // column coordinate of the cell on the board

    /**
     * Constructs a new cell with specified row and column coordinates.
     * 
     * @param r the row coordinate
     * @param c the column coordinate
     */
    public Cell(int r, int c) {
        visited = false;
        row = r;
        col = c;
    }

    /**
     * Sets the element of the cell.
     * 
     * @param ele the element to set
     */
    public void setElement(E ele) {
        this.element = ele;
    }

    /**
     * Removes the element from the cell.
     */
    public void removeElement() {
        this.element = null;
    }

    /**
     * Checks if the cell has an element.
     * 
     * @return true if the cell has an element, false otherwise
     */
    public boolean hasElement() {
        return element != null;
    }

    /**
     * Gets the element of the cell.
     * 
     * @return the element of the cell
     */
    public E getElement() {
        return element;
    }

    /**
     * Sets a percept for the cell.
     * 
     * @param per the percept to set
     */
    public void setPercept(String per) {
        for (int i = 0; i < this.perceptions.length; i++) {
            if (this.perceptions[i].equals("None")) {
                this.perceptions[i] = per;
            }
        }
    }

    /**
     * Removes all percepts from the cell.
     */
    public void removePercept() {
        this.perceptions = null;
    }

    /**
     * Checks if the cell has any percept.
     * 
     * @return true if the cell has a percept, false otherwise
     */
    public boolean hasPercept() {
        for (int i = 0; i < this.perceptions.length; i++) {
            if (!(this.perceptions[i].equals("None")))
                return true;
        }
        return false;
    }

    /**
     * Gets the array of percepts for the cell.
     * 
     * @return the array of percepts
     */
    public String[] getPercepts() {
        return perceptions;
    }

    /**
     * Checks if the cell has been visited.
     * 
     * @return true if the cell has been visited, false otherwise
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Sets the cell as visited.
     */
    public void setVisited() {
        visited = true;
    }

    /**
     * Overrides the toString method to represent the cell as a string.
     * 
     * @return a string representation of the cell
     */
    @Override
    public String toString() {
        if (this.element == null) {
            return "| |"; // empty cell representation
        }
        return this.element + ""; // representation with element
    }

}
