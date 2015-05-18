import java.util.ArrayList;

/**
 * This tradicional cache I think it is only a perpective of what all the caches must contain.
 * So this one is already performed by the Super-Class "Cache".
 * But in doubt, ask the tutor.
 * (I'm pretty sure it is what I just said ... ).
 *
 *  @version 08/05/2015
 */
public class Traditional extends Cache
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
    public Traditional () {
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
    public Traditional (String id, Coordinates coords, String mail) {
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
    public Traditional (Cache cache) {
        this.id = cache.getId();
        this.coords = cache.getCoords();
        this.mail = cache.getMail();
        this.registry = cache.getRegistry();
        this.treasure = cache.getTreasure();
        this.infos = cache.getInfos();
    }

    // equals

    /**
     * Create a clone of Traditional cache
     */
    public Traditional clone () {
        return new Traditional(this);
    }

}
