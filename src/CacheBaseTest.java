

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
    private MicroCache microCac2;
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
        coordina1 = new Coordinates(20, 10);
        coordina2 = new Coordinates(20.1, 10.5);
        coordina3 = new Coordinates(20.03, 10.05);
        traditio1 = new TraditionalCache(1.0, coordina1, "jess@gmail.com");
        microCac1 = new MicroCache(2.0, coordina2, "jess@gmail.com");
        microCac2 = new MicroCache(3.0, coordina3, "jess@gmail.com");
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
    public void testTreeOrdered()
    {
        cacheBas1.addCache(1.0, traditio1);
        cacheBas1.addCache(1.0, microCac1);
        cacheBas1.addCache(1.0, microCac2);
        assertEquals(3, cacheBas1.getNumOfCaches());
        assertNotNull(cacheBas1.getCache(1.0));
        cacheBas1.getTreeOrderedByDistance(coordina1, 1000);
        cacheBas1.getTreeOrderedByDistance(coordina2, 100000);
    }
}

