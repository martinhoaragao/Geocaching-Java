

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class MeteoTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class MeteoTest
{
    private Meteo meteo1;
    private Meteo meteo2;
    private Meteo meteo3;

    /**
     * Default constructor for test class MeteoTest
     */
    public MeteoTest()
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
        meteo1 = new Meteo();
        meteo1.calcPointsT();
        meteo1.calcPointsW();
        meteo1.calcPoints();
        meteo2 = new Meteo();
        meteo2.calcPointsW();
        meteo2.calcPointsT();
        meteo2.calcPoints();
        meteo3 = new Meteo(50, 1);
        meteo3.calcPointsT();
        meteo3.calcPointsW();
        meteo3.calcPointsT();
        meteo3.calcPoints();
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
    public void testPoints()
    {
        Meteo meteo1 = new Meteo();
        meteo1.getTemp();
        meteo1.getWeather();
        assertNotNull(meteo1.toString());
        meteo1.calcPoints();
        Meteo meteo2 = new Meteo(50, 1);
        assertEquals(20, meteo2.calcPoints());
        assertNotNull(meteo2.toString());
        assertEquals(10, meteo2.calcPointsW());
        assertEquals(10, meteo2.calcPointsT());
        Meteo meteo3 = new Meteo(23, 3);
        assertEquals(10, meteo3.calcPoints());
    }
}

