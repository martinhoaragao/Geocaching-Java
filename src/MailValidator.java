import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.Serializable;

public class MailValidator implements Serializable {
    private static Pattern pattern;
    private static Matcher matcher;

    private static final String EMAIL_PATTERN =
    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /** Unparameterized constructor */
    public MailValidator () {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Validate mail with regular expression
     *
     * @param mail Mail to test if valid
     * @return true valid mail, false invalid mail
     */
    public boolean validate (final String mail) {

        matcher = pattern.matcher(mail);
        return matcher.matches();

    }
}