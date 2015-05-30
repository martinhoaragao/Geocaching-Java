import Exceptions.*;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class GeocachingPOOTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GeocachingPOOTest
{
    private GeocachingPOO geocachi1;
    private GeocachingPOO asdsad;
    private GeocachingPOO geocachi2;
    private java.util.GregorianCalendar gregoria1;
    private NormalUser normalUs1;

    /**
     * Default constructor for test class GeocachingPOOTest
     */
    public GeocachingPOOTest()
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
        geocachi1 = new GeocachingPOO();
        asdsad = new GeocachingPOO();
        geocachi2 = new GeocachingPOO();
        gregoria1 = new java.util.GregorianCalendar(1994, 8, 25);
        normalUs1 = new NormalUser("jessica@gmail.com", "0", "Je", 1.0, gregoria1);
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
    public void testIdExceptionFolder()
    {
        try{
        geocachi1.register(normalUs1);
       }catch(Exception e){System.out.println(e.getMessage());}
        NormalUser normalUs2 = new NormalUser("jessidsadca@gmail.com", "0", "Dasd", 1.0, gregoria1);
    }

    
}


