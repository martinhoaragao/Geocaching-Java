

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class StatsTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class StatsTest
{
    /**
     * Default constructor for test class StatsTest
     */
    public StatsTest()
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
    public void StatsMethods()
    {
        java.util.GregorianCalendar gregoria1 = new java.util.GregorianCalendar(2015, 8, 15);
        java.util.GregorianCalendar gregoria2 = new java.util.GregorianCalendar(2015, 8, 30);
        java.util.GregorianCalendar gregoria3 = new java.util.GregorianCalendar(2015, 9, 6);
        Coordinates coordina1 = new Coordinates(20, 10);
        MicroCache microCac1 = new MicroCache(2, coordina1, "jess@hotmail.com");
        Activity activity1 = new Activity(gregoria1, microCac1, 100);
        Activity activity2 = new Activity(gregoria2, microCac1, 200);
        Activity activity3 = new Activity(gregoria3, microCac1, 300);
        Statistic statisti1 = new Statistic();
        statisti1.addAct(activity1);
        statisti1.addAct(activity2);
        statisti1.addAct(activity3);
        assertEquals(300, statisti1.getSumkmsM(8), 0.1);
        assertEquals(5, statisti1.getCMonth());
        assertEquals(1, statisti1.getSumAM(9));
        assertEquals(2, statisti1.getSumAM(8));
        assertEquals(0, statisti1.getSumAM(10));
        statisti1.getTreeSet(9);
        assertNotNull(statisti1.getinfoNCaches());
    }

    

    

    @Test
    public void test3()
    {
        java.util.GregorianCalendar gregoria1 = new java.util.GregorianCalendar(2015, 8, 15);
        java.util.GregorianCalendar gregoria2 = new java.util.GregorianCalendar(2015, 8, 30);
        java.util.GregorianCalendar gregoria3 = new java.util.GregorianCalendar(2015, 6, 9);
        MultiCache multiCac1 = new MultiCache();
        TraditionalCache traditio1 = new TraditionalCache();
        MysteryCache mysteryC1 = new MysteryCache();
        Activity activity1 = new Activity(gregoria1, multiCac1, 100);
        Activity activity2 = new Activity(gregoria2, mysteryC1, 200);
        Activity activity3 = new Activity(gregoria3, traditio1, 150);
        Activity activity4 = new Activity(gregoria3, mysteryC1, 30);
        activity1.setPoints(5);
        activity2.setPoints(4);
        activity3.setPoints(2);
        activity4.setPoints(4);
        Statistic statisti1 = new Statistic();
        statisti1.addAct(activity1);
        statisti1.addAct(activity2);
        statisti1.addAct(activity3);
        statisti1.addAct(activity4);
        assertEquals(4, statisti1.getSumTotalCaches());
        //assertEquals(15, statisti1.getSumPoints());
        assertEquals(480, statisti1.getSumKms(), 0.1);
        statisti1.getinfoNCaches();
        statisti1.toString();
        statisti1.getinfoNCaches();
        statisti1.getTreeSet(9);
        assertEquals(180, statisti1.getSumkmsM(6), 0.1);
        assertEquals(0, statisti1.getSumkmsM(7), 0.1);
        assertEquals(300, statisti1.getSumkmsM(8), 0.1);
        statisti1.getSumkmsM(6);
        activity4.getMonth();
        statisti1.getTreeSet(8);
        statisti1.getTreeSet(6);
        statisti1.getSumTotalCaches();
        //assertEquals(6, statisti1.getSumPointsM(6));
        //assertEquals(9, statisti1.getSumPointsM(8));
        assertEquals(2, statisti1.getSumAM(6));
        statisti1.getCMonth();
    }
}




