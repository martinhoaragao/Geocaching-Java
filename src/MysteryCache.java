/**
 * Subclass of Cache which the User needs to solve a Puzzle to get the right
 * coordinates to find it.
 *
 *  @version 08/05/2015
 */

import java.io.Serializable;

public class MysteryCache extends Cache implements Serializable
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
     * @param id        Cache identifier
     * @param coord     Coordinates
     * @param mail      String e-mail of owner
     * @param question  Question for Puzzle
     * @param answer    Answer for Puzzle
     */
    public MysteryCache (double id, Coordinates coords, String mail, String question, String answer) {
        super(id, coords, mail);
        this.puzzle = new Puzzle(question, answer, coords);
    }

    /**
     * Construct a MysteryCache using another MysteryCache as reference
     * @arg mysteryCache
     */
    public MysteryCache (MysteryCache mysteryCache) {
        super(mysteryCache);
        this.puzzle = mysteryCache.puzzle.clone();
    }
    
    /**
     * @return the puzzle
     */
    public Puzzle getPuzzle(){
        return this.puzzle.clone();
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
        boolean comp = (this.getId() == aux.getId());
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
