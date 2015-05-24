

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class StatisticYearTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class StatisticYearTest
{
    private java.util.GregorianCalendar ano2005;
    private java.util.GregorianCalendar gregoria1;
    private TraditionalCache traditio1;
    private Activity activity1;
    private MicroCache microCac1;
    private Activity activity2;
    private User user1;

    /**
     * Default constructor for test class StatisticYearTest
     */
    public StatisticYearTest()
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
        ano2005 = new java.util.GregorianCalendar(2005, 8, 25);
        gregoria1 = new java.util.GregorianCalendar(2008, 8, 25);
        traditio1 = new TraditionalCache();
        activity1 = new Activity(ano2005, traditio1, 200);
        microCac1 = new MicroCache();
        microCac1.setCoordinates(400, 400);
        activity2 = new Activity(gregoria1, microCac1, 20);
        user1 = new User("das@gmail.com", "99", "DASD", 9.0, ano2005);
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
    public void testeYearMonth()
    {
        user1.addActivity(activity1);
        user1.addActivity(activity2);
        StatisticYear statisti1 = new StatisticYear();
    }

    

    @Test
    public void test3()
    {
        activity2.setDate(4, 3, 2015);
        StatisticYear statisti1 = new StatisticYear();
        assertEquals(true, statisti1.addActY(activity2));
        assertEquals(true, statisti1.addActY(activity1));
    }

    @Test
    public void test4agoraadicionatodos()
    {
        StatisticYear statisti1 = new StatisticYear();
        activity2.setDate(24, 8, 2015);
        assertEquals(8, activity1.getMonth());
        assertEquals(2005, activity1.getYear());
        assertEquals(true, statisti1.addActY(activity1));
        assertEquals(true, statisti1.addActY(activity2));
        assertNotNull(statisti1.getStatsyear());
    }
}




