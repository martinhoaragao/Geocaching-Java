import java.util.GregorianCalendar;

/**
 * Class that simply creates a Date (Useful for Activity constructor completition).
 * Note: name date already been used in class Activity.
 * 
 * @version 11/05/2015
 */

public class GDate{

    private GregorianCalendar gdate;
    
    public GDate(int day, int month, int year){
        gdate = new GregorianCalendar(year, month, day);
        
        //public GregorianCalendar(int year,int month, int dayOfMonth)
    }
    
    public GregorianCalendar getgDate(){
        return this.gdate;
    }
    
    public void setgDate(int day, int month, int year){
        this.gdate = new GregorianCalendar(year,month,day);
    }
    
    
}