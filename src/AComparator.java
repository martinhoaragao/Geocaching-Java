import java.util.Comparator;
import java.io.Serializable;

/**	
*	This comparator compares two activities depending on the date.
*/
public class AComparator implements Comparator<Activity>, Serializable{
        public int compare(Activity a, Activity b){
            if(a.equals(b)) return 0;
            int m = (a.getDate().compareTo(b.getDate()));
            if(m==0) return -1;
            else return m;
            //if(num < 0) return -1;
            //if(num >=0) return 1;
        }
    }