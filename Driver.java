import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
    int size = 4;
    Random rand = new Random();
    Player agent = new Player("agent");
    rand = getGenerator(rand);   
    Board brd = new Board(size, agent, rand);
    KnowledgeBase kb = new KnowledgeBase(brd);
    int cnt = 0;
    String prevMove = "";

    while(true){
        clear();
        brd.displayGrid();
        clear();
        if(prevMove.equals(""))
            System.out.printf("%s is at (%d, %d).", agent.getName(), agent.getRow()+1, agent.getCol()+1); // (0, 0) 
        else if(prevMove.equalsIgnoreCase("Left") | prevMove.equalsIgnoreCase("Right") | prevMove.equalsIgnoreCase("Down") | prevMove.equalsIgnoreCase("Up")){
            System.out.printf("After moving *%s*, %s is at (%d, %d).", prevMove, agent.getName(), agent.getRow()+1, agent.getCol()+1);
        }
        else{
            prevMove = prevMove.substring(6);
            System.out.printf("After shooting *%s*, %s is still at (%d, %d).", prevMove, agent.getName(), agent.getRow()+1, agent.getCol()+1);
        }
        for(int i=0; i<brd.getPercepts(agent).length; i++){
            if(!brd.getPercepts(agent)[i].equals("None")){
                System.out.print(brd.getPercepts(agent)[i] + "");
            }
            else{
                cnt++;}
            }
        if(cnt==brd.getPercepts(agent).length){
            System.out.println("\nNothing");
        }
        System.out.println("is sensed here");

        
        String move = kb.KB_Agent(brd.getPercepts(agent));
        //String move = getMove(agent, size);
        int typeMove = brd.applyMove(move);
        prevMove = move;

        
        if(typeMove == -1){ // bad move
            clear();
            endGame(agent, typeMove);
            break;
        }
        else if(typeMove == 0){ // no move (bump)
            System.out.printf("*bump*", agent.getName());
            continue;
        }
        else{ // good move 
            if(agent.getRow() == 0 & agent.getCol() == 0 & agent.gotGold()){
                endGame(agent, typeMove);
                break;
            }
            continue;
        }
    }


}

    private static String getMove(Player agent, int brdSize){
        // Fix the String representation.
        
        Scanner in = new Scanner(System.in);
        int row = agent.getRow();
        int col = agent.getCol();
        String move = "";
        boolean choice = false;
        String validDirections = "";

        if(col != 0){
            validDirections += "Left, "; 
            if(agent.hasArrow){
                validDirections += "Shoot Left, ";
            }
        }
        if(col != brdSize){
            validDirections += "Right, "; 
            if(agent.hasArrow){
                validDirections += "Shoot Right, ";
            }
        }
        if(row != 0){
            validDirections += "Up, ";
            if(agent.hasArrow){
                validDirections += "Shoot Up, ";
            }
        }
        if(row != brdSize){
            validDirections += "Down, ";
            if(agent.hasArrow){
                validDirections += "Shoot Down";
            }
        }

        while(!choice){
            System.out.printf("\nPick your move (%s): ", validDirections);
            move = in.nextLine();
            if(move.equalsIgnoreCase("left") | move.equalsIgnoreCase("right") | move.equalsIgnoreCase("down") | move.equalsIgnoreCase("up")
            | move.equalsIgnoreCase("Shoot Left") | move.equalsIgnoreCase("Shoot Right") | move.equalsIgnoreCase("Shoot Up") | move.equalsIgnoreCase("Shoot Down"))
                choice = true;
        }
        
        return move;
    }



    private static Random getGenerator(Random rand){
        Scanner in = new Scanner(System.in);
        boolean inp = false;
        long seed;

        while(!inp){
            System.out.println("Do I pick the seed? (yes/no)");
            String pickSeedStr = in.nextLine();

            if(pickSeedStr.equalsIgnoreCase("yes")){
                seed = rand.nextLong();
                rand.setSeed(seed);
                System.out.printf("Seed is: %d", seed);
                inp = true;
            }
            else if(pickSeedStr.equalsIgnoreCase("no")){
                System.out.println("Enter Seed: ");
                if(in.hasNextLong()){
                    seed = in.nextLong();
                    rand.setSeed(seed);
                    inp = true;
                }
            }
        }

        return rand;
    }

    private static void endGame(Player agent, int typeMove){
        if(typeMove == -1){
            System.out.printf("%s was swallowed up", agent.getName());}
        else if (typeMove == 1){
            System.out.printf("%s can climb. Type (Climb) to continue:");

        }
        clear();
        System.out.printf("Final Score: %d", agent.getScore());
        
    }

    private static void clear(){
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }
}
