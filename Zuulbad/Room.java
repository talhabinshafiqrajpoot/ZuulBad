import java.util.*;
/*
 * this -> calling room object
 * description -> room description
 * exits -> all possible exits of rooms
 * isItem -> check if room has room or not
 * isLocked -> check if door is locked or not
*/

public class Room 
{
    private String description;
    private Item item;
    private HashMap<String, Room> exits;
    private boolean isItem;
    private boolean isLocked;
    
    public Room (String description,boolean isLocked) 
    {
        this.description = description;
        this.isLocked=isLocked;
        exits = new HashMap<String, Room>();
    }
    
   /* set all the exits possible for this room*/
    public void setExits(Room east,Room west,Room north,Room south,Room up,Room down)
    {
        if (north != null)
            exits.put("north", north);
        if (east != null)
            exits.put("east", east);
        if (south != null)
            exits.put("south", south);
        if (west != null)
            exits.put("west", west);
        if (up!=null)
            exits.put("up",up);
        if (down!=null)
            exits.put("down",down);
    }
    //set only one exits for this room
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }
    
    //check if door of room is locked
    public boolean checkLocked()
    {
        if(isLocked)return true;
        return false;
    }
    
    //return room description of this room
    public String getDescription()
    {
        return description;
    }
    
    //return all details regarding this room
    public String getLongDescription()
    {
        return "You are " + description + "\n" + getExitString();
    }
    
    //return all exits of this room
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    //return stirng of all the exits possible
    public String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
          for(String exit : keys) {
            returnString += " " + exit;
          }
        return returnString;
    }    
    
    // set item for this room to have
    public void setItem(Item item)
    {
        this.item = item;
        if(this.item!=null)isItem=true;
        else isItem=false;
    }
    
    //return cuurent room item name
    public String getItemName()
    {
        return item.getName();
    }
    
    //return item detials in this room
    public String getItem()
    {   
        if(item==null)return "";
        System.out.println("Item\tDescription");
        return item.getName()+"\t"+item.getDescription();
    }
    
    //return item of this room
    public Item getItemInTheRoom()
    {
        if(item==null)return null;
        return this.item;
    }
    
    //check if item is taken or not
    public boolean takeItem()
    {
        if(isItem)isItem=false;
        else return false;
        
        return true;
    }
    
    //return item description of this room
    public String getItemDescription()
    {
        return item.getDescription();
    }
    
    //check if item is present in the room
    public boolean isItemPresent()
    {
        if(isItem)return true;
        return false;
    }
}