import java.util.*;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> roomStack;    
    private Room previousRoom;
    private ArrayList<Item> inventory;
    private static final String key="Key";
    private int moves=10;
    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomStack = new Stack<Room>();
        inventory = new ArrayList<>();
    }

    //create all the rooms for the gamse
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, cellar,classRoom,playGround;

        outside = new Room("outside the main entrance of the university",false);
        theater = new Room("in a lecture theater",true);
        pub = new Room("in the campus pub",true);
        lab = new Room("in a computing lab",false);
        office = new Room("in the computing admin office",false);
        cellar = new Room("in the cellar",true);
        classRoom = new Room("in the classroom",true);
        playGround = new Room("in the playground",false);
        
        //exits in the rooms
        outside.setExits(theater,pub,playGround,lab,null,null);
        theater.setExit("west",outside);
        pub.setExit("east",outside);
        lab.setExits(office,null,outside,null,classRoom,null);
        office.setExits(null,lab,null,null,null,cellar);
        cellar.setExit("up", office);
        classRoom.setExit("down",lab);
        playGround.setExit("south",outside);
        
        //items in room
        outside.setItem(new Item("Torch","Bright Light"));
        theater.setItem(new Item("Sword","Melee Weapon"));
        pub.setItem(null);
        lab.setItem(null);
        office.setItem(new Item("Key","Unlock Doors"));
        cellar.setItem(null);
        classRoom.setItem(new Item("Medicine","Healer"));
        playGround.setItem(new Item("Rope","Climbing"));
        
        previousRoom = outside;
        currentRoom = outside; 
    }
    
    //make ui of the game
    public void play() 
    {            
        printWelcome();
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        
        if(moves==0)
        {
            System.out.println("Total moves exhausted");
            System.out.println("Better Luck Next Time !!!");
        }
        else
        {
            String[] item = new Item().validItemList();
            for(int i=0;i<inventory.size();i++)
            {
                int temp=0;
                for(int j=0;j<item.length;j++)
                {
                    if(inventory.get(i).equals(item[j]))temp+=1;
                }
                if(temp==0)
                {
                    System.out.println("All valid item is not found!!!");
                    break;
                }
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    //print welcome message to the screen
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul....");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        getRoomExitsAndDescription();
    }
    
    //check if enter command is valid or not
    private boolean processCommand(Command command) 
    {
        boolean quit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        
        if(moves==0)
        {
            return true;
        }
        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if(commandWord.equals("take")){
            take(command);
        }
        else if (commandWord.equals("go")) {
            moves-=1;
            goRoom(command);
        }
        else if (commandWord.equals("look")){
            look(command);
        }
        else if (commandWord.equals("splash")) {
            splash();
        }
        else if (commandWord.equals("inventory")) {
            displayInventory();
        }
        else if (commandWord.equals("drop")) {
                drop(command);
        }
        else if (commandWord.equals("quit")) {
            quit = quit(command);
        }
        else if (commandWord.equals("back")) {
            goBack();
        }

        return quit;
    }
    
    //print room and exit description
    private void getRoomExitsAndDescription()
    {
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        getRoomItem();
        System.out.println();
    }
    
    //print item in the room
    private void getRoomItem()
    {
        System.out.println(currentRoom.getItem());
    }
    
    //print help and command to screen
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:" + parser.showCommands());
        
    }
    
    //helps in traveling rooms
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            
            System.out.println("Go where?");
            return;
        }
                
        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);
            
        boolean hasKey=false;
        for(int i=0;i<inventory.size();i++)
        {
            if(inventory.get(i).getName().equals(key))
            {
                hasKey = true;
            }
        }
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        if(nextRoom != null && nextRoom.checkLocked() && !hasKey){
            System.out.println("Door is locked you need Key");
            return;
        }
        else {
            roomStack.push(currentRoom);
            currentRoom = nextRoom;
            getRoomExitsAndDescription();
        }
    }    
   
    //dislpay inventory all items
    private void displayInventory()
    {
        if(inventory.size()==0)
        {
            System.out.println("Empty Inventory");
            return;
        }
        System.out.println("Item \t\t Description");
        for(Item i :inventory)
        {
            System.out.println(i.getName()+" \t\t "+i.getDescription());
        }
    }
    
    //end the game
    private boolean quit(Command command)     {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  
        }
    }
    
    //print direction if look alone is given
    //print item description if along with look item name is given
    private void look(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println(currentRoom.getLongDescription());
            getRoomItem();
        }
        else
        {
            String item = command.getSecondWord();
            if(item.equals(currentRoom.getItemName()))
            {
               System.out.println(currentRoom.getItemDescription());
            }
            else
            {
               System.out.println("Look What ??");     
            }
        }
        
    }
    
    //drop the particular item in a particular room if that item is in the inventory
    private void drop(Command command)
    {
         if(!command.hasSecondWord()) {
            System.out.println("Drop what??");
            return;
        }
        
        if(inventory.size()==0)
        {
            System.out.println("Empty Inventory");
            return;
        }
        
         if(currentRoom.isItemPresent())
        {
            System.out.println("Can't Drop item in this room");
            return;
        }
        
        String item = command.getSecondWord();
        int temp=-1;
        for(int i=0;i<inventory.size();i++)
        {   
            if(inventory.get(i).getName().equals(item))
            {
                temp=i;
                break;
            }
        }
        
        if(temp==-1)
        {
            System.out.println("Item not present in the inventory");
            return;
        }
        
        currentRoom.setItem(inventory.get(temp));
        inventory.remove(temp);
        
    }
    
    //take item in the room
    private void take(Command command)
    {   
        String item = currentRoom.getItemName();
        if(!command.hasSecondWord() || !command.getSecondWord().equals(item)) {
            System.out.println("Take what??");
            return;
        }
        
        if(!currentRoom.isItemPresent())
        {
            System.out.println("No item present in the room");
            return;
        }
        if(inventory.size() == 5)
        {
            System.out.println("Inventory is Full !!");
        }
        else if(currentRoom.takeItem())
        {
            System.out.println("Item picked");
            inventory.add(currentRoom.getItemInTheRoom());
            currentRoom.setItem(null);
        }
        else
        {
            System.out.println("Item not picked");
        }
        
        
    }
    
    
    private void splash()
    {
        System.out.println("It's not very effective....");
    }
    
    //go back to the previous room
    private void goBack()
    { 
        if (roomStack.empty())
        {   System.out.println("You cannot go back further than your starting point.");
        } else {
            currentRoom = roomStack.pop();
            System.out.println(currentRoom.getLongDescription());
        }
    }
}