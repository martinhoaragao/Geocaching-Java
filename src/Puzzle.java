import Exceptions.*;
/**
 * A puzzle to solve by the user, when looking for a "MisteryCache".
 *
 * @version 08/05/2015
 */

public class Puzzle {
    private String question;
    private String answer;
    private Coordinates local;
    private int star; //The star difficulty rating for 1 to 10;


    /**
     * Constructor with arguments
     * @arg q Question
     * @arg a Answer
     * @arg l Coordinates
     */
    public Puzzle(String q, String a, Coordinates l){
        this.question = q;
        answer = a;
        local = l;
        this.star = 3;
    }

    /**
     * Empty Constructor
     */
    public Puzzle(){
        this.local = new Coordinates(0,0);
        this.question = "";
        this.answer = "";
        this.star = 2;

    }

    /**
     * Copy Constructor
     */
    public Puzzle(Puzzle a){
        this.question = a.question;
        this.answer = a.answer;
        this.star = a.star;
        this.local = a.local.clone();
    }
    

    //Setters

    /**
     * Set the Coordinates for the treasure local.
     *
     * @arg lat Latitude
     * @arg lon Longitude
     */
    public void setCord(int lat, int lon){
        this.local.setLon(lon); this.local.setLat(lat);
    }

    /**
     * Make a question and an answer for this Puzzle
     *
     * @arg q Question as a String
     * @arg a Answer as a String
     */
    public void setQuestion(String q, String a){
        this.question = q;
        this.answer = a;
    }

    /**
     * Sets the rate of the difficulty of the puzzle by giving a 1-10 star.
     *
     * @arg n Number ratting.
     */
    public void setStar(int n) throws PuzzleStarEx{
        if(n<=0 || n >=11) //star only of range 1-10
        {
            throw new PuzzleStarEx("Star rating only of range 1-10.");
        }
        this.star = n;
        //else exception handler... send message to user (do this by an exception)
    }

    //Getters and Shows
    /**
     * Get the difficulty of a Puzzle.
     */
    public int getStar(){
        return this.star;
    }

    /**
     * Get the coordinates of the treasure.
     */
    public Coordinates getCoord(){
        return local;
    }

    /**
     * Get the Question of this puzzle
     */
    public String getQuestion(){
        return this.question;
    }

    /**
     * Show the Question of this puzzle
     */
    public void showQuestion(){
        System.out.println(this.getQuestion());
    }

    /**
     * Get the answer of this puzzle
     */
    public String getAnswer(){
        return this.answer;
    }

    /**
     * Show the answer of this puzzle
     */
    public void showAnswer(){
        System.out.println(this.getAnswer());
    }

    /**
     * Some Puzzles.
     * The methods that return this puzzels are written in this signature, so don't confuse with other methods:
     * "public Puzzle p_____" where in the underscore there is the type of the puzzle followed by a number,
     * that can be seen as an index.
     *
     *
     * Types of Puzzles:
     * Visual (picture where coordinates are hidden)
     * Newspaper (sudokku...)
     * World (giving a clue where the coordinates are)
     * Cipher (Cipher Code) (or different alphabet)...
     * Trivia (answering questions)
     * Numbers (Math to get the right coordinates, the c are... )
     *
     * (String q, String a, Coordinates l)
     */
    //TODO - dá erro se nao tiver o catch, mas acho que nao faz sentido meter aqui catch do erro porque eu vou mete estrela 7 e já sei qual o range.... 
    public Puzzle pNumber0(){
        Coordinates c = new Coordinates(35,40);
        Puzzle a = new Puzzle(" It is Transcendental and irrational. \n We learn it since pre-school... \n We may confuse it with food \n which makes us look like a fool. ", "PI", c);

        try{
        a.setStar(11);
        }
        catch(Exception e){
            System.out.println("Only add star rage 1-10");
        }
        
        
        return a;
    }

    public Puzzle pCipher0(){
        Coordinates o = new Coordinates(20, 15);
        //twenty _ fifteen
        Puzzle a = new Puzzle(" t ve 3 ~ t j   ,  1 & 1 t 3 3 ~ " ,"(twenty,fifteen)",o);
        
       try { a.setStar(3); }
       catch(Exception e){
          System.out.println("Only add star rage 1-10");
        }
        
        return a;
    }




    public String Cipher(){
        StringBuilder c = new StringBuilder();
        c.append("| A * |"); c.append(" B 8 |"); c.append(" C [ |"); c.append(" D } |");
        c.append(" E 3 |" + " F 1 |" + " G 6 |" + " H / |" + " I & |" + " J x | K 0 | L l | M + | N ~ | O o | P § | Q 0 | R { | S % | T t | U ç | V bb | W ve | X ics | Y j | Z 4 |");

        return c.toString();

    }


    /**
     * Clone, toString and equals
     */

    public boolean equals(Object o){
        if(this == o) return true;
        if(this.getClass() != o.getClass()) return false;

        Puzzle a = (Puzzle) o;
        return a.question.equals(this.question) && a.answer.equals(this.answer) && a.local.equals(this.local);

    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Q: " + this.getQuestion()); sb.append(" A: " + this.getAnswer());
        sb.append(local.toString());
        return sb.toString();
    }

    public Puzzle clone(){
        return new Puzzle(this);
    }
}
