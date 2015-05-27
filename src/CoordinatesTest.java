

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
    private Coordinates coordina3;
    private Coordinates coordina4;
    private Coordinates coordina5;
    private Coordinates coordina6;
    private Coordinates coordina7;
    private Coordinates coordina8;
    private Coordinates coordina9;
    private Coordinates coordina10;
    private Coordinates coordina11;

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
        coordina1 = new Coordinates(70, -180);
        coordina2 = new Coordinates(70, 180);
        coordina2.getCoordinatesDist(coordina1, coordina2);
        coordina3 = new Coordinates(102913, 138213);
        coordina4 = new Coordinates(398942, 498231);
        coordina4.getCoordinatesDist(coordina4, coordina3);
        coordina5 = new Coordinates(1040, 1983);
        coordina6 = new Coordinates(3000, 3233);
        coordina5.getCoordinatesDist(coordina5, coordina6);
        coordina7 = new Coordinates(10, 180);
        coordina8 = new Coordinates(34.24124234, 45.43142342);
        coordina8.getCoordinatesDist(coordina7, coordina8);
        coordina9 = new Coordinates(10, 30.3213214);
        coordina10 = new Coordinates(10, 40.41341);
        coordina10.getCoordinatesDist(coordina9, coordina10);
        coordina11 = new Coordinates(10, 30.09392193219);
        coordina11.getCoordinatesDist(coordina9, coordina11);
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
    public void testKms()
    {
        coordina10.compareTo(coordina11);
        coordina10.getLat();
        coordina11.getCoordinatesDist(coordina11, coordina10);
        coordina1.getCoordinatesDist(coordina1, coordina2);
        coordina2.toString();
        coordina1.toString();
        coordina10.toString();
        coordina11.toString();
        coordina9.toString();
        coordina9.getCoordinatesDist(coordina9, coordina11);
    }
}

