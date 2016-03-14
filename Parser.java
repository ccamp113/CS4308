/* File: Token.java
Name: Cameron Campbell 
CS4308-02 
   Valid Token Types. enum type enables for Tokentype 
   to be the following set of predefined constants */

import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

public class Parser {
    private LexicalAnalyzer lex;

    public Parser(String fileName) throws FileNotFoundException, LexicalException
    {
        lex = new LexicalAnalyzer (fileName);
    }

    /*Modified Header: The parsing algorithm should detect any syntactical or semantic error. 
    The first such error discovered should cause an appropriate error message to be printed, 
    and then the interpreter should terminate. Run-time errors should also be detected with 
    appropriate error messages being printed.*/

    public void parse() throws ParserException, LexicalException
    {
        Token tok = lex.getNextToken();
        if (tok.getTokType() == TokenType.FUNCTION_TOK)
        {
            if (((tok = lex.getNextToken()).getTokType()) == TokenType.ID_TOK) {
                if (((tok = lex.getNextToken()).getTokType()) == TokenType.OPENP_TOK) {
                    if (((tok = lex.getNextToken()).getTokType()) == TokenType.CLOSEP_TOK) {
                        getBlock();
                        if (((tok = lex.getNextToken()).getTokType()) != TokenType.END_TOK) {
                            throw new ParserException("END expected at row " +
                                    tok.getRowNumber() + " and column " + tok.getColumnNumber());
         }

                    }else
                            throw new ParserException(") expected at row " +
                                    tok.getRowNumber() + " and column " + tok.getColumnNumber());
                }else
                        throw new ParserException("( expected at row " +
                                tok.getRowNumber() + " and column " + tok.getColumnNumber());
            } else
                    throw new ParserException("ID expected at row " +
                            tok.getRowNumber() + " and column " + tok.getColumnNumber());
        } else
                throw new ParserException("FUNCTION expected at row " +
                    tok.getRowNumber() + " and column " + tok.getColumnNumber());
    }



    /*****************************************************
     * implements the production <assign> -> <id> assign_op  <expr>
     */
   public void getBlock() throws ParserException, LexicalException
    {
        Token tok = lex.getLookaheadToken();
        if((tok.getTokType() != TokenType.EOS_TOK) && (tok.getTokType() != TokenType.END_TOK) && (tok.getTokType() != TokenType.UNTIL_TOK))
        {
            getStatement();
            getBlock();
        }

    }

    public void getStatement() throws ParserException, LexicalException {
        Token tok = lex.getLookaheadToken();
        if (tok.getTokType() == TokenType.IF_TOK)
            resolveIf();
        else if (tok.getTokType() == TokenType.WHILE_TOK)
            resolveWhile();
        else if (tok.getTokType() == TokenType.REPEAT_TOK)
            resolveRepeat();
        else if (tok.getTokType() == TokenType.PRINT_TOK)
            printState();
        else if (tok.getTokType() == TokenType.ID_TOK)
            Assign();
        else if (tok.getTokType() == TokenType.ELSE_TOK)
            return;
        else if (tok.getTokType() == TokenType.UNTIL_TOK)
            return;

        else
            throw new ParserException("Some statement expected at row " +
                    tok.getRowNumber() + " and column " + tok.getColumnNumber());

    }

    public void resolveIf() throws ParserException, LexicalException{

        Token tok = lex.getLookaheadToken();
        if (getBooleanExpression().evaluate() == 1)
        {
            tok = lex.getNextToken();
            if (tok.getTokType() == TokenType.THEN_TOK)
            {
                getBlock();
                tok = lex.getNextToken();
                if(tok.getTokType() == TokenType.ELSE_TOK)
                {
                    while (tok.getTokType() != TokenType.END_TOK)
                        tok = lex.getNextToken();
                    if (tok.getTokType() == TokenType.END_TOK)
                        return;
                }
                else
                {
                        throw new ParserException("ELSE expected at row " +
                                tok.getRowNumber() + " and column " + tok.getColumnNumber());
                }
            }
            else
            {
                    throw new ParserException(" THEN expected at row " +
                        tok.getRowNumber() + " and column " + tok.getColumnNumber());
            }
        }

        else
        {
            while (tok.getTokType() != TokenType.ELSE_TOK)
                    tok = lex.getNextToken();
            getBlock();
            tok = lex.getNextToken();
            if(tok.getTokType() == TokenType.END_TOK)
                return;
            else {
                throw new ParserException("END expected at row " +
                        tok.getRowNumber() + " and column " + tok.getColumnNumber());
            }
        }

    }
    private void resolveWhile() throws ParserException, LexicalException{
        Expression whileCondition = getBooleanExpression();
        List<Token> save1 = new ArrayList<Token>();
        List<Token> save2 = new ArrayList<Token>();
        Token tok = lex.getNextToken();
        if(tok.getTokType() == TokenType.DO_TOK) {
            while (whileCondition.evaluate() == 1)
            {
                save1 = lex.saveState();
                getBlock();
                save2 = lex.saveState();
                lex.loadState(save1);
            }
            lex.loadState(save2);

            if(((tok = lex.getNextToken()).getTokType()) == TokenType.END_TOK)
                return;
            else{
                throw new ParserException("END expected at row " +
                        tok.getRowNumber() + " and column " + tok.getColumnNumber());
            }

        }
        else  {
            throw new ParserException(" DO expected at row " +
                    tok.getRowNumber() + " and column " + tok.getColumnNumber());
        }

    }
    private void resolveRepeat() throws ParserException, LexicalException{

        List<Token> save1 = lex.saveState();
        lex.getNextToken();
        getBlock();

            Expression repeatCondition = getBooleanExpression();
            if (repeatCondition.evaluate() != 1)
            {
                lex.loadState(save1);
                resolveRepeat();
            }


    }
    public void printState() throws ParserException, LexicalException{

        Token tok = lex.getNextToken();
        if(tok.getTokType() != TokenType.PRINT_TOK) {
            throw new ParserException("PRINT expected at row " +
                    tok.getRowNumber() + " and column " + tok.getColumnNumber());
        }
        tok = lex.getNextToken();
        if(tok.getTokType() == TokenType.OPENP_TOK){
            System.out.println(getExpression().evaluate());

            tok = lex.getNextToken();
            if(tok.getTokType() != TokenType.CLOSEP_TOK){
                throw new ParserException(") expected at row " +
                        tok.getRowNumber() + " and column " + tok.getColumnNumber());
            }
        }
        else{
            throw new ParserException("( expected at row " +
                    tok.getRowNumber() + " and column " + tok.getColumnNumber());
        }
    }
    // Original header
    //public Assignment parse() throws ParserException, LexicalException
    private void Assign() throws ParserException, LexicalException
    {
        Id var = getId();
        Token tok = lex.getNextToken();
        match (tok, TokenType.ASSIGN_TOK);
        Expression expr = getExpression();
        new Assignment(var, expr).execute();
    }


    /*private Expression getStatement() throws ParserException, LexicalException
    {
        Expression expr;
        Token tok = lex.getLookaheadToken();

    }*/
    /**************************************************************
     * implements the production <expr> -> <operator> <expr> <expr> | id | constant
     */
    private Expression getExpression() throws ParserException, LexicalException
    {
        Expression expr;
        Token tok = lex.getLookaheadToken ();
        if (tok.getTokType() == TokenType.ADD_TOK || tok.getTokType() == TokenType.MUL_TOK
                || tok.getTokType() == TokenType.SUB_TOK || tok.getTokType() == TokenType.DIV_TOK)
            expr = getBinaryExpression ();
        else if (tok.getTokType() == TokenType.ID_TOK)
            expr = getId();
        else
            expr = getConstant();
        return expr;
    }



    /****************************************************
     * implements the production <expr> -> <operator> <expr> <expr>
     */
    private Expression getBinaryExpression() throws ParserException, LexicalException
    {
        ArithmeticOperator op;
        Token tok = lex.getNextToken();
        if (tok.getTokType() == TokenType.ADD_TOK)
        {
            match (tok, TokenType.ADD_TOK);
            op = ArithmeticOperator.ADD_OP;
        }
        else if (tok.getTokType() == TokenType.MUL_TOK)
        {
            match (tok, TokenType.MUL_TOK);
            op = ArithmeticOperator.MUL_OP;
        }
        else if (tok.getTokType() == TokenType.SUB_TOK)
        {
            match (tok, TokenType.SUB_TOK);
            op = ArithmeticOperator.SUB_OP;
        }
        else if (tok.getTokType() == TokenType.DIV_TOK)
        {
            match (tok, TokenType.DIV_TOK);
            op = ArithmeticOperator.DIV_OP;
        }
        else
            throw new ParserException (" operator expected at row " +
                    tok.getRowNumber() +" and column "  + tok.getColumnNumber());
        Expression expr1 = getExpression();
        Expression expr2 = getExpression();
        return new BinaryExpression (op, expr1, expr2);
    }

    private Expression getBooleanExpression() throws ParserException, LexicalException
    {
        BooleanOperator op;
        //I can't explain why I decided to offload the responsibility
        //of purging the initial conditional token to the getBooleanExpression
        //method by calling getNextToken twice but I'm closing in on the end and
        //I'd rather just make a note to both of us rather than deal with the inevitable
        //fallout that would ensue from tampering with anything in this this
        //delicately hacked together mess.
        lex.getNextToken();
        Token tok = lex.getNextToken();
        if (tok.getTokType() == TokenType.LE_TOK)
        {
            match (tok, TokenType.LE_TOK);
            op = BooleanOperator.LE_OP;
        }
        else if (tok.getTokType() == TokenType.LT_TOK)
        {
            match (tok, TokenType.LT_TOK);
            op = BooleanOperator.LT_OP;
        }
        else if (tok.getTokType() == TokenType.GE_TOK)
        {
            match (tok, TokenType.GE_TOK);
            op = BooleanOperator.GE_OP;
        }
        else if (tok.getTokType() == TokenType.GT_TOK)
        {
            match (tok, TokenType.GT_TOK);
            op = BooleanOperator.GT_OP;
        }
        else if (tok.getTokType() == TokenType.EQ_TOK)
        {
            match (tok, TokenType.EQ_TOK);
            op = BooleanOperator.EQ_OP;
        }
        else if (tok.getTokType() == TokenType.NE_TOK)
        {
            match (tok, TokenType.NE_TOK);
            op = BooleanOperator.NE_OP;
        }
        else
            throw new ParserException (" operator expected at row " +
                    tok.getRowNumber() +" and column "  + tok.getColumnNumber());
        Expression expr1 = getExpression();
        Expression expr2 = getExpression();


        return new BooleanExpression(op, expr1, expr2);
    }

    private Id getId() throws LexicalException, ParserException
    {
        Token tok = lex.getNextToken();
        match (tok, TokenType.ID_TOK);
        return new Id (tok.getLexeme().charAt(0));
    }

    private Expression getConstant() throws ParserException, LexicalException
    {
        Token tok = lex.getNextToken();
        match (tok, TokenType.CONST_TOK);
        int value = Integer.parseInt(tok.getLexeme());
        return new Constant (value);
    }

    private void match(Token tok, TokenType tokType) throws ParserException
    {
        assert (tok != null && tokType != null);
        if (tok.getTokType() != tokType)
            throw new ParserException (tokType.name() + " expected at row " +
                    tok.getRowNumber() +" and column "  + tok.getColumnNumber());
    }
}
