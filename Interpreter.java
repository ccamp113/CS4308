import java.io.FileNotFoundException;


public class Interpreter
{

    public static void main(String[] args)
    {
        try
        {
            String fileName = "test4.lua";

            //DEBUG: Displays Tokens
            /*LexicalAnalyzer test = new LexicalAnalyzer(fileName);
            while (test.getLookaheadToken().getTokType() != TokenType.EOS_TOK) {
                Token tok = test.getNextToken();
                System.out.println("Token test: " + tok.getLexeme() + " with token_id: " + tok.getTokType());
            }*/

            Parser p = new Parser(fileName);
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

/***********************************************
98/100 = 49/50 (late) Please see my comments in your
code.
*/