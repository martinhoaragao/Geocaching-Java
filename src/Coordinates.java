/**
 * Class to represent Coordinates with a given latitude and longitude.
 *
 * @version 06/05/2015
 */

public class Coordinates {
    private double longitude;
    private double latitude;

    /**
     * Constructor with arguments
     * @arg lat Latitude
     * @arg lon Longitude
     */
    public Coordinates (double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    // Getters

    /**
     * @return Coordinates latitude
     */
    public double getLat () {
        return this.latitude;
    }

    /**
     * @return Coordinates longitude
     */
    public double getLon () {
        return this.longitude;
    }

    // toString, equals and clone

    /**
     * Creates a clone of this object
     */
    public Coordinates clone () {
        return new Coordinates(this.latitude, this.longitude);
    }
}