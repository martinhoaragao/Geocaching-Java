/**
 * Class to represent a user with some personal information, the user activities,
 * statistics and friends
 * 
 * @version 08/05/2015
 */
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String mail;              // User mail
    private String pass;              // User Password
    private String name;              // User name
    private String gender;            // User gender
    private Address address;          // User Address
    private GregorianCalendar bdate;  // User birthdate
    private int points;               // User points

    private TreeMap<Integer, Activity> activities;  // User activities
    private Statistic statistics;                   // User statistics
    private ArrayList<User> friends;                // User friends

    /**
     * Constructor without arguments
     */
    public User () {
        this.mail = ""; this.pass = ""; this.name = "";
        this.gender = "";
        this.address = new Address("New York","USA");
        this.bdate = new GregorianCalendar();
        this.points = 0;
        this.activities = new TreeMap<Integer, Activity>();
        this.statistics = new Statistic();
        this.friends = new ArrayList<User>();
    }

    /**
     * Construtor with arguments
     * @arg mail User e-mail
     * @arg pass User password
     * @arg name User name
     */
    public User (String mail, String pass, String name) {
        this.mail = mail;
        this.name = name;

        // Encrypt password
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < byteData.length; i++)
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

            this.pass = sb.toString();
        } catch (NoSuchAlgorithmException e){
            this.pass = null;
        }
    }

    // Getters

    /**
     * @return User mail
     */
    public String getMail () {
        return this.mail;
    }

    /**
     * @return User name
     */
    public String getName () {
        return this.name;
    }
    
    /**
     * @return User password
     */
    public String getPass () {
        return this.pass;
    }
}
