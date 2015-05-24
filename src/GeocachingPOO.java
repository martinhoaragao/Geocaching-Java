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
    private static Double id = 1.0;            // User ID
    private static NormalUser user = null;     // User that is logged in
    private static Double idAdmin = 2.0;     // Admin ID
    private static Admin admin = null;     // Admin that is logged in
    private static UserBase userbase = null;   // User data base
    private static CacheBase cachebase = null;
    private static Double idcache = 1.0;
    private static Cache cache = null;
    private static Console c = System.console();

    //Random main method/function to complete.
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
    }

    /* ----------------- MENUS ----------------- */

    /** Display the main menu and return the user choice */
    private static int mainMenu () {
        Scanner sc = new Scanner(System.in);

        clean();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("3: Admin");
        System.out.println("4: Exit");

        return sc.nextInt();
    }


    /** Auxiliary function to display admin menu */
    private static int adminMenu () {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nLogged in as ADMIN: " + admin.getName() + " | " + admin.getPermi() + " power level");
        System.out.println("-------------------------");
        System.out.println("1: Report Menu");
        System.out.println("2: List Users and Admins");
        System.out.println("3: Show Caches");
        System.out.println("4: Delete user");
        System.out.println("5: Create new admin");
        System.out.println("6: Delete admin");
        System.out.println("10: Log Out");

        return sc.nextInt();
    }

    /** Auxiliary function to display user menu */
    private static int userMenu () {
        Scanner sc = new Scanner(System.in);
        ArrayList<Double> requests = user.getFriendRequests();

        clean();
        System.out.println("Logged in as: " + user.getName() + " | " + user.getPoints() + " points");
        System.out.println("-------------------------");
        if (requests.size() > 0)  /* There are friend requests */
            System.out.println("Friend Requests: " + requests.size());
        System.out.println("1: Personal Information");
        System.out.println("2: Create new cache");
        System.out.println("3: Friends");
        System.out.println("4: Show Caches");
        System.out.println("5: Show My Statistics");
        System.out.println("6: Show Last 10 activities");
        System.out.println("7: Show Friend Activities");
        System.out.println("8: Show My Caches");
        System.out.println("9: Add Activity");
        System.out.println("10: Log Out");

        return sc.nextInt();
    }

    /**Menu 1. Personal Info + Change Personal Info after this */
    private static void personalInfo(){
        int escolha = subpersonalInfo();
        switch (escolha) {
            case 1: changePassword(); break;
            case 2: changeName(); break;
            case 3: changeAddress(); break;
            case 4: changeBDate(); break;
            case 5: changeGender(); break;
            case 6: printInfo(); break;

            default: break;
        }
    }

    /** SubMenu PersonalInfo */
    private static int subpersonalInfo(){
        Scanner sc = new Scanner(System.in);

        clean();
        printInfo();
        System.out.println("1: Change Password");
        System.out.println("2: Change Name");
        System.out.println("3: Change Address");
        System.out.println("4: Change Birthdate");
        System.out.println("5: Change Gender");
        System.out.println("6: Return menu");

        return sc.nextInt();
    }

    private static void friendsMenu(){
        boolean running = true;
        int escolha;

        while (running) {
            escolha = friendsMenudisplay();
            switch (escolha) {
                case 1: sendFriendRequest(); break;
                case 2: showRequests(); break;
                case 3: acceptFriendRequest(); break;
                case 4: showFriends(); break;
                case 5: running = false; break;
            }
        }
    }

    private static int friendsMenudisplay(){
        Scanner sc = new Scanner(System.in);

        clean();
        System.out.println("1: Send Friend Request");
        System.out.println("2: Show Friend Requests");
        System.out.println("3: Accept Friend Request");
        System.out.println("4: Friends List");
        System.out.println("5: Main Menu");

        return sc.nextInt();
    }

    /* ------------------- REGISTER & LOGIN --------------------- */

    /** Auxiliary function to register new user */
    private static void register () {
        Scanner sc = new Scanner(System.in);
        String name, pass, country, city, mail = "";
        String[] bdate_fields;
        String bbdate;
        GregorianCalendar bdate = null;
        NormalUser newuser = new NormalUser();
        int bdate_return;
        MailValidator mv = new MailValidator();
        boolean aux = true, gender;
        int i=0; //Tries for the user. After 3rd try, menu to register closes. (Avoiding infinite loop).
        int d,m,y,yy;

        clean();
        do {

            if (i == 3) { System.out.println("Invalid e-mail!"); return; }
            System.out.print("E-mail: ");
            mail = sc.nextLine().replaceAll("[\n\r]","");
            aux = mv.validate(mail);
            if (!aux) System.out.println("Invalid e-mail! Please try a valid one.");
            i++;
        } while (!aux);

        if (userbase.userExists(mail)) {    // E-mail already in use
            System.out.println("E-mail already in use.");
            return;
        } else newuser.setMail(mail);

        // Get user name
        System.out.print("Name: ");
        newuser.setName(sc.nextLine().replaceAll("[\n\r]", ""));

        // Get user password
        if (c != null) {
            pass = new String(c.readPassword("Pass: "));
            newuser.setPass(pass);
        } else {
            System.out.print("Pass: ");
            newuser.setPass(sc.nextLine().replaceAll("[\n\r]",""));
        }

        //New code for the bdate approval
        GregorianCalendar bbbdate = typebdate();
        if(bbbdate != null){
            System.out.print("Country: ");
            country = sc.nextLine().replaceAll("[\n\r]","");
            System.out.print("City: ");
            city = sc.nextLine().replaceAll("[\n\r]","");
            System.out.print("Gender (g for girl, b for boy): ");
            if (sc.nextLine().replaceAll("[\n\r]","").equals("g"))
                newuser.setGender(true);
            else newuser.setGender(false);

            newuser.setAddress(city, country);
            newuser.setId(id);
            userbase.addUser(newuser);
            id = id + 1.0;
            newuser = null;
            System.out.println("User sucessfully created!");
        }

        if (c != null)
            c.readLine();
    }

    /** Auxiliary login function */
    private static void login (String type) {
        Scanner sc = new Scanner(System.in);
        String mail, pass;
        int i = 0;

        clean();
        do {
            i++;
            System.out.print("E-mail: ");
            mail = sc.nextLine().replaceAll("[\n\r]","");
            if (c != null)
                pass = new String(c.readPassword("Password: "));
            else {
                System.out.print("Password: ");
                pass = sc.nextLine().replaceAll("[\n\r]","");
            }

            if (type.equals("admin"))
                admin = userbase.getAdmin (mail, pass);
            else
                user = userbase.getUser(mail, pass);

            if(user == null && admin == null && i==3){
                System.out.println("E-mail or password were incorrect.");
                return;
            }
            if(user == null && admin == null){
                System.out.println("E-mail or password incorrect. Please try again.");
            }

        } while ((user == null && admin == null) );
    }

    /* ----------------- INFORMATION MODIFICATION --------------------*/


    /** Auxiliary function to print user information */
    private static void printInfo () {
        System.out.println(user.toString());
    }

    /** Auxiliary function to change user password */
    private static void changePassword() {
        String currentpass, newpass;
        Scanner sc = new Scanner(System.in);
        int i = 0; //3 trys to change password each time

        clean();

        // Give user 3 tries to insert current password
        do {
            if (i == 2){
                System.out.println(" Passwords don't match! ");
                return;
            }

            if (c != null)
                currentpass = new String(c.readPassword("Current Password: "));
            else {
                System.out.print("Current Password: ");
                currentpass = sc.nextLine().replaceAll("[\n\r]","");
            }
            i++ ;
        } while(i<3 && !user.confirmPass(currentpass));

        //If User sucessfully types current password, he may change it
        if (user.confirmPass(currentpass)) {
            if (c != null)
                newpass = new String(c.readPassword("New Password: "));
            else {
                System.out.print("New Password: ");
                newpass = sc.nextLine().replaceAll("[\n\r]","");
            }

            try {
                userbase.getUser(user.getMail(),currentpass).setPass(newpass);
                System.out.println("Sucessfuly changed password!");
            } catch (Exception e) {
                System.out.println("Error! Unable to change password!");
            }
            if (c != null)
                c.readLine();
            personalInfo();
        }
    }

    /** Auxiliary function to change User name */
    private static void changeName () {
        Scanner sc = new Scanner(System.in);

        clean();
        System.out.print("Name: ");
        try {
            user.setName(sc.nextLine().replaceAll("[\n\r]", ""));
            System.out.println("Name changed sucessfully!");
        } catch (Exception e) {
            System.out.println("Error! Unable to change name!");
        }
        if (c != null)
            c.readLine();
        personalInfo();
    }

    /** Auxiliary function to change User Address */
    private static void changeAddress () {
        Scanner sc = new Scanner(System.in);
        String city, country;

        clean();
        System.out.print("City: ");
        city = sc.nextLine().replaceAll("[\n\r]", "");
        System.out.print("Country: ");
        country = sc.nextLine().replaceAll("[\n\r]", "");

        try {
            user.setAddress(city, country);
            System.out.println("Sucessfuly changed Address.");
        }
        catch (Exception e) {
            System.out.println("Error! Unable to change Address!");
        }
        if (c != null) c.readLine();
        personalInfo();
    }

    /** Auxiliary function to change User birthdate */
    private static void changeBDate () {
        clean();
        GregorianCalendar bb = typebdate();
        if (bb != null) {
            user.setBDate(bb);
            System.out.println("Sucessfuly changed birthdate!");
        }

        if (c != null) c.readLine();
        personalInfo();
    }

    /** Auxiliary function to change User gender */
    private static void changeGender () {
        Scanner sc = new Scanner(System.in);

        clean();
        System.out.print("Gender (g for girl/b for boy): ");
        if (sc.nextLine().replaceAll("[\n\r]", "").equals("g"))
            user.setGender(true);
        else user.setGender(false);
        System.out.println("Sucessfuly changed gender.");

        if (c != null) c.readLine();
        personalInfo();
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
        do{
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
        }while( aux );
        return null;
    }

    /* -------------------- FRIENDS -------------------- */
    /** Auxiliary function to send a friend request */
    private static void sendFriendRequest () {
        Scanner sc = new Scanner(System.in);
        User u;

        /* Get user e-mail */
        System.out.print("Friend e-mail: ");
        String mail = sc.nextLine().replaceAll("[\n\r]", "");

        try {
            userbase.sendFriendRequest(user.getId(), mail);
            System.out.println("Friend Request Sent!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (c != null) c.readLine();
    }

    /** Auxiliary function to show friend requests */
    private static void showRequests () {
        ArrayList<Double> requests = user.getFriendRequests();
        User u;

        if (requests.size() == 0)
            System.out.println("There are no friend requests.");
        else {
            /* Iterate over friend requests */
            for (Double id : requests) {
                u = userbase.getUserInfo(id);
                System.out.println(u.getName() + " - " + u.getMail());
            }
        }
        if (c != null) c.readLine();
    }

    /** Auxiliary function to show friends */
    private static void showFriends () {
        User u;

        ArrayList<Double> friends = user.getFriends();

        if (friends.size() > 0 ) {  /* User has friends */
            System.out.println("Friends list:");

            for (Double id : friends) {
                u = userbase.getUserInfo(id);
                if (u != null)
                    System.out.println(u.getName() + " - " + u.getMail());
            }
        }
        else System.out.println("You have not added friends yet.");
        if (c != null) c.readLine();
    }

    /** Auxiliary function to accept friend requests */
    private static void acceptFriendRequest () {
        Scanner sc = new Scanner(System.in);
        String mail;

        /* Check if there are requests */
        if (user.getFriendRequests().size() == 0) {
            System.out.println("You have no friend requests.");
        } else {    /* Get e-mail to be accepted */
            System.out.print("Friend e-mail: ");
            mail = sc.nextLine().replaceAll("[\n\r]", "");
            userbase.acceptFriendRequest(user.getId(), mail);
            System.out.println("Friend request accepted!");
        }
        if (c != null) c.readLine();
    }

    /* ------------------- ACTIVITIES -------------------------*/

    /** Auxiliary function to display User 10 last activities */
    private static void showLastActivities () {
        ArrayList<Activity> acts = user.getLastActivities();
        Iterator it;

        clean();
        if (acts.size() == 0)
            System.out.println("You have no activities yet!");
        else {
            for (Activity act : acts)
                System.out.println(act.toString());
        }
        if (c != null) c.readLine();
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

    /** Auxiliary function to add a new Activity */
    private static void addActivity () {
        Scanner sc = new Scanner(System.in);
        Activity act = new Activity();  /* Activity to be added */
        Cache cache;                    /* Activity cache */
        GregorianCalendar date;         /* Activity date */
        Double cache_id, kms;           /* Cache id and kilometers */

        /* Get the activity date */
        clean();
        /* TODO: Change typebdate() to different function */
        System.out.print("Date: "); date = typebdate();
        /* Get the cache id */
        System.out.print("Cache id: "); cache_id = sc.nextDouble();
        /* Get the kilometeres covered */
        System.out.print("Kilometeres: "); kms = sc.nextDouble();
        /* Retrieve cache from the cachebase */
        try {
            cache = cachebase.getCache(cache_id);
            act.setCache(cache); act.setKms(kms);
            act.setDate(date); act.setPoints(20);
            /* TODO: Update to cache.getPoints() */
            user.addActivity(act);
            System.out.println("Activity sucessfully added!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (c != null) c.readLine();
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
        System.out.println("0. Show all caches + menu");
        return sc.nextInt();
    }

    /**Auxiliary function: Show all caches + menu
     * Menu Option: Shows all caches to * report one already, * Show details   ** Report this cache ** return to show all caches
     */
    private static void showAllCaches(){
        double n ;
        Scanner sc = new Scanner(System.in);
        ArrayList<Cache> caches = cachebase.getAllCaches();

        if(caches.size() == 0){ System.out.println("There are no caches. Sorry");
        return;
        }
        for(Cache c : caches){
            System.out.println(c.toString());
        }

        System.out.println("-------------------------");
        System.out.println("Cache Menu");
        System.out.println("-------------------------");

<<<<<<< HEAD
=======

>>>>>>> Modify 'src/User.java' to save all the activities the user registers, Update 'src/GeocachingPOO.java' due to User changes
        int o = CacheMenuaux();
        while(o!=0){

            switch (o) {
                case 0:
                for(Cache c : caches){
                    System.out.println("| ID : " + c.getId() + "| " + "Coords: " + c.getCoords().toString() + "Creator: " + c.getMail());
                }
<<<<<<< HEAD
=======

>>>>>>> Modify 'src/User.java' to save all the activities the user registers, Update 'src/GeocachingPOO.java' due to User changes

                CacheMenuaux();
                o = sc.nextInt();

                case 1:
                System.out.println("Type cache id to report");
                double u = sc.nextDouble();
                UserReportCache(u);
                System.out.println("Type 0 to return to Cache menu");
                if(sc.nextInt() == 0){
                    o = CacheMenuaux();
                }
                break;

                case 2:
                System.out.println("Type cache id to see all the treasures. (0 to leave)");
                n = sc.nextDouble();
                if(n == 0) break;
                else{
                    Iterator it = caches.iterator();
                    Cache cache;
                    while(it.hasNext()){
                        cache = (Cache) it.next();
                        if(cache.getId() == n){
                            ArrayList<Treasure> treasures = cache.getTreasure();
                            for(Treasure t : treasures) System.out.println(t.toString());
                        }
                        else{
                            System.out.println("There isn't such cache with that id. Sorry.");
                        }
                    }
                }
                //Nao sei se fará sentido chamar o menu aqui outra vez... se tiver mts treasures, talvez?
                //User ve os treasures todos e depois vê o menu outra vez para poder fazer outras coisas.. ?

                System.out.println("Type 0 to return to Cache menu");
                if(sc.nextInt() == 0){
                    o = CacheMenuaux();
                }

                break;

                case 3:
                System.out.println("Type the cache id to see registry book. (0 to leave)");
                n = sc.nextDouble();
                if(n == 0) break;
                else{
                    Iterator it = caches.iterator();
                    Cache cache;
                    while(it.hasNext()){
                        cache = (Cache) it.next();
                        if(cache.getId() == n){
                            ArrayList<String> regs = cache.getRegistry();
                            for(String s : regs) System.out.println(s);
                        }
                        else{
                            System.out.println("There isn't such cache with that id. Sorry.");
                        }
                    }
                }

                //
                System.out.println("Type 0 to return to Cache menu");
                if(sc.nextInt() == 0){
                    o = CacheMenuaux();
                }
                //
                break;

                case 4:
                System.out.println("Type the id of the cache to see more details. (0 to leave)");
                n = sc.nextDouble();
                if(n == 0) break;
                else{
                    Iterator it = caches.iterator();
                    Cache cache;
                    while(it.hasNext()){
                        cache = (Cache) it.next();
                        if(cache.getId() == n){
                            ArrayList<String> info = cache.getInfo();
                            for(String s : info) System.out.println(s);
                        }
                        else{
                            System.out.println("There isn't such cache with that id. Sorry.");
                        }
                    }

                    System.out.println("Report this cache? [y/n]");
                    if(!sc.nextLine().toUpperCase().contains("Y")){
                        break;
                    }
                    else{
                        UserReportCache(n);
                    }

                }
                //
                System.out.println("Type 0 to return to Cache menu");
                if(sc.nextInt() == 0){
                    o = CacheMenuaux();
                }
                //
                break;

                case 5:
                System.out.println("Leaving...");
                o=0;
                break;

                default:
                break;
            }
        }
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

    /**
     *   Auxiliary function for menu option 2. Create Cache.
     *
     *
     */
    private static void createCacheUser(){
        Scanner sc = new Scanner(System.in);
        Double id;

        clean();
        System.out.println("What type of cache do you want to create? (0 to cancel)");
        System.out.println("1: Tradicional");
        System.out.println("2: Multicache");
        System.out.println("3: Microcache");
        System.out.println("4: Mysterycache");
        int type = sc.nextInt();

        if (type==0) {
            System.out.println("Canceling...");
            return;
        }
        System.out.println("Type the latitude of the cache.");
        double lat = sc.nextDouble();
        System.out.println("Type the longitude of the cache.");
        double lon = sc.nextDouble();
        Coordinates coordinates = new Coordinates(lat, lon);
        //idcache variable.

        switch(type){
            case 1:
            cache = new TraditionalCache(idcache, coordinates, user.getMail());
            idcache++;
            break;

            case 2:
            cache = new MultiCache(idcache, coordinates, user.getMail());
            idcache++;
            break;

            case 3:
            cache = new MicroCache(idcache, coordinates, user.getMail());
            idcache++;
            break;

            case 4:
            cache = new MysteryCache(idcache, coordinates, user.getMail());
            idcache++;

            /* TODO: Add puzzle cache */
            System.out.println("Sucessfully created cache!\n" + cache.toString());
            break;

            default:
            break;
        }
        try {
            cachebase.addCache(user.getId(), cache);
            System.out.println("sucessfully created cache" + cache.toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (c != null) c.readLine();
    }

    /** Menu option 4. See Caches */
    private static void showCaches () {
        ArrayList<Cache> caches = cachebase.getAllCaches();

        for (Cache c : caches)
            System.out.println(c.toString());
    }

    /** Auxiliary function to show the logged in user caches */
    private static void showUserCaches () {
        ArrayList<Cache> caches = cachebase.getCaches(user.getId());

        clean();
        if (caches != null) {
            for (Cache c : caches)
                System.out.println(c.toString());
        }
        else System.out.println("You have not created any Cache!");

        if (c != null) c.readLine();
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

    /** Aux Function for us to clean the terminal when we call it */
    private final static void clean(){
       System.out.print("\u001b[2J" + "\u001b[H");
    }
}
