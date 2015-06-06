import Exceptions.*;
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
    private static Double id;            // User ID
    private static NormalUser user;     // User that is logged in
    private static Double idAdmin;     // Admin ID
    private static Admin admin;     // Admin that is logged in
    private static UserBase userbase;   // User data base
    private static CacheBase cachebase;
    private static Double idcache;
    private static Cache cache;
    private static Console c = System.console();

    /** Unparameterized Constructor */
    public GeocachingPOO () {
        this.id = 1.0;
        this.idAdmin = 2.0;
        this.idcache = 1.0;
        this.userbase = new UserBase();
        this.cachebase = new CacheBase();
        this.user = null;
        this.admin = null;
        this.cache = null;
        Admin ad = new Admin("grupoajm@gmail.com", "Alhpa", "AdminAdmin", 1.0, 2);
        this.userbase.addAdmin(ad);
    }


    /** Logout the User from the application */
    public void logout () {
        this.user = null;
        this.admin = null;
    }

    /* ------------------- REGISTER & LOGIN --------------------- */

    /** Add a user to the user base
     * @param u User to be added
     */
    public void register (NormalUser u) throws EmailAlreadyInUseException, IdAlreadyAssignedException {
        userbase.addUser(u);

        this.id++;
    }

    /** Retrieve a user from the UserBase if login suceeded
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

    /* ----------------- INFORMATION MODIFICATION --------------------*/

    /** @return ArrayList with all the users */
    public ArrayList<NormalUser> getUsers () {
        return userbase.getUsers();
    }

    /** @return ArrayList with all the admins */
    public ArrayList<Admin> getAdmins () {
        return userbase.getAdmins();
    }

    /**
     * @return Clone of the currently logged in user
     */
    public NormalUser getLoggedUser () throws NoUserLoggedInException {
        StringBuilder sb = new StringBuilder();

        if (user == null)
            throw new NoUserLoggedInException("No user is logged in.");
        else return user.clone();
    }

    /** Compare given password with the password of the current
     *  logged in user
     *  @param pass Password to be compared
     *  @return true if the password matches, false otherwise */
    public boolean confirmPass (String pass) throws NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException("No user logged in.");
        return user.confirmPass(pass);
    }

    /** Change logged in User Password
     *  @arg currentpass    The Current Password
     *  @arg newpass        New password
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

    /** Change logged in User Name
     *  @param name New name for the user */
    public static void changeName (String name) throws NullPointerException, NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException("No user logged in.");
        if (name == null)
            throw new NullPointerException("name can't be null.");
        user.setName(name);
    }

    /** Change logged in User Adrress
     *  @param address The new Address */
    public static void changeAddress (Address address) throws NullPointerException, NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException();
        user.setAddress(address);
    }

    /** Change logged in User Birthdate
     *  @param bdate GregorianCalendar Object with the new bdate */
    public static void changeBDate (GregorianCalendar bdate) throws NullPointerException, NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException();
        if (bdate == null)
            throw new NullPointerException("bdate can't be null.");
        user.setBDate(bdate);
    }

    /** Change logged in User gender
     *  @param gender true for female, false for male */
    public static void changeGender (boolean gender) throws NoUserLoggedInException {
        if (user == null)
          throw new NoUserLoggedInException();
        user.setGender(gender);
    }

    /* -------------------- FRIENDS -------------------- */
    /** Send a friend request to a given user
     *  @param mail User mail to send the request */
    public void sendFriendRequest (String mail) throws NullPointerException, NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException();
        if (mail == null)
            throw new NullPointerException("mail can't be null.");
        userbase.sendFriendRequest(user.getId(), mail);
    }

    /** Auxiliary function to show friend requests */
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

    /** Auxiliary function to show friends */
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

    /** Accept friend request
     *  @param mail User that sent the request */
    public static void acceptFriendRequest (String mail) throws NullPointerException, IllegalArgumentException, NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException();
        userbase.acceptFriendRequest(user.getId(), mail);
    }

    /* ------------------- ACTIVITIES -------------------------*/

    /** @return ArrayList with user 10 last activities */
    public ArrayList<Activity> getLastActivities () throws NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException("There is no user logged in.");
        return user.getLastActivities();
    }

    /**
     * Get list of a friends 10 last activities
     * @param mail Friend's e-mail
     * @return ArrayList with user 10 last activities
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

    /** Add a activity to the currently logged in user
     *  simulates kms travelled and meteo as well if____
     *  @param act  Activity to be added
     *  @param id   Id of the found cache
     */
    public void addActivity (Double id, GregorianCalendar date ) throws IllegalArgumentException, NullPointerException, NotAddedActivityYearIncorrectException, NoUserLoggedInException {
        Cache cache             = cachebase.getCache(id);
        ArrayList<String> reg   = null;
        Activity act = new Activity();
         Double kms;

         ArrayList<Activity> last_activity = user.getLastActivities();
         //The last activity of the user for the coordinates.
         
        if (user == null)
            throw new NoUserLoggedInException();

        if (cache == null)
            throw new IllegalArgumentException("No cache with the given id.");

        //The argument of coordinates can be passed as null. If so, the coordinates will be simulated and kms will be calculated with these random coordinates
        //Otherwise, the kms will be calculated following this coordinates.
        //This param must be the coordinates of the previous cache location. The location of this current cache is available in the cache.

       // if(last_activity.size() == 0){
            Coordinates coordinates_simulated = cache.getCoords();
            coordinates_simulated.incLat(); coordinates_simulated.incLon();
            kms = coordinates_simulated.getCoordinatesDist( cache.getCoords() );
            act.setKms(kms);

            //Comments on the previous Main class where these ones:
            //date done, cache done, kms done and meteo will be simulated as well. After this, updatePoints should be correctly executed.
            //Because all this information is in the Activity now.
            //Note: when calling the empty constructor new Activity() it creates the Meteo already. So all done.


       /* }
        else{
            Coordinates coordinates = last_activity.get(0).getCache().getCoords();
            kms = coordinates.getCoordinatesDist(cache.getCoords());
            act.setKms(kms);
            //This will calculate the kms already for that coordinates of the last activity.
        }*/

        reg = cache.getRegistry();
        reg.add(user.getName() + " ( " + user.getMail() + " ) ");
        cache.setRegistry(reg);
        act.setCache(cache);
        act.setDate(date);
        act.updatePoints();
        user.addActivity(act);
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

    /** Report a cache
     *  @param rep The report to be added */
    public void reportCache (Report rep) throws NullPointerException, NoUserLoggedInException, IllegalArgumentException {
        if (user == null)
            throw new NoUserLoggedInException();
        rep.setMail(user.getMail());
        cachebase.addReport(rep);
    }

    /** Get all reports from the CacheBase */
    public TreeMap<Double, ArrayList<Report>> getAllReports () throws NoUserLoggedInException {
        if (admin == null)
            throw new NoUserLoggedInException("No admin logged in.");

        return cachebase.getAllReports();
    }

    /** @return ArrayList with clone of all of the caches */
    public ArrayList<Cache> getAllCaches () {
        return cachebase.getAllCaches();
    }

    /** @return ArrayList with all the caches created by a given user */
    public ArrayList<Cache> getUserCaches () {
        ArrayList<Cache> caches = cachebase.getCaches(user.getId());

        if (caches != null) return caches;
        else return null;
    }

    /**
     *  Create a Traditional Cache associated to the logged in User
     *  @param coords       Cache coordinates
     *  @param treasures    The Treasures on the Cache
     *  @param info         Information about this Cache
     */
    public void createTraditionalCache (Coordinates coords, ArrayList<Treasure> treasures, String info) throws NullPointerException, IllegalStateException, IllegalArgumentException, NoUserLoggedInException {
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
     *  Create a MultiCache associated to the logged in User
     *  @param coords       List with all the Coordinates
     *  @param treasures    Cache Treasures
     *  @param info         Infomation about the Cache
     */
    public void createMultiCache (ArrayList<Coordinates> coords, ArrayList<Treasure> treasures, String info) {
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
     *  Create a  MicroCache associated to the logged in User
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
     *  Create a MysteryCache associated to the logged in User
     *  @param coords       The Cache Coordinates
     *  @param treasures    List of Treasures for the Cache
     *  @param info         Information about this Cache
     *  @param puzzle       This Cache's Puzzle
     */
    public void createMysteryCache (Coordinates coords, ArrayList<Treasure> treasures, String info, Puzzle puzzle) throws NullPointerException, NoUserLoggedInException {
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
     *  created by him, an Admin can delete any Cache
     *  @param id The cache ID
     */
    public void invalidateCache (Double id) throws IllegalArgumentException, NoUserLoggedInException {
        if (user != null && admin == null)      /* Invalidate as User */
            cachebase.invalidateCache(id, user);
        else if (user == null && admin != null) /* Invalidate as Admin */
            cachebase.invalidateCache(id);
        else
            throw new NoUserLoggedInException("There is no Admin Logged in.");
    }

    /** Get the Registry of a given cache
     *  @param id The cache id
     *  @return ArrayList<String> with all the people tha found the Cache
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
    public ArrayList<Treasure> getCacheTreasures (Double id) throws IllegalArgumentException {
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

    /* ------------------------- ADMIN ------------------------*


    /** Create a new Admin if the logged in admin has permissions to do so
     *  @param new_admin Admin to be added */
    public void createAdmin (Admin new_admin) throws IllegalStateException, IllegalArgumentException, NullPointerException, NoAdminLoggedInException {
        if (new_admin == null)
            throw new NullPointerException("new_admin can't be null");
        if (admin == null)
            throw new NoAdminLoggedInException("There is no admin logged in.");

        new_admin.setId(idAdmin);
        userbase.addAdmin(new_admin);
        idAdmin++;
    }

    /** Delete an admin if the logged in user has permissions
     *  @param mail Mail of the admin to be deleted */
    public void deleteAdmin (String mail) throws IllegalStateException, NoAdminLoggedInException {
        if (this.admin.getPermi() != 2)
            throw new IllegalStateException("You have no permissions.");
        if (admin == null)
            throw new NoAdminLoggedInException("There is no Admin logged in.");

        userbase.removeAdmin(mail);
    }

    /** Delete user given it's e-mail
     *  @param mail User e-mail */
    public void deleteUser (String mail) throws IllegalStateException, IllegalArgumentException, NoAdminLoggedInException {
        if (admin.getPermi() < 1)
            throw new IllegalStateException("You lack permission.");
        if (admin == null)
            throw new NoAdminLoggedInException("There is no Admin Logged in.");

        userbase.removeUser(mail);
    }

    /* ----------------------- STATISTICS -----------------*/

    /** @return Current User id */
    public Double getCurrentUserId () {
        return this.id;
    }

    //TODO - test from this on
    /**
    * @param int year
    * @return Statistic of a given year
    *
    */
    public Statistic getStatistic(int year) throws NoStatsException{
        return user.getStatistics(year);
        //made this method in the class NormalUser
    }
    //TODO needs testing all this stats
    /** Method that receives a String with Global/Anual Statistic's information, from the user
    *
    *@return String
    */
    public String getSTATSGlobal() throws NoStatsException{
        return user.getSTATS_PKC();
    }

     /** Method that receives a String with a year Statistic's information (Mensal), from the user
    * @param year
    *@return String
    */
    public String getSTATSGlobal(int year) throws NoStatsException{
        return user.getSTATS_PKC(year);
    }

     /** Method that receives a String with Statistic's information realted only to a month, from the user
    * @param month
    *@return String
    */
    public String getSTATS_Month(int month) throws NoStatsException{
        return user.getSTATSM_PKC(month);
    }

    /** @return the current year */
    public int getCurrentYear(){
        return user.getCurrentYear();
    }

    /* -------------- APPLICATION STATE ---------------------*/

    /** Method which defines what will be written by the
     *  ObjectOutputStream */
    private void writeObject (java.io.ObjectOutputStream stream)
     throws IOException {
        stream.writeObject(id);
        stream.writeObject(user);
        stream.writeObject(idAdmin);
        stream.writeObject(admin);
        stream.writeObject(userbase);
        stream.writeObject(cachebase);
        stream.writeObject(idcache);
        stream.writeObject(cache);
     }

    /** Method which defines what will be read by the
     *  ObjectInputStream */
    private void readObject (java.io.ObjectInputStream stream)
     throws IOException, ClassNotFoundException {
        id = (Double) stream.readObject();
        user = (NormalUser) stream.readObject();
        idAdmin = (Double) stream.readObject();
        admin = (Admin) stream.readObject();
        userbase = (UserBase) stream.readObject();
        cachebase = (CacheBase) stream.readObject();
        idcache = (Double) stream.readObject();
        cache = (Cache) stream.readObject();
     }

     /*TODO method named to "Look For Caches" */

    /*TODO toString equals clone*/
}
