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
    private String id;                  //Cache identifier
    private Coordinates coords;          // Cache coordinates
    private String mail;                // Cache owner mail
    private ArrayList<String> registry;  // registration of the cache / Record book / Registry
    private ArrayList<Treasure> treasure;  // Cache treasure
    private ArrayList<String> infos;    // Cache info

    // Constructors

    /*+
     * Construtor without arguments
     */
    public Cache () {
       this.id = "";
       this.coords = new Coordinates();
       this.mail = "";
       this.registry = new ArrayList<String>();
       this.treasure = new ArrayList<Treasure>();
       this.infos = new ArrayList<String>();
     }

    /**
     * Constructor of new Cache
     * @arg id String cache identifier
     * @arg coord Coordinates
     * @arg mail String e-mail of owner
     */
    public Cache (String id, Coordinates coords, String mail) {
        this.id = id;
        this.coords = coords.clone();
        this.mail = mail;
        this.registry = new ArrayList<String>();
        this.treasure = new ArrayList<Treasure>();
        this.infos = new ArrayList<String>();
    }

    /**
     * Construct a Cache using another cache as reference
     * @arg cache Cache
     */
    public Cache (Cache cache) {
        this.id = cache.getId();
        this.coords = cache.getCoords();
        this.mail = cache.getMail();
        this.registry = cache.getRegistry();
        this.treasure = cache.getTreasure();
        this.infos = cache.getInfos();
    }

    // Getters

    /**
     * @return ID of a Cache
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return Cache coordinates
     */
    public Coordinates getCoords () {
        return this.coords.clone();
    }

    /**
     * @return Owner e-mail
     */
    public String getMail () {
        return this.mail;
    }

    /**
     *  @return Registry (The Record book)
     */
    public ArrayList<String> getRegistry () {
        ArrayList<String> registry = new ArrayList<String>();

        for(String aux : this.registry)
            registry.add(aux);
        return registry;
    }

    /**
     *  @return Treasure
     */
    public ArrayList<Treasure> getTreasure () {
        ArrayList<Treasure> treasure = new ArrayList<Treasure>();

        for(Treasure aux : this.treasure)
            treasure.add(aux.clone());
        return treasure;
    }

    /**
     *  @return Information of a cache
     */
    public ArrayList<String> getInfos () {
        ArrayList<String> infos = new ArrayList<String>();

        for(String aux : this.infos)
            infos.add(aux);
        return infos;
    }

    // Setters

    /**
     * Set cache ID
     * @arg, id, String cache identifier
     */
    public void setId (String id) {
        this.id = id;
    }

    /**
     * Set cache coordinates
     * @arg lon, coordinates longitude
     * @arg lat, coordinates latitude
     */
    public void setCoordinates (double lon, double lat) {
        this.coords = new Coordinates(lon, lat);
    }





}
