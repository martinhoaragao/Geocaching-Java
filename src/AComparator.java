import java.util.Comparator;
import java.io.Serializable;

public class AComparator implements Comparator<Activity>, Serializable{
        public int compare(Activity a, Activity b){
            int m = (a.getDate().compareTo(b.getDate()));
            if(m==0) return -1;
            else return m;
            //if(num < 0) return -1;
            //if(num >=0) return 1;
        }
    }