/**
 * Created by Weston Ford on 2/12/2016.
 */
public class Memory {
    private static int[] mem = new int[57]; //57 or 58?

    /**
     * @param ch - must be a valid identifier
     * @return value of ch
     * @throws IllegalArgumentException if ch is not a valid identifier
     */
    public static int fetch (char ch)
    {
        return mem[getIndex(ch)];
    }

    /**
     * @param ch - must be a valid identifier
     * @param value
     * @throws IllegalArgumentException if ch is not a valid identifier
     */
    public static void store (char ch, int value)
    {
        mem[getIndex(ch)] = value;
    }

    private static int getIndex (char ch)
    {
        if (!LexicalAnalyzer.isValidIdentifier(ch))
            throw new IllegalArgumentException (ch + " is not a valid identifier");
        return ch - 'A';
    }

    public static void displayMemory()
    {
        //57 or 58?
        for (int i = 0; i < 57; i++)
            System.out.println ((char)('A' + i) + ": " + mem[i]);
    }
}
