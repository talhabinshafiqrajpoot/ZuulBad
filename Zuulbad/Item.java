import java.util.*;
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
   private String name;
   private String description;

   private final String[]  win = {"Rope","Sword","Armor","Medicine","Torch"};

   public Item(){}
   public Item(String name,String description)//initialize name and description
   {
       this.name = name;
       this.description = description;
   }
   
   public String getName(){return name;}//return item name
   public String getDescription(){return description;}//return item description
   public String[] validItemList()//return array of item that player needs to win
   {return win;}
   
}
