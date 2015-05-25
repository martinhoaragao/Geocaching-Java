

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ActivityTesting.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ActivityTesting
{
    /**
     * Default constructor for test class ActivityTesting
     */
    public ActivityTesting()
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
    public void ActivityMethods()
    {
        java.util.GregorianCalendar gregoria1 = new java.util.GregorianCalendar(2015, 4, 25);
        Coordinates CordLocal = new Coordinates(20, 30);
        TraditionalCache traditio1 = new TraditionalCache(1, CordLocal, "jess@gmail.com");
        MicroCache microCac1 = new MicroCache(2, CordLocal, "jess@gmail.com");
        Activity activity1 = new Activity(gregoria1, traditio1, 300, 10);
        Activity activity2 = new Activity(gregoria1, microCac1, 500, 10);
        activity1.getMonth();
        java.util.GregorianCalendar gregoria2 = activity2.getDate();
        
        assertEquals(false, activity1.equals(activity2));
        Activity activity3 = new Activity(activity1);
        assertEquals(true, activity3.equals(activity1));
        Activity iguala1 = new Activity(gregoria1, traditio1, 300, 10);
        assertEquals(true, iguala1.equals(activity1));
        
        assertNotNull(activity1.toString());
        assertEquals(activity1, activity1.clone());
        
        assertEquals(4, activity1.getMonth());
        assertEquals(300, activity1.getKms(), 0.1);
        activity1.setDate(25, 8, 1994);
        
        activity1.setDate(30, 1, 2005);
        
        activity1.setDate(30, 1, 2005);
        
        Activity activity4 = new Activity(gregoria1, microCac1, 300,10);
        assertEquals(4, activity4.getMonth());
        activity4.setDate(25, 4, 2005);
        
        activity4.setDate(24, 6, 2009);
        assertEquals(6, activity4.getMonth());
        /*activity4.setpoints(44);
        assertEquals(44, activity4.getpoints());
        */
        activity4.setCache(traditio1);
        
    }
}

