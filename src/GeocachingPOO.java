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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.Console;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;

public class GeocachingPOO {
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

    /* ----------------- MENUS ----------------- */

    /**Menu 1. Personal Info + Change Personal Info after this */
    private static void personalInfo(){
        int escolha = subpersonalInfo();
        switch (escolha) {

            default: break;
        }
    }

    /** SubMenu PersonalInfo */
    private static int subpersonalInfo(){
        Scanner sc = new Scanner(System.in);

        printInfo();
        System.out.println("1: Change Password");
        System.out.println("2: Change Name");
        System.out.println("3: Change Address");
        System.out.println("4: Change Birthdate");
        System.out.println("5: Change Gender");
        System.out.println("6: Return menu");

        return sc.nextInt();
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
    public static void login (String mail, String pass, boolean type) throws WrongPasswordException, IllegalArgumentException {
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
    public String getUserInfo () throws IllegalStateException {
        if (user == null)
            throw new IllegalStateException("No user is logged in.");
        else return user.toString();
    }

    /** Compare given password with the password of the current
     *  logged in user
     *  @param pass Password to be compared
     *  @return true if the password matches, false otherwise */
    public boolean confirmPass (String pass) {
        return user.confirmPass(pass);
    }

    /** Change logged in User Password
     *  @arg currentpass    The Current Password
     *  @arg newpass        New password
     */
    public void changePassword (String currentpass, String newpass) throws IllegalArgumentException, NullPointerException {
        if ((currentpass == null) || (newpass == null))
            throw new NullPointerException("Arguments can't be null!");

        if (this.confirmPass(currentpass))
            user.setPass(newpass);
        else
            throw new IllegalArgumentException("Current Password is wrong.");
    }

    /** Change logged in User Name
     *  @param name New name for the user */
    public static void changeName (String name) throws NullPointerException, IllegalStateException {
        user.setName(name);
    }

    /** Change logged in User Adrress
     *  @param address The new Address */
    public static void changeAddress (Address address) throws NullPointerException {
        user.setAddress(address);
    }

    /** Change logged in User Birthdate
     *  @param bdate GregorianCalendar Object with the new bdate */
    public static void changeBDate (GregorianCalendar bdate) {
        user.setBDate(bdate);
    }

    /** Change logged in User gender
     *  @param gender true for female, false for male */
    public static void changeGender (boolean gender) {
        user.setGender(gender);
    }

    /** Auxiliary function to print user information */
    private static void printInfo () {
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
    public void sendFriendRequest (String mail) {
        userbase.sendFriendRequest(user.getId(), mail);
    }

    /** Auxiliary function to show friend requests */
    public String getFriendRequests () {
        ArrayList<Double> requests = user.getFriendRequests();
        StringBuilder sb = new StringBuilder();

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
    public String getFriends () {
        User u;
        ArrayList<Double> friends = user.getFriends();
        StringBuilder sb = new StringBuilder();

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
    public static void acceptFriendRequest (String mail) throws NullPointerException, IllegalArgumentException {
        userbase.acceptFriendRequest(user.getId(), mail);
    }

    /* ------------------- ACTIVITIES -------------------------*/

    /** @return ArrayList with user 10 last activities */
    public ArrayList<Activity> getLastActivities () {
        return user.getLastActivities();
    }

    /** Auxiliary function to display friend 10 last activities */
    private static void showFriendActivities () {
        ArrayList<Double> friends = user.getFriends();
        Scanner sc = new Scanner(System.in);
        NormalUser friend; String mail; Iterator it;
        ArrayList<Activity> acts;

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
     *  @param id   Id of the found cache */
    public void addActivity (Activity act, Double id) throws IllegalArgumentException, NullPointerException {
        Cache cache = cachebase.getCache(id);

        if (cache == null)
            throw new IllegalArgumentException("No cache with the given id.");
        else {
            act.setCache(cache);
            user.addActivity(act);
        }
    }

    /* ------------------- CACHES ----------------------------*/

    /** Report a cache
     *  @param rep The report to be added */
    public void reportCache (Report rep) throws NullPointerException {
        rep.setMail(user.getMail());
        cachebase.addReport(rep);
    }

    /** Get all reports from the CacheBase */
    public TreeMap<Double, ArrayList<Report>> getAllReports (){
        return cachebase.getAllReports();
    }

    /**Menu pints */
    public static int CacheMenuaux(){
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Report one cache ");
        System.out.println("2. See treasures of a cache");
        System.out.println("3. Show registry book of a cache");
        System.out.println("4. Show other details of a cache");
        System.out.println("5. Leave cache menu");
        System.out.println("6. Show all caches again");
        return sc.nextInt();
    }

    /** @return ArrayList with clone of all of the caches */
    public ArrayList<Cache> getAllCaches () {
        return cachebase.getAllCaches();
    }

    /* ------------------------- ADMINS ------------------------*


    /** Create a new Admin if the logged in admin has permissions to do so
     *  @param new_admin Admin to be added */
    public void createAdmin (Admin new_admin) throws IllegalStateException, IllegalArgumentException, NullPointerException {
        if (new_admin == null)
            throw new NullPointerException("new_admin can't be null");

        new_admin.setId(idAdmin);
        userbase.addAdmin(new_admin);
        idAdmin++;
    }

    /** Delete an admin if the logged in user has permissions
     *  @param mail Mail of the admin to be deleted */
    public void deleteAdmin (String mail) throws IllegalStateException {
        if (this.admin.getPermi() != 2)
            throw new IllegalStateException("You have no permissions.");

        userbase.removeAdmin(mail);
    }

    /** Delete user given it's e-mail
     *  @param mail User e-mail */
    public void deleteUser (String mail) throws IllegalStateException, IllegalArgumentException {
        if (admin.getPermi() < 1)
            throw new IllegalStateException("You lack permission.");

        userbase.removeUser(mail);
    }

    private static void listUsers () {
        System.out.println (userbase.toString());
    }

    /**
     * Auxiliary function: Menu Option for Admin: Show all reported caches to perform menu options shown in the menuAdminReports()
     */

    /*private static void AdminReportsMenu () {
        double n; int u;
        Scanner sc = new Scanner(System.in);
        TreeMap<Double, ArrayList<Report>> reps = cachebase.getAllReports();

        System.out.println("Want to display ID's and report messages? [y/n]");
        if(!sc.nextLine().toUpperCase().contains("Y") ){

            for (Double id : reps.keySet()) {
                for (Report r : reps.get(id)) {
                    System.out.println("| ID : " + r.getId() + "| " );
                }
            }

            u = menuAdminReports();
        }
        else{
            showAllReports(); //shows id and message

            u = menuAdminReports();

        }

        while(u != 4){ //4 is the menu option to leave

            switch (u){
                case 1:
                System.out.println("Type the cache id to delete");
                n = sc.nextDouble();
                cachebase.delCache(n);

                System.out.println("Type 0 to return to Admin Reports Menu");
                if(u==0)
                    u = menuAdminReports();
                break;

                case 2:
                System.out.println("Type the report id to delete");
                n = sc.nextDouble();
                cachebase.delReport(n);

                System.out.println("Type 0 to return to Admin Reports Menu");
                if(u==0)
                    u = menuAdminReports();
                break;

                case 3:

                System.out.println("Type 0 to return to Admin Reports Menu");
                if(u==0)
                    u = menuAdminReports();
                break;

                default:
                break;
            }

            /*
             *  * 3. Show details of a cache.
             * (Sub-Menu)
             * 3.1 Delete this cache.
             * 3.2 Detele this report and maintain this cache.
             * 3.3 Return to show all reported caches
             *
             *
             * faltam estes menus mas nao acho que sejam mesmo necessários...
             * ele vê os ids e as mensagens, para que quer saber de quem as fez ou que?
             * é-lhe igual...
             * e iso de mostrar mais detalhes do report só ia acrescentar ao email.
             */
        /*}

        System.out.println("Type the ID of the cache to see details: (0 to leave)");
        if ((n = sc.nextDouble()) != 0) {
            for (Report rep : reps.get(n)) {
                System.out.println(rep.getMessage());
            }
        }
    }  */

    /**
     * Aux menu prints
     * 1. Delete one cache.
     * 2. Delete one report.
     * 3. Show details of a cache.
     * (Sub-Menu)
     * 3.1 Delete this cache.
     * 3.2 Detele this report and maintain this cache.
     * 3.3 Return to show all reported caches
     */
    private static int menuAdminReports(){
        Scanner sc = new Scanner(System.in);
        System.out.println("----------");
        System.out.println("1. Approve report and delete cache");
        System.out.println("2. Delete report and mantain cache");
        System.out.println("3. Show details of a report");
        System.out.println("4. Leave");
        return sc.nextInt();
    }

    /** Create a new cache associated to the currently logged in User
     *  @param type Cache type. 1: Traditional, 2: MultiCache, 3: MicroCache, 4: MysteryCache
     *  @param coord The cache coordinates */
    public static void createCache (int type, Coordinates coord) throws IllegalStateException, NullPointerException, IllegalArgumentException {
        Cache cache;

        switch (type) {
            case 1: cache = new TraditionalCache(idcache, coord, user.getMail());   break;
            case 2: cache = new MultiCache(idcache, coord, user.getMail());         break;
            case 3: cache = new MicroCache(idcache, coord, user.getMail());         break;
            case 4: cache = new MysteryCache(idcache, coord, user.getMail());       break;
            default:
                throw new IllegalArgumentException("type must be between 1 and 4.");
        }

        try {
            cachebase.addCache(user.getId(), cache);
            idcache++;
        } catch (Exception e) {
            throw e;
        }
    }

    /** Menu option 4. See Caches */
    private static void showCaches () {
        ArrayList<Cache> caches = cachebase.getAllCaches();

        for (Cache c : caches)
            System.out.println(c.toString());

        if (c != null) c.readLine();
    }

    /** Auxiliary function to show the logged in user caches */
    public ArrayList<Cache> getUserCaches () {
        ArrayList<Cache> caches = cachebase.getCaches(user.getId());

        if (caches != null) return caches;
        else return null;
    }

    /* ----------------------- STATISTICS -----------------*/
    //TODO  statistics year falta
    /** Menu option 5. Show My Statistics*/
    private static void showStatistics() {
        Scanner sc = new Scanner(System.in);
        System.out.println("My total points: " + user.getPoints());
        System.out.println("1. See my Month Statistics");
        if (sc.nextInt()==1)
        System.out.println(user.getStatistics().toString());
    }

    /** @return Current User id */
    public Double getCurrentUserId () {
        return this.id;
    }

}
