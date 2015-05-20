/**
 * Class that represents the Statistics of a given User, in a given month
 * represented by an integer from 0 to 11.
 * Name has to be different from statistics (already used in class User) so I called it "stats".
 *
 * @version 11/05/2015
 */

import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;

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
     * Methods
     */

    /**
     * Sum points of this statistic
     * @param points Total points of this User Statistic
     */
    public int getSumPoints(){
        int r=0;
        for(int i=0;i<12;i++){ //In every month of this TreeMap

            TreeSet<Activity> actaux = this.stats.get(i);
            //Act of this month
            for(Activity a : actaux){
                r+=a.getpoints();
            }


        }
        return r;
    }

    /**
     * Add an Activity in the Statistic.
     * (The month is in the information of the Activity, so no need
     * to receive the extra param month for this method).
     */
    public void addAct(Activity a){
        int monthh = a.getMonth();
        TreeSet<Activity> actSet = this.stats.get(monthh);
        //Now check is this set is empty or not
        if(actSet == null){

            actSet.add(a);
            this.stats.put(monthh, actSet);
        }
        else{
            actSet.add(a);
            this.stats.put(monthh, actSet);
        }
        //I think there is no need to test at all... right?
    }

    /**
     * Creates a Set of 3 Activities of the same month.
     * [?]
     * (For testing)
     */
    public TreeSet<Activity> creatSetAct(Activity a, Activity b, Activity c){
        int m1, m2, m3; m1 = a.getMonth(); m2 = b.getMonth(); m3 = c.getMonth();
        if(m1 == m2 && m2 == m3){
            TreeSet<Activity> ret = new TreeSet<Activity>();
            ret.add(a); ret.add(b); ret.add(c);
            System.out.println("Sucessfuly added");
            return ret;
        }

        else System.out.println("Those Activities are not of the same month!");

        return null;


    }

    /**
     * [?] Puts a Set of Activities of the same month in the stats.
     */
    public void putActSet(TreeSet<Activity> aa){
        int monthhh = aa.first().getMonth();
        int monthh;

        //Testing the month
        for(Activity a : aa){
            monthh = a.getMonth();
            if(monthh != monthhh){
                System.out.println("The Activities are not of the same month!");
                return;
            }
        }

        this.stats.put(monthhh, aa);
    }

    /**
     * Removes an Activity
     */
    public void removeAct(Activity a){

        int month = a.getMonth();
        this.stats.get(month).remove(a);
        //in Stats, acts of the month, remove the object.

    }

    /**
     * [?] Get the Set of Activities by a given month:
     */
    /**
     * Make method to give the acts of a month
     *
     * @arg m Month of the Activity Set we want.
     * Note: must not return clones. This method is auxiliary to the equals method.
     */
    public TreeSet<Activity> getActMonth(int m){
        TreeSet<Activity> ret = this.stats.get(m-1);
        return ret;
    }

    /**
     * [?] Get a Set of Activities that has a given Cache
     */
    public TreeSet<Activity> getSetCache(Cache m ){
        TreeSet<Activity> ret = new TreeSet<>();
        for(int i=0;i<12;i++){
            for(Activity a : this.stats.get(i)){
                if(a.getCache().equals(m))
                ret.add(a);
            }
        }
        return ret;
    }

   /**
    * Returns the current month
    */
   public int getCMonth(){
    GregorianCalendar gc = new GregorianCalendar();
        String monthh = String.valueOf(gc.get(GregorianCalendar.MONTH));
        return Integer.parseInt(monthh)+1;
    }

    /**
     * Returns a List of the last 10 activities
     */
    public ArrayList<Activity> get10LastA(){
        TreeSet<Activity> am = new TreeSet<>();
        int m = this.getCMonth();
        am = this.getActMonth(m);

        ArrayList<Activity> ret  = new ArrayList<>();
        while(am == null){
            m--;
            am = this.getActMonth(m);
        }
        if(am != null ){
            while(ret.size()!=10){
              for(Activity a : am){
                ret.add(a.clone());
              }

              am = this.getActMonth(m-1);
              while(am == null) am = this.getActMonth(m-1);


            }
        }

        return ret;

    }

    /**
     * Find a cache with a given id
     */
    public Cache getCacheid(double idd){

        for(int i=0;i<12;i++){
            TreeSet<Activity> actaux = this.stats.get(i);
                for(Activity a : actaux){
                    if(a.getCache().getId() == idd) return a.getCache();
                }
        }
        return null;
    }

    /**
     * Removes a Cache given the id
     */
    public void removeCache(double id){
         for(int i=0;i<12;i++){
            TreeSet<Activity> actaux = this.stats.get(i);
                for(Activity a : actaux){
                    if(a.getCache().getId() == id) actaux.remove(a);
                }
        }
    }

    /**
     * Clone,toString and equals
     */

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
        if(aux.stats.size() == 0 && this.stats.size() == 0) return true;
        //If both Map's don't have the same size then they must be different.

        for(int i=0;i<12;i++){

            TreeSet<Activity> actaux = this.stats.get(i);
            //Act of this month
            TreeSet<Activity> actauxaux = aux.stats.get(i);
            //Act of this month of the stats of the parameter.
            if(actaux.size() != actauxaux.size()) return false;

        }
        //Check the size of each TreeSet in the month.



        for(int i = 0; i<12;i++){ //Complete!
            //Make method to give the acts of a month
            //TreeSet<Activity> actaux = this.getActMonth(i+1);
            //Delete the method
            TreeSet<Activity> actaux = this.stats.get(i);
            //get(i) returns the values of the map for the month i;

            TreeSet<Activity> actauxaux = aux.stats.get(i);

            for(Activity a : actauxaux){
                if(!actaux.contains(a)) return false;
                /*Since we tested the size, now we test if the activity is in the other, in te scope of
                a given month, of course.
                */
            }

        }
        return true;

    }

    /**
     * Convert the info of this Statistic into a string
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<12;i++){
            sb.append("Month: " + (i+1));
            TreeSet<Activity> aa = this.stats.get(i);
            for(Activity a : aa){
                sb.append(a.toString());
                sb.append("\n");
            }
            sb.append("\n");

        }


        return sb.toString();
    }
}
