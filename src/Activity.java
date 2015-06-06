/**
 * Class that represents an Activity, which is the finding of a cache by a User.
 *
 * @version 04/06/2015
 */

// DONE

import java.util.GregorianCalendar;
import java.io.Serializable;
import java.io.IOException;

public class Activity implements Serializable {
    private GregorianCalendar date; // Activity date
    private Cache cache;            // Cache found on this activity
    private Double kms;             // Kilometers covered
    private int points;             // Each activity gives points to the User.
    private Meteo meteo;            // Each activity has a random meteorology.

    private static int limit_points = 100;
    private static int limit_points_cache = 50;
    private static int limit_points_kms = 30;
    private static int limit_points_meteo = 20;

    /**
     * Constructor without paramuments
     */
    public Activity () {
        this.date = new GregorianCalendar();
        this.cache = null;
        this.kms = 0.0;
        this.points = 0;
        this.meteo = new Meteo();
    }

    /**
     * Constructor with paramuments
     * @param date Date in which the cache was found
     * @param cache The cache that was found
     * @param kms Kilometers covered to find the cache
     * @param points Points gained in this Activity
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
     * @param act Activity to use as reference
     */
    public Activity (Activity act) {
        this.date = act.getDate();
        this.cache = act.getCache();
        this.kms = act.getKms();
        this.points = act.points;
        this.meteo = act.meteo.clone();
    }

    // Getters
    /**
     * Method that returns the date of an Activity.
     * @return Date of the activity in GregorianCalendar object form.
     */
    public GregorianCalendar getDate () {
        return (GregorianCalendar) this.date.clone();
    }

    /**
     * Method that returns the month of an Activity as an int.
     * @return the month of the activity
     */
    public int getMonth(){
        return this.date.get(GregorianCalendar.MONTH);
    }

    /**
     * Method that returns the year of an Activity as an int.
     * @return the year of the activity
     */
    public int getYear(){
        return this.date.get(GregorianCalendar.YEAR);
    }

    /**
     * Method that returns the Cache of an Activity.
     * @return this.cache Cache found in this activity
     */
    public Cache getCache () {
        return this.cache;
    }

    /**
     * Method that returns the kms traveled in an Activity.
     * @return this.kms Kilometers covered in this activity
     */
    public double getKms () {
        return this.kms;
    }

    /**
     * Method that returns the points of an Activity.
     * @return this.points
     */
    public int getPoints () {
        return this.points;
    }

    /**
     *   Method that returns the Meteorology of an Activity
     * @returns this.meteo cloned, as form of Meteo object.
     */
    public Meteo getMeteo(){
        return this.meteo.clone();
    }

    // Setters

    /**
     * Change date of the activity
     * @param day day which the Activity was found
     * @param month which the Activity was found
     * @param year which the Activity was found
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
     * @param cache New cache to replace
     */
    public void setCache (Cache cache) {
        this.cache = cache;
    }

    /**
     * Change the number of kilometers covered in this activity
     * @param kms New value for kilometers
     */
    public void setKms (double kms) {
        this.kms = kms;
    }

    public void setPoints (int points){
        this.points = points;
    }

    /**
     *  Method that sets the Meteo to an Activity
     */
    public void setMeteo(Meteo meteo){
        this.meteo = meteo.clone();
    }

    /**
     * Auxiliary function that calculates the points for the kms with limit of 30.
     * If the user has travelled 1000 meters, it returns 1point.
     * 30km or more returns maximum points which are 30 points.
     * Otherwise gives the user the points equivalent to the kms travelled.
     *
     * @return points for kms
     */
    private int calcPointsKms(){
        int points = (int) Math.round(this.getKms()) ;
        if(points >= limit_points_kms) points = limit_points_kms;

        return points;
    }

    /**
     * Auxiliary function to calculate the points for the cache given the type.
     * limit_points_cache = 50.
     *
     * @return points for the cache.
     */
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

    /* auxiliary functions to calculate points for weather */
    public int calcPointsW(){
        int p=0;
        int w = this.meteo.getWeather();
        switch (w){

            case 0: p+=9 ;
            break;

            case 1:  p+=10;
            break;

            case 2:  p+=4;
            break;

            case 3:  p+=6;
            break;

            case 4:  p+=8;
            break;

            case 5:  p+=7;
            break;

            case 6:  p+=10;
            break;

            default:
            break;
            /*
            Rainy 0
            Stormy 1
            Sunny 2
            Cloudy 3
            Windy 4
            Foggy 5
            Hail 6
             */
        }
        return p;
    }

    /* auxiliary functions to calculate points for temperature */
    public int calcPointsT(){
        int p =0;
        int t = this.meteo.getTemp();

        if(t <= -5) p+=10;
        else if(t>=30) p+=10;
        else if(t> -5 && t < 0) p+=8;
        else if(t>16 && t<25 ) p+=4;
        else if(t>=0 && t <=15) p+=6;
        else p+=7;
        //25->30
        return p;
    }

    public int calcPointsMeteo(){
        int p=0;
        p+=calcPointsT();
        p+=calcPointsW();
        return p;
    }

    /**
     * Method that returns and sets all points from Cache, Kms travelled and Meteo.
     */
    public void updatePoints(){
        int points = this.calcPointsCache() + this.calcPointsKms() + this.calcPointsMeteo();
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
        month = String.valueOf(date.get(GregorianCalendar.MONTH));
        year = String.valueOf(date.get(GregorianCalendar.YEAR));
        latitude = this.cache.getCoords().getLat();
        longitude = this.cache.getCoords().getLon();

        sb.append("Cache found on " + day + "/" + month + "/" + year + "\n");
        sb.append("Meteo information: " +this.meteo.toString()+ "\n");
        sb.append("Cache localization: (" + latitude + "," + longitude +")" +".\n");
        sb.append("(Approx.)"+Math.round(this.kms * 100.0)/100.0 + " kilometers covered! ");
        sb.append(" Total points accumulated: " + this.points+ ".\n");
        return sb.toString();
    }

    /**
     * Compare this Activity to another to check if they are equal
     * @param act Object to use for comparison
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
        return new Activity(this);
    }
}
