package hospital.metier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by ferlicotdelbe on 23/10/15.
 */
public class Interact extends Action{

    protected Map<String, String> commands;

    public void action(BufferedReader br) throws IOException {
        System.out.println("Bonjour, quelle action voulez vous effectuer ?");
        this.generateCommandHelp();
        String command = br.readLine().trim();
        try {
            Action action = (Action) Class.forName(this.commands.get(command)).newInstance();
            action.action(br);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NullPointerException e) {
          //We catch the exceptions for user convenience
            this.action(br);
        }

    }

    public void generateCommandHelp(){
        for(String command: this.commands.keySet()){
            //TODO :) GENERATE THE HELP
        }
    }

    /*
    *   I take a Map to let someone extend this application.
    *   If someone need more command he can initialize the Map with the new commands then I will add the basic methods of the application.
     *   A switch case would not have allow that :(
     */
    public void setCommandWith(Map<String, String> map) {
        map.put(Quit.COMMAND, "hospital.metier.Quit");
        this.commands = map;
    }
}
