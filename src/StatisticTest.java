

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class StatisticTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class StatisticTest
{
    private java.util.GregorianCalendar gregoria1;
    private java.util.GregorianCalendar mybdate;
    private java.util.GregorianCalendar vinte52015;
    private java.util.GregorianCalendar vinte62015;
    private java.util.GregorianCalendar vinte052014;
    private MicroCache micro;
    private MysteryCache mys;
    private TraditionalCache tra;
    private Activity activity1;
    private Activity activity2;
    private Activity activity3;
    private NormalUser normalUs1;
    private Statistic stats;

    /**
     * Default constructor for test class StatisticTest
     */
    public StatisticTest()
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
        gregoria1 = new java.util.GregorianCalendar(1994, 8, 25);
        mybdate = new java.util.GregorianCalendar(1994, 8, 25);
        vinte52015 = new java.util.GregorianCalendar(2015, 5, 20);
        vinte62015 = new java.util.GregorianCalendar(2015, 6, 20);
        vinte052014 = new java.util.GregorianCalendar(2014, 5, 20);
        micro = new MicroCache();
        mys = new MysteryCache();
        tra = new TraditionalCache();
        activity1 = new Activity(vinte52015, micro, 100, 1);
        activity2 = new Activity(vinte052014, tra, 30, 3);
        activity3 = new Activity(vinte62015, mys, 444, 444);
        normalUs1 = new NormalUser("jess@gmail.com", "0", "Jessica", 1.0, mybdate);
        stats = new Statistic();
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
    public void testStatsDeUmAno()
    {
        assertEquals(true, stats.addAct(activity1));
        assertEquals(2015, activity1.getYear());
        assertEquals(2014, activity2.getYear());
        assertEquals(false, stats.addAct(activity2));
        assertEquals(true, stats.addAct(activity3));
        Statistic statisti1 = new Statistic(stats);
        stats.equals(statisti1);
        Statistic statisti2 = new Statistic(stats);
        assertEquals(true, statisti2.equals(stats));
        stats.getSumKms();
        stats.getSumPoints();
        stats.getSumPointsM(8);
        assertEquals(444, stats.getSumPointsM(6));
        assertEquals(1, stats.getSumPointsM(5));
        assertNotNull(stats.getinfoNCaches());
        assertEquals(2015, stats.getYearStatsMonth());
        assertEquals(2, stats.getTotalCaches());
        stats.removeAct(activity1);
        stats.getSumPoints();
        stats.getinfoNCaches();
        stats.removeAct(activity2);
        stats.getinfoNCaches();
        stats.removeAct(activity1);
        stats.removeAct(activity2);
        stats.removeAct(activity3);
        stats.getinfoNCaches();
    }
}

