import java.util.ArrayList;
/**
 * This class was created to test the Meteorology and its probability.
 * 
 * @author Jp
 * @date 30/05/2015
 */
public class testMeteoProbability
{
    /**
    *   This will print the medium value of 10 temperatures created in a Meteorology to test if values are correct.
    */
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
