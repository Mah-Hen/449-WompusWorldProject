import java.util.Random;

public class Board {
    private String[] elements = { "Wumpus", "Pit", "Pit", "Gold" }; // possible elements on the board
    private Player agent; // the player's character
    private Cell[][] cells; // grid of cells composing the board
    private int brdSize; // size of the board

    /**
     * Constructs a new board with the specified size, player, and random object.
     * 
     * @param size   the size of the board
     * @param player the player object
     * @param rand   the random object for generating hazard positions
     */
    public Board(int size, Player player, Random rand) {
        agent = player;
        brdSize = size;
        cells = new Cell[size][size];

        // Initialize cells
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                cells[row][col] = new Cell(row, col);
                if (row == 0 && col == 0) {
                    cells[row][col].setElement(agent); // Set agent at position [0, 0]
                    agent.setPosition(row, col);
                    cells[row][col].setVisited();
                }
            }
        }

        // Generate hazards on the board
        for (String ele : elements) {
            int row, col;
            boolean foundEmptyCell = false;

            while (!foundEmptyCell) {
                row = rand.nextInt(size - 1) + 1;
                col = rand.nextInt(size - 1) + 1;
                if (!cells[row][col].hasElement()) {
                    cells[row][col].setElement(ele);
                    generatePerceptions(ele, row, col); // Generate perceptions for adjacent cells
                    foundEmptyCell = true;
                }
            }
        }
    }

    /**
     * Gets the cells of the board.
     * 
     * @return the grid of cells
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Gets the player object.
     * 
     * @return the player object
     */
    public Player getPlayer() {
        return this.agent;
    }

    /**
     * Applies the player's move on the board.
     * 
     * @param move the move to apply
     * @return 1 if move successful, 0 if invalid move, -1 if move leads to hazard
     */
    public int applyMove(String move) {
        int row = agent.getRow();
        int col = agent.getCol();
        int prevRow = row;
        int prevCol = col;
        String element = "";

        if (move.equalsIgnoreCase("right")) {
            agent.takeFromScore(1);
            if (col == brdSize - 1) {
                return 0;
            }
            col += 1;
            if (cells[row][col].hasElement()) {
                element = (String) cells[row][col].getElement();
                if (badMove(row, col)) {
                    return -1;
                }
            }
            cells[row][prevCol].removeElement(); // could do .setElement(null);, but picks the agent up to place in a
                                                 // different row
            cells[row][col].setElement(agent);
            agent.setPosition(row, col);
            return 1;
        } else if (move.equalsIgnoreCase("left")) {
            agent.takeFromScore(1);
            if (col == 0) {
                return 0;
            }
            col -= 1;
            if (cells[row][col].hasElement()) {
                element = (String) cells[row][col].getElement();
                if (badMove(row, col)) {
                    return -1;
                }
            }
            cells[row][prevCol].removeElement();
            cells[row][col].setElement(agent);
            agent.setPosition(row, col);
            return 1;
        } else if (move.equalsIgnoreCase("up")) {
            agent.takeFromScore(1);
            if (row == 0) {
                return 0;
            }
            row -= 1;
            agent.takeFromScore(1);
            if (cells[row][col].hasElement()) {
                element = (String) cells[row][col].getElement();
                if (badMove(row, col)) {
                    return -1;
                }
            }
            cells[prevRow][col].removeElement();
            cells[row][col].setElement(agent);
            agent.setPosition(row, col);
            return 1;
        } else if (move.equalsIgnoreCase("down")) {
            agent.takeFromScore(1);
            if (row == brdSize) {
                return 0;
            }
            row += 1;
            if (cells[row][col].hasElement()) {
                element = (String) cells[row][col].getElement();
                if (badMove(row, col)) {
                    return -1;
                }
            }
            // Move the agent
            cells[prevRow][col].removeElement();
            cells[row][col].setElement(agent);
            agent.setPosition(row, col);
            return 1;
        } else { // shoot
            String shotDirection = move.substring(6);
            agent.shootArrow();

            if (shotDirection.equalsIgnoreCase("Down")) {
                for (int nextRow = row; nextRow < brdSize; nextRow++) {
                    if (cells[nextRow][col].hasElement()) {
                        if (cells[nextRow][col].getElement().equals("Wumpus")) {
                            killWumpus(nextRow, col);
                        }
                    }
                }
            } else if (shotDirection.equalsIgnoreCase("Up")) {
                for (int nextRow = row; nextRow <= 0; nextRow--) {
                    if (cells[nextRow][col].hasElement()) {
                        if (cells[nextRow][col].getElement().equals("Wumpus")) {
                            killWumpus(nextRow, col);
                        }
                    }
                }
            } else if (shotDirection.equalsIgnoreCase("Right")) {
                for (int nextCol = col; nextCol < brdSize; nextCol++) {
                    if (cells[row][nextCol].hasElement()) {
                        if (cells[row][nextCol].getElement().equals("Wumpus")) {
                            killWumpus(row, nextCol);
                            break;
                        }
                    }
                }
            } else { // left
                for (int nextCol = col; nextCol <= 0; nextCol--) {
                    if (cells[row][nextCol].hasElement()) {
                        if (cells[row][nextCol].getElement().equals("Wumpus")) {
                            killWumpus(row, nextCol);
                        }
                    }
                }
            }
            return 1;
        }
    }

    /**
     * Displays the grid representation of the board.
     */
    public void displayGrid() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print(cells[i][j] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Checks if a move leads to a hazard.
     * 
     * @param row the row of the move
     * @param col the column of the move
     * @return true if move leads to a hazard, false otherwise
     */
    public boolean badMove(int row, int col) {
        String hazard = (String) cells[row][col].getElement();
        if (hazard.equalsIgnoreCase("Wumpus")) {
            return true;
        } else if (hazard.equalsIgnoreCase("Pit")) {
            return true;
        } else { // hazard is gold
            retrieveGold(row, col);
            return false;
        }

    }

    /**
     * Kills the Wumpus at the specified position.
     * 
     * @param row the row of the Wumpus
     * @param col the column of the Wumpus
     */
    private void killWumpus(int row, int col) {
        cells[row][col].removeElement();
        removePerceptions(row, col);
        System.out.println("AAARRRGGGHHH");
    }

    /**
     * Retrieves the gold at the specified position.
     * 
     * @param row the row of the gold
     * @param col the column of the gold
     */
    private void retrieveGold(int row, int col) {
        cells[row][col].setElement(null);
        agent.grab();
    }

    /**
     * Generates perceptions for adjacent cells based on the element placed.
     * 
     * @param ele    the element placed on the board
     * @param eleRow the row of the element
     * @param eleCol the column of the element
     */
    private void generatePerceptions(String ele, int eleRow, int eleCol) {
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                int adjacentRow = eleRow + row;
                int adjacentCol = eleCol + col;
                if (validCell(adjacentRow, adjacentCol)) {
                    if (adjacentCell(eleRow, eleCol, adjacentRow, adjacentCol)
                            || ((eleRow == adjacentRow) & (eleCol == adjacentCol))) {
                        setPerceptions(ele, adjacentRow, adjacentCol);
                    }
                }
            }
        }

    }

    /**
     * Removes perceptions from adjacent cells based on the specified position.
     * 
     * @param eleRow the row of the element
     * @param eleCol the column of the element
     */
    public void removePerceptions(int eleRow, int eleCol) {
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                int adjacentRow = eleRow + row;
                int adjacentCol = eleCol + col;
                if (validCell(adjacentRow, adjacentCol)) {
                    if (adjacentCell(eleRow, eleCol, adjacentRow, adjacentCol)
                            || ((eleRow == adjacentRow) & (eleCol == adjacentCol))) {
                        cells[adjacentRow][adjacentCol].removePercept();
                    }
                }
            }
        }
    }

    /**
     * Sets perceptions for adjacent cells based on the specified element and
     * position.
     * 
     * @param ele the element placed on the board
     * @param row the row of the adjacent cell
     * @param col the column of the adjacent cell
     */
    private void setPerceptions(String ele, int row, int col) {
        if (ele.equals("Pit")) {
            this.cells[row][col].setPercept("Breeze");
        } else if (ele.equals("Gold")) {
            this.cells[row][col].setPercept("Glitter");
        } else {
            this.cells[row][col].setPercept("Stench");
        }
    }

    /**
     * Gets the perceptions for the player at their current position.
     * 
     * @param agent the player object
     * @return an array of perceptions for the player's current position
     */
    public String[] getPercepts(Player agent) {
        int row = agent.getRow();
        int col = agent.getCol();
        String[] percept = new String[3];

        percept = cells[row][col].getPercepts();

        return percept;
    }

    /**
     * Checks if the specified row and column form a valid cell on the board.
     * 
     * @param row the row to check
     * @param col the column to check
     * @return true if the cell is valid, false otherwise
     */
    public boolean validCell(int row, int col) {
        return (row >= 0 && row < this.brdSize) && (col >= 0 && col < brdSize);
    }

    /**
     * Checks if the specified row and column correspond to an adjacent cell to an
     * element.
     * 
     * @param eleRow the row of the element
     * @param eleCol the column of the element
     * @param row    the row to check
     * @param col    the column to check
     * @return true if the cell is adjacent, false otherwise
     */
    public boolean adjacentCell(int eleRow, int eleCol, int row, int col) {
        return ((eleRow == row && eleCol != col) || (eleCol == col && eleRow != row));
    }
}
