/**
 * Created by Weston Ford on 2/12/2016.
 */
public class BinaryExpression implements Expression {
    private Expression expr1, expr2;

    private ArithmeticOperator op;

    /**
     * @param expr1 - cannot be null
     * @param expr2 - cannot be null
     * @throws IllegalArgumentException if either argument is null
     */
    public BinaryExpression(ArithmeticOperator op, Expression expr1, Expression expr2)
    {
        if (expr1 == null || expr2 == null)
            throw new IllegalArgumentException ("null expression argument");
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.op = op;
    }

    @Override
    public int evaluate()
    {
        int value;
        if (op == ArithmeticOperator.ADD_OP)
            value = expr1.evaluate() + expr2.evaluate();
        else if (op == ArithmeticOperator.MUL_OP)
            value = expr1.evaluate() * expr2.evaluate();
        else if (op == ArithmeticOperator.SUB_OP)
            value = expr1.evaluate() - expr2.evaluate();
        else
            value = expr1.evaluate()/expr2.evaluate();
        return value;
    }
}
