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
        this.gender = "";
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
    public User (String mail, String pass, String name, int id, GregorianCalendar bdate) {
        this();
        this.mail = mail; this.name = name;
        this.pass = encryptPass(pass); this.id = id;
        this.address = new Address();
        this.bdate = (GregorianCalendar) bdate.clone();
    }

    /**
     * Construct a new User with the same info as a given User
     * @param user User from which the information will be fetched
     */
    public User (User user) {
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
    public void removeCache(String id){
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
    public String getGender () {
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
     * @param passw Password for the user
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
     * @param date Date formated as 'DD/MM/YY'
     */
    public void setBDate(String date){
        //"03/05/1994"
        String[] parts = date.split("/");

        int d = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);

        this.bdate = new GregorianCalendar(y,m,d);
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
    public void reportCache(String id){
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
        if (gender != null) sb.append("Gender: " + this.gender + "\n");
        if (address != null) sb.append("Address: " + this.address.toString() + "\n");
        sb.append("User id: " + this.id + "\n");

        return sb.toString();
    }

    /**
     * Compares this object with another User to check if they are equal
     * @param user User to compare with
     */
    public boolean equals (Object user) {
        if (this == user) return true;

        if ((user == null) || (this.getClass() != user.getClass())) return false;

        User aux = (User) user;
        boolean comp = this.mail.equals(aux.getMail());
        comp = comp && (this.pass.equals(aux.getPass()));
        comp = comp && (this.name.equals(aux.getName()));
        comp = comp && (this.gender.equals(aux.getGender()));
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
    public boolean confirmPass (String pass) {
        if (this.getPass().equals(encryptPass(pass))) return true;
        else return false;
    }

    /**
     * Function to encrypt password when creating user
     * @param pass Password to be encrypted
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
    
    /**
     * Change the user birthdate
     * @param date Date formated as GregorianCalendar
     */
    public void setBDate(GregorianCalendar date){
        //"03/05/1994"
        GregorianCalendar bbb = (GregorianCalendar) date.clone();
        this.bdate = bbb;
    }
}