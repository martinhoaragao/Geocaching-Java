import java.util.Scanner;

/**
 * A class to represent the Server. 
 * Only the Server has full access to all Data-Bases (Caches and Users).
 * 
 * @version 06/05/2015
 */

public class Server
{
    private Scanner reader;
    
    public Server(){
        reader = new Scanner(System.in);
    }
    
    
    public String getInput()
    {
        System.out.print("> ");         // print prompt
        String inputLine = reader.nextLine();

        return inputLine;
    }
    
}