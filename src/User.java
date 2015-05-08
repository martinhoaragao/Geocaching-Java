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
        this.pass = encryptPass(pass);

        if (this.pass.equals(pass))
            throw new IllegalStateException("Try another password");
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
     * @return User encrypted password
     */
    public String getPass () {
        return this.pass;
    }

    /**
     * @return User B-date
     */
    public String getBdate(){
        return this.bdate.toString();
    }

    //Setters
    /**
     * Chanches the User name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Change the gender
     */
    public void setGender(String gender){
        this.gender = gender;
    }
    
    /**
     * Set the password for the first time
     */
    public void setPass(String passw){
        this.pass = encryptPass(passw);
    }
    
    /**
     * Change the adress
     */
    public void setAddress (String city, String country){
        Address r = new Address(city, country);

        this.address = r;
    }

    /**
     * Change the user birthdate
     * @arg date Date formated as 'DD/MM/YY'
     */
    public void setBDate(String date){ 
        //"03/05/1994"
        String[] parts = date.split("/");

        int d = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);

        this.bdate = new GregorianCalendar(y,m,d);
    }

    /**
     * Confirm if the given password is equal to the one stored
     * @arg pass
     */
    public boolean confirmPass (String pass) {
        if (this.getPass().equals(encryptPass(pass))) return true;
        else return false;
    }
    
    /**
     * Function to encrypt password when creating user
     * @arg pass Password to be encrypted
     */
    private String encryptPass (String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());

            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < byteData.length; i++)
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

            return sb.toString();
        } catch (NoSuchAlgorithmException e){   // Unable to ecnrypt password
            return pass;
        }
    }
}
