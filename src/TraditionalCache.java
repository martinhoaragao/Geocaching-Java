import java.util.ArrayList;
import java.io.Serializable;

/**
 * This tradicional cache I think it is only a perpective of what all the caches must contain.
 * So this one is already performed by the Super-Class "Cache".
 * But in doubt, ask the tutor.
 * (I'm pretty sure it is what I just said ... ).
 *
 *  @version 08/05/2015
 */
public class TraditionalCache extends Cache implements Serializable
{

    // Constructors

    /*+
     * Construtor without arguments
     */
    public TraditionalCache () {
        super();
     }

    /**
     * Constructor of new Cache
     * @arg id double cache identifier
     * @arg coord Coordinates
     * @arg mail String e-mail of owner
     */
    public TraditionalCache (double id, Coordinates coords, String mail) {
        super(id, coords, mail);
    }

    /**
     * Construct a Cache using another cache as reference
     * @arg cache Cache
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
