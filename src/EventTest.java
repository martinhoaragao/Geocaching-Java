

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class EventTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class EventTest
{
    private TraditionalCache traditio1;
    private MicroCache microCac1;
    private MultiCache multiCac1;
    private MysteryCache mysteryC1;
    private TraditionalCache traditio2;
    private MicroCache microCac2;
    private java.util.ArrayList<Cache> arrayLis1;
    private java.util.GregorianCalendar gregoria1;
    private Event event1;
    private java.util.ArrayList<User> arrayLis2;
    private NormalUser normalUs1;
    private NormalUser normalUs2;
    private NormalUser normalUs3;

    /**
     * Default constructor for test class EventTest
     */
    public EventTest()
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
        traditio1 = new TraditionalCache();
        microCac1 = new MicroCache();
        multiCac1 = new MultiCache();
        mysteryC1 = new MysteryCache();
        traditio2 = new TraditionalCache();
        microCac2 = new MicroCache();
        arrayLis1 = new java.util.ArrayList<Cache>();
        arrayLis1.add(microCac2);
        arrayLis1.add(traditio2);
        arrayLis1.add(mysteryC1);
        arrayLis1.add(multiCac1);
        arrayLis1.add(microCac1);
        arrayLis1.add(traditio1);
        gregoria1 = new java.util.GregorianCalendar(2015, 8, 25);
        event1 = new Event(1.0, gregoria1, arrayLis1);
        arrayLis2 = new java.util.ArrayList<User>();
        normalUs1 = new NormalUser("jessica@gmail.com", "0", "Jessica", 1.0, gregoria1);
        normalUs2 = new NormalUser("jessicapereira@gmail.com", "0", "0", 2.0, gregoria1);
        normalUs3 = new NormalUser("martinho@gmail.com", "0", "Martinho", 3.0, gregoria1);
        arrayLis2.add(normalUs1);
        arrayLis2.add(normalUs2);
        arrayLis2.add(normalUs3);
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
    public void testEvent()
    {
        java.util.ArrayList<NormalUser> arrayLis3 = new java.util.ArrayList<NormalUser>();
        try{
        normalUs3.addActivity(multiCac1, gregoria1);
        normalUs3.addActivity(traditio2, gregoria1);
        normalUs1.addActivity(traditio2, gregoria1);
        normalUs2.addActivity(traditio2, gregoria1);
        normalUs2.addActivity(multiCac1, gregoria1);
        arrayLis3.add(normalUs3);
        arrayLis3.add(normalUs1);
        arrayLis3.add(normalUs2);
        event1.setUsers(arrayLis3);
    }catch(Exception e){System.out.println(e.getMessage());}
        java.util.GregorianCalendar gregoria2 = new java.util.GregorianCalendar(2015, 8, 30);
    }
}

