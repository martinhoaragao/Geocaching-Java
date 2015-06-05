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

    /** @return User toString() result if a user is logged in */
    public String getUserInfo () throws NoUserLoggedInException {
        if (user == null)
            throw new NoUserLoggedInException("No user is logged in.");
        else return user.toString();
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

    /** Auxiliary function to print user information */
    private static void printInfo () throws NoUserLoggedInException {
        if (user == null)
            throw new NullPointerException();
        System.out.println(user.toString());
    }

    /**
     * Auxiliary function to create GregorianCalendar bdate to constructor user
     * With prints
     */
    private static GregorianCalendar typebdate(){
      boolean aux = true;
      String bbdate;
      GregorianCalendar bbbdate;
      String[] bdate_fields;
      int d,m,y,yy;
      Scanner sc = new Scanner(System.in);
      do {
        System.out.print("Birthdate (Day/Month/Year): ");
        bbdate = sc.nextLine();
        bdate_fields = bbdate.split("/");
        d = Integer.parseInt(bdate_fields[0]);
        m = Integer.parseInt(bdate_fields[1]);
        y = Integer.parseInt(bdate_fields[2]);
        GregorianCalendar Calendar  = new GregorianCalendar();
        yy = Calendar.get(Calendar.YEAR); //returns the current year yay.

        if(d <=0 || d >31 ) System.out.println("Day invalid!");
        else if(m <= 0 || m >12) System.out.println("Month invalid!");
        else if(y <=0 || y > yy) System.out.println("Year invalid!");
        else if( (y%4 != 0 && m == 2 && d>28) || (m == 4 && d > 30) || (m == 6 && d > 30) || (m == 9 && d > 30) || (m == 11 && d > 30)   )
        System.out.println("Date invalid!");

        else {
          aux = false;
          bbbdate = new GregorianCalendar(y,m,d);
          return bbbdate;
        }
        //when numm == 0 everything is ok.
      } while ( aux );
      return null;
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
                    sb.append(u.getName() + " - " + u.getMail());
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
            throw new NoUserLoggedInException();
        return user.getLastActivities();
    }

    /** Auxiliary function to display friend 10 last activities */
    private static void showFriendActivities () throws NoUserLoggedInException {
        ArrayList<Double> friends = user.getFriends();
        Scanner sc = new Scanner(System.in);
        NormalUser friend; String mail; Iterator it;
        ArrayList<Activity> acts;

        if (user == null)
            throw new NoUserLoggedInException();

        // Get friend e-mail
        System.out.print("Friend e-mail: ");
        mail = sc.nextLine().replaceAll("[\n\r]","");
        friend = userbase.getUserInfo(mail);

        if (friend == null)                     /* No user with the given mail */
            System.out.println("Theres no user with the given e-mail!");
        else if (!friends.contains(friend.getId()))  /* No friend with the give mail */
            System.out.println("You have no friends with the given e-mail!");
        else {
            acts = friend.getLastActivities();
            if (acts.size() != 0) {
               for (Activity act : acts)
                System.out.println(act.toString());
            } else
                System.out.println("Your friend has no activities yet!");
        }
    }

    /** Add a activity to the currently logged in user
     *  @param act  Activity to be added
     *  @param id   Id of the found cache
     */
    public void addActivity (Activity act, Double id) throws IllegalArgumentException, NullPointerException, NotAddedActivityYearIncorrectException, NoUserLoggedInException {
        Cache cache             = cachebase.getCache(id);
        ArrayList<String> reg   = null;

        if (user == null)
            throw new NoUserLoggedInException();

        if (cache == null)
            throw new IllegalArgumentException("No cache with the given id.");

        reg = cache.getRegistry();
        reg.add(user.getName() + " ( " + user.getMail() + " ) ");
        cache.setRegistry(reg);
        act.setCache(cache);
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

        System.out.println(info);
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
