import java.util.ArrayList;

public class KnowledgeBase {
    ArrayList<String> KnowledgeBase; // a knowledge base
    int time; // a counter indicating time

    private void KnowledgeBase(){
        KnowledgeBase = new ArrayList<String>();
        time = 0;
    }
    
    private String KB_Agent(String[] percept){
        Tell(Make_Percept_Sentence(percept, time));
        String action = Ask(Make_Action_Query(time));
        Tell(Make_Action_Sentence(action, time));

        time += 1;
        return action;
    }

    private String Make_Percept_Sentence(String[] percepts, int time){
        String perceptSentence = "";

        for(String percept:percepts){
            if(percept.equals("Stench")){
                perceptSentence += "S";
            }
            if(percept.equals("Breeze")){
                perceptSentence += "Br";
            }
            if(percept.equals("Glitter")){
                perceptSentence += "G";
            }
            if(percept.equals("Bump")){
                perceptSentence += "Bm";
            }
            if(percept.equals("Scream")){
                perceptSentence += "S";
            }
        }

        perceptSentence += "V";

        return perceptSentence;
    }

    private void Tell(String perceptSequence){
        
    }
}
