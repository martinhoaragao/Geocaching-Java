/**
 * Class that represents the Statistics of a given User, in a given month
 * represented by an integer from 0 to 11.
 * Name has to be different from statistics (already used in class User) so I called it "stats".
 * 
 * @version 10/05/2015
 */

import java.util.TreeMap;

public class Statistic
{
    //Could I declare private int month and then TreeSet<month, a> , for instace?
    //my opinion is no, but what do you think?
    private TreeMap<Integer, Activity> stats; // Visited Caches. key: month. value: activity.
    
    /**
     * Constructor without arguments
     */
    public Statistic(){
        stats = new TreeMap<Integer, Activity>();
    }
    
    /**
     * Copy Constructor (preserving encapsulation)
     */
    public Statistic(Statistic stt){
        stats = new TreeMap<>(); //Diamond style
        for(Activity a : stt.stats.values()){
            this.stats.put(a.getMonth(), a.clone());
        }
    }
    
    
    
    /**
     * Clone
     */
    public Statistic clone(){
        return new Statistic(this);
    }
    
}
