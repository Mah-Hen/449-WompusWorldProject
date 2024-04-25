

public class Cell<E> {
    E element = null; // the hazard or gold in the 
    Boolean wumpusDead = false;
    String [] perceptions = {"None", "None", "None"}; // the thing to be sensed from your senses
    boolean visited = false;; 
    int row; // coordinates of the cell on the board
    int col;

    public Cell(int r, int c){
        visited = false;
        row = r;
        col = c;
    }   

    public void setElement(E ele){  
        this.element = ele;
    }

    public void removeElement(){
        this.element = null;

    }

    public boolean hasElement(){
        return element != null;
    }

    public E getElement(){
        return element;
    }

    public void setPercept(String per){
        for(int i=0; i<this.perceptions.length; i++){
            if(this.perceptions[i].equals("None")){
                this.perceptions[i] = per;
            }
    }
}

    public void removePercept(){
        this.perceptions = null;
    }

    public boolean hasPercept(){
        for(int i=0; i<this.perceptions.length; i++){
            if(!(this.perceptions[i].equals("None")))
                return true;
        }
        return false; 
    }

    public String[] getPercepts(){
        return perceptions;
    }

    public boolean isVisited(){
        return visited;
    }

    public void setVisited(){
        visited = true;
    }

    @Override
    public String toString(){
        if(this.element== null){
            return "| |";
    }   
        return this.element+"";
}

}
