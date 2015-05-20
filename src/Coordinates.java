/**
 * Class to represent Coordinates with a given latitude and longitude.
 *
 * @version 11/05/2015
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

    /**
     * Empty Constructor
     */
    public Coordinates(){
        this.longitude = 20;
        this.latitude = 20;
    }

    /**
     * Copy Constructor
     */
    public Coordinates(Coordinates a){
        this.longitude = a.longitude;
        this.latitude = a.latitude;
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

    //Setters
    /**
     * Sets the latitude.
     */
    public void setLat(int lat) throws IllegalArgumentException {
        if (lat < 0 || lat > 90)
            throw new IllegalArgumentException("Out of boundary argument");
        this.latitude = lat;
    }

    /**
     * Sets the longitude.
     */
    public void setLon(int lon) throws IllegalArgumentException {
        if (lon < -180 || lon > 180)
            throw new IllegalArgumentException("Out of boundary argument");
        this.longitude = lon;
    }


    // toString, equals and clone

    /**
     * Creates a clone of this object
     */
    public Coordinates clone () {
        //return new Coordinates(this.latitude, this.longitude);
        return new Coordinates(this);
    }

    /**
     * Convert the info of this Coordinates into a string
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Coordinates: ");
        sb.append("(" + latitude + ", " + longitude + ")" );

        return sb.toString();
    }

    /**
     * Test if this Coordinates and another ones are equal.
     *
     * @arg cc Coordinates to test.
     */
    public boolean equals(Object cc){
        if(cc == this) return true;
        if(cc.getClass() != this.getClass()) return false;

        Coordinates a = (Coordinates) cc;
        return ( a.latitude == this.latitude && a.longitude == this.longitude);
    }
}
