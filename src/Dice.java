import java.util.Random;
/**
 * Class for Dice
 *
 * @author Cody Richter
 * @version 1.0
 */
public class Dice
{
    private final int MIN_FACES = 4;

    private static Random generator = new Random();
    private int numFaces;
    private int faceValue;

    public Dice()
    {
        numFaces = 6;
        faceValue = 1;

    }

    public Dice(int faces)
    {
        if (faces < MIN_FACES)
            numFaces = MIN_FACES;
        else
            numFaces = faces;

        faceValue = 1;
    }

    public int roll()
    {
        faceValue = generator.nextInt(numFaces) + 1;
        return faceValue;
    }

    public int getFaceValue()
    {
        return faceValue;
    }

    public int getNumFaces()
    {
        return numFaces;
    }

    public int getMinFaces()
    {
        return MIN_FACES;
    }
}
