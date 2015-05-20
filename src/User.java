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
    private boolean gender;           // User gender
    private Address address;          // User Address
    private GregorianCalendar bdate;  // User birthdate
    private int points;               // User points
    private int id;                   // User Id

    private TreeMap<Integer, Activity> activities;  // User activities
    private Statistic statistics;                   // User statistics
    private ReportedCacheBase reportedmine;        // My reports
    private ArrayList<Integer> friends;             // User friends

    /**
     * Constructor without arguments
     */
    public User () {
        this.mail = ""; this.pass = ""; this.name = "";
        this.gender = true;
        this.address = new Address("New York","USA");
        this.bdate = new GregorianCalendar();
        this.points = 0; this.id = 0;
        this.activities = new TreeMap<Integer, Activity>();
        this.statistics = new Statistic();
        this.friends = new ArrayList<Integer>();
    }

    /**
     * Construtor with arguments
     * @param mail  User e-mail
     * @param pass  User password
     * @param name  User name
     * @param id    User id
     * @param bdate User birthdate
     */
    public User (String mail, String pass, String name, int id, GregorianCalendar bdate) throws NullPointerException, IllegalStateException {
        this();

        if (mail == null)
            throw new NullPointerException("mail can't be null!");
        if (mail.trim() == "")
            throw new IllegalStateException("mail can't be empty!");
        this.mail = mail;

        if (pass == null)
            throw new NullPointerException("pass can't be null!");
        if (pass.trim() == "")
            throw new IllegalStateException("pass can't be empty!");
        this.pass = encryptPass(pass);

        if (name == null)
            throw new NullPointerException("name can't be null!");
        if (name.trim() == "")
            throw new IllegalStateException("name can't be empty!");
        this.name = name;

        this.id = id;
        this.address = new Address();
        this.bdate = (GregorianCalendar) bdate.clone();
    }

    /**
     * Construct a new User with the same info as a given User
     * @param user User from which the information will be fetched
     */
    public User (User user) throws NullPointerException {
        if (user == null)
            throw new NullPointerException("user can't be null!");

        this.mail = user.getMail(); this.pass = user.getPass();
        this.name = user.getName(); this.address = user.getAddress();
        this.bdate = user.getBDate(); this.points = user.getPoints();
        this.activities = user.getActivities();
        this.statistics = user.getStatistics();
        this.friends = user.getFriends();
        this.id = user.getId();
    }


    /**
     * Removes a Cache from this User: from Statistics and from the TreeMap, given an id
     *
     * @arg id Identifier of the Cache
     *
     * private TreeMap<Integer, Activity> activities;  // User activities  ? whats that integer?
     */
    public void removeCache (String id) throws NullPointerException, IllegalStateException {
        if (id == null)
            throw new NullPointerException("id can't be null!");
        if (id.trim() == "")
            throw new IllegalStateException("id can't be empty");

        this.statistics.removeCache(id); //Removes from Statistics.

        for(Activity a : this.activities.values()){
            if(a.getCache().getId().equals(id)) activities.remove(a.getCache());
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
     * @return User encrypted password
     */
    public String getPass () {
        return this.pass;
    }

    /**
     * @return User gender
     */
    public boolean getGender () {
        return this.gender;
    }

    /**
     * @return User Address
     */
    public Address getAddress () {
        return this.address.clone();
    }

    /**
     * @return User birthdate
     */
    public GregorianCalendar getBDate () {
        return (GregorianCalendar) this.bdate.clone();
    }

    /**
     * @return User points
     */
    public Integer getPoints () {
        return this.points;
    }

    /**
     * @return User activities
     */
    @SuppressWarnings("unchecked")
    public TreeMap<Integer, Activity> getActivities () {
        return (TreeMap<Integer, Activity>) this.activities.clone();
    }

    /**
     * @return User statistics
     */
    public Statistic getStatistics () {
        return this.statistics.clone();
    }

    /**
     * @return User friends list
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Integer> getFriends () {
        return (ArrayList<Integer>) this.friends.clone();
    }

    /**
     * @return The user Id
     */
    public Integer getId () {
        return this.id;
    }

    //Setters

    /**
     * Chanches the User name
     */
    public void setName (String name) throws NullPointerException, IllegalStateException {
        if (name == null)
            throw new NullPointerException("name can't be null!");
        if (name.trim() == "")
            throw new IllegalStateException("name can't be empty");
        this.name = name;
    }

    /**
     * Change the gender
     */
    public void setGender (boolean gender) {
        this.gender = gender;
    }

    /**
     * Set the password for the first time
     * @param passw Password for the user
     */
    public void setPass (String passw) throws NullPointerException, IllegalStateException {
        if (passw == null)
            throw new NullPointerException("passw can't be null!");
        if (passw.trim() == "")
            throw new IllegalStateException("passw can't be empty!");

        this.pass = encryptPass(passw);
    }

    /**
     * Change the adress
     */
    public void setAddress (String city, String country) throws NullPointerException, IllegalStateException {

        // Exceptions
        if (city == null)
            throw new NullPointerException("city can't be null!");
        if (city.trim() == "")
            throw new IllegalStateException("city can't be empty!");
        if (country == null)
            throw new NullPointerException("country can't be null!");
        if (country.trim() == "")
            throw new IllegalStateException("country can't be empty!");

        this.address = new Address(city, country);
    }

    /**
     * Change user e-mail
     * @param mail E-mail to be saved
     */
    public void setMail (String mail) throws NullPointerException, IllegalStateException {
        if (mail == null)
            throw new NullPointerException("mail can't be null!");
        if (mail.trim() == "")
            throw new IllegalStateException("mail can't be empty!");

        this.mail = mail;
    }

    /**
     * Change the user birthdate
     * @param date Date formated as 'DD/MM/YY'
     * @return 0 if valid date, 1 if invalid day, 2 if invalid month, 3 if invalid year
     */
    public int setBDate (String date) throws NullPointerException, IllegalStateException {
        String[] parts;
        int return_value = 0;

        // Exceptions
        if (date == null)
            throw new NullPointerException("date can't be null!");
        if (date.trim() == "")
            throw new IllegalStateException("date can't be empty!");

        parts = date.split("/");

        if (parts.length != 3)
            throw new IllegalStateException("date is in wrong format!");

        int d = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);

        this.bdate = new GregorianCalendar(y,m,d);

        if (d != this.bdate.get(GregorianCalendar.DAY_OF_MONTH))
            return_value = 1;
        else if (m != this.bdate.get(GregorianCalendar.MONTH))
            return_value = 2;
        else if  (y != this.bdate.get(GregorianCalendar.YEAR))
            return_value = 3;

        return return_value;
    }

    /**
     * Change the user birthdate
     * @param date Date formated as GregorianCalendar
     */
    public void setBDate(GregorianCalendar date) {
        //"03/05/1994"
        GregorianCalendar bbb = (GregorianCalendar) date.clone();
        this.bdate = bbb;
    }

    /**
     * Change the user id
     * @param id New user id
     */
    public void setId (Integer id) throws NullPointerException, IllegalStateException {
        if (id == null)
          throw new NullPointerException("id can't be null!");
        if (id < 0)
          throw new IllegalStateException("id can't be negative!");

        this.id = id;
    }

    // Other methods

    /**
     * Report a Cache - call the method addCache (Cache ca as argument.
     * The id is one field of the cache so its not necessary to pass as an argument.)
     * (now...) For the user it is important to report by id.
     * -- Report Cache by an id: Changes:
     * he inserts the id. Check if it exists in the reports base already.
     * if not make method on Cache to find that Cache by the id.
     *
     * (done).
     *
     * CHANGE the SCOPE OF THIS...
     * Usually this goes to GeoCaching with all the prints and all...
     */
    public void reportCache(String id) throws NullPointerException, IllegalStateException {
        if (id == null)
            throw new NullPointerException("id can't be null!");
        if (id.trim() == "")
            throw new IllegalStateException("id can't be empty!");

        if(!reportedmine.exists(id)){
            reportedmine.addCache(id);
        }
    }

    // toString, equals and clone

    /**
     * Transform user info into a String
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();
        String day, month, year;

        sb.append("Mail: " + this.mail + "\n");
        sb.append("Name: " + this.name + "\n");
        if (bdate != null) {
            day = String.valueOf(bdate.get(GregorianCalendar.DAY_OF_MONTH));
            month = String.valueOf(bdate.get(GregorianCalendar.MONTH));
            year = String.valueOf(bdate.get(GregorianCalendar.YEAR));
            sb.append("Birthdate: " + day + "/" + month + "/" + year + "\n");
        }
        sb.append("Gender: " + (this.gender ? "F" : "M" + "\n"));
        sb.append("Address: " + this.address.toString());
        sb.append("User id: " + this.id + "\n");

        return sb.toString();
    }

    /**
     * Compares this object with another User to check if they are equal
     * @param user User to compare with
     */
    public boolean equals (Object user) throws NullPointerException {
        if (user == null)
            throw new NullPointerException("user can't be null!");

        if (this == user) return true;

        if ((user == null) || (this.getClass() != user.getClass())) return false;

        User aux = (User) user;
        boolean comp = this.mail.equals(aux.getMail());
        comp = comp && (this.pass.equals(aux.getPass()));
        comp = comp && (this.name.equals(aux.getName()));
        comp = comp && (this.gender == (aux.getGender()));
        comp = comp && (this.address.equals(aux.getAddress()));
        comp = comp && (this.bdate.equals(aux.getBDate()));
        comp = comp && (this.points == aux.getPoints());
        comp = comp && (this.activities.equals(aux.getActivities()));
        comp = comp && (this.statistics.equals(aux.getStatistics()));
        comp = comp && (this.friends.equals(aux.getFriends()));
        return comp;
    }

    /**
     * Create a clone of this object
     */
    public User clone () {
        return new User(this);
    }

    // Other methods

    /**
     * Confirm if the given password is equal to the one stored
     * @param pass
     */
    public boolean confirmPass (String pass) throws NullPointerException, IllegalStateException {
        if (pass == null)
            throw new NullPointerException("pass can't be null!");
        if (pass.trim() == "")
            throw new IllegalStateException("pass can't be empty!");

        if (this.getPass().equals(encryptPass(pass))) return true;
        else return false;
    }

    /**
     * Function to encrypt password when creating user
     * @param pass Password to be encrypted
     */
    private String encryptPass (String pass) throws NullPointerException, IllegalStateException {
        if (pass == null)
            throw new NullPointerException("pass can't be null!");
        if (pass.trim() == "")
            throw new IllegalStateException("pass can't be empty!");

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

    /**
     * Add a user as a friend
     * @param user User to be added
     * @return true if friend was added, false otherwise
     */
    public boolean addFriend (User user) {
        if (user == null) return false;
        else {
            if (friends == null) friends = new ArrayList<Integer>();
            friends.add(user.getId());
        }
        return true;
    }
}
