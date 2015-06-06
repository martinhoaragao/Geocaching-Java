import java.util.Comparator;
import java.io.Serializable;

/**
* Comparator that compares two emails given in String form.
*/

public class MailComparator implements Comparator<String>, Serializable {
    
	/**
	* Compares two emails.
	*/
    public int compare (String mail1, String mail2) {
        return mail1.compareTo(mail2);
    }
}
