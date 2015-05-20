/**
 * Subclass of Cache which the User needs to solve a Puzzle to get the right
 * coordinates to find it.
 *
 *  @version 08/05/2015
 */

public class MysteryCache extends Cache
{
    private Puzzle puzzle; //Solve the puzzle to find the right coordinates
    /*Implement: method solve
    Only after the method solve returns true, the user gains access to the right coordinates
     for the cache he is looking for. */

    // Constructors

    /*+
     * Construtor without arguments
     */
    public MysteryCache () {
        super();
        this.puzzle = new Puzzle();
    }

    /**
     * Constructor of new MysteryCache
     * @arg id String cache identifier
     * @arg coord Coordinates
     * @arg mail String e-mail of owner
     */
    public MysteryCache (String id, Coordinates coords, String mail) {
        super(id, coords, mail);
        this.puzzle = new Puzzle();
    }

    /**
     * Construct a MysteryCache using another MysteryCache as reference
     * @arg mysteryCache
     */
    public MysteryCache (MysteryCache mysteryCache) {
        super(mysteryCache);
        this.puzzle = mysteryCache.puzzle.clone();
    }

    // toString, equals, clone

    /**
     * Translate MicroCache's basic info to a String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.toString());
        sb.append(this.puzzle.toString());

        return sb.toString();
    }

    /**
     * Compares this MysteryCache with another object to check if they are equal
     * @arg MysteryCache Object to compare with it
     */
    public boolean equals (Object mysteryCache) {
        if (this == mysteryCache) return true;

        if ((mysteryCache == null) || (this.getClass() != mysteryCache.getClass())) return false;

        MysteryCache aux = (MysteryCache) mysteryCache;
        boolean comp = this.getId().equals(aux.getId());
        comp = comp && (this.getMail().equals(aux.getMail()));
        comp = comp && (this.getCoords().equals(aux.getCoords()));
        comp = comp && (this.getRegistry().equals(aux.getRegistry()));
        comp = comp && (this.getTreasure().equals(aux.getTreasure()));
        comp = comp && (this.getInfo().equals(aux.getInfo()));
        comp = comp && (this.puzzle.equals(aux.getInfo()));

        return comp;
    }

    /**
     * Create a clone of MysteryCache
     */
    @Override
    public MysteryCache clone () {
        return new MysteryCache(this);
    }

}
