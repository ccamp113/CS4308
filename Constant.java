/* File: Constant.java
 Name: Cameron Campbell
 CS4308-02
 */
public class Constant implements Expression {

    private int value;

    public Constant(int value)
    {
        this.value = value;
    }

    @Override
    public int evaluate()
    {
        return value;
    }

}
