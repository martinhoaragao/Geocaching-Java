

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class CoordinatesTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class CoordinatesTest
{
    private Coordinates coordina1;
    private Coordinates coordina2;

    /**
     * Default constructor for test class CoordinatesTest
     */
    public CoordinatesTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        coordina1 = new Coordinates(1.00158, 3.1323123);
        coordina2 = new Coordinates(coordina1);
        coordina2.incLat(0.1113213);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testValueOfKms()
    {
        coordina2.getCoordinatesDist(coordina1);
        Coordinates coordina3 = new Coordinates(coordina1);
        assertEquals(0.0, coordina3.getCoordinatesDist(coordina1), 0.1);
        coordina3.incLat(0.023123);
        coordina3.getCoordinatesDist(coordina1);
        Coordinates coordina4 = new Coordinates(coordina1);
        coordina4.incLat(0.000111);
        coordina4.getCoordinatesDist(coordina1);
    }
}

