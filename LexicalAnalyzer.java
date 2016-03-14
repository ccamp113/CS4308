/* File: LexicalAnalyzer.java
 Name: Cameron Campbell
 CS4308-02
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LexicalAnalyzer {

    private List<Token> tokens;

    public LexicalAnalyzer(String fileName) throws FileNotFoundException, LexicalException
    {
        assert (fileName != null);
        tokens = new ArrayList<Token>();
        Scanner sourceCode = new Scanner (new File (fileName));
        int lineNumber = 0;
        while (sourceCode.hasNext())
        {
            String line = sourceCode.nextLine();
            processLine (line, lineNumber);
            lineNumber++;
        }
        tokens.add(new Token (lineNumber, 1, "EOS", TokenType.EOS_TOK));
        sourceCode.close();
    }

    private void processLine(String line, int lineNumber) throws LexicalException
    {
        assert (line != null && lineNumber >= 1);
        int index = 0;
        index = skipWhiteSpace (line, index);
        while (index < line.length())
        {
            String lexeme = getLexeme (line, lineNumber, index);
            TokenType tokType = getTokenType (lexeme, lineNumber, index);
            tokens.add(new Token (lineNumber + 1, index + 1, lexeme, tokType));
            index += lexeme.length();
            index = skipWhiteSpace (line, index);
        }
    }

    private TokenType getTokenType(String lexeme, int lineNumber, int columnNumber) throws LexicalException
    {
        assert (lexeme != null && lineNumber >= 1 && columnNumber >= 1);
        TokenType tokType = null;
        if (Character.isLetter(lexeme.charAt(0)))
        {
            if (lexeme.length() == 1)
                if (isValidIdentifier (lexeme.charAt(0)))
                    tokType = TokenType.ID_TOK;
                else
                    throw new LexicalException ("invalid lexeme at row number " +
                            (lineNumber + 1) + " and column " + (columnNumber + 1));
            else if(lexeme.length() > 1){
                switch(lexeme){
                    case "print": tokType = TokenType.PRINT_TOK;
                        break;
                    case "if": tokType = TokenType.IF_TOK;
                        break;
                    case "then": tokType = TokenType.THEN_TOK;
                        break;
                    case "else": tokType = TokenType.ELSE_TOK;
                        break;
                    case "while": tokType = TokenType.WHILE_TOK;
                        break;
                    case "do": tokType = TokenType.DO_TOK;
                        break;
                    case "repeat": tokType = TokenType.REPEAT_TOK;
                        break;
                    case "until": tokType = TokenType.UNTIL_TOK;
                        break;
                    case "end": tokType = TokenType.END_TOK;
                        break;
                    case "function": tokType = TokenType.FUNCTION_TOK;
                        break;
                }

            }
            else
                throw new LexicalException ("invalid lexeme at row number " +
                        (lineNumber + 1) + " and column " + (columnNumber + 1));
        }
        else if (Character.isDigit (lexeme.charAt(0)))
        {
            if (allDigits (lexeme))
                tokType = TokenType.CONST_TOK;
            else
                throw new LexicalException ("invalid lexeme at row number " +
                        (lineNumber + 1) + " and column " + (columnNumber + 1));
        }
        else if (lexeme.length() == 1 && !Character.isLetter(lexeme.charAt(0)) && !Character.isDigit(lexeme.charAt(0)))
        {
            switch (lexeme) {
                case "+": tokType = TokenType.ADD_TOK;
                    break;
                case "*": tokType = TokenType.MUL_TOK;
                    break;
                case "=": tokType = TokenType.ASSIGN_TOK;
                    break;
                case "-": tokType = TokenType.SUB_TOK; 
                    break;
                case "/": tokType = TokenType.DIV_TOK; 
                    break;
                case ">": tokType = TokenType.GT_TOK;
                    break;
                case "<": tokType = TokenType.LT_TOK;
                    break;
                case "(": tokType = TokenType.OPENP_TOK;
                    break;
                case ")": tokType = TokenType.CLOSEP_TOK;
                    break;
                default: throw new LexicalException ("invalid lexeme at row number" +
                        (lineNumber + 1) + " and column " + (columnNumber + 1));
            }

        }
        else if (lexeme.length() > 1 && !Character.isLetter(lexeme.charAt(0)) && !Character.isDigit(lexeme.charAt(0)))
        {
            switch (lexeme) {
                case "==": tokType = TokenType.EQ_TOK;
                    break;
                case "~=": tokType = TokenType.NE_TOK;
                    break;
                case ">=": tokType = TokenType.GE_TOK;
                    break;
                case "<=": tokType = TokenType.LE_TOK;
                    break;
                default: throw new LexicalException ("invalid lexeme at row number" +
                        (lineNumber + 1) + " and column " + (columnNumber + 1));
            }
        }
        else
            throw new LexicalException ("invalid lexeme at row number " +
                    (lineNumber + 1) + " and column " + (columnNumber + 1));
        return tokType;
    }

    private boolean allDigits(String s)
    {
        assert (s != null);
        int i = 0;
        while (i < s.length() && Character.isDigit(s.charAt(i)))
            i++;
        return i == s.length();
    }

    private String getLexeme(String line, int lineNumber, int index)
    {
        assert (line != null && lineNumber >= 1 && index >= 0);
        int i = index;
        while (i < line.length() && !Character.isWhitespace(line.charAt(i)))
            i++;
        return line.substring(index, i);
    }

    private int skipWhiteSpace(String line, int index)
    {
        assert (line != null && index >= 0);
        while (index < line.length() && Character.isWhitespace(line.charAt(index)))
            index++;
        return index;
    }

    public Token getNextToken() throws LexicalException
    {
        if (tokens.isEmpty())
            throw new LexicalException ("no more tokens");
        return tokens.remove(0);
    }

    public Token getLookaheadToken() throws LexicalException
    {
        if (tokens.isEmpty())
            throw new LexicalException ("no more tokens");
        return tokens.get(0);
    }

    public static boolean isValidIdentifier (char ch)
    {
        int checkAscii = (int) ch;
        return ((checkAscii >= 65 && checkAscii <= 90) || (checkAscii >= 97 && checkAscii <= 122));
    }

    


}
