/**
 * This class represents the Meteorology to be used in Activity.
 * This will calculate points depending on the temperature, time_state, etc...
 * And sets these fields in the constructors arbitrarily.
 */

import java.util.Random;

public class Meteo{
    
    private int temperature; 
    private int weather;

    private static int limit_points = 20;
    /* 
        Rainy 0
        Stormy 1
        Sunny 2
        Cloudy 3
        Windy 4
        Foggy 5
        Hail 6 
    */

    public Meteo(){
        Random temperaturer = new Random();
        int Low = -10;
        int High = 40;
        int R;
        double probability;
        /* 5% of time it prints between -10 and 0
         * 10% of time it prints between 35 and 40
         * 15% of time it prints between 30 and 40
         * 30% of time it prints between 21 and 25
         * 
         * 
         */
         
        if( (probability = new Random().nextDouble() ) <=0.03 ){
            System.out.println(probability);
            Low = -10; High = 0;
         R = temperaturer.nextInt(High-Low) + Low;
         System.out.println(R);
        }
        else if((probability = new Random().nextDouble() ) <=0.05) {
            System.out.println(probability);
            Low = 1; High = 10;
            R = temperaturer.nextInt(High-Low) + Low;
            System.out.println(R);
        }
        else if((probability = new Random().nextDouble() ) <=0.13) {
            System.out.println(probability);
            Low = 11; High = 19;
            R = temperaturer.nextInt(High-Low) + Low;
            System.out.println(R);
        }
        else if((probability = new Random().nextDouble() ) <=0.17) {
            System.out.println(probability);
            Low = 31; High = 40;
            R = temperaturer.nextInt(High-Low) + Low;
            System.out.println(R);
        }
         else if((probability = new Random().nextDouble() ) <=0.30) {
            System.out.println(probability);
            Low = 20; High = 23;
            R = temperaturer.nextInt(High-Low) + Low;
            System.out.println(R);
        }
        else if((probability = new Random().nextDouble() ) <=0.4) {
            System.out.println(probability);
            Low = 27; High = 30;
            R = temperaturer.nextInt(High-Low) + Low;
            System.out.println(R);
        }
        else{
            //Last probability of 50% chance.
            System.out.println(probability);
            Low = 24; High = 26;
            R = temperaturer.nextInt(High-Low) + Low;
            System.out.println(R);
           
        }
        
        this.temperature = R;

        Random w = new Random();
        int W = w.nextInt(7); // 0,1,2,3,4,5,6   7 exclusive


        this.weather = W;
    }

    public Meteo(int temperature, int weather){
        this.temperature = temperature;
        this.weather = weather;
    }

    public Meteo(Meteo a){
        this.temperature = a.getTemp();
        this.weather = a.getWeather();
    }

    /* auxiliary functions to calculate points for weather */
    public int calcPointsW(){
        int p=0;
        int w = this.getWeather();
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
        int t = this.getTemp();

        if(t <= -5) p+=10;
        else if(t>=30) p+=10;
        else if(t> -5 && t < 0) p+=8;
        else if(t>16 && t<25 ) p+=4;
        else if(t>=0 && t <=15) p+=6;
        else p+=7;
            //25->30

        return p;
    }

    public int calcPoints(){
        int p=0;
        p+=calcPointsT();
        p+=calcPointsW();

        return p;

        
    }

    //Gets e Sets
    public int getWeather(){
        return this.weather;
    }

    public int getTemp(){
        return this.temperature;
    }
    
    public void setTemp(int temperature){
        this.temperature = temperature;
    }
    
    public void setWeather(int weather){
        this.weather = weather;
    }

    /* auxiliary function to get String of weather */
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
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("Temperature: ");
        sb.append(this.getTemp());

        sb.append("Weather conditions: ");
        sb.append(this.getStringW());

        return sb.toString();
    }

    public boolean equals(Object o){
        if(this == o) return true;

        if(this.getClass() != o.getClass()) return false;

        Meteo a = (Meteo) o;
        return a.getTemp() == this.getTemp() && a.getWeather() == this.getWeather();
    }

    public Meteo clone(){
        return new Meteo(this);

    }
}