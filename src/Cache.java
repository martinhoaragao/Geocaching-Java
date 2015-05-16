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
public abstract class Cache
{
    private Coordinates coord;          // Cache coordinates
    private ArrayList<String> registry;  // registration of the cache / Record book / Registry
    private ArrayList<Treasure> treasure;  // Cache treasure
    private ArrayList<String> infos;    // Cache info
    private String mail;                // Cache owner mail
    private String id;                  //Cache identifier
    
    /**
     * Constructor of copy - Fazer de novo, só para evitar o erro nas outras classes...
     * Construtores apenas fazer em cada subclasse.
     */
    

    /**
     * Set cache coordinates
     * @arg lon, coordinates longitude
     * @arg lat, coordinates latitude
     */
    public void setCoordinates (double lon, double lat) {
        this.coord = new Coordinates(lon, lat);
    }

    // Getters

    /**
     * @return Cache coordinates
     */
    public Coordinates getCoords () {
        return this.coord.clone();
    }

    /**
     * @return Owner e-mail
     */
    public String getMail () {
        return this.mail;
    }
    
    /**
     * Get the id of a class
     */
    public String getID(){
        return this.id;
    }
    
    
    
    /**
     * Clone a cache -- Este clone é abstrato. 
     * O necessário é fazer clone e construtores em cada subclasse...
     */
    public Cache clone(){
        return this;
    }
}
