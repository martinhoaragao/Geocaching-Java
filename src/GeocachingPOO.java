/**
 * This class will represent the program in which a random user with an e-mail will login
 * and do all the available operations, with his account.
 * He will only have access to the functions of which he has power.
 * All the classes and all the methods created for User-Application will be avaiable here.
 *
 *  @version 08/05/2015
 */


import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.Console;
import java.io.Serializable;
import java.io.IOException;
import Exceptions.*;

public class GeocachingPOO implements Serializable {
    private static Double id;               /* User ID */
    private static NormalUser user;         /* User that is logged in */
    private static Double idAdmin;          /* Admin ID */
    private static Admin admin;             /* Admin that is logged in */
    private static UserBase userbase;       /* User data base */
    private static CacheBase cachebase;     /* Cache data base */
    private static Double idcache;          /* Cache ID */
    private static ArrayList<Event> events; /* List of events */

    /**
     * Unparameterized Constructor, all instance variables are initialized, the following
     * values are attributed: id = 1.0, idAdmin = 2.0, idcache = 1.0.
     * user and admin are set to null and a Admin with e-mail "grupoajm@gmail.com" and pass
     * "AdminAdmin" is created and saved in the UserBase
     */
    public GeocachingPOO () {
        this.id = 1.0;
        this.idAdmin = 2.0;
        this.idcache = 1.0;
        this.userbase = new UserBase();
        this.cachebase = new CacheBase();
        this.events = new ArrayList<Event>();
        this.user = null;
        this.admin = null;
        Admin ad = new Admin("grupoajm@gmail.com", "Alhpa", "AdminAdmin", 1.0, 2);
        this.userbase.addAdmin(ad);
    }

    /* ------------------- REGISTER & LOGIN --------------------- */

    /**
     * Add a user to the User data base
     * @param u User to be added
     */
    public void register (NormalUser u) throws EmailAlreadyInUseException, IdAlreadyAssignedException {
        userbase.addUser(u);

        this.id++;
    }

    /**
     * Login to GeocahingPOO
     * @param mail User e-mail
     * @param pass User password
     * @param type true if logging in as Admin, false if logging in as User
     */
    public  void login (String mail, String pass, boolean type) throws WrongPasswordException, IllegalArgumentException {
        if (mail.trim().equals(""))
            throw new IllegalArgumentException("mail can't be null!");

        if (type == true)   /* Logging in as Admin */
            admin = userbase.getAdmin(mail, pass);
        else                /* Logging in as User */
            user = userbase.getUser(mail, pass);
    }


    /**
     * Logout the User from the application
     */
    public void logout () {
        this.user = null;
        this.admin = null;
    }

    /* ----------------- INFORMATION MODIFICATION --------------------*/

    /**
     * Get list with all the users currently registered
     * @return ArrayList with all the instances of NormalUser
     */
    public ArrayList<NormalUser> getUsers () {
        return userbase.getUsers();
    }

    /**
     * Get list with all the admins currently registered
     * @return ArrayList with all the instances of Admin
     */
    public ArrayList<Admin> getAdmins () {
        return userbase.getAdmins();
    }

    /**
     * Get the currently logged in user
     * @return Clone of the currently logged in user
     */
    public NormalUser getLoggedUser () throws NoUserLoggedInException {
        StringBuilder sb = new StringBuilder();

        if (user == null)
            throw new NoUserLoggedInException("No user is logged in.");
        else return user.clone();
    }

    /**
     * Compare given password with the password of the current
     * logged in user
     * @param pass Password to be compared
     * @return true if the password matches, false otherwise
     */
    public boolean confirmPass (String pass) throws NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException("No user logged in.");
        return user.confirmPass(pass);
    }

    /**
     * Change the password of the currently logged in user
     * @param currentpass    The Current Password
     * @param newpass        New password
     */
    public void changePassword (String currentpass, String newpass) throws IllegalArgumentException, NullPointerException, NoUserLoggedInException {
        if ((currentpass == null) || (newpass == null))
            throw new NullPointerException("Arguments can't be null!");
        if (user == null)
            throw new NoUserLoggedInException("No User logged in.");

        if (this.confirmPass(currentpass))
            user.setPass(newpass);
        else
            throw new IllegalArgumentException("Current Password is wrong.");
    }

    /**
     * Change the name of the currently logged in user
     *  @param name New name for the user
     */
    public static void changeName (String name) throws NullPointerException, NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException("No user logged in.");
        if (name == null)
            throw new NullPointerException("name can't be null.");
        user.setName(name);
    }

    /**
     * Change the Address of the currently logged in user
     * @param address The new Address
     */
    public static void changeAddress (Address address) throws NullPointerException, NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException();
        user.setAddress(address);
    }

    /**
     * Change the birthdate of the currently logged in user
     * @param bdate GregorianCalendar Object with the new birthdate
     */
    public static void changeBDate (GregorianCalendar bdate) throws NullPointerException, NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException();
        if (bdate == null)
            throw new NullPointerException("bdate can't be null.");
        user.setBDate(bdate);
    }

    /**
     * Change the gender of the currently logged in user
     * @param gender true for female, false for male */
    public static void changeGender (boolean gender) throws NoUserLoggedInException {
        if (user == null)
          throw new NoUserLoggedInException();
        user.setGender(gender);
    }

    /* -------------------- FRIENDS -------------------- */

    /**
     * Send a friend request to a given user with the currently logged in user
     * @param mail User mail to send the request
     */
    public void sendFriendRequest (String mail) throws NullPointerException, NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException();
        if (mail == null)
            throw new NullPointerException("mail can't be null.");
        userbase.sendFriendRequest(user.getId(), mail);
    }

    /**
     * Get String with all the pending friend requests of the currently logged in user
     */
    public String getFriendRequests () throws NoUserLoggedInException {
        ArrayList<Double> requests = user.getFriendRequests();
        StringBuilder sb = new StringBuilder();

        if (user == null)
            throw new NoUserLoggedInException();

        if (requests.size() == 0)
            return null;
        else {
            /* Iterate over friend requests */
            for (Double id : requests) {
                User u = userbase.getUserInfo(id);
                sb.append(u.getName() + " - " + u.getMail());
            }
            return sb.toString();
        }
    }

    /**
     * Get String with all the friends of the currently logged in user
     */
    public String getFriends () throws NoUserLoggedInException {
        User u;
        ArrayList<Double> friends = user.getFriends();
        StringBuilder sb = new StringBuilder();

        if (user == null)
            throw new NoUserLoggedInException();

        if (friends.size() == 0)
            sb.append("You have no friends yet.");
        else {
            for (Double id : friends) {
                u = userbase.getUserInfo(id);
                if (u != null)
                    sb.append(u.getName() + " - " + u.getMail() + "\n");
            }
        }
        return sb.toString();
    }

    /**
     * Accept friend request with the currently logged in user
     * @param mail User that sent the friend request
     */
    public static void acceptFriendRequest (String mail) throws NullPointerException, IllegalArgumentException, NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException();
        userbase.acceptFriendRequest(user.getId(), mail);
    }

    /* ------------------- ACTIVITIES -------------------------*/

    /**
     * Get the currently logged in most recent 10 activities if he has that many
     * @return ArrayList with user 10 most recent activities
     */
    public ArrayList<Activity> getLastActivities () throws NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException("There is no user logged in.");
        return user.getLastActivities();
    }

    /**
     * Get all the Activities of the current logged in User
     */
    public TreeSet<Activity> getActivities () {
      return user.getActivities();
    }

    /**
     * Get list of a friends 10 most recent activities
     * @param mail Friend's e-mail
     * @return ArrayList with user 10 most recent activities
     */
    public ArrayList<Activity> getFriendLastActivities (String mail) throws NoUserLoggedInException {
        NormalUser friend = userbase.getUser(mail);
        if (user == null)
            throw new NoUserLoggedInException("There is no user logged in.");
        if (friend == null)
            throw new IllegalArgumentException("No user with the given mail.");
        if (!user.getFriends().contains(friend.getId()))
            throw new IllegalArgumentException("No friend with the given e-mail.");

        return friend.getLastActivities();
    }

    /**
     * Add a activity as the currently logged in user, this simulates the activity
     * meteorology aswell as the kilometers traveled
     * @param act  Activity to be added
     * @param id   Id of the cache found
     */
    public void addActivity (Double id, GregorianCalendar date ) throws IllegalArgumentException, NullPointerException, NotAddedActivityYearIncorrectException, NoUserLoggedInException {


        Cache cache             = cachebase.getCache(id);

         //The last activity of the user for the coordinates.

        if (user == null)
            throw new NoUserLoggedInException();

        if (cache == null)
            throw new IllegalArgumentException("No cache with the given id.");

        user.addActivity(cache, date);
    }

    /**
    * Remove a user Activity from the list of activities of the currently logged in user
    * @param act Activity to be removed
    */
    public void removeActivity (Activity act) throws NullPointerException {
      user.removeAct(act);
    }

    /* ------------------- CACHES ----------------------------*/

    /**
     *  Get a clone of a given Cache
     *  @param id The Cache ID
     *  @return Clone of the given cache
     */
    public Cache getCache (Double id) throws IllegalArgumentException {
        Cache cache = cachebase.getCache(id);
        return cache.clone();
    }

    /**
     * Report a cache as the currently logged in user
     * @param rep The report to be added */
    public void reportCache (Report rep) throws NullPointerException, NoUserLoggedInException, IllegalArgumentException {
        if (user == null)
            throw new NoUserLoggedInException();
        rep.setMail(user.getMail());
        cachebase.addReport(rep);
    }

    /**
     * Get all the reports from the Cache data base
     * @return TreeMap with the mapping between caches IDs and the list of its reports
     */
    public TreeMap<Double, ArrayList<Report>> getAllReports () throws NoUserLoggedInException {
        if (admin == null)
            throw new NoUserLoggedInException("No admin logged in.");

        return cachebase.getAllReports();
    }

    /**
     * Get all the caches stored in the Cache data base
     * @return ArrayList with clone of all of the caches
     */
    public ArrayList<Cache> getAllCaches () {
        return cachebase.getAllCaches();
    }

    /**
     * Get the list of caches created by the currently logged in user
     * @return ArrayList with all the caches created by a given user
     */
    public ArrayList<Cache> getUserCaches () {
        ArrayList<Cache> caches = cachebase.getCaches(user.getId());

        if (caches != null) return caches;
        else return null;
    }

    /**
     *  Create a Traditional Cache associated to the logged in user
     *  @param coords       Cache coordinates
     *  @param treasures    The Treasures on the Cache
     *  @param info         Information about this Cache
     */
    public void createTraditionalCache (Coordinates coords, ArrayList<String> treasures, String info) throws NullPointerException, IllegalStateException, IllegalArgumentException, NoUserLoggedInException {
        TraditionalCache cache;

        if (coords == null)
            throw new NullPointerException("coords can't be null.");
        if (info == null)
            throw new NullPointerException("info can't be null.");
        if (user == null)
            throw new NoUserLoggedInException("There is no user logged in.");

        cache = new TraditionalCache(idcache, coords, user.getMail(), treasures, info);
        cachebase.addCache(idcache, cache);
        idcache++;
    }

    /**
     *  Create a MultiCache associated to the logged in user
     *  @param coords       List with all the Coordinates
     *  @param treasures    Cache Treasures
     *  @param info         Infomation about the Cache
     */
    public void createMultiCache (ArrayList<Coordinates> coords, ArrayList<String> treasures, String info) {
        MultiCache cache;

        if (coords == null)
            throw new NullPointerException("coords can't be null.");
        if (treasures == null)
            throw new NullPointerException("treasures can't be null.");
        if (info == null)
            throw new NullPointerException("info can't be null.");

        cache = new MultiCache(idcache, coords, user.getMail(), treasures, info);
        cachebase.addCache(idcache, cache);
        idcache++;
    }

    /**
     *  Create a  MicroCache associated to the logged in user
     *  @param coords       The Cache Coordinates
     *  @param info         Information about this Cache
     */
    public void createMicroCache (Coordinates coords, String info) {
        MicroCache cache;

        if (coords == null)
            throw new NullPointerException("coords can't be null.");
        if (info == null)
            throw new NullPointerException("info can't be null.");

        cache = new MicroCache(idcache, coords, user.getMail(), info);
        cachebase.addCache(idcache, cache);
        idcache++;
    }

    /**
     *  Create a MysteryCache associated to the logged in user
     *  @param coords       The Cache Coordinates
     *  @param treasures    List of Treasures for the Cache
     *  @param info         Information about this Cache
     *  @param puzzle       This Cache's Puzzle
     */
    public void createMysteryCache (Coordinates coords, ArrayList<String> treasures, String info, Puzzle puzzle) throws NullPointerException, NoUserLoggedInException {
        MysteryCache cache;

        if (coords == null)
            throw new NullPointerException("coords can't be null.");
        if (treasures == null)
            throw new NullPointerException("treasures can't be null.");
        if (info == null)
            throw new NullPointerException("info can't be null.");
        if (puzzle == null)
            throw new NullPointerException("puzzle can't be null.");
        if (user == null)
            throw new NoUserLoggedInException("There is no user logged in.");

        cache = new MysteryCache(idcache, coords, user.getMail(), treasures, info, puzzle);
        cachebase.addCache(idcache, cache);
        idcache++;
    }

    /**
     * Invalidate (Delete) a Cache, if a User is logged in he can only delete caches
     * created by him, an Admin can delete any Cache
     * @param id The cache ID
     */
    public void invalidateCache (Double id) throws IllegalArgumentException, NoUserLoggedInException {
        if (user != null && admin == null)      /* Invalidate as User */
            cachebase.invalidateCache(id, user);
        else if (user == null && admin != null) /* Invalidate as Admin */
            cachebase.invalidateCache(id);
        else
            throw new NoUserLoggedInException("There is no Admin Logged in.");
    }

    /**
     * Get the Registry of a given cache
     * @param id The cache ID
     * @return ArrayList<String> with all the people tha found the Cache
     */
    public ArrayList<String> getCacheRegistry (Double id) {
        Cache cache = cachebase.getCache(id);
        return cache.getRegistry();
    }

    /**
     *  Get the Treasures of a given Cache
     *  @param id The Cache ID
     *  @return ArrayList containing clones of the Cache Treasures
     */
    public ArrayList<String> getCacheTreasures (Double id) throws IllegalArgumentException {
        return cachebase.getCacheTreasures(id);
    }

    /**
     * Create a TreeMap with the caches near a give location and inside a given radius
     *
     * @param coords The location from where to search
     * @param radius Radius of the search
     */
    public TreeMap<Double, ArrayList<Cache>> getNearCaches (Coordinates coords, Double radius) throws NullPointerException {
        return cachebase.getNearCaches(coords, radius);
    }

    /* ------------------------- ADMIN ------------------------*/

    /**
     * Create a new Admin if the logged in admin has permissions to do so
     * @param new_admin Admin to be added to the User data base
     */
    public void createAdmin (Admin new_admin) throws IllegalStateException, IllegalArgumentException, NullPointerException, NoAdminLoggedInException {
        if (new_admin == null)
            throw new NullPointerException("new_admin can't be null");
        if (admin == null)
            throw new NoAdminLoggedInException("There is no admin logged in.");

        new_admin.setId(idAdmin);
        userbase.addAdmin(new_admin);
        idAdmin++;
    }

    /**
     * Delete an admin if the logged in admin has permissions
     * @param mail Mail of the admin to be deleted
     */
    public void deleteAdmin (String mail) throws IllegalStateException, NoAdminLoggedInException {
        if (this.admin.getPermi() != 2)
            throw new IllegalStateException("You have no permissions.");
        if (admin == null)
            throw new NoAdminLoggedInException("There is no Admin logged in.");

        userbase.removeAdmin(mail);
    }

    /**
     * Delete user given it's e-mail
     * @param mail User e-mail */
    public void deleteUser (String mail) throws IllegalStateException, IllegalArgumentException, NoAdminLoggedInException {
        if (admin.getPermi() < 1)
            throw new IllegalStateException("You lack permission.");
        if (admin == null)
            throw new NoAdminLoggedInException("There is no Admin Logged in.");

        userbase.removeUser(mail);
    }

    /* ----------------------- STATISTICS -----------------*/

    /**
     * Get the current number of user ID
     * @return Current User id
     */
    public Double getCurrentUserId () {
        return this.id;
    }

    /**
     * Get the statistics of a given year for the currently logged in user
     * @param int year
     * @return Statistic of a given year
     */
    public Statistic getStatistic(int year) throws NoStatsException{
        return user.getStatistics(year);
        //made this method in the class NormalUser
    }

    /**
     * Get the Global statistics for the currently logged in user
     * @return String with the statistics
     */
    public String getSTATSGlobal() throws NoStatsException{
        return user.getSTATS_PKC();
    }

    /**
     * Get the statistics of a given year for the currently logged in user
     * @param year Year for which to get the statistics
     * @return String with the statistics
     */
    public String getSTATSGlobal(int year) throws NoStatsException{
        return user.getSTATS_PKC(year);
    }


    /**
     * Get the statistics of a given month for the currently logged in user
     * @param month MOnth for which to get the statistics
     * @return String with the statistics
     */
    public String getSTATS_Month(int month) throws NoStatsException{
        return user.getSTATSM_PKC(month);
    }

    /**
     * Get the current year
     * @return the current year
     */
    public int getCurrentYear(){
        return user.getCurrentYear();
    }

    /* -------------- APPLICATION STATE ---------------------*/

    /**
     * Define which variables that will be written by the ObjectOutputStream
     */
    private void writeObject (java.io.ObjectOutputStream stream)
     throws IOException {
        stream.writeObject(id);
        stream.writeObject(user);
        stream.writeObject(idAdmin);
        stream.writeObject(admin);
        stream.writeObject(userbase);
        stream.writeObject(cachebase);
        stream.writeObject(events);
        stream.writeObject(idcache);
     }

    /**
     * Define which variables will hold what's read by the ObjectInputStream
     */
    @SuppressWarnings("unchecked")
    private void readObject (java.io.ObjectInputStream stream)
     throws IOException, ClassNotFoundException {
        id = (Double) stream.readObject();
        user = (NormalUser) stream.readObject();
        idAdmin = (Double) stream.readObject();
        admin = (Admin) stream.readObject();
        userbase = (UserBase) stream.readObject();
        cachebase = (CacheBase) stream.readObject();
        events = (ArrayList<Event>) stream.readObject();
        idcache = (Double) stream.readObject();
     }


    /*TODO toString equals clone*/
}
