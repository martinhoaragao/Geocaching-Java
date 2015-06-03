/**
 * Class to represent Coordinates with a given latitude and longitude.
 *
 * @version 11/05/2015
 */

import java.io.Serializable;

public class Coordinates implements Comparable<Coordinates>, Serializable {
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

    /**
     * Method that gets the Distance from the user's location and the cache's location
     * @return double kms travelled between these two points
     * @param Coordinates localuser
     */
    public double getCoordinatesDist(Coordinates localuser )
    {
       double kms = 0; //This always return in kms form.
       //Coordinates are represented in meters form, so the result will be divided by 1000.

       if(this.equals(localuser)) return 0; //Same place

       double x1, y1, x2, y2;
       x1 = localuser.getLat(); y1 = localuser.getLon();
       x2 = this.getLat(); y2 = this.getLon();
       return getDistanceFromLatLonInKm(x1,y1,x2,y2);

    }

    /** Auxiliary function that given 2 latitutes and 2 longitudes, calculares the distance in kms */
    private  double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2,double lon2)
    {
            double R = 6371; // Radius of the earth in km
            double dLat = deg2rad(lat2-lat1);  // deg2rad below
            double dLon = deg2rad(lon2-lon1);
            double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *  Math.sin(dLon/2) * Math.sin(dLon/2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double d = R * c; // Distance in km
            return d;
      }
      
   /** Auxiliary function to convert degrees to radians*/
    private double deg2rad(double deg) {
        return deg * (Math.PI/180);
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

    /* Method only created to be able to use the TreeMap<Coordinates, Double> in CacheBase */
    @Override
    /**
     * Method that compares Coordinates
     */
    public int compareTo (Coordinates coords) {
        if ((latitude == coords.getLat()) && (longitude == coords.getLon()))
            return 0;
        else if (latitude < coords.getLat())
            return -1;
        else return 1;
    }
}
