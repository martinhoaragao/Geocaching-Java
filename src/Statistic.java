/**
 * Class that represents the Statistics of a given User, in a given month
 * represented by an integer from 0 to 11.
 * Name has to be different from statistics (already used in class User) so I called it "stats".
 * 
 * @version 10/05/2015
 */

import java.util.TreeMap;
import java.util.TreeSet;

public class Statistic
{
    //Could I declare private int month and then TreeSet<month, a> , for instace?
    //my opinion is no, but what do you think?
    private TreeSet<Activity> acts; //List of actitivites
    private TreeMap<Integer,  TreeSet<Activity> > stats; // Visited Caches. key: month. value: activity.
    
    /**
     * Constructor without arguments
     */
    public Statistic(){
        acts = new TreeSet<Activity>();
        stats = new TreeMap<Integer, TreeSet<Activity>>();
    }
    
    /**
     * Copy Constructor (preserving encapsulation)
     * !Complex!
     */
    public Statistic(Statistic stt){
        stats = new TreeMap<>(); //Diamond style
        
        
        for(int i=0;i<12;i++){
            acts = new TreeSet<>();
            for(Activity a : stt.acts){
                 acts.add(a.clone());
            }
            this.stats.put(i,acts); 
            //a.getMonth() == i  for sure!
        }
    }
    

    /**
     * Create a clone of this object
     */
    public Statistic clone(){
        return new Statistic(this);
    }
    
    /**
     * Compare this Statistic to another to check if they are equal
     * @arg sa Statistic to use for comparison
     */
    public boolean equals(Object sa){
        if (this == sa) return true;

        if ((sa == null) || (sa.getClass() != this.getClass())) return false;
        
        
        Statistic aux = (Statistic) sa;
        if(aux.stats.size() != this.stats.size()) return false;
        //If both Map's don't have the same size then they must be different.
        
        for(int i=0;i<12;i++){
            if(aux.acts.size() != this.acts.size()) return false;
        }
        //Check the size of each TreeSet in the month.
        
        
        
        for(int i = 0; i<12;i++){ //Complete!
            //Make method to give the acts of a month
            for(Activity a : aux.acts){
                if(!this.acts.constains(a)) return false;
            }
            
        }
        return true;
        
    }
}
