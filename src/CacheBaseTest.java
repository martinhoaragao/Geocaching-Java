

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class CacheBaseTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class CacheBaseTest
{
    private Coordinates coordina1;
    private Coordinates coordina2;
    private Coordinates coordina3;
    private TraditionalCache traditio1;
    private MicroCache microCac1;
    private TraditionalCache traditio2;
    private CacheBase cacheBas1;

    /**
     * Default constructor for test class CacheBaseTest
     */
    public CacheBaseTest()
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
        coordina1 = new Coordinates(1, 1);
        coordina2 = new Coordinates(1, 1.2312356123);
        coordina3 = new Coordinates(1.2312, 1.0221);
        traditio1 = new TraditionalCache(1.0, coordina1, "je@gmail.com");
        microCac1 = new MicroCache(2.0, coordina2, "je@gmail.com");
        traditio2 = new TraditionalCache(3.0, coordina3, "je@gmail.com");
        cacheBas1 = new CacheBase();
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
    public void testAddCache_getTreeOrdered()
    {
    }
}


