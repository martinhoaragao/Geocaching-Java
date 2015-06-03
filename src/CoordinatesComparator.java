import java.util.Comparator;
import java.io.Serializable;

public class CoordinatesComparator implements Comparator<String>, Serializable {
    public int compare (Coordinates c1, Coordinates c2) {
      if (c1.equals(c2)) return 0;
      else if (c2.getLat() > c1.getLat()) return 1;
      else return -1;
    }
}
