/* File: Interpreter.java
 Name: Cameron Campbell
 CS4308-02
 */
import java.io.FileNotFoundException;


public class Interpreter
{

    public static void main(String[] args)
    {
        try
        {
          
            Parser p = new Parser("test1.lua");
            p.parse();
        }
            try
            {
              
                Parser p = new Parser("test2.lua");
                p.parse();
            }
                try
                {
                  
                    Parser p = new Parser("test3.lua");
                    p.parse();
                }
                    try
                    {
                      
                        Parser p = new Parser("test4.lua");
                        p.parse();
                    }
        catch (ParserException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (LexicalException e)
        {
            e.printStackTrace();
        }
    }

}
