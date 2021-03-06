import Exceptions.*;
/**
 * Class that will run the Geocaching Application, will display the menus and deal with
 * the I/O component.
 *
 * @author jfc, jp and ma
 * @version 06/06/2015
 */

import java.util.Scanner;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;
import java.io.Console;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import Exceptions.*;

public class Main implements Serializable {
    private static Console console = System.console();
    private static GeocachingPOO gc;
    private static boolean user_logged;         /* Control if a user is logged in */
    private static boolean admin_looged;        /* Control if a admin is logged in */

    /**
     * main initializes the GeocahingPOO instance and controls the main menu, and the
     * first layer of the user menu and the admin menu
     */
    public static void main (String args[]) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        /* Create GeocachingPOO Object */
        gc = new GeocachingPOO();

        while (running) {
            if (!user_logged && !admin_looged) {  /* No user or admin logged in */
                mainMenu();

                switch (sc.nextInt()) {
                    case 1: register();       break;
                    case 2: login(false);     break;
                    case 3: login(true);      break;
                    case 4: saveState();      break;
                    case 5: loadState();      break;
                    case 6: running = false;  break;
                    default: break;
                }
            } else if (user_logged) {             /* User logged in */
                userMenu();

                switch (sc.nextInt()) {
                    case 1: infoMenu();                 break;
                    case 2: cachesMenu();               break;
                    case 3: friendsMenu();              break;
                    case 4: activititesMenu();          break;
                    case 5: mystatsMenu();              break;
                    case 6: logout();                  break;
                    default: break;
                }
            } else {                              /* Admin logged in */
                adminMenu();

                switch (sc.nextInt()) {
                    case 1: reporstMenu();        break;
                    case 2: displayUsersAdmins(); break;
                    case 3: displayAllCaches();   break;
                    case 4: deleteUser();         break;
                    case 5: createAdmin();        break;
                    case 6: deleteAdmin();        break;
                    case 7: logout();             break;
                    default: break;
                }
            }
        }
    }

    /* ---------------------------- MENUS ----------------------------*/

    /**
     * Display the main menu
     */
    private static void mainMenu () {
        clean();

        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("3: Admin");
        System.out.println("4: Save Application State");
        System.out.println("5: Load Application State");
        System.out.println("6: Exit");
    }

    /**
     * Display the user menu
     */
    private static void userMenu () {
        NormalUser user;

        clean();
        try {
            user = gc.getLoggedUser();
            System.out.println(user.getName() + " | " + user.getPoints() + " points");
            System.out.println("1: Personal Information");
            System.out.println("2: Caches");
            System.out.println("3: Friends");
            System.out.println("4: Activities");
            System.out.println("5: Statistics");
            System.out.println("6: Logout");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Display and control the personal information menu
     */
    private static void infoMenu () {
        Scanner sc = new Scanner(System.in);
        boolean done = false;

        while (!done) {
            clean();
            try {
                System.out.println(gc.getLoggedUser().toString());
                System.out.println("1: Change Password");
                System.out.println("2: Change Name");
                System.out.println("3: Change Address");
                System.out.println("4: Change Birthdate");
                System.out.println("5: Change Gender");
                System.out.println("6: Return menu");

                switch (sc.nextInt()) {
                    case 1: changePassword(); break;
                    case 2: changeName();     break;
                    case 3: changeAddress();  break;
                    case 4: changeBDate();    break;
                    case 5: changeGender();   break;
                    case 6: done = true;      break;
                    default: break;
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Display Admin menu
     */
    private static void adminMenu () {
        clean();
        System.out.println("1: Report Menu");
        System.out.println("2: List Users and Admins");
        System.out.println("3: Show Caches");
        System.out.println("4: Delete user");
        System.out.println("5: Create new admin");
        System.out.println("6: Delete admin");
        System.out.println("7: Log Out");
    }

    /**
     * Display and control friends menu
     */
    private static void friendsMenu () {
        Scanner sc = new Scanner(System.in);
        boolean done = false;

        while (!done) {
            clean();
            System.out.println("1: Friends List");
            System.out.println("2: Send Friend Request");
            System.out.println("3: Show Friend Requests");
            System.out.println("4: Accept Friend Request");
            System.out.println("5: Return");

            switch (sc.nextInt()) {
                case 1: showFriends();    break;
                case 2: sendRequest();    break;
                case 3: showRequests();   break;
                case 4: acceptRequest();  break;
                case 5: done = true;      break;
                default: break;
            }
        }
    }

    /**
     * Display and control the Caches menu
     */
    private static void cachesMenu () {
        Scanner sc = new Scanner(System.in);
        boolean done = false;

        while (!done) {
            clean();
            System.out.println("1: Create a cache");
            System.out.println("2: Report one cache ");
            System.out.println("3: Show all caches");
            System.out.println("4: Show my caches");
            System.out.println("5: Find caches near a position");
            System.out.println("6: Invalidate my cache");
            System.out.println("7: Return");

            switch (sc.nextInt()) {
                case 1: createCache();          break;
                case 2: reportCache();          break;
                case 3: displayAllCaches();     break;
                case 4: displayUserCaches();    break;
                case 5: getNearCaches();        break;
                case 6: invalidateCache();      break;
                case 7: done = true;            break;
                default: break;
            }
        }
    }

    /**
     * Display and control the Activities menu
     */
    private static void activititesMenu () {
        Scanner sc = new Scanner(System.in);
        boolean done = false;

        while (!done) {
            clean();
            System.out.println("1: Add Activity");
            System.out.println("2: Show my last activitites");
            System.out.println("3: Show friend last activitites");
            System.out.println("4: Show all my activitites");
            System.out.println("5: Remove activity");
            System.out.println("6: Return");

            switch (sc.nextInt()) {
                case 1: createActivity();           break;
                case 2: displayLastActivities();    break;
                case 3: displayFriendActivities();  break;
                case 4: displayAllActivities();     break;
                case 5: removeActivity();           break;
                case 6: done = true;            break;
                default: break;
            }
        }
    }

    /**
     * Display and control the Admin Reports menu
     */
    private static void reporstMenu () {
        Scanner sc = new Scanner(System.in);
        boolean done = false;

        while (!done) {
            clean();

            displayReports();

            System.out.println("1: Invalidate a cache");
            System.out.println("2: Go back to Menu");

            switch (sc.nextInt()) {
                case 1: invalidateCache();  break;
                case 2: done = true;        break;
                default: break;
            }
        }
    }

    /**
     * Display and control the Statistics menu
     */
    private static void mystatsMenu() {
        Scanner sc    = new Scanner(System.in);
        boolean done  = false;

        while(!done){
            clean();
            System.out.println("1. Global Stats");
            System.out.println("2. Year Stats");
            System.out.println("3. Mensal Stats");
            System.out.println("4. Graphic #caches Mensal");
            System.out.println("5: Return");

            switch (sc.nextInt()) {
                case 1: displayGlobalStats();       break;
                case 2: displayMensalStats();       break;
                case 3: displayaMonthStats();       break;
                case 4: displayNumberCachesTypes(); break;
                case 5: done = true;                break;
                default: break;
            }
        }
    }

    /* ----------------------- REGISTER & LOGIN ----------------------*/

    /**
     * Auxiliary function to register a user, controls the I/O and the calls to
     * GeocachingPOO instance
     */
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
        int i = 0;
        int d,m,y,yy;

        clean();

        // Get User E-mail
        do {
            if (i == 3) { System.out.println("Invalid e-mail!"); return; }
            System.out.print("E-mail: ");
            mail = sc.nextLine().replaceAll("[\n\r]","");
            aux = mv.validate(mail);
            if (!aux) System.out.println("Invalid e-mail! Please try a valid one.");
            i++;
        } while (!aux);
        newuser.setMail(mail);

        // Get user name
        System.out.print("Name: ");
        newuser.setName(sc.nextLine().replaceAll("[\n\r]", ""));

        // Get user password
        if (console != null) {
            pass = new String(console.readPassword("Pass: "));
            newuser.setPass(pass);
        } else {
            System.out.print("Pass: ");
            newuser.setPass(sc.nextLine().replaceAll("[\n\r]",""));
        }

        //New code for the bdate approval
        GregorianCalendar bbbdate = typeDate();
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
            newuser.setId(gc.getCurrentUserId());
            newuser.setBDate(bbbdate);
            try {
                gc.register(newuser);
                System.out.println("User successfully created!");
            } catch (Exception e) { System.out.println(e.getMessage()); }
        }

        if (console != null)
            console.readLine();
    }

    /**
     * Auxiliary function to login
     */
    private static void login (boolean type) {
        String mail, pass;
        Scanner sc = new Scanner(System.in);

        clean();
        System.out.print("E-mail: ");
        mail = sc.nextLine().replaceAll("[\n\r]", "");
        if (console != null)
            pass = new String(console.readPassword("Password:"));
        else {
            System.out.print("Password: ");
            pass = sc.nextLine().replaceAll("[\n\r]", "");
        }

        try {
            gc.login(mail, pass, type);
            if (type) admin_looged = true;
            else user_logged = true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            if (console != null) console.readLine();
        }
    }

    /**
     * Logout from GeocachingPOO
     */
    private static void logout () {
        gc.logout();
        if (user_logged)  user_logged = false;
        if (admin_looged) admin_looged = false;
    }

    /* --------------------------- USER INFOS ------------------------*/

    /**
     * Controls the I/O and the messages sent to GeocachingPOO to change the currently
     * logged in user's password
     */
    private static void changePassword() {
        String currentpass = null, newpass = null;
        Scanner sc = new Scanner(System.in);
        int tries = 0;
        boolean pass_confirmation = false;

        clean();
        /* Give User 3 tries to input the current password */
        do {
            if (tries == 2) {
                System.out.println(" Passwords don't match! ");
            } else {
                /* Get User current password */
                if (console != null)
                    currentpass = new String(console.readPassword("Current Password: "));
                else {
                    System.out.print("Current Password: ");
                    currentpass = sc.nextLine().replaceAll("[\n\r]","");
                }
            }
            tries ++;
            try {
                pass_confirmation = gc.confirmPass(currentpass);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while ((tries < 3) && !pass_confirmation);

        /* Change user password */
        if (tries != 3) {
            if (console != null)
                newpass = new String(console.readPassword("New Password: "));
            else {
                System.out.print("New Password: ");
                newpass = sc.nextLine().replaceAll("[\n\r]","");
            }

            try {
                gc.changePassword(currentpass, newpass);
                System.out.println("Sucessfuly changed password!");
            } catch (Exception e) {
                System.out.println("Error! Unable to change password!");
            }
            if (console != null) console.readLine();
        }
    }

    /**
     * Controls the I/O and the messages sent to GeocachingPOO to change the currently
     * logged in user's name
     */
    private static void changeName () {
        Scanner sc = new Scanner(System.in);

        clean();
        System.out.print("New Name: ");
        try {
            gc.changeName(sc.nextLine().replaceAll("[\n\r]", ""));
            System.out.println("Name changed sucessfully!");
        } catch (Exception e) {
            System.out.println("Unable to change name: " + e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /**
     * Controls the I/O and the messages sent to GeocachingPOO to change the currently
     * logged in user's address
     */
    private static void changeAddress () {
        Scanner sc = new Scanner(System.in);
        Address add;
        String city, country;

        clean();
        System.out.print("City: ");
        city = sc.nextLine().replaceAll("[\n\r]", "");
        System.out.print("Country: ");
        country = sc.nextLine().replaceAll("[\n\r]", "");
        add = new Address(city, country);

        try {
            gc.changeAddress(add);
            System.out.println("Sucessfuly changed Address.");
        } catch (Exception e) {
            System.out.println("Error! Unable to change Address!");
        }
        if (console != null) console.readLine();
    }

    /**
     * Controls the I/O and the messages sent to GeocachingPOO to change the currently
     * logged in user's birthdate
     */
    private static void changeBDate () {
        clean();
        GregorianCalendar bb = typeDate();
        try {
            gc.changeBDate(bb);
            System.out.println("Successfully changed birthdate.");
        }
        catch (Exception e) {
            System.out.println("Unable to change birthdate: " + e.getMessage());
        }
    }

    /**
     * Controls the I/O and the messages sent to GeocachingPOO to change the currently
     * logged in user's gender
     */
    private static void changeGender () {
        Scanner sc = new Scanner(System.in);
        boolean gender;

        clean();
        System.out.print("Gender (F: Female | M: Male): ");
        gender = sc.nextLine().replaceAll("[\n\r]","").equals("F");
        System.out.println(gender);
        try {
            gc.changeGender(gender);
            System.out.println("Successfully change gender.");
        } catch (Exception e) {
            System.out.println("Unable to change gender: " + e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /* ----------------------- CACHES ----------------------------*/

    /**
     * Controls the I/O and the messages sent to GeocachingPOO to create different types
     * of caches, the created cache will be associated with the currently logged in user
     */
    private static void createCache () {
        Scanner sc = new Scanner(System.in);
        Double lat, lon;                /* Latitude and Longitude */
        String info;                    /* Cache Information */
        ArrayList<String> treasures;    /* Cache Treasures */
        ArrayList<Coordinates> coords;  /* Coordinates */
        int type, stages;               /* Cache type and numer of stages for Muticache */
        String question, answer;        /* For MysteryCache */

        clean();
        coords      = new ArrayList<Coordinates>();
        treasures   = new ArrayList<String>();
        question = answer = null;

        System.out.println("What type of cache do you want to create? (0 to cancel)");
        System.out.println("1: Tradicional");
        System.out.println("2: Multicache");
        System.out.println("3: Microcache");
        System.out.println("4: Mysterycache");

        type = sc.nextInt();
        if (type == 1) {                            /* Traditional Cache */
            System.out.print("Latitude: ");         lat = sc.nextDouble();
            System.out.print("Longitude: ");        lon = sc.nextDouble();
            sc.nextLine();                          /* Flush Scanner */
            System.out.print("Treasure: ");
            treasures.add(sc.nextLine().replaceAll("[\n\r]", ""));
            System.out.print("More information: "); info = sc.nextLine();

            try {
                gc.createTraditionalCache(new Coordinates(lat, lon), treasures, info);
                System.out.println("Cache successfully created.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (type == 2) {                     /* MultiCache */
            System.out.print("Number of stages: "); stages = sc.nextInt();
            for (int i = 0; i < stages; i++) {
                System.out.print("Latitude: ");     lat = sc.nextDouble();
                System.out.print("Longitude: ");    lon = sc.nextDouble();
                coords.add(new Coordinates(lat, lon));
            }
            sc.nextLine();                          /* Flush Scanner */
            System.out.print("Treasure: ");
            treasures.add(sc.nextLine().replaceAll("[\n\r]", ""));
            System.out.print("More information: "); info = sc.nextLine();

            try {
                gc.createMultiCache(coords, treasures, info);
                System.out.println("Cache successfully created.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (type == 3) {                     /* Micro Cache */
            System.out.print("Latitude: ");         lat = sc.nextDouble();
            System.out.print("Longitude: ");        lon = sc.nextDouble();
            sc.nextLine();                          /* Flush Scanner */
            System.out.print("More information: "); info = sc.nextLine();

            try {
                gc.createMicroCache(new Coordinates(lat, lon), info);
                System.out.println("Cache successfully created.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (type == 4) {                     /* Micro Cache */
            System.out.print("Latitude: ");         lat = sc.nextDouble();
            System.out.print("Longitude: ");        lon = sc.nextDouble();
            sc.nextLine();                           /* Flush Scanner */
            System.out.print("Question: ");         question = sc.nextLine();
            System.out.print("Answer: ");           answer = sc.nextLine();
            System.out.print("Treasure: ");
            treasures.add(sc.nextLine().replaceAll("[\n\r]", ""));
           System.out.print("More information: "); info = sc.nextLine();

            try {
                gc.createMysteryCache(new Coordinates(lat, lon), treasures, info, new Puzzle(question, answer));
                System.out.println("Cache successfully created.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else System.out.println("Invalid option.");
        if (console != null) console.readLine();
    }

    /**
     * Auxiliary function to report a cache, the Report will be associated with the
     * currently logged in user
     */
    private static void reportCache () {
        Scanner sc = new Scanner(System.in);
        String message = ""; Double id;
        Report rep;

        clean();
        System.out.print("Cache id: ");
        id = sc.nextDouble();
        message = sc.nextLine();  /* To flush input */
        while (message.trim().equals("")) {
            System.out.print("Why are you reporting this cache: ");
            message = sc.nextLine();
        }
        rep = new Report();
        rep.setId(id); rep.setMessage(message);
        try {
            gc.reportCache(rep);
            System.out.println("Successfully reported cache!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /**
     * Display the currently logged in user caches if the user has created any
     */
    private static void displayUserCaches() {
        ArrayList<Cache> caches = gc.getUserCaches();

        clean();
        if (caches != null)
            for (Cache c : caches)
                System.out.println(c.toString());
        else
            System.out.println("You have not created any caches yet.");

        if (console != null) console.readLine();
    }

    /**
     * Display all caches that exist in GeocachingPOO
     */
    private static void displayAllCaches () {
        ArrayList<Cache> caches = gc.getAllCaches();

        clean();
        if (caches.size() == 0)
            System.out.println("There are no caches.");
        else
            for (Cache c : caches)
                System.out.println("Type of cache: "+ c.getClass().getName().toString() +".\n "+ c.toString());
        if (console != null) console.readLine();
    }

    /**
     * Display all the reports that exist in GeocachingPOO
     */
    private static void displayReports () {
        TreeMap <Double, ArrayList<Report>> tm = null;
        try {
            tm = gc.getAllReports();
        } catch (Exception e) {
            System.out.println("There is no user logged in.");
        }

        clean();
        if (tm.size() == 0) {
            System.out.println("There are no reports.");
        } else {
            for (Double id : tm.keySet())
                for (Report rep : tm.get(id))
                    System.out.println(rep.toString());
        }
    }

    /**
     * Auxiliary function to display the registry of a cache specified by the user
     */
    private static void displayCacheRegistry () {
        Scanner sc = new Scanner(System.in);
        Double id;                            /* Cache id */
        ArrayList<String> registry;           /* Cache registry */

        clean();
        System.out.print("Cache id: ");
        id = sc.nextDouble();

        try {
            registry = gc.getCacheRegistry(id);
            if (registry.size() == 0)
                System.out.println("No one found this cache yet.");
            else
                for (String s : registry)
                    System.out.println(s);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /**
     * Auxiliary function to display the treasures of a cache specified by the user
     */
    private static void displayCacheTreasures () {
        Scanner sc = new Scanner(System.in);
        Double id;
        ArrayList<String> treasures;

        clean();
        System.out.print("Cache id: ");
        id = sc.nextDouble();

        try {
            treasures = gc.getCacheTreasures(id);

            if (treasures.size() == 0)
                System.out.println("This cache has no Treasures.");
            else
                for (String t : treasures) {
                    System.out.println(t);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /**
     *  Auxiliary function to display All info of a given Cache
     *  its ID, Coordinates, who created it, its registry and its
     *  Treasures
     */
    private static void displayAllCacheInfo () {
        Scanner                 sc = new Scanner(System.in);
        Double                  id;
        ArrayList<String>       treasures;
        ArrayList<String>       registry;
        String                  info;

        clean();
        System.out.print("Cache id: ");
        id = sc.nextDouble();

        try {
            treasures   = gc.getCacheTreasures(id);
            registry    = gc.getCacheRegistry(id);
            info        = gc.getCache(id).toString();

            System.out.println(info);
            System.out.println("REGISTRY: ");
            if (registry.size() == 0)
                System.out.println("No one found this cache yet.");
            else
                for (String s : registry) {
                    System.out.println(s);
                }
            System.out.println("TREASURES: ");
            if (treasures.size() == 0)
                System.out.println("This Cache has no Treasures.");
            else
                for (String t : treasures) {
                    System.out.println(t);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /**
     * Display list of caches near a location given it's coordinates and a radius, both
     * will be specified by the user
     */
    private static void getNearCaches () {
        Scanner sc = new Scanner(System.in);
        Double lat, lon, radius;
        TreeMap<Double, ArrayList<Cache>> map;

        clean();
        System.out.print("Latitude: ");     lat = sc.nextDouble();
        System.out.print("Longitude: ");    lon = sc.nextDouble();
        System.out.print("Radius: ");       radius = sc.nextDouble();

        try {
            map = gc.getNearCaches(new Coordinates(lat, lon), radius);

            if (map.size() != 0) {
                for (Double dist : map.keySet()) {
                    System.out.println(dist + " Kms");  /* Print distance */

                    for (Cache c : map.get(dist)) {
                        System.out.println(c.toString());
                    }
                }

            } else System.out.println("There are no caches near.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (console != null) console.readLine();
    }

    /**
     * Display all activities of the currently logged in user
     */
    private static void displayAllActivities () {
        TreeSet<Activity> ts;
        Iterator it;
        Activity aux;

        try {
            ts = gc.getActivities();
            it = ts.descendingIterator();

            while (it.hasNext()) {
                aux = (Activity) it.next();
                System.out.println(aux.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /**
     * Remove a activities that belongs to the currently logged in user
     */
    private static void removeActivity () {
        Scanner sc = new Scanner(System.in);
        TreeSet<Activity> act_ts;           /* TreeSet with all the activities */
        ArrayList<Activity> act_list;       /* To create links between indexes and caches */
        Iterator it;                        /* To iterate over the TreeSet */
        Activity act;                       /* Auxiliary variable */
        int act_num;                        /* Activity num */

        clean();
        try {
            act_ts      = gc.getActivities();
            act_list    = new ArrayList<Activity>();
            it          = act_ts.descendingIterator();


            /* Copy all the activities to the ArrayList */
            while (it.hasNext()) {
                act = (Activity) it.next();
                act_list.add(act);
            }

            /* Display all Activities */
            for (int i = 0; i < act_list.size(); i++) {
                System.out.println("Activity number -> " + (i+1));
                System.out.println(act_list.get(i).toString());
            }

            /* Get user choice */
            System.out.print("Activity to delete: "); act_num = sc.nextInt();

            if (act_num > act_list.size())
                System.out.println("There is no activity with that number...");
            else {
                gc.removeActivity(act_list.get(act_num - 1));
                System.out.println("Successfully deleted activity");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /* ------------------------- FRIENDS -------------------------*/

    /**
     * Auxiliary function to send a friend request, the request will be associated with
     * the currently logged in user
     */
    private static void sendRequest () {
        Scanner sc = new Scanner(System.in);

        clean();
        System.out.print("Friend e-mail: ");
        try {
            gc.sendFriendRequest(sc.nextLine());
            System.out.println("Friend Request sent.");
        } catch (Exception e) {
            System.out.println("Unable to send friend request: " + e.getMessage());
        }

        if (console != null) console.readLine();
    }

    /**
     * Display all the friend requests of the currently logged in user
     */
    private static void showRequests () {
        String requests = null;
        try {
            requests = gc.getFriendRequests();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        clean();
        if (requests == null)
            System.out.println("You have no friend requests.");
        else
            System.out.println(requests);

        if (console != null) console.readLine();
    }

    /**
     * Auxiliary function to accept a friend request of the currently logged in user
     */
    private static void acceptRequest () {
        Scanner sc = new Scanner(System.in);

        clean();
        System.out.print("User e-mail: ");
        try {
            gc.acceptFriendRequest(sc.nextLine().replaceAll("[\n\r]",""));
            System.out.println("Friend sucessfully added.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /**
     * Display friends list of the currently logged in user
     */
    private static void showFriends () {
        clean();
        try {
            System.out.println(gc.getFriends());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /**
     * Auxiliary function to display a given friend, sppecified by the user,
     * 10 most recent activities
     */
    private static void displayFriendActivities () {
        Scanner sc = new Scanner(System.in);
        String mail;
        ArrayList<Activity> acts = null;

        clean();
        System.out.print("Friend e-mail: ");
        mail = sc.nextLine().replaceAll("[\n\r]","");

        try {
            acts = gc.getFriendLastActivities(mail);

            if (acts.size() == 0)
                System.out.println("Your friend has no activitites yet.");
            else
                for (Activity act : acts) {
                    System.out.println(act.toString());
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /* ------------------------- ACTIVITIES -----------------------*/

    /**
     *  Auxiliary function to create a new Activity.
     */
    private static void createActivity () {
        Scanner sc = new Scanner(System.in);
        GregorianCalendar date;
        Double id;
        Cache cache = null;

        clean();
        System.out.print("Date: "); date = typeDate();
        System.out.print("Cache id: "); id = sc.nextDouble();

        //Mystery Cache answer right the question
        try{
            cache = gc.getCache(id); //get the cache to see if its a mystery cache

            if (cache instanceof MysteryCache){
                MysteryCache mys = (MysteryCache) cache;
                System.out.println("Q: " + mys.getPuzzle().getQuestion().toString());
                System.out.println("What's the correct answer? ");
                String answer = sc.next();

                if(answer.toLowerCase().contains(mys.getPuzzle().getAnswer().toLowerCase())    ){
                    System.out.println("Correct! Here are the coordinates: " + mys.getCoords().toString());
                    gc.addActivity(id, date);
                    System.out.println("Successfully added activity!");
                } else{
                    System.out.println("Nope.");
                }
            } else {
                gc.addActivity(id, date);
                System.out.println("Successfully added activity!");
            }
        } catch(Exception e ) {
            System.out.println(e.getMessage());
        }

        if (console != null) console.readLine();
    }

    /**
     * Display the currently logged in user 10 most recent activities
     */
    private static void displayLastActivities () {
        ArrayList<Activity> acts = null;
        try {
            acts = gc.getLastActivities();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        clean();
        if (acts.size() == 0)
            System.out.println("You have no activitites yet.");
        else {
            for (Activity a : acts)
                System.out.println(a.toString());
        }
        if (console != null) console.readLine();
    }

    /* ------------------------- STATISTIC -----------------------*/

    /**
     * Display the currently logged in user global statistics
     */
    private static void displayGlobalStats () {
        try{
            System.out.println(gc.getSTATSGlobal());
        }
        catch(Exception e){ System.out.println(e.getMessage()); }
    }

    /**
     * Display the currently logged in user anual statistics, given a year,
     * specified by the user
     */
    private static void displayMensalStats () {

        System.out.println("Type the year if you want to specify or type 0");
        Scanner sc = new Scanner(System.in);
        int year = sc.nextInt();
        if(year == 0){
            year = gc.getCurrentYear(); //returns the current year function present in normaluser and passed/available in geocaching
        }

        try{
            System.out.println(gc.getSTATSGlobal(year));
        }
        catch(Exception e){ System.out.println(e.getMessage()); }

        if (console != null) console.readLine();
    }

    /**
     * Display the currently logged in user monthly statistics, given a month,
     * specified by the user
     */
    private static void displayaMonthStats () {
        System.out.println("Type the month you want to look at: ");
        Scanner sc = new Scanner(System.in);
        int month = sc.nextInt();

        int year = gc.getCurrentYear();

        try{
            System.out.println(gc.getSTATS_Month(month-1));
        }
        catch(Exception e){ System.out.println(e.getMessage()); }

        if (console != null) console.readLine();
    }

    /**
     * Display a graph with the number of types of caches found for a given month,
     * specified by the user
     */
    private static void displayNumberCachesTypes () {
        System.out.println("Type the month you want to look at: ");
        Scanner sc = new Scanner(System.in);
        int month = sc.nextInt();
        int year = gc.getCurrentYear();
        try{
            System.out.println(gc.getStatistic(year).printTypesCaches(month).toString() ) ;
        }catch(Exception e){System.out.println(e.getMessage());}

    }

    /* -------------------- ADMIN --------------------------------*/

    /**
     * Display list of users and admins
     */
    private static void displayUsersAdmins () {
        ArrayList<Admin> admins = gc.getAdmins();
        ArrayList<NormalUser> users = gc.getUsers();

        clean();
        if (users.size() == 0) System.out.println("There are no users.");
        else {
            System.out.println("--- USERS ---");
            for (NormalUser u : users)
                System.out.println(u.toString());
        }

        if (admins.size() == 0) System.out.println("There are no admins");
        else {
            System.out.println("--- ADMINS ---");
            for (Admin ad : admins)
                System.out.println(ad.toString());
        }
        if (console != null) console.readLine();
    }

    /**
     * Delete a user given its e-mail, to be used by an Admin
     */
    private static void deleteUser () {
        Scanner sc = new Scanner(System.in);

        clean();
        System.out.print("User e-mail: ");
        try {
            gc.deleteUser(sc.nextLine().replaceAll("[\n\r]",""));
            System.out.println("User successfully deleted.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /**
     * Create a new Admin if the currently logged admin has the permissions to do so
     */
    private static void createAdmin () {
        Scanner sc = new Scanner(System.in);
        String name, pass, mail = "";
        Admin new_admin = new Admin();
        boolean valid_mail = false;
        MailValidator mv = new MailValidator();

        clean();
        /* Get Admin mail */
        for (int i = 0; !valid_mail && i<3; i++){
            System.out.print("E-mail: ");
            mail = sc.nextLine().replaceAll("[\n\r]","");
            valid_mail = mv.validate(mail);
            if (!valid_mail) {
                System.out.println("Invalid e-mail! Please try a valid one.");
                valid_mail = false;
            } else {
                new_admin.setMail(mail);
                valid_mail = true;
            }
        }

        if (valid_mail) {
            /* Get Admin name */
            System.out.print("Name: ");
            new_admin.setName(sc.nextLine().replaceAll("[\n\r]",""));

            /* Get Admin password */
            if (console != null)
                new_admin.setPass(new String(console.readPassword("Password: ")));
            else {
                System.out.print("Password: ");
                new_admin.setPass(sc.nextLine().replaceAll("[\n\r]",""));
            }

            /* Get Admin permissions */
            System.out.println("\n<--Permissions-->\n\n0:\nAble to see Reports\nAble to invalidate Caches\n");
            System.out.println("1:\nAll of 0's abilities\nAble to create events\n");
            System.out.println("2:\nAll of 1's abilities\nAble to create new admins\n Able to remove admins");
            new_admin.setPermi(sc.nextInt());

            try {
                gc.createAdmin(new_admin);
                System.out.println("Successfully created new Admin");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else System.out.println("Invalid e-mail.");

        if (console != null) console.readLine();
    }

    /**
     * Delete an Admin if the currently logged in admin has the permissions to do so
     */
    private static void deleteAdmin () {
        Scanner sc = new Scanner(System.in);
        String mail;

        System.out.print("Admin e-mail: ");
        mail = sc.nextLine().replaceAll("[\n\r]","");

        try {
            gc.deleteAdmin(mail);
            System.out.println("Successfully deleted admin.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (console != null) console.readLine();
    }

    /**
     * Invalidate a cache given its ID, which will be specified by the currently logged in
     * user/admin, a user can only invalidate a cache if he's the owner
     */
    private static void invalidateCache () {
        Scanner sc = new Scanner(System.in);
        Double id;

        clean();
        System.out.print("Cache id: ");
        id = sc.nextDouble();

        try {
            gc.invalidateCache(id);
            System.out.println("Cache deleted successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (console != null) console.readLine();
    }

    /* ---------------------------- SEPARATOR -----------------------*/

    /**
     * Auxiliary function to create a GregorianCalendar instance given a string
     * typed by the user
     */
    private static GregorianCalendar typeDate (){
        boolean aux = true;
        String bbdate;
        GregorianCalendar bbbdate;
        String[] bdate_fields;
        int d,m,y,yy;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Date (Day/Month/Year): ");
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

    /**
     * Clean the terminal view
     */
    private final static void clean(){
        if (console != null) System.out.print("\u001b[2J" + "\u001b[H");
    }

    /*--------------------- APPLICATION STATE -----------------------*/

    /**
     * Save the application state to an object file named 'geocaching'
     */
    private static void saveState () {
        try {
            FileOutputStream fos = new FileOutputStream("geocaching");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gc);
            oos.close();
            System.out.println("Guardado!");
            if (console != null) console.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Load a previous application state from the file 'geocaching', if the file
     * is not in the directory the state will not be loaded
     */
    private static void loadState () {
        try {
            FileInputStream fis = new FileInputStream("geocaching");
            ObjectInputStream ois = new ObjectInputStream(fis);
            gc = (GeocachingPOO) ois.readObject();
            ois.close();
            System.out.println("Successfully loaded state.");
            if (console != null) console.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}