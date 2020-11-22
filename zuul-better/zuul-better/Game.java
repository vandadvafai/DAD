import java.util.*;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private objects object;
    private HashMap<Room, String> objectHolder;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        object = new objects(); 
        objectHolder = new HashMap<>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room room1, room2, room3, room4, room5, room6, room7, room8, room9 ;
      
        // create the rooms
        room1 = new Room("in room 1");
        room2 = new Room("in room 2");
        room3 = new Room("in room 3");
        room4 = new Room("in room 4");
        room5 = new Room("in room 5");
        room6 = new Room("in room 6");
        room7 = new Room("in room 7");
        room8 = new Room("in room 8");
        room9 = new Room("in room 9");
        
        
        // initialise room exits
        room1.setExit("east", room2);
        room1.setExit("south", room4);
        
        room2.setExit("east", room3);
        room2.setExit("south-west", room4);
        room2.setExit("west", room1);
        
        room3.setExit("west" , room2);
        room3.setExit("south-west" , room5);
        room3.setExit("south" , room6);
        
        room4.setExit("east", room5);
        room4.setExit("north", room1);
        room4.setExit("north-east", room2);

        room5.setExit("west", room4);
        room5.setExit("east", room6);
        room5.setExit("south-west", room7);
        room5.setExit("north-east", room3);

        room6.setExit("north", room3);
        room6.setExit("west", room5);
        room6.setExit("south-west", room5);

        room7.setExit("east", room8);
        room7.setExit("north-east", room5);
        room7.setExit("north", room4);
        
        room8.setExit("east", room9);
        room8.setExit("west", room7);
        room8.setExit("north-east", room6);
        
        room9.setExit("west", room8);
        room9.setExit("north", room6);

        currentRoom = room5;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("pick")) {
            picking(command);
        } 
        else if(commandWord.equals("give")) {
            giving(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are an assistant. You need to get people the stuff they want");
        System.out.println("from different rooms and give it to them.");
        System.out.println();
        System.out.println("If you get caught by the police you will die.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
     /**
     * the player wants to pick up an object.
     * @return true, if the player has picked up an object
     */
    private boolean picking(Command command)
    {
        object.pickUpObjects();
        
        if(!command.hasSecondWord()) {
            System.out.println("pick what?");
            return false;
        }
        else {
            return true;  
        }
    }
    
    /**
     * the player wants to give an object.
     * @return true, if the player has given an object
     */
    private boolean giving(Command command)
    {
        object.giveObjects();
        
        if(!command.hasSecondWord()) {
            System.out.println("give what?");
            return false;
        }
        else {
            return true;  
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
