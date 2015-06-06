import java.util.ArrayList;
import java.io.Serializable;

/**
 * This class represents the Tradicional Cache.
 *
 *
 *  @version 08/05/2015
 */
public class TraditionalCache extends Cache implements Serializable
{

    // Constructors

    /**
     * Construtor without arguments
     */
    public TraditionalCache () {
        super();
     }

    /**
     *  Parameterized Constructor
     *  @param id           The Cache ID
     *  @param coords       Cache Coordinates
     *  @param mail         E-mail of the owner
     *  @param treasures    The Cache Treasures
     *  @param info         Information about the Cache
     */
    public TraditionalCache (Double id, Coordinates coords, String mail, ArrayList<String> treasures, String info) {
        super(id, coords, mail);
        this.setTreasure(treasures);
        this.setInfo(info);
    }

    /**
     * Construct a Cache using another cache as reference
     * @param cache Cache I want to construct from.
     */
    public TraditionalCache (Cache cache) {
        super(cache);
    }

    // clone

    /**
     * Create a clone of TraditionalCache cache
     */
    @Override
    public TraditionalCache clone () {
        return new TraditionalCache(this);
    }

}
