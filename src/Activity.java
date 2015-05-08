/**
 * Class that represents the Activity of a given User.
 * 
 * @version 07/05/2015
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
    public Activity (int year, int month, int day, Cache cache, double kms) {
        // Code for exceptions missing
        
        this.date = new GregorianCalendar(year, month, day);
        this.cache = cache;
        this.kms = kms;
    }
}