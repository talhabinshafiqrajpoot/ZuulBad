
public class Command
{
    private String firstWord;
    private String secondWord;

    public Command(String firstWord, String secondWord)
    {
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    public String getCommandWord()//return first word of command
    {
        return firstWord;
    }

    public String getSecondWord()//return second word of command
    {
        return secondWord;
    }

    public boolean isUnknown()//check if firstWord is empty or not
    {
        return (firstWord == null);
    }

    public boolean hasSecondWord()//check if SecondWord is empty or not
    {
        return (secondWord != null);
    }
}
