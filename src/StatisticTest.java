

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
    private MysteryCache micro;
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
        micro = new MysteryCache();
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

    @Test  //This test is not updated.
    public void testStatsDeUmAno()
    {
        try{
            stats.addAct(activity1);
        }
        catch(Exception e){ System.out.println(e.getMessage()); }
        //assertEquals(true, stats.addAct(activity1));
        assertEquals(2015, activity1.getYear());
        assertEquals(2014, activity2.getYear());
        
        try{
            stats.addAct(activity2);
        }
        catch(Exception e){ System.out.println(e.getMessage()); }
        
        try{
            stats.addAct(activity3);
        }
        catch(Exception e){ System.out.println(e.getMessage()); }
        
        
        //assertEquals(false, stats.addAct(activity2));
        //assertEquals(true, stats.addAct(activity3));
        Statistic statisti1 = new Statistic(stats);
        stats.equals(statisti1);
        Statistic statisti2 = new Statistic(stats);
        
        
        
        assertEquals(true, statisti2.equals(stats));
        stats.getSumKms();
        stats.getSumPoints();
        stats.getSumPoints(8);
        assertEquals(444, stats.getSumPoints(6));
        assertEquals(1, stats.getSumPoints(5));
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

    @Test
    public void testSeRemove()
    {
        stats.getinfoNCaches();
        activity2.getMonth();
        
        try{
            stats.addAct(activity2);
        }
        catch(Exception e){ System.out.println(e.getMessage()); }
        activity2.getYear();
        stats.setYearStatsMonth(2014);
        assertEquals(2014, activity2.getYear());
        
        try{
             stats.addAct(activity2);
        }
        catch(Exception e){ System.out.println(e.getMessage()); }
        
         try{
            stats.addAct(activity2);
        }
        catch(Exception e){ System.out.println(e.getMessage()); }
        //true
       
        stats.getinfoNCaches();
        stats.removeAct(activity2);
    }

    

    @Test
    public void testStatisticDe1ano()
    {
        try{
        stats.addAct(activity1);
    }
    catch(Exception e){ System.out.println(e.getMessage()); }
        assertNotNull(stats.getinfoNCaches());
        assertEquals(5, activity1.getMonth());
        stats.getYearStatsMonth();
        assertEquals(1, stats.getTotalCaches(5));
        assertEquals(0, stats.getTotalCaches(4));
    }

    @Test
    public void testRemoveActivityTrue()
    {
        java.util.GregorianCalendar gregoria2 = new java.util.GregorianCalendar(2015, 8, 25);
        Coordinates cachecoordinates = new Coordinates(20, 24);
        TraditionalCache traditio1 = new TraditionalCache(2.0, cachecoordinates, "jess@gmail.com");
        Statistic STATS = new Statistic();
        assertEquals(2015, STATS.getYearStatsMonth());
        Activity activity4 = new Activity(gregoria2, traditio1, 350, 5);
        assertEquals(2015, activity4.getYear());
        
        try{
            STATS.addAct(activity4);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
        assertNotNull(STATS.getinfoNCaches());
        assertEquals(true, STATS.removeCache(3.0));
        STATS.getTotalCaches();
        STATS.getinfoNCaches();
        assertEquals(true, STATS.removeAct(activity4));
        assertEquals(0, STATS.getTotalCaches());
        STATS.getSumKms(8);
        
       try{
            STATS.addAct(activity4);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        assertEquals(5, STATS.getSumPoints(8));
    }
}





