public class CommandWords
{
    private static final String[] validCommands = {
        "go", "quit", "help", "look" , "splash", "back","take","inventory","drop"
    };

    public CommandWords(){}

    public boolean isCommand(String aString)//check if command is valid or not
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        return false;
    }
    
    public String getAllCommand()//print all the commands
    {
        String commandString = "";
        for(String command : validCommands){
            commandString += (command)+" ";
        }
        return commandString;
    }
}