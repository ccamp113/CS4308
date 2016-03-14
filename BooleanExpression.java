/* File: BooleanExpression.java
 Name: Cameron Campbell
 CS4308-02
 */
public class BooleanExpression implements Expression {

    private Expression expr1, expr2;

    private BooleanOperator op;

    public BooleanExpression (BooleanOperator op, Expression expr1, Expression expr2)
    {
        if (expr1 == null || expr2 == null)
            throw new IllegalArgumentException ("null expression argument");

        this.op = op;
        this.expr1 = expr1;
        this.expr2 = expr2;

    }

    public int evaluate()
    {
        int bool;
        if(op == BooleanOperator.LE_OP) {
            if (expr1.evaluate() <= expr2.evaluate())
                bool = 1;
            else
                bool = 0;
        }
        else if(op == BooleanOperator.LT_OP) {
            if (expr1.evaluate() < expr2.evaluate())
                bool = 1;
            else
                bool = 0;
        }
        else if(op == BooleanOperator.GE_OP) {
            if (expr1.evaluate() >= expr2.evaluate())
                bool = 1;
            else
                bool = 0;
        }
        else if(op == BooleanOperator.GT_OP) {
            if (expr1.evaluate() > expr2.evaluate())
                bool = 1;
            else
                bool = 0;
        }
        else if(op == BooleanOperator.EQ_OP) {
            if (expr1.evaluate() == expr2.evaluate())
                bool = 1;
            else
                bool = 0;
        }
        else {
            if (expr1.evaluate() != expr2.evaluate())
                bool = 1;
            else
                bool = 0;
        }

        return bool;
    }
}
