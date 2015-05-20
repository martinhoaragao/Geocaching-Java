import java.util.Comparator;
import java.io.Serializable;
import java.util.GregorianCalendar;

public class CacheDateComparator implements Comparator<Activity>, Serializable {

  /** Compare two activities dates */
  public int compare (Activity a, Activity b) {
    GregorianCalendar adate, bdate;

    adate = a.getDate();
    bdate = b.getDate();

    if (adate.get(GregorianCalendar.YEAR) < bdate.get(GregorianCalendar.YEAR))
      return -1;
    else if (adate.get(GregorianCalendar.YEAR) > bdate.get(GregorianCalendar.YEAR))
      return 1;

    if (adate.get(GregorianCalendar.MONTH) < bdate.get(GregorianCalendar.MONTH))
      return -1;
    if (adate.get(GregorianCalendar.MONTH) > bdate.get(GregorianCalendar.MONTH))
      return 1;

    if (adate.get(GregorianCalendar.DAY_OF_MONTH) > bdate.get(GregorianCalendar.DAY_OF_MONTH))
      return -1;
    if (adate.get(GregorianCalendar.DAY_OF_MONTH) < bdate.get(GregorianCalendar.DAY_OF_MONTH))
      return 1;

    return 0;
  }
}
