/**
 * Class that represents an Activity, which is the finding of a cache by a User.
 *
 * @version 11/05/2015
 */

import java.util.GregorianCalendar;

public class Activity {
    private GregorianCalendar date; // Acitivty date
    private Cache cache;            // Cache found on this activity
    private double kms;             // Quilometers covered

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
        this.kms = 0;
    }

    /**
     * Constructor with arguments
     * @arg date Date in which the cache was found
     * @arg cache The cache that was found
     * @arg kms Kilometers covered to find the cache
     */
    public Activity (GregorianCalendar date, Cache cache, double kms) {
        this.date = (GregorianCalendar) date.clone();
        this.cache = cache;
        this.kms = kms;
    }
    
    /**
     * Constructs an Activity using another Activity as reference
     * @arg act Activity to use as reference
     */
    public Activity (Activity act) {
        this.date = act.getDate();
        this.cache = act.getCache();
        this.kms = act.getKms();
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
        sb.append(this.kms + " kilometers covered!");
        sb.append("Cache localization: " + latitude + "," + longitude + "\n");
        return sb.toString();
    }

    /**
     * Compare this Activty to another to check if they are equal
     * @arg act Activity to use for comparison
     */
    public boolean equals (Object act) {
        if (this == act) return true;

        if ((act == null) || (act.getClass() != this.getClass())) return false;

        Activity aux = (Activity) act;
        boolean comp = (this.kms == aux.getKms());
        comp = comp && (this.cache.equals(aux.getCache()));
        // Test this because of GregorianCalendar equals
        comp = comp && (this.date.equals(aux.getDate()));
        return comp;
    }

    /**
     * Create a clone of this object
     */
    public Activity clone () {
        return new Activity(date, cache, kms);
    }
}