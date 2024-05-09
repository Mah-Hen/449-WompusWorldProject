import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        // Initialization
        int size = 4; // Size of the board
        Random rand = new Random(); // Random generator for placing hazards and gold
        Player agent = new Player("agent"); // Creating the player
        rand = getGenerator(rand); // Initializing random generator
        Board brd = new Board(size, agent, rand); // Creating the game board
        KnowledgeBase kb = new KnowledgeBase(brd); // Initializing the knowledge base
        int cnt = 0; // Counter for sensing no hazards
        String prevMove = ""; // Previous move made by the agent

        // Game loop
        while (true) {
            clear(); // Clearing console
            brd.displayGrid(); // Displaying the game board
            clear(); // Clearing console
            // Displaying agent's current position and previous move
            if (prevMove.equals(""))
                System.out.printf("%s is at (%d, %d).", agent.getName(), agent.getRow() + 1, agent.getCol() + 1); // (0,
                                                                                                                  // 0)
            else if (prevMove.equalsIgnoreCase("Left") | prevMove.equalsIgnoreCase("Right")
                    | prevMove.equalsIgnoreCase("Down") | prevMove.equalsIgnoreCase("Up")) {
                System.out.printf("After moving *%s*, %s is at (%d, %d).", prevMove, agent.getName(),
                        agent.getRow() + 1, agent.getCol() + 1);
            } else {
                prevMove = prevMove.substring(6);
                System.out.printf("After shooting *%s*, %s is still at (%d, %d).", prevMove, agent.getName(),
                        agent.getRow() + 1, agent.getCol() + 1);
            }
            // Displaying sensed percepts
            for (int i = 0; i < brd.getPercepts(agent).length; i++) {
                if (!brd.getPercepts(agent)[i].equals("None")) {
                    System.out.print(brd.getPercepts(agent)[i] + "");
                } else {
                    cnt++;
                }
            }
            if (cnt == brd.getPercepts(agent).length) {
                System.out.println("\nNothing");
            }
            System.out.println("is sensed here");

            // Asking the knowledge base for the next move
            String move = kb.KB_Agent(brd.getPercepts(agent));
            int typeMove = brd.applyMove(move); // Applying the move to the game board
            prevMove = move; // Setting the previous move

            // Handling different move types
            if (typeMove == -1) { // Bad move
                clear();
                endGame(agent, typeMove); // Ending the game
                break; // Exiting the game loop
            } else if (typeMove == 0) { // No move (bump)
                System.out.printf("*bump*", agent.getName());
                continue; // Continuing the game loop
            } else { // Good move
                if (agent.getRow() == 0 & agent.getCol() == 0 & agent.gotGold()) {
                    endGame(agent, typeMove); // Ending the game
                    break; // Exiting the game loop
                }
                continue; // Continuing the game loop
            }
        }
    }

    // Method to get user input for choosing the move
    private static String getMove(Player agent, int brdSize) {
        // Fix the String representation.
        Scanner in = new Scanner(System.in);
        int row = agent.getRow();
        int col = agent.getCol();
        String move = "";
        boolean choice = false;
        String validDirections = "";

        // Constructing valid move options based on agent's position
        if (col != 0) {
            validDirections += "Left, ";
            if (agent.hasArrow) {
                validDirections += "Shoot Left, ";
            }
        }
        if (col != brdSize) {
            validDirections += "Right, ";
            if (agent.hasArrow) {
                validDirections += "Shoot Right, ";
            }
        }
        if (row != 0) {
            validDirections += "Up, ";
            if (agent.hasArrow) {
                validDirections += "Shoot Up, ";
            }
        }
        if (row != brdSize) {
            validDirections += "Down, ";
            if (agent.hasArrow) {
                validDirections += "Shoot Down";
            }
        }

        // Getting user input for the move
        while (!choice) {
            System.out.printf("\nPick your move (%s): ", validDirections);
            move = in.nextLine();
            if (move.equalsIgnoreCase("left") | move.equalsIgnoreCase("right") | move.equalsIgnoreCase("down")
                    | move.equalsIgnoreCase("up")
                    | move.equalsIgnoreCase("Shoot Left") | move.equalsIgnoreCase("Shoot Right")
                    | move.equalsIgnoreCase("Shoot Up") | move.equalsIgnoreCase("Shoot Down"))
                choice = true;
        }

        return move;
    }

    // Method to initialize the random generator
    private static Random getGenerator(Random rand) {
        Scanner in = new Scanner(System.in);
        boolean inp = false;
        long seed;

        while (!inp) {
            System.out.println("Do I pick the seed? (yes/no)");
            String pickSeedStr = in.nextLine();

            if (pickSeedStr.equalsIgnoreCase("yes")) {
                seed = rand.nextLong();
                rand.setSeed(seed);
                System.out.printf("Seed is: %d", seed);
                inp = true;
            } else if (pickSeedStr.equalsIgnoreCase("no")) {
                System.out.println("Enter Seed: ");
                if (in.hasNextLong()) {
                    seed = in.nextLong();
                    rand.setSeed(seed);
                    inp = true;
                }
            }
        }

        return rand;
    }

    // Method to handle end of the game
    private static void endGame(Player agent, int typeMove) {
        if (typeMove == -1) {
            System.out.printf("%s was swallowed up", agent.getName());
        } else if (typeMove == 1) {
            System.out.printf("%s can climb. Type (Climb) to continue:");

        }
        clear();
        System.out.printf("Final Score: %d", agent.getScore());

    }

    // Method to clear the console
    private static void clear() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }
}
