import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Abstract Class for all the kinds of existing caches.
 * It has the mutual information that we find in every kind of cache: id, coordinates, e-mail of the creator, registry book, treasures and information related to the cache.
 *
 *
 *  @version 06/06/2015
 */
public abstract class Cache implements Serializable {
    private Double id;                  //Cache identifier
    private Coordinates coords;          // Cache coordinates
    private String mail;                // Cache owner mail
    private ArrayList<String> registry;  // registration of the cache / Record book / Registry
    private ArrayList<Treasure> treasure;  // Cache treasure
    private String info;    // Cache info

    // Constructors

    /*+
     * Construtor without arguments
     */
    public Cache () {
        this.id = 0.0;
        this.coords = new Coordinates();
        this.mail = new String();
        this.registry = new ArrayList<String>();
        this.treasure = new ArrayList<Treasure>();
        this.info = new String();
    }

    /**
     * Constructor of new Cache
     * @param id double cache identifier
     * @param coord Coordininates
     * @param mail String e-mail of owner
     */
    public Cache (double id, Coordinates coords, String mail) {
        this.id = id;
        this.coords = coords.clone();
        this.mail = mail;
        this.registry = new ArrayList<String>();
        this.treasure = new ArrayList<Treasure>();
        this.info = new String();
    }

    /**
     * Construct a Cache using another cache as reference
     * @param cache Cache
     */
    public Cache (Cache cache) {
        this.id = cache.getId();
        this.coords = cache.getCoords();
        this.mail = cache.getMail();
        this.registry = cache.getRegistry();
        this.treasure = cache.getTreasure();
        this.info = cache.getInfo();
    }

    // Getters

    /**
     * @return ID of a Cache
     */
    public Double getId() {
        return this.id;
    }

    /**
     * @return Cache coordinates
     */
    public Coordinates getCoords () {
        return this.coords.clone();
    }

    /**
     * @return Owner's e-mail
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
     *  @return Treasures
     */
    public ArrayList<Treasure> getTreasure () {
        ArrayList<Treasure> treasure = new ArrayList<Treasure>();

        for(Treasure aux : this.treasure)
        //fazer clone treasuere
            treasure.add(aux);
        return treasure;
    }

    /**
     *  @return a Cache's information
     */
    public String getInfo () {
        return this.info;
    }

    // Setters

    /**
     * Set cache ID
     * @param id, Double cache identifier
     */
    public void setId (Double id) {
        this.id = id;
    }

    /**
     * Set cache coordinates
     * @param lon, coordinates longitude
     * @param lat, coordinates latitude
     */
    public void setCoordinates (double lon, double lat) {
        this.coords = new Coordinates(lon, lat);
    }

    /**
     * Set mail of owner
     * @param mail, String email
     */
    public void setMail (String mail) {
        this.mail = mail;
    }

    /**
     * Set book of registers
     * @param registry, ArrayList of Strings regarding previous activities
     */
    public void setRegistry (ArrayList<String> registry) {
        ArrayList<String> newRegistry = new ArrayList<String>();

        for(String aux : registry)
            newRegistry.add(aux);

        this.registry = newRegistry;
    }

    /**
     * Set list of treasures
     * @param treasure, ArrayList of Treasures
     */
    public void setTreasure (ArrayList<Treasure> treasure) throws NullPointerException {
        if(this instanceof MicroCache)
            treasure = null;
        else{
            if(treasure == null && !(this instanceof MicroCache)) throw new NullPointerException("parameter List<treasure> can't be null!");

            ArrayList<Treasure> newTreasure = new ArrayList<Treasure>();
            for(Treasure aux : treasure)
                newTreasure.add(aux);

            this.treasure = newTreasure;
        }
    }

    /**
     * Set cache's information
     * @param info, String
     */
    public void setInfo (String info) {
        this.info = info;
    }

    // toString, equals, clone

    /**
     * Translate Cache's basic info to a String
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: " + this.id.intValue() + " | ");
        sb.append(this.coords.toString());
        
        sb.append(" , created by: " + this.mail + "\n");
        sb.append(" More info: " + this.info + "\n");

        return sb.toString();
    }

    /**
     * Compares this cache with another object to check if they are equal
     * @param cache Object to compare with it
     */
    public boolean equals (Object cache) {
        if (this == cache) return true;

        if ((cache == null) || (this.getClass() != cache.getClass())) return false;

        Cache aux = (Cache) cache;
        boolean comp = (this.id == aux.getId());
        comp = comp && (this.mail.equals(aux.getMail()));
        comp = comp && (this.coords.equals(aux.getCoords()));
        comp = comp && (this.registry.equals(aux.getRegistry()));
        comp = comp && (this.treasure.equals(aux.getTreasure()));
        comp = comp && (this.info.equals(aux.getInfo()));
        return comp;
    }

    /**
     *  Create a clone of Cache, to be overridden.
     */
    public abstract Cache clone();

}
