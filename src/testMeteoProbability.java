import java.util.ArrayList;
/**
 * Write a description of class testMeteoProbability here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class testMeteoProbability
{
    
    public static void main(String args[]){
        int i,j;
        int media = 0;
        int soma = 0;
        ArrayList<Meteo> arrayl = new ArrayList<Meteo>();
        int temp[] = new int[30];
        
        for(i=0; i< 10; i++){
            Meteo nova = new Meteo();
            arrayl.add(nova);
        }
        
        for( j=0; j<arrayl.size(); j++){
            temp[j] = arrayl.get(j).getTemp();
        }
        temp[j] = -999;
        
        for(j=0;j<arrayl.size() && temp[j] != -999; j++){
            soma+=temp[j];
        }
        media = soma/(arrayl.size());
        System.out.println("The medium value for temperature is " + media);
        
        
    }
}
