/**
 * Class that represents an Activity, which is the finding of a cache by a User.
 *
 * @version 14/05/2015
 */

import java.util.GregorianCalendar;
import java.io.Serializable;
import java.io.IOException;

public class Activity implements Serializable {
    private GregorianCalendar date; // Activity date
    private Cache cache;            // Cache found on this activity
    private Double kms;             // Kilometers covered
    private int points;             // Each activity gives points to the User.
    private Meteo meteo;            //Each activity has a random meteorology.

    //TODO  IF it has var kms and in main asks for kms covered...   its ok right?  only calc distances for "1. Look for caches play game" all right?
    //otherwise will be confusing and it will mess all the code.
    private static int limit_points = 100;
    private static int limit_points_cache = 50;
    private static int limit_points_kms = 30;


    /**
     * Constructor with arguments
     * @arg year Year
     * @arg month Month of the year
     * @arg day Day of the Month
     * @arg cache Cache that was found
     * @arg kms Quilometers covered
     */

    /**
     * Constructor without arguments
     */
    public Activity () {
        this.date = new GregorianCalendar();
        this.cache = null;
        this.kms = 0.0;
        this.points = 0;
        this.meteo = new Meteo();
    }

    /**
     * Constructor with arguments
     * @arg date Date in which the cache was found
     * @arg cache The cache that was found
     * @arg kms Kilometers covered to find the cache
     */
    public Activity (GregorianCalendar date, Cache cache, double kms, int points) {
        this.date = (GregorianCalendar) date.clone();
        this.cache = cache;
        this.kms = kms;
        this.points = points;
        this.meteo = new Meteo();
    }

    /**
     * Constructs an Activity using another Activity as reference
     * @arg act Activity to use as reference
     */
    public Activity (Activity act) {
        this.date = act.getDate();
        this.cache = act.getCache();
        this.kms = act.getKms();
        this.points = act.points;
        this.meteo = act.meteo.clone(); //TODO TEST THIS PART, im not sure...
    }


    // Getters

    /**
     * @return Date of the activity
     */
    public GregorianCalendar getDate () {
        return (GregorianCalendar) this.date.clone();
    }

    /**
     * @return the month of the activity
     */
    public int getMonth(){
        return this.date.get(GregorianCalendar.MONTH);
    }

    /**
     * @return the year of the activity
     */
     public int getYear(){
        return this.date.get(GregorianCalendar.YEAR);
    }

    /**
     * @return Cache found in this activity
     */
    public Cache getCache () {
        return this.cache;
    }

    /**
     * @return Kilometers covered in this activity
     */
    public double getKms () {
        return this.kms;
    }

    public int getPoints () {
        return this.points;
    }

    // Setters

    /**
     * Change date of the activity
     * @arg day
     * @arg month
     * @arg year
     */
    public void setDate (int day, int month, int year) {
        this.date = new GregorianCalendar(year, month, day);
    }

    /** Set the date of the activity
     * @param date GregorianCalendar date */
    public void setDate (GregorianCalendar date) {
        this.date = (GregorianCalendar) date.clone();
    }

    /**
     * Change the cache that was found in the activity
     * @arg cache New cache to replace
     */
    public void setCache (Cache cache) {
        this.cache = cache;
    }

    /**
     * Change the number of kilometers covered in this activity
     * @arg kms New value for kilometers
     */
    public void setKms (double kms) {
        this.kms = kms;
    }

    public void setPoints (int points){
        this.points = points;
    }

    /**
     * Auxiliary function that calculates the points for the kms with limit of 30.
     * If the user has travelled more than 20 kms it returns the total points which are 30.
     * Otherwise gives the user the points equivalent to the kms travelled.
     *
     * @return points for kms
     */
    private int calcPointsKms(){
        int points = (int) Math.round(kms) ;  //TODO CHECK THIS ALSO
        if(kms >= limit_points_kms) points = limit_points_kms;

        return points;
    }

    /**
     * Auxiliary function to calculate the points for the cache given the type.
     * limit_points_cache = 50.
     *
     * @return points for the cache.
     */
    //TODO CHECK THIS ERROR... CANNOT FIND METHOD getPuzzle. but i did it... ont he mysterycache class
    private int calcPointsCache(){
        int points = 0;

        if(cache instanceof MicroCache) points = 10;
        if(cache instanceof MultiCache) points= 30;
        if(cache instanceof TraditionalCache) points = 20;
        if(cache instanceof MysteryCache){
            MysteryCache aux = (MysteryCache) cache;
            Puzzle x = aux.getPuzzle();
            points = x.getValuePoints();
        }
        return points;
    }

    /**
     * Method that returns and sets all points from Cache, Kms travelled and Meteo.
     */
    public void updatePoints(){
        int points = this.calcPointsCache() + this.calcPointsKms() + meteo.calcPoints();
        this.setPoints (points);
    }

    // toString, equals and clone

    /**
     * Convert the info of this activity into a string
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();
        String day, month, year;
        double latitude, longitude;

        day = String.valueOf(date.get(GregorianCalendar.DAY_OF_MONTH));
        month = String.valueOf(date.get(GregorianCalendar.MONTH)+1);
        year = String.valueOf(date.get(GregorianCalendar.YEAR));
        latitude = this.cache.getCoords().getLat();
        longitude = this.cache.getCoords().getLon();
        //TODO test 
        sb.append("Cache found on " + day + "/" + month + "/" + year + "\n");
        sb.append("Meteo information: " +this.meteo.toString() );
        sb.append("Cache localization: (" + latitude + "," + longitude +")" +"\n");
        sb.append(this.kms + " kilometers covered!");
        sb.append(" Total points accumulated: " + this.points);
        return sb.toString();
    }

    /**
     * Compare this Activity to another to check if they are equal
     * @arg act Object to use for comparison
     */
    public boolean equals (Object act) {
        if (this == act) return true;

        if ((act == null) || (act.getClass() != this.getClass())) return false;

        Activity aux = (Activity) act;
        boolean comp = (this.kms == aux.getKms());
        comp = comp && (this.cache.equals(aux.getCache()));

        comp = comp && (this.getPoints() == aux.getPoints());
        comp = comp && (this.getDate().compareTo(aux.getDate())==0);
        return comp;
    }

    /**
     * Create a clone of this object
     */
    public Activity clone () {
        return new Activity(date, cache, kms, points);
    }
}
