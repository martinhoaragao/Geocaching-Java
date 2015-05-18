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

    // Constructors

    /*+
     * Construtor without arguments
     */
    public Traditional () {
        super();
     }

    /**
     * Constructor of new Cache
     * @arg id String cache identifier
     * @arg coord Coordinates
     * @arg mail String e-mail of owner
     */
    public Traditional (String id, Coordinates coords, String mail) {
        super(id, coords, mail);
    }

    /**
     * Construct a Cache using another cache as reference
     * @arg cache Cache
     */
    public Traditional (Cache cache) {
        super(cache);
    }

    // clone

    /**
     * Create a clone of Traditional cache
     */
    @Override
    public Traditional clone () {
        return new Traditional(this);
    }

}
