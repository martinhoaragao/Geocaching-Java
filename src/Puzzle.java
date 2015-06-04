import Exceptions.*;
/**
 * A puzzle to solve by the user, when looking for a "MisteryCache".
 *
 * @version 08/05/2015
 */

public class Puzzle {
    private String question;
    private String answer;
    private int star; //The star difficulty rating for 1 to 10;
    
    private int valuepoints; //calculates the value of this cache depending on the star rating.

    /**
     * Constructor with arguments
     * @arg q Question
     * @arg a Answer
     * @arg l Coordinates
     */
    public Puzzle(String q, String a){
        this.question = q;
        answer = a;
        this.star = 3;
        this.valuepoints = 30;
    }

    /**
     * Empty Constructor
     */
    public Puzzle(){
        this.question = "";
        this.answer = "";
        this.star = 2;
        this.valuepoints = 30;
    }

    /**
     * Copy Constructor
     */
    public Puzzle(Puzzle a){
        this.question = a.question;
        this.answer = a.answer;
        this.star = a.star;
        this.valuepoints = a.getValuePoints();
    }
    
    /**
     * Set the valuepoints comparing the star rating.
     */
    //everytime creating a puzzle ormystery cache , do the updatepoints after seting the star dificulty.
    //This is the points for the entire mystery cache.
    public void updatePoints(){
        
        setValuePoints(this.star*5); 
        
        /*switch (this.star){
            case 10: setValuePoints(50); break;//maximum
            case 9: setValuePoints(45); break;
            case 8: setValuePoints(
        }*/
        
        
        
    }
    
    /**
     * Sets the valuepoints for this puzzle
     */
    public void setValuePoints(int points){
        this.valuepoints = points;
    }
    
    /**
     * Get the valuepoints for this puzzle
     */
    public int getValuePoints(){
        return this.valuepoints;
    }
    //Setters


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
    public Puzzle pNumber0() throws PuzzleStarEx{
        Puzzle a = new Puzzle(" It is Transcendental and irrational. \n We learn it since pre-school... \n We may confuse it with food \n which makes us look like a fool. ", "PI");
        a.setStar(8);
        return a;
    }

    /**
     * Clone, toString and equals
     */

    public boolean equals(Object o){
        if(this == o) return true;
        if(this == null || o == null || this.getClass() != o.getClass()) return false;

        Puzzle a = (Puzzle) o;
        return a.question.equals(this.question) && a.answer.equals(this.answer);

    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Q: " + this.getQuestion()); sb.append(" A: " + this.getAnswer());
        return sb.toString();
    }

    public Puzzle clone(){
        return new Puzzle(this);
    }
}
