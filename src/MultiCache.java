/**
 * Subclass of Cache which the main difference between all other types of caches is that
 * the User needs to visit more than one place to find the right coordinates for the
 * final Cache for his treasure.
 *
 *  @version 08/05/2015
 */

import java.util.ArrayList;

public class MultiCache extends Cache
{
    private ArrayList<Coordinates> locals;
    private int stage; //Indicates the local where

    // Constructors

    /*+
     * Construtor without arguments
     */
    public MultiCache () {
        super();
        this.locals = new ArrayList<Coordinates>();
        this.stage = 0;
    }

    /**
     * Constructor of new MultiCache
     * @arg id String cache identifier
     * @arg coord Coordinates
     * @arg mail String e-mail of owner
     */
    public MultiCache (String id, Coordinates coords, String mail) {
        super(id, coords, mail);
        this.locals = new ArrayList<Coordinates>();
        this.stage = 0;
    }

    /**
     * CHECK THIS, MAY BE WRONG
     * Construct a MultiCache using another MultiCache as reference
     * @arg multiCache
     */
    public MultiCache (MultiCache multiCache) {
        super(multiCache);
        for (Coordinates aux : multiCache.locals)
            this.locals.add(aux.clone());
        this.stage = multiCache.stage;
    }

    // Getters

    /**
     * @return Coordinates of all multiCaches
     */
    public ArrayList<Coordinates> getLocals () {
        ArrayList<Coordinates> locals = new ArrayList<Coordinates>();

        for(Coordinates aux : this.locals)
            locals.add(aux.clone());
        return locals;
    }

    /**
     * @return stage at which the user is
     */
    public int getStage () {
        return this.stage;
    }

    // Setters

    /**
     * Set List of Coordinates (caches of multiCache)
     * @arg locals, ArrayList of Coordinates of caches in multiCache
     */
    public void setLocals (ArrayList<Coordinates> locals) {
        ArrayList<Coordinates> newLocals = new ArrayList<Coordinates>();

        for(Coordinates aux : locals)
            newLocals.add(aux);

        this.locals = newLocals;
    }

    /**
     * Set stage number
     * @arg stage, Int to identify stage
     */
    public void setStage (int stage) {
        this.stage = stage;
    }

    // toString, equals, clone

    /**
     * Translate MicroCache's basic info to a String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.toString());
        sb.append(this.locals.toString());
        sb.append("At: " + this.stage + "stage\n");

        return sb.toString();
    }

    /**
     * Compares this MultiCache with another object to check if they are equal
     * @arg MultiCache Object to compare with it
     */
    public boolean equals (Object multiCache) {
        if (this == multiCache) return true;

        if ((multiCache == null) || (this.getClass() != multiCache.getClass())) return false;

        MultiCache aux = (MultiCache) multiCache;
        boolean comp = this.getId().equals(aux.getId());
        comp = comp && (this.getMail().equals(aux.getMail()));
        comp = comp && (this.getCoords().equals(aux.getCoords()));
        comp = comp && (this.getRegistry().equals(aux.getRegistry()));
        comp = comp && (this.getTreasure().equals(aux.getTreasure()));
        comp = comp && (this.getInfo().equals(aux.getInfo()));
        comp = comp && (this.getLocals().equals(aux.getLocals()));
        comp = comp && (this.getStage() == (aux.getStage()));

        return comp;
    }

    /**
     * Create a clone of MultiCache
     */
    @Override
    public MultiCache clone () {
        return new MultiCache(this);
    }


}
