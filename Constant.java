/**
 * Created by Weston Ford on 2/12/2016.
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
