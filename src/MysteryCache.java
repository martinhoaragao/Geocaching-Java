/**
 * Subclass of Cache which the User needs to solve a Puzzle to get the right
 * coordinates to find it.
 *
 *  @version 08/05/2015
 */

import java.io.Serializable;
import java.util.ArrayList;

public class MysteryCache extends Cache implements Serializable
{
    private Puzzle puzzle; //Solve the puzzle to find the right coordinates
    /*Implement: method solve
    Only after the method solve returns true, the user gains access to the right coordinates
     for the cache he is looking for. */

    // Constructors

    /**
     * Construtor without arguments
     */
    public MysteryCache () {
        super();
        this.puzzle = new Puzzle();
    }

    /**
     * Constructor of new MysteryCache
     * @param id        Cache identifier
     * @param coord     Coordinates
     * @param mail      String e-mail of owner
     * @param puzzle    the Puzzle I want to set this cache with.
     */
    public MysteryCache (double id, Coordinates coords, String mail, Puzzle puzzle) {
        super(id, coords, mail);
        this.puzzle = puzzle.clone();
    }

    /**
     *  Parameterized Constructor
     *  @param id           Cache ID
     *  @param coords       Cache Coordinates
     *  @param mail         Owner's e-mail
     *  @param treasures    List of Treasures for this Cache
     *  @param info         Information about the Cache
     *  @param puzzle       Puzzle for this Cache
     */
    public MysteryCache (Double id, Coordinates coords, String mail, ArrayList<String> treasures, String info, Puzzle puzzle) {
        super(id, coords, mail);
        this.setTreasure(treasures);
        this.setInfo(info);
        this.setPuzzle(puzzle);
    }

    /**
     * Construct a MysteryCache using another MysteryCache as reference
     * @param mysteryCache
     */
    public MysteryCache (MysteryCache mysteryCache) {
        super(mysteryCache);
        this.puzzle = mysteryCache.puzzle.clone();
    }

    /**
     * Method that gets the Puzzle of this Cache.
     * @return the puzzle
     */
    public Puzzle getPuzzle(){
        return this.puzzle.clone();
    }
    // toString, equals, clone

    /**
     * Translate MicroCache's basic info to a String
     *@return the information as a String.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Id: " + this.getId().intValue() + " | ");
       

        sb.append(this.puzzle.getQuestion().toString());
        sb.append(" , created by: " + this.getMail() + "\n");
        sb.append(" More info: " + this.getInfo() + "\n");

        return sb.toString();
    }

    /**
     * Compares this MysteryCache with another object to check if they are equal
     * @param MysteryCache Object to compare with it
     */
    public boolean equals (Object mysteryCache) {
        if (this == mysteryCache) return true;

        if ((mysteryCache == null) || (this.getClass() != mysteryCache.getClass())) return false;

        MysteryCache aux = (MysteryCache) mysteryCache;
        boolean comp = (this.getId() == aux.getId());
        comp = comp && (this.getMail().equals(aux.getMail()));
        comp = comp && (this.getCoords().equals(aux.getCoords()));
        comp = comp && (this.getRegistry().equals(aux.getRegistry()));
        comp = comp && (this.getTreasure().equals(aux.getTreasure()));
        comp = comp && (this.puzzle.equals(aux.getInfo()));

        return comp;
    }

    /**
     *  Change the Cache's puzzle
     *  @param puzzle New puzzle for the Cache
     */
    public void setPuzzle (Puzzle puzzle) throws NullPointerException {
        if (puzzle == null)
            throw new NullPointerException("puzzle can't be null.");
        this.puzzle = puzzle.clone();
    }

    /**
     * Create a clone of MysteryCache
     */
    @Override
    public MysteryCache clone () {
        return new MysteryCache(this);
    }

}
