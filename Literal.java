public class Literal {
        boolean not = false; // indicates negation of the percept
        String percept = ""; // the percept associated with the literal
        int row; // row coordinate of the literal
        int col; // column coordinate of the literal
    
        /**
         * Constructs a new literal with the specified percept, row, and column.
         * @param percept the percept associated with the literal
         * @param row the row coordinate of the literal
         * @param col the column coordinate of the literal
         */
        public Literal(String percept, int row, int col){
            this.percept = percept;
            this.row = row;
            this.col = col;
        }
    
        /**
         * Gets the percept associated with the literal.
         * @return the percept
         */
        public String getPercept(){
            return this.percept;
        }
    
        /**
         * Sets the percept associated with the literal.
         * @param perc the percept to set
         */
        public void setPercept(String perc){
            this.percept = perc;
        }
    
        /**
         * Gets the row coordinate of the literal.
         * @return the row coordinate
         */
        public int getRow(){
            return this.row;
        }
    
        /**
         * Gets the column coordinate of the literal.
         * @return the column coordinate
         */
        public int getCol(){
            return this.col;
        }
    
        /**
         * Checks if the literal is negated.
         * @return true if the literal is negated, false otherwise
         */
        public boolean isNot(){
            return this.not;
        }
    
        /**
         * Toggles the negation status of the literal.
         */
        public void setNot(){
            this.not = !this.not;
        }
    
        /**
         * Checks if this literal is equal to another literal.
         * @param oLiteral the other literal to compare
         * @return true if the literals are equal, false otherwise
         */
        public boolean equals(Literal oLiteral){
            return this.not == oLiteral.not & this.percept.equals(oLiteral.percept) & this.row == oLiteral.row & this.col == oLiteral.col;
        }
    
        /**
         * Overrides the toString method to represent the literal as a string.
         * @return a string representation of the literal
         */
        @Override
        public String toString(){
            if(this.not){
                return String.format("!%s(%d, %d)", this.percept, this.row, this.col); // negated literal representation
            }
            return String.format("%s(%d, %d)", this.percept, this.row, this.col); // positive literal representation
        }
}

