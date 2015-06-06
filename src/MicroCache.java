/**
 * This subclass holds a little box where only fits the
 * registry book already defined in the super class.
 *
 *  @version 11/05/2015
 */

import java.io.Serializable;

public class MicroCache extends Cache implements Serializable
{
    // Constructors

    /**
     * Construtor without arguments
     */
    public MicroCache () {
        super();
        this.setTreasure(null);
     }

    /**
     * Constructor of new MicroCache
     * @param id double cache identifier
     * @param coord Coordinates
     * @param mail String e-mail of owner
     */
    public MicroCache (double id, Coordinates coords, String mail) {
        super(id, coords, mail);
        this.setTreasure(null);
    }

    /**
     *  Parameterized Constructor
     *  @param id       The Cache ID
     *  @param coords   The Cache Coordinates
     *  @param mail     Owner's e-mail
     *  @param info     Information about this Cache
     */
    public MicroCache (Double id, Coordinates coords, String mail, String info) {
        super(id, coords, mail);
        this.setInfo(info);
    }


    /**
     * Construct a MicroCache using another MicroCache as reference
     * @param microCache MicroCache
     */
    public MicroCache (MicroCache microCache) {
        super(microCache);
        this.setTreasure(null);
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
