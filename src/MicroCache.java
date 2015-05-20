/**
 * This subclass holds a little box where only fits the
 * registry book already defined in the super class.
 *
 *  @version 11/05/2015
 */

public class MicroCache extends Cache
{
    // Constructors

    /*+
     * Construtor without arguments
     */
    public MicroCache () {
        super();
     }

    /**
     * Constructor of new MicroCache
     * @arg id double cache identifier
     * @arg coord Coordinates
     * @arg mail String e-mail of owner
     */
    public MicroCache (double id, Coordinates coords, String mail) {
        super(id, coords, mail);
    }

    /**
     * Construct a MicroCache using another MicroCache as reference
     * @arg microCache MicroCache
     */
    public MicroCache (MicroCache microCache) {
        super(microCache);
    }

    // clone

    /**
     * Create a clone of MicroCache
     */
    @Override
    public MicroCache clone () {
        return new MicroCache(this);
    }

}
