import java.util.GregorianCalendar;
import java.util.ArrayList;

/**
 * Super-Class for all the kinds of existing caches. 
 * The other classes will be sub-classes of this one by writing this signature:
 *  
 * public class CacheMini extends Cache
 * 
 * for instance.
 * 
 *  @version 08/05/2015
 */
public class Cache
{
    private Coordinates coord;          // Cache coordinates
    private ArrayList<String> registry;  // registration of the cache / Record book / Registry
    private ArrayList<Object> treasure;  // Cache treasure
    private ArrayList<String> infos;    // Cache info
    private String mail;                // Cache owner mail
}
