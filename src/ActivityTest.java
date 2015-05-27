

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
    private java.util.GregorianCalendar gregoria2;
    private java.util.GregorianCalendar gregoria3;
    private Coordinates coordina1;
    private Coordinates coordina2;

    
    
    
    
    

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
        gregoria1 = new java.util.GregorianCalendar(2015, 8, 4);
        gregoria2 = new java.util.GregorianCalendar(4, 8, 2004);
        gregoria3 = new java.util.GregorianCalendar(4, 9, 2015);
        coordina1 = new Coordinates(30, 40);
        coordina2 = new Coordinates(50, 50);
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

    @Test
    public void testgetsandsets()
    {
        MicroCache microCac1 = new MicroCache(1.0, coordina2, "jess@gmail.com");
        Activity activity1 = new Activity(gregoria1, microCac1, 100, 3);
        assertEquals(8, activity1.getMonth());
        assertEquals(2015, activity1.getYear());
        assertEquals(3, activity1.getPoints());
        assertEquals(100, activity1.getKms(), 0.1);
        assertNotNull(activity1.getDate());
        assertNotNull(activity1.getCache());
        Activity activity2 = new Activity(activity1);
        assertEquals(true, activity2.equals(activity1));
        activity1.setKms(150);
        assertNotNull(activity1.toString());
        assertEquals(false, activity1.equals(activity2));
        activity1.setDate(4, 4, 2000);
        assertEquals(2000, activity1.getYear());
        assertEquals(4, activity1.getMonth());
        assertEquals(150, activity1.getKms(), 0.1);
        activity1.setDate(gregoria2);
        activity1.getMonth();
        activity1.setDate(gregoria2);
        
    }

    @Test
    public void testequals()
    {
        TraditionalCache traditio1 = new TraditionalCache();
        Activity activity1 = new Activity(gregoria1, traditio1, 100, 10);
        Activity activity2 = new Activity(activity1);
        assertEquals(true, activity1.equals(activity2));
        activity1.setDate(gregoria2);
        activity1.getCache();
        assertEquals(false, activity1.equals(activity2));
        activity2.getMonth();
        activity1.getMonth();
    }

    
}




