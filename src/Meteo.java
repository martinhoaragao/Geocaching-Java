import java.util.Random;
import java.io.Serializable;


/**
 * This class represents the Meteorology to be used in Activity.
 * This will calculate points depending on the temperature, time_state, etc...
 * And sets these fields in the constructors arbitrarily.
 */

public class Meteo implements Serializable {
    private int temperature;
    private int weather;
    /*
        Rainy 0
        Stormy 1
        Sunny 2
        Cloudy 3
        Windy 4
        Foggy 5
        Hail 6
    */

    /**
    * Empty Constructor
    */    
    public Meteo(){
        Random temperaturer = new Random();
        int Low = -10;
        int High = 40;
        int R;
        double probability = new Random().nextDouble();
        /*
         * for instance
         * 5% of time it prints between -10 and 0
         * 10% of time it prints between 35 and 40
         * 15% of time it prints between 30 and 40
         * 30% of time it prints between 21 and 25
         *
         *
         */

        if( (probability) <=0.03 ){

            Low = -10; High = 0;
         R = temperaturer.nextInt(High-Low) + Low;
        }
        else if((probability) <=0.08) {
            Low = 1; High = 10;
            R = temperaturer.nextInt(High-Low) + Low;
        }
        else if((probability ) <=0.15) {

            Low = 11; High = 16;
            R = temperaturer.nextInt(High-Low) + Low;
        }
        else if((probability) <=0.20) {

            Low = 31; High = 40;
            R = temperaturer.nextInt(High-Low) + Low;
        }
         else if((probability) <=0.35) {
            Low = 17; High = 19;
            R = temperaturer.nextInt(High-Low) + Low;
        }
        else if((probability = new Random().nextDouble() ) <=0.5) {
            Low = 25; High = 30;
            R = temperaturer.nextInt(High-Low) + Low;
        }
        else{
            //Last probability of 50% chance.
            Low = 20; High = 24;
            R = temperaturer.nextInt(High-Low) + Low;
        }
        this.temperature = R;
        Random w = new Random();
        int W = w.nextInt(7); // 0,1,2,3,4,5,6   7 exclusive
        this.weather = W;
    }

    /**
    * Constructor with parameters.
    * @param temperature the temperature of this meteorology
    * @param weather the weather I want to set for this meteorology
    */
    public Meteo(int temperature, int weather){
        this.temperature = temperature;
        this.weather = weather;
    }

    /**
    *
    * Copy Constructor
    * @param a Meteorology to be copied.
    */
    public Meteo(Meteo a){
        this.temperature = a.getTemp();
        this.weather = a.getWeather();
    }

    

    //Gets e Sets

    /**
    * Gets the weather
    * @return this.weather
    */
    public int getWeather(){
        return this.weather;
    }


    /** 
    *  Gets the Temperature
    * @return this.temperature
    */
    public int getTemp(){
        return this.temperature;
    }

    /**
    * Method that sets a new temperature
    * @param temperature new temperature to be set.
    */
    public void setTemp(int temperature){
        this.temperature = temperature;
    }

    /**
    * Method that sets a new Weather
    * @param weather the new weather to be set.
    */
    public void setWeather(int weather){
        this.weather = weather;
    }

    /** Auxiliary function to get String of weather 
    * @return the String with the information of the weather.
    */
    /*Rainy 0
        Stormy 1
        Sunny 2
        Cloudy 3
        Windy 4
        Foggy 5
        Hail 6*/
    private String getStringW(){
        int w = this.weather;
        String info = new String();
        switch (w){

            case 0: info =new String( "Rainy");
            break;

            case 1:  info = new String("Stormy");
            break;

            case 2: info =new  String( "Sunny");
            break;

            case 3: info =new  String( "Cloudy");
            break;

            case 4:info = new String( "Windy");
            break;

            case 5:info =new  String("Foggy");
            break;

            case 6: info = new String("Hail");
            break;

            default:
            break;
        }

        return info;
    }

    /* toString, equals, clone */

    /**
    * Converts the information of this Meteo in a String
    *@return String information of this meteorology
    */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(" |***| Temperature: ");
        sb.append(this.getTemp() + "º | ");
        sb.append(" Weather conditions: ");
        sb.append(this.getStringW() + " |***| ");
        return sb.toString();
    }

    /**
    * Tests if two Meteorologies are equal.
    */
    public boolean equals(Object o){
        if(this == o) return true;
        if(this.getClass() != o.getClass()) return false;
        Meteo a = (Meteo) o;
        return a.getTemp() == this.getTemp() && a.getWeather() == this.getWeather();
    }

    /**
    * Clones this meteorology.
    */
    public Meteo clone(){
        return new Meteo(this);
    }
}
