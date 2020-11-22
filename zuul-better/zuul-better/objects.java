import java.util.*;

/**
 * Write a description of class objects here.
 *
 * @author Vandad Vafai Tabrizi
 * @version 2020.Nov.21
 */
public class objects
{
    private HashSet<String> obj;
    private CommandWords command;
    private HashMap<Room, HashSet<String>> storeObj;

    /**
     * Constructor for objects of class objects
     */
    public objects()
    {
        obj = new HashSet<>();
        command = new CommandWords();
        storeObj = new HashMap<>();
    }

    /**
     * this method will add specific objects to the HashSet.
     */
    public void addObjects()
    {
        obj.add("coffee");
        obj.add("camera");
        obj.add("costumes");
        obj.add("notepad");
        obj.add("mirophone");
    }
    
    public void pickUpObjects()
    {
        boolean isPicked = false;
        if(command.equals("pick")){
            isPicked = true;
        }
    }
    
    public void giveObjects()
    {
        boolean isGiven = false;
        if(command.equals("give")){
            isGiven = true;
        }
    }
}
