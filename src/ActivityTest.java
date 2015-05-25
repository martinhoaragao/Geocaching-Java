

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ActivityTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ActivityTest
{
    private java.util.GregorianCalendar gregoria1;
    private MicroCache microCac1;
    private MysteryCache mysteryC1;
    private Activity activity1;
    private Activity activity2;

    /**
     * Default constructor for test class ActivityTest
     */
    public ActivityTest()
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
        gregoria1 = new java.util.GregorianCalendar(2004, 4, 4);
        microCac1 = new MicroCache();
        mysteryC1 = new MysteryCache();
        activity1 = new Activity(gregoria1, microCac1, 300, 0);
        activity2 = new Activity(gregoria1, mysteryC1, 100, 1000);
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
    public void testgetPoints()
    {
        assertEquals(0, activity1.getPoints());
        assertEquals(1000, activity2.getPoints());
        activity1.setPoints(10);
        assertEquals(10, activity1.getPoints());
    }
}

