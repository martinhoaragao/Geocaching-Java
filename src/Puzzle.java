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
    
    
    //Seters
    
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
    public void setStar(int n){
        this.star = n;
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

    public Puzzle pNumbers(){
        Coordinates c = new Coordinates(35,40);
        Puzzle a = new Puzzle("What is, what is, this little thing, that we learn since pre-school... It is Transcendental and irrational \n We may confuse it with food which makes us look like a fool?!", "", c);
    
    
        a.setStar(7);
    
    }
    
    
    
}