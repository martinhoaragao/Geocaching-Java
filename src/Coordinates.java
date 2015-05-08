/**
 * Class to represent Coordinates with a given latitude and longitude
 */

public class Coordinates {
    private double longitude;
    private double latitude;
    
    /**
     * Constructor with arguments
     * @arg lon Longitude
     * @arg lat Latitude
     */
    public Coordinates (double lon, double lat) {
        this.longitude = lon;
        this.latitude = lat;
    }
}