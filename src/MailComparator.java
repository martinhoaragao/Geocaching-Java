import java.util.Comparator;
import java.io.Serializable;

public class MailComparator implements Comparator<String>, Serializable {
    public int compare (String mail1, String mail2) {
        return mail1.compareTo(mail2);
    }
}
