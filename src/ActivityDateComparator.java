/**
 * Compare two Activity dates
 */

import java.util.Comparator;
import java.io.Serializable;
import java.util.GregorianCalendar;

public class ActivityDateComparator implements Comparator<Activity>, Serializable {

  /** Compare two activities dates */
  public int compare (Activity a, Activity b) {
    if (a.equals(b)) return 0;                  /* Check if activities are equal */

    int m = a.getDate().compareTo(b.getDate());
    if (m == 0) return -1;
    else return m;
  }
}
