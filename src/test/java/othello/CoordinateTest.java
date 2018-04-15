package othello;



import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * Created by kennyyu on 4/15/2018.
 */
public class CoordinateTest {
    @Test
    public void testEquals() {
        Coordinate a = new Coordinate(1, 2);
        Coordinate b = new Coordinate(1, 2);
        Assert.assertEquals(a.equals(b), true);

        HashSet<Coordinate> setA = new HashSet<Coordinate>();
        setA.add(a);

        HashSet<Coordinate> setB = new HashSet<Coordinate>();
        setB.add(b);

        Assert.assertEquals(setA, setB);
    }
}