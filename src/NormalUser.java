/**
 * Class to represent a user with some personal information, the user activities,
 * statistics and friends
 *
 * @version 08/05/2015
 */

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.Serializable;
import java.io.IOException;
import Exceptions.*;

public class NormalUser extends User implements Serializable {
    private boolean gender;           // User gender
    private Address address;          // User Address
    private GregorianCalendar bdate;  // User birthdate
    private int points;               // User points

    private TreeSet<Activity> activities;       // Last 10 User activities
    private StatisticYear statistics;               // User statistics
    private ArrayList<Double> friends;          // User friends
    private ArrayList<Double> friend_requests;  // Friend Requests

    /**
     * Constructor without arguments
     */
    public NormalUser () {
        super();
        this.gender = true;
        this.address = new Address("New York","USA");
        this.bdate = new GregorianCalendar();
        this.points = 0;
        this.activities = new TreeSet<Activity>(new CacheDateComparator());
        this.statistics = new StatisticYear();
        this.friends = new ArrayList<Double>();
        this.friend_requests = new ArrayList<Double>();
    }

    /**
     * Construtor with arguments
     * @param mail  NormalUser e-mail
     * @param pass  NormalUser password
     * @param name  NormalUser name
     * @param id    NormalUser id
     * @param bdate NormalUser birthdate
     */
    public NormalUser (String mail, String pass, String name, Double id, GregorianCalendar bdate) throws NullPointerException, IllegalStateException {
        super(mail, pass, name, id);

        this.address = new Address();
        this.bdate = (GregorianCalendar) bdate.clone();

        this.points = 0;
        this.activities = new TreeSet<Activity>(new CacheDateComparator());
        this.statistics = new StatisticYear();
        this.friends = new ArrayList<Double>();
        this.friend_requests = new ArrayList<Double>();
    }

    /**
     * Construct a new NormalUser with the same info as a given NormalUser
     * @param user NormalUser from which the information will be fetched
     */
    public NormalUser (NormalUser user) throws NullPointerException {
        super(user);

        this.bdate = user.getBDate();
        this.points = user.getPoints();
        this.activities = user.getActivities();
        this.statistics = user.getStatistics();
        this.friends = user.getFriends();
        this.address = user.getAddress();
    }

    // Getters

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

    //Setters

    /** Change User gender
     *  @param true for female, false for male */
    public void setGender (boolean gender) {
        this.gender = gender;
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

    /** Change user Address
     *  @param address The new Address */
    public void setAddress (Address address) throws NullPointerException {
        if (address == null)
            throw new NullPointerException("address can't be null.");
        else this.address = address;
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
    public void setBDate(GregorianCalendar date) throws NullPointerException {
        if (date == null)
            throw new NullPointerException("date can't be null.");
        GregorianCalendar bbb = (GregorianCalendar) date.clone();
        this.bdate = bbb;
    }

    /* -------------------------------- FRIENDS -----------------------------*/

    /** Add a User id to the friends request
     * @param id The user id
     */
    public void addFriendRequest (Double id) throws IllegalArgumentException {
        if (id < 0)
            throw new IllegalArgumentException("id has to be positive!");

        this.friend_requests.add(id);
    }

    /** Remove User id from the friends request
     * @param id Id to be removed
     */
    public void removeFriendRequest (Double id) throws IllegalArgumentException {
        if (id < 0) /* Invalid id */
            throw new IllegalArgumentException("id has to be positive.");

        this.friend_requests.remove(id);
    }

    /**
     * Add a user as a friend
     * @param id User to be added id
     */
    public void addFriend (Double id) throws IllegalArgumentException {
        if (id < 0) /* Invalid id */
            throw new IllegalArgumentException("id has to be positive.");

        this.friends.add(id);
    }

    /**
    * @return User friends list
    */
    @SuppressWarnings("unchecked")
    public ArrayList<Double> getFriends () {
        return (ArrayList<Double>) this.friends.clone();
    }

    /** @return Friend Requests list */
    @SuppressWarnings("unchecked")
    public ArrayList<Double> getFriendRequests () {
        return (ArrayList<Double>) this.friend_requests.clone();
    }

    /* ---------------------------- ACTIVITIES --------------------------- */

    /** Add an Activity to the activities queue
     * and automaticaly to the statistics
     * @param act Activity to be added
     */
    public void addActivity (Activity act) throws NullPointerException,NotAddedActivityYearIncorrectException {
        Activity aux = null;

        if (act == null)
            throw new NullPointerException("act can't be null!");

        /* Remove head if there are already 10 activities */
        if (this.activities.size() == 10)
            try {
                aux = this.activities.first();
            } catch (Exception e) { aux = null; }
        if (aux != null) this.activities.remove(aux);

        this.activities.add(act);
        this.points += act.getPoints();

        this.statistics.addAct(act);

    }

    /** @return TreeSet with the user activities */
    @SuppressWarnings("unchecked")
    public TreeSet<Activity> getActivities () {
        TreeSet<Activity> ts = new TreeSet<Activity>(new CacheDateComparator());
        Iterator it = activities.iterator();
        Activity aux;

        while (it.hasNext()) {
            aux = (Activity) it.next();
            ts.add(aux.clone());
        }
        return ts;
    }

    /** @return ArrayList with the last 10 activities if the user has that many */
    public ArrayList<Activity> getLastActivities () {
        ArrayList<Activity> acts = new ArrayList<Activity>(10);
        Iterator it = this.activities.descendingIterator();

        while (it.hasNext() && (acts.size() != 10)) {
            Activity act = (Activity) it.next();
            acts.add(act.clone());
        }

        return acts;
    }

    /* ---------------------------- STATISTIC --------------------------- */
    //TODO

    /**
     * @return User statistics of all years
     */
    public StatisticYear getStatistics () {
        return this.statistics.clone();
    }

    /**
     * @return User statistic of a given year
     * @param year
     */
    public Statistic getStatistics(int year){
        return this.statistics.getStatistic(year).clone();
    }

    //TODO test please.
    /** @returns stats in form of a string with the information of totalpoints, totalkms, and numbercaches of all years*/
    public String getSTATS_PKC(){
        StringBuilder sb = new StringBuilder();
        int totalpoints = this.statistics.getSumPoints();
        double totalkms = this.statistics.getSumKms();
        int totalcaches = this.statistics.getTotalCaches();
        sb.append("Global Statistics: \n");
        sb.append("Caches found:"); sb.append(Integer.toString(totalcaches) + ".\n");
        sb.append("Kms traveled:"); sb.append(Double.toString(totalkms) + ".\n");
        sb.append("Total earned points:"); sb.append(Integer.toString(totalpoints) + "!!!\n");

        return sb.toString();

    }

    /** @returns stats in form of a string with the information of totalpoints, totalkms, and numbercaches of a whole year
    * @param year
    */
    public String getSTATS_PKC(int year) throws NullPointerException{

      StringBuilder sb = new StringBuilder();
      if(this.statistics.getStatistic(year) == null)
      throw new NullPointerException("There are no Statistics in that given year!");

      int totalpoints = this.statistics.getStatistic(year).getSumPoints();
      double totalkms = this.statistics.getStatistic(year).getSumKms();
      int totalcaches = this.statistics.getStatistic(year).getTotalCaches();

      sb.append("Statistics of year "+ Integer.toString(year)  +": \n");
      sb.append("Caches found:"); sb.append(Integer.toString(totalcaches) + ".\n");
      sb.append("Kms traveled:"); sb.append(Double.toString(totalkms) + ".\n");
      sb.append("Total earned points:"); sb.append(Integer.toString(totalpoints) + "!!!\n");
      return sb.toString();
    }

    /** @returns stats in form of a string with the information of totalpoints, totalkms, and numbercaches of a given month
     * assuming that the user wants the current year as Statistic to look for the month.
     * @param month
     */

    //TODO test the indices of month and it keeps returning null so test in geocaching as well
    //TODO add exceptions to avoid receiving the message NULL EXCEPTION...
    public String getSTATSM_PKC(int month)throws NullPointerException{
      /*GregorianCalendar gc = new GregorianCalendar();
      String years = String.valueOf(gc.get(GregorianCalendar.YEAR));
      int year = Integer.parseInt(years);*/
      int year = getCurrentYear();
      //System.out.println(years);  -> printed 2015 so all good. Moving on...

      StringBuilder sb = new StringBuilder();


      if(this.statistics.getStatistic(year) == null)
      throw new NullPointerException("There are no Statistics in the current year!");

      int totalpoints = this.statistics.getStatistic(year).getSumPoints(month+1);
      double totalkms = this.statistics.getStatistic(year).getSumKms(month+1);
      int totalcaches = this.statistics.getStatistic(year).getTotalCaches(month+1);


      sb.append("Statistics year "+ Integer.toString(year)  + ", month " + Integer.toString(month) +": \n");
      sb.append("Caches found:"); sb.append(Integer.toString(totalcaches) + ".\n");
      sb.append("Kms traveled:"); sb.append(Double.toString(totalkms) + ".\n");
      sb.append("Total earned points:"); sb.append(Integer.toString(totalpoints) + "!!!\n");
      System.out.println(sb.toString());
      return sb.toString();
    }

    /** @returns the current year */
    public int getCurrentYear(){
        GregorianCalendar gc = new GregorianCalendar();
        String years = String.valueOf(gc.get(GregorianCalendar.YEAR));
        return Integer.parseInt(years);
    }

    /* ---------------------------- tostring, equals and clone --------------------------- */
    /**
     * Transform user info into a String
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();
        String day, month, year;

        sb.append(super.toString());
        if (bdate != null) {
            day = String.valueOf(bdate.get(GregorianCalendar.DAY_OF_MONTH));
            month = String.valueOf(bdate.get(GregorianCalendar.MONTH));
            year = String.valueOf(bdate.get(GregorianCalendar.YEAR));
            sb.append("Birthdate: " + day + "/" + month + "/" + year + "\n");
        }
        sb.append("Gender: " + (this.gender ? "F" + "\n" : "M" + "\n"));
        sb.append("Address: " + this.address.toString());

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

        NormalUser aux = (NormalUser) user;
        return this.getMail().equals(aux.getMail());
    }

    /**
     * Create a clone of this object
     */
    @Override
    public NormalUser clone () {
        return new NormalUser(this);
    }

    /* ---------------- SERIALIZABLE IMPLEMENTATION ----------------- */

    /** Method which defines what will be written by the
     *  ObjectOutputStream */
    private void writeObject (java.io.ObjectOutputStream stream)
    throws IOException {
        stream.writeObject(gender);
        stream.writeObject(address);
        stream.writeObject(bdate);
        stream.writeObject(points);
        stream.writeObject(activities);
        stream.writeObject(statistics);
        stream.writeObject(friends);
        stream.writeObject(friend_requests);
    }

    /** Method which defines what will be read by the
     *  ObjectInputStream */
    @SuppressWarnings("unchecked")
    private void readObject (java.io.ObjectInputStream stream)
    throws IOException, ClassNotFoundException {
        gender          = (boolean) stream.readObject();
        address         = (Address) stream.readObject();
        bdate           = (GregorianCalendar) stream.readObject();
        points          = (int) stream.readObject();
        activities      = (TreeSet<Activity>) stream.readObject();
        statistics      = (StatisticYear) stream.readObject();
        friends         = (ArrayList<Double>) stream.readObject();
        friend_requests = (ArrayList<Double>) stream.readObject();
    }

}
