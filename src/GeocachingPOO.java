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
        this.userbase = new UserBase();
        this.cachebase = new CacheBase();
        this.admin = new Admin("grupoajm@gmail.com", "Alpha", "AdminAdmin", 1.0, 2);
        this.userbase.addAdmin(admin);
        this.cachebase = new CacheBase();
        this.idcache = 1.0;
        this.user = null;
        this.admin = null;
        this.cache = null;
    }

    /** Logout the User from the application */
    public void logout () {
        this.user = null;
    }

    /*//Random main method/function to complete.
    public static void main(String[] args) {
        boolean running = true;     // Set the program as running
        int option = 0;
        userbase = new UserBase();  // Create new user base
        cachebase = new CacheBase();
        admin = new Admin ("grupoajm@gmail.com", "Alpha", "AdminAdmin", 1.0, 2);
        userbase.addAdmin(admin);
        admin = null;


        while (running) {
            if (user == null && admin == null) { // No user logged in
                option = mainMenu();
                switch (option) {
                    case 1: register(); break;
                    case 2: login(""); break;
                    case 3: login("admin"); break;
                    case 4: running = false; break;
                    default: break;
                }
            } else if (admin != null) {
                option = adminMenu();
                switch (option) {
                    case 1: AdminReportsMenu(); break;
                    case 2: listUsers(); break;
                    case 3: showAllCaches(); break;
                    case 4: deleteUser(); break;
                    case 5: createAdmin(); break;
                    case 6: deleteAdmin(); break;
                    case 10: admin = null; break;
                    default: break;
                }
            } else {    // User logged in
                option = userMenu();
                switch (option) {
                    //case 1: printInfo(); break;
                    case 1: personalInfo(); break;
                    case 2: createCacheUser(); break;
                    case 3: friendsMenu(); break;
                    case 4: showAllCaches(); break;
                    case 5: showStatistics(); break;
                    case 6: showLastActivities(); break;
                    case 7: showFriendActivities(); break;
                    case 8: showUserCaches(); break;
                    case 9: addActivity(); break;
                    case 10: user = null; break;
                    default: break;
                }
            }
        }
    }*/

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
     * @param type User type, Normaluser or admin
     */
    public void login (String mail, String pass, String type) throws WrongPasswordException, IllegalArgumentException {
        if (type.equals("admin"))
            admin = userbase.getAdmin(mail, pass);
        else
            user = userbase.getUser(mail, pass);
    }

    /* ----------------- INFORMATION MODIFICATION --------------------*/


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
    public void showFriendActivities () {
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

    /**
     * Auxiliary function to report a cache
     * @param double id The cache id to be reported
     */
    private static void UserReportCache(double id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Are you sure you want to report this cache? [y/n]");
        if(!sc.nextLine().toUpperCase().contains("Y") ){
            //Não tem certeza de que quer reportar, logo nao introduziu y
            return;
        } else {

            String email = user.getMail();
            System.out.println("Reasons why you want to report this cache: ");
            String message = sc.nextLine();

            Report rep = new Report(id, email, message);

            cachebase.addReport(rep);
            System.out.println("sucessfully reported cache with id number of" + " " + id);

        }
        if (c != null) c.readLine();
    }

    /**Auxiliary funtion: Show all reports*/
    private static void showAllReports (){
        TreeMap<Double, ArrayList<Report>> reps = cachebase.getAllReports();

        for (Double id : reps.keySet()) {
            for (Report r : reps.get(id)) {
                System.out.println("| ID : " + r.getId() + "| " + "Msg: " + r.getMessage());
            }
        }
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


    /*
     + Create new Admin
     */
    private static void createAdmin () {
        Scanner sc = new Scanner(System.in);
        String name, pass, mail = "";
        Admin newuser = new Admin();
        boolean aux = false;
        MailValidator mv = new MailValidator();

        if (admin.getPermi() < 2) {
            System.out.println("You lack permission! Contact a superior admin.");
            return;
        }

        for (int i = 0; !aux && i<3; i++){

            System.out.print("E-mail: ");
                mail = sc.nextLine().replaceAll("[\n\r]","");
                aux = mv.validate(mail);
            if (!aux) {
                System.out.println("Invalid e-mail! Please try a valid one.");
            } else if (userbase.adminExists(mail)) {    // E-mail already in use
                System.out.println("E-mail already in use.");
                aux = false;
            } else {
                newuser.setMail(mail);
                aux = true;
            }
        }

        // Get admin name
        System.out.print("Name: ");
        newuser.setName(sc.nextLine().replaceAll("[\n\r]", ""));

        // Get admin password
        System.out.print("Pass: ");
        newuser.setPass(sc.nextLine().replaceAll("[\n\r]",""));

        // Get permissions
        System.out.println("\n<--Permissions-->\n\n0:\nAble to see Reports\nAble to invalidate Caches\n");
        System.out.println("1:\nAll of 0's abilities\nAble to create events\n");
        System.out.println("2:\nAll of 1's abilities\nAble to create new admins\n Able to remove admins");
        newuser.setPermi(sc.nextInt());

        newuser.setId(idAdmin);
        userbase.addAdmin(newuser);
        idAdmin = idAdmin + 1.0;
        newuser = null;
        System.out.println("Admin sucessfully created!");
    }

    /*
     * Delete an admin
     */
    private static void deleteAdmin () {
        if (admin.getPermi() < 2) {
            System.out.println("You lack permission! Contact a superior admin.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("To delete an Admin type his email: ");

        try {
            userbase.removeAdmin(sc.nextLine().replaceAll("[\n\r]",""));
            System.out.println("Sucessfully deleted admin");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Delete user
     */
    private static void deleteUser () {
        if (admin.getPermi() < 1) {
            System.out.println("You lack permission! Contact a superior admin.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("To delete a User type his email: ");

        try {
            userbase.removeUser(sc.nextLine().replaceAll("[\n\r]",""));
            System.out.println("Sucessfully delete user");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listUsers () {
        System.out.println (userbase.toString());
    }

    /**
     * Auxiliary function: Menu Option for Admin: Show all reported caches to perform menu options shown in the menuAdminReports()
     */

    private static void AdminReportsMenu () {
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
        }

        System.out.println("Type the ID of the cache to see details: (0 to leave)");
        if ((n = sc.nextDouble()) != 0) {
            for (Report rep : reps.get(n)) {
                System.out.println(rep.getMessage());
            }
        }
    }

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
    public void showStatistics() {
        
        Scanner sc = new Scanner(System.in);
        System.out.println("My total points: " + user.getPoints());
        System.out.println("1. See my Month Statistics");
        System.out.println("2. See Statistics of a given year");
        if (sc.nextInt()==1)
        System.out.println(user.getStatistics().toString());
        
        if(sc.nextInt()==2){
            //Do stuff
            
        }
        
    }

    /** @return Current User id */
    public Double getCurrentUserId () {
        return this.id;
    }

}
