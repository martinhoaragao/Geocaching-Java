/**
 * Class that represents the Statistics of a given year.
 * The data structure is an ArrayList which indexes represent the month (from 0 to 12).
 * In each index there is a TreeSet containing all Activities of that month.
 * It has the field year representing this Statistic's year.
 *
 * @version 06/06/2015
 */
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;
import Exceptions.*;

public class Statistic implements Serializable
{

    private ArrayList< TreeSet<Activity>>  stats; // Visited Caches for each month. Comparable by date.
    private int year; //Year of this statistics.

    /**
     * Constructor without arguments
     */
    public Statistic(){
       int i;
       stats = new ArrayList<TreeSet<Activity>>(12);

       for(i=0;i<12;i++){
           stats.add(new TreeSet<Activity>(new ActivityDateComparator()));
       }
       this.year = 2015;
    }

    /**
     * Copy Constructor (preserving encapsulation)
     *
     */
    public Statistic(Statistic stt){ //MUDAR
        this.stats = stt.getStats();
        this.year = stt.getYearStatsMonth();
    }

    /**
     * Method used for the Copy constructor.
     * @return the ArrayList of this Statistic.
     */
    public ArrayList< TreeSet<Activity>> getStats(){
        ArrayList<TreeSet<Activity>> aux = new ArrayList<>();
        int i;
        for( TreeSet<Activity> este : this.stats ){
            aux.add(cloneTreeSet(este));
        }
        return aux;
    }

    /**
     * Method that returns a clones a the set containing all Activities of a month.
     * @param the set I want to clone.
     * @return the set of activities ordered by date.
     *
     */
    public TreeSet<Activity> cloneTreeSet( TreeSet<Activity> ts){
        TreeSet<Activity> novo = new TreeSet<>(new ActivityDateComparator());
        Iterator<Activity> it = ts.iterator();

        while(it.hasNext()){
            novo.add(it.next().clone());
        }

        return novo;
    }

    /**
     * Get the year of this Statistic.
     * @return the integer value of the year.
     */
    public int getYearStatsMonth(){
        return this.year;
    }

    /**
     * Method that sets the year of this Statistic.
     *@param year the year that will be set in this Statistic.
     */
    public void setYearStatsMonth(int year){
        this.year = year;
    }

    /**
     * Adds an Activity in the Statistic.
     * @param a the Activity to add.
     */
    public void addAct(Activity a) throws NotAddedActivityYearIncorrectException{  //returns a boolean if it was inserted or not.
        //Negative testing: if i want to add activity of year 2005 in stats of year 2006, should return false.
        //expecting negative tests to elaborate ways of communication with user.
        //In geocaching if result == false, print the message.
        int year = a.getYear();

        if(year == this.year){
        int month = a.getMonth();
        this.stats.get(month-1).add(a.clone());

        }
        else throw new NotAddedActivityYearIncorrectException("Didn't insert. The year is incorrect!");

    }



    /**
     * Removes an Activity.
     *
     * @param a Activity to remove
     */
    public void removeAct(Activity a){
       int month = a.getMonth();
       this.stats.get(month-1).remove(a);
   }
    /**
     * Gets the Set of Activities by a given month.
     * @param m month of activities present in this statitic.
     * @return the set of activities of the month received as parameter.
     */
    public TreeSet<Activity> getMonthActivities(int m){
        //Array starts from 0 to 11. Receives 1 , returns stats[0].
        TreeSet<Activity> ret = this.stats.get(m-1);
        TreeSet<Activity> novo = new TreeSet<Activity>(new ActivityDateComparator());
        for(Activity ac : ret){
            novo.add(ac.clone());
        }
        return novo;
    }

    /**
    * Method that returns the number of types of caches in a given month.
    *@param month the month of which activities I want to process.
    *@return an array with the information of how many caches of each type exists.
    */
    public int[] getNumberCaches(int month){
        int[] caches = new int[4];
        int micro=0, multi=0, trad=0, mystery=0;

        for(Activity a : this.stats.get(month-1)){
                if (a.getCache() instanceof MicroCache) caches[0]++; //micro++;
                if(a.getCache() instanceof MultiCache) caches[1]++; //multi++;
                if(a.getCache() instanceof TraditionalCache) caches[2]++; //trad++;
                if(a.getCache() instanceof MysteryCache) caches[3]++;//mystery++;
        }
        return caches;
    }

    /**
     * 
     * Get average of number of caches found per day by type during the current year
     * @return avg, average of caches found per day this year
     */
    public double[] averageActPerDay () {
        int[] numberCaches;
        double[] avg = new double[4];
        int numberMonths = stats.size();

        /* Reset Array */
        for (int i = 0; i < 4; i++)
            avg[i] = 0.0;

        /* Go through each month */
        for(int i = 0; i < numberMonths; i++) {
            numberCaches = this.getNumberCaches(i);

            /* Go through each cache type */
            for(int cacheType = 0; i < 4; cacheType++) {
                avg[cacheType] += numberCaches[cacheType];
            }
        }

        /* Do the average */
        for (int i = 0; i < 4; i++)
            avg[i] /= numberMonths * 30.45;

        return avg;
    }

    /**
    *   Method that returns a String ready to be printed.
    *   Shows information about how many types of caches were found in a given month.
    *   @param month the month I want to process.
    *
    */
    public String printTypesCaches(int month){
        int[] caches = getNumberCaches(month);
        int rightLimit = 20; int micro = caches[0], multi = caches[1], trad = caches[2], mystery = caches[3];
        int index; int max_four = micro;
        if(multi>max_four) max_four = multi; if(trad>max_four)max_four=trad; if(mystery>max_four)max_four=mystery;
        StringBuilder sb = new StringBuilder();
        //1st line is for caches[0] which are the MicroCaches
        //2nd line is for MultiCaches
        //3rd line is for TraditionalCaches
        //4th line is for MysteryCaches
        sb.append("\n||||||||||||"); for(index=0;index<max_four;index++)sb.append("||");sb.append("|");
        sb.append("\n| MICROcs:  ");
        for(index = 0; index < rightLimit && index < micro ; index++)
            sb.append("# ");
        for(index=index; index<rightLimit && index < max_four; index++)
            sb.append("  ");
            sb.append("|");

        sb.append("\n| MULTIcs:  ");
        for(index = 0; index < rightLimit && index < multi ; index++)
            sb.append("# ");
        for(index=index; index<rightLimit && index < max_four; index++)
            sb.append("  ");
            sb.append("|");
        sb.append("\n| TRADIcs:  ");
        for(index = 0; index < rightLimit && index < trad ; index++)
            sb.append("# ");
        for(index=index; index<rightLimit && index < max_four; index++)
            sb.append("  ");
            sb.append("|");
        sb.append("\n| MYSTERY:  ");
        for(index = 0; index < rightLimit && index < mystery ; index++)
           sb.append("# ");
        for(index=index; index<rightLimit && index < max_four; index++)
            sb.append("  ");
           sb.append("|");

         sb.append("\n||||||||||||"); for(index=0;index<max_four;index++)sb.append("||");sb.append("|");

        return sb.toString();

    }


    /**
     * Returns an auxiliary array with the number of all type caches that exists in a year.
     * @return the array which contains the information of number of caches in the indexes.
     */
    public int[] getNumberCaches(){
        int[] caches = new int[4];
        int micro=0, multi=0, trad=0, mystery=0,i;
         for(i=0;i<12;i++){
            for(Activity a : this.stats.get(i)){
                if (a.getCache() instanceof MicroCache) caches[0]++; //micro++;
                if(a.getCache() instanceof MultiCache) caches[1]++; //multi++;
                if(a.getCache() instanceof TraditionalCache) caches[2]++; //trad++;
                if(a.getCache() instanceof MysteryCache) caches[3]++;//mystery++;

            }
        }
        return caches;
    }

    /**
     * Method that returns information related to caches number of a year.
     * @return String with information of how many caches of different type user has.
     */
    public String getinfoNCaches(){
        int[] caches = getNumberCaches();
        StringBuilder sb = new StringBuilder();

        sb.append("Micro :"); sb.append(Integer.toString(caches[0]) + ".");
        sb.append("\n");
        sb.append("Multi :"); sb.append(Integer.toString(caches[1])+ ".");
        sb.append("\n");
        sb.append("Tradicional :"); sb.append(Integer.toString(caches[2])+ ".");
        sb.append("\n");
        sb.append("Mystery :"); sb.append(Integer.toString(caches[3])+ ".");
        sb.append("\n");
        System.out.println(sb.toString());
        return sb.toString();
    }

   /**
    * Returns the current month.
    * @return current month.
    */
   public int getCMonth(){
    GregorianCalendar gc = new GregorianCalendar();
        String monthh = String.valueOf(gc.get(GregorianCalendar.MONTH));
        return Integer.parseInt(monthh)+1;
    }

    /**
     * Finds a cache with a given id.
     * @return Cache that I want to look for.
     */
    public Cache getCacheid(double id){
        int i;
        for(i=0;i<12;i++){
            for(Activity a : this.stats.get(i)){
                if(a.getCache().getId() == id)
                return a.getCache();
            }
        }
        return null;
    }

    /**
     * Removes a Cache given the id.
     */
    public void removeCache(double id){
        int i;
        for(i=0;i<12;i++){
            for(Activity a : this.stats.get(i)){
                if(a.getCache().getId() == id)
                this.stats.get(i).remove(id);
            }
        }
    }

    /**
     * Method that sums all points of Statistic related to a year.
     * Used as auxiliary method in the StatisticYear Class.
     * @return points: Total of points of this User's Statistic.
     */
    public int getSumPoints(){
        int i,r=0;
        for(i=0;i<12;i++){
            r+=getSumPoints(i+1);
        }
        return r;
    }

    /**
     * Method that sums all points of a given month.
     * @param month the month I want to process.
     * @return Sum of points.
     */
    public int getSumPoints(int month){
        int sum=0;
        for(Activity a : this.stats.get(month-1)){
            sum+=a.getPoints();
        }
        return sum;
    }

    /**
     * Method that sums all kilometers of a given month.
     * @param month the month I want to process.
     * @return Total kms of a month.
     */
    public double getSumKms(int month){
        double sum=0;
        for(Activity a : this.stats.get(month-1)){
            sum+=a.getKms();
        }
        return sum;
    }

    /**
     * Method that sums all kms.
     * Used as auxiliary method in the StatisticYear Class.
     * @return int kms : Total of kms of this User's Statistic.
     */
    public double getSumKms(){
        int i; double r=0;
        for(i=0;i<12;i++){
            r+=getSumKms(i+1);
        }
        return r;
    }

    /**
     * Method that sums all Caches.
     * @return int Total of caches of this User's Statistic.
     */
    public int getTotalCaches(){
        int i,r=0;
        for(i=0;i<12;i++){
            r+=getTotalCaches(i+1);
        }
        return r;
    }

    /**
     * Method that sums all activities / caches of a given month.
     */
    public int getTotalCaches(int month){
        int sum=0;
        for(Activity a : this.stats.get(month-1)){
            sum++;
        }
        return sum;
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
        int i;
        if (this == sa) return true;
        if(sa.getClass() != this.getClass()) return false;

        Statistic a = (Statistic) sa;
        for(i=0;i<12;i++){
            if(a.stats.get(i).size() != this.stats.get(i).size()) return false;
            for(Activity ac : a.stats.get(i)){
                if(!this.stats.get(i).contains(ac)) return false;
            }
        }
        return true;
    }

    /**
     * Convert the info of this Statistic into a string
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int i;
        for(i=0;i<12;i++){
            sb.append("Month: " + (i+1));
            sb.append(" #Caches: " + this.getTotalCaches(i+1) + ".");
            sb.append("\n");
            sb.append(" #Points: " + this.getSumPoints(i+1)+ ".");
            sb.append("\n");
            sb.append(" #Kms travelled: " + this.getSumKms(i+1) + ".");
            sb.append("\n");


        }
        sb.append(this.getinfoNCaches());

        return sb.toString();
    }
}
