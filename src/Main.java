/**
 * Class that has the main method to run Geocaching
 *
 * @author jfc
 * @version 25/05/2015
 */

import java.util.Scanner;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.TreeMap;
import java.io.Console;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Main implements Serializable {
  private static Console console = System.console();
  private static GeocachingPOO gc;
  private static boolean user_logged;       /* Control if a user is logged in */
  private static boolean admin_looged;      /* Control if a admin is logged in */

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
          case 7: System.out.println(gc.getCurrentUserId()); break;
          default: break;
        }
      } else if (user_logged) {             /* User logged in */
        userMenu();

        switch (sc.nextInt()) {
          case 1: infoMenu();               break;
          case 2: cachesMenu();             break;
          case 3: friendsMenu();            break;
          case 4: displayAllCaches();       break;
          case 6: displayLastActivities();  break;
          case 8: displayUserCaches();      break;
          case 9: createActivity();         break;
          case 10: logout();                break;
          default: break;
        }
      } else {                              /* Admin logged in */
        adminMenu();

        switch (sc.nextInt()) {
          case 1: displayReports();     break;
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

  /** Auxiliary function to display Main Menu */
  private static void mainMenu () {
    clean();

    System.out.println("1: Register");
    System.out.println("2: Login");
    System.out.println("3: Admin");
    System.out.println("4: Save Application State");
    System.out.println("5: Load Application State");
    System.out.println("6: Exit");
  }

  /** Auxiliary function to display the User Menu */
  private static void userMenu () {
    clean();
    System.out.println("1: Personal Information");
    System.out.println("2: Caches Menu");
    System.out.println("3: Friends");
    System.out.println("4: Show Caches");
    System.out.println("5: Show My Statistics");
    System.out.println("6: Show Last 10 activities");
    System.out.println("7: Show Friend Activities");
    System.out.println("8: Show My Caches");
    System.out.println("9: Add Activity");
    System.out.println("10: Log Out");
  }

  /** Auxiliary function to display and control the Information Menu */
  private static void infoMenu () {
    Scanner sc = new Scanner(System.in);
    boolean done = false;

    while (!done) {
      clean();
      System.out.println(gc.getUserInfo());
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
    }
  }

  /** Auxiliary function to display Admin Menu */
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

  /** Auxiliary function to display Friends Menu */
  private static void friendsMenu () {
    Scanner sc = new Scanner(System.in);
    boolean done = false;

    while (!done) {
      clean();
      System.out.println("1: Send Friend Request");
      System.out.println("2: Show Friend Requests");
      System.out.println("3: Accept Friend Request");
      System.out.println("4: Friends List");
      System.out.println("5: Main Menu");

      switch (sc.nextInt()) {
        case 1: sendRequest();    break;
        case 2: showRequests();   break;
        case 3: acceptRequest();  break;
        case 4: showFriends();    break;
        case 5: done = true;      break;
        default: break;
      }
    }
  }

  /** Auxiliary function to display and control the Cache Menu */
  private static void cachesMenu () {
    Scanner sc = new Scanner(System.in);
    boolean done = false;

    while (!done) {
      clean();
      System.out.println("1: Create a cache");
      System.out.println("2: Report one cache ");
      System.out.println("3: See treasures of a cache");
      System.out.println("4: Show registry book of a cache");
      System.out.println("5: Show other details of a cache");
      System.out.println("6: Leave cache menu");

      switch (sc.nextInt()) {
        case 1: createCache();  break;
        case 2: reportCache();  break;
        case 6: done = true;    break;
        default: break;
      }
    }
  }

  /* ----------------------- REGISTER & LOGIN ----------------------*/

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

  /** Auxiliary function to login
   * @param type true if logging is as Admin, false if logging in as User
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

  /** Auxiliary function to logout from Geocaching */
  private static void logout () {
    gc.logout();
    if (user_logged)  user_logged = false;
    if (admin_looged) admin_looged = false;
  }

  /* --------------------------- USER INFOS ------------------------*/

  /** Auxiliary function to change user password */
  private static void changePassword() {
    String currentpass = null, newpass = null;
    Scanner sc = new Scanner(System.in);
    int tries = 0;

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
    } while ((tries < 3) && !gc.confirmPass(currentpass));

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

  /** Auxiliary function to change User name */
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

  /** Auxiliary function to change User Address */
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

  /** Auxiliary function to change User birthdate */
  private static void changeBDate () {
    clean();
    GregorianCalendar bb = typebdate();
    try {
      gc.changeBDate(bb);
      System.out.println("Successfully changed birthdate.");
    }
    catch (Exception e) {
      System.out.println("Unable to change birthdate: " + e.getMessage());
    }
  }

  /** Auxiliary function to change User gender */
  private static void changeGender () {
    Scanner sc = new Scanner(System.in);
    boolean gender;

    clean();
    System.out.print("Gender (F: Female | M: Male): ");
    gender = sc.nextLine().replaceAll("[\n\r]","").equals("F");
    try {
      gc.changeGender(gender);
      System.out.println("Successfully change gender.");
    } catch (Exception e) {
      System.out.println("Unable to change gender: " + e.getMessage());
    }
    if (console != null) console.readLine();
  }

  /* ----------------------- CACHES ----------------------------*/

  /** Auxiliary function to create a new Cache */
  private static void createCache () {
    Scanner sc = new Scanner(System.in);
    Double id, lat, lon;
    Coordinates coords = null;
    int type;

    clean();
    System.out.println("What type of cache do you want to create? (0 to cancel)");
    System.out.println("1: Tradicional");
    System.out.println("2: Multicache");
    System.out.println("3: Microcache");
    System.out.println("4: Mysterycache");

    if ((type = sc.nextInt()) == 0) {
      System.out.println("Canceling...");
    } else {
      System.out.print("Latitude: ");
      lat = sc.nextDouble();
      System.out.print("Longitude: ");
      lon = sc.nextDouble();
      coords = new Coordinates(lat, lon);
    }

    try {
      gc.createCache(type, coords);
      System.out.println("sucessfully created cache!");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    if (console != null) console.readLine();
  }

  /** Auxiliary function to repoort a cache */
  private static void reportCache () {
    Scanner sc = new Scanner(System.in);
    String message = ""; Double id;
    Report rep;

    clean();
    System.out.print("Cache id: ");
    id = sc.nextDouble();
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

  /** Auxiliary function to display user created caches */
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

  /** Auxiliary function to display all caches */
  private static void displayAllCaches () {
      ArrayList<Cache> caches = gc.getAllCaches();

      clean();
      if (caches.size() == 0)
        System.out.println("There are no caches.");
      else
        for (Cache c : caches)
          System.out.println(c.toString());
        if (console != null) console.readLine();
  }

  /** Auxiliary function to display all Reports */
  private static void displayReports () {
    TreeMap <Double, ArrayList<Report>> tm = gc.getAllReports();

    clean();
    if (tm.size() == 0) {
      System.out.println("There are no reports.");
    } else {
      for (Double id : tm.keySet())
        for (Report rep : tm.get(id))
          System.out.println(rep.toString());
    }
    if (console != null) console.readLine();
  }

  /* ------------------------- FRIENDS -------------------------*/

  /** Auxiliary function to send friend request */
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

  /** Auxiliary function to show friend requests */
  private static void showRequests () {
        String requests = gc.getFriendRequests();

        clean();
        if (requests == null)
          System.out.println("You have no friend requests.");
        else
          System.out.println(requests);

        if (console != null) console.readLine();
  }

  /** Auxiliary function to accept friend requests */
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

  /** Auxiliary function to display list of friends */
  private static void showFriends () {
        clean();
        System.out.println(gc.getFriends());
        if (console != null) console.readLine();
  }

  /* ------------------------- ACTIVITIES -----------------------*/

  /** Auxiliary function to create a new Activity */
  private static void createActivity () {
        Scanner sc = new Scanner(System.in);
        Activity act = new Activity();
        GregorianCalendar date;
        Double id;

        clean();
        System.out.print("Date: "); act.setDate(typebdate());
        System.out.print("Cache id: "); id = sc.nextDouble();
        System.out.print("Kilomteres covered: "); act.setKms(sc.nextDouble());
        /* TODO: Change the way points are added */
        act.setPoints(20);

        try {
          gc.addActivity(act, id);
          System.out.println("Successfully added activity!");
        } catch (Exception e) {
          System.out.println("Error: " + e.getMessage());
        }
        if (console != null) console.readLine();
  }

  /** Auxiliary function to display user 10 last activitites */
  private static void displayLastActivities () {
        ArrayList<Activity> acts = gc.getLastActivities();

        clean();
        if (acts.size() == 0)
          System.out.println("You have no activitites yet.");
        else {
          for (Activity a : acts)
            System.out.println(a.toString());
        }
        if (console != null) console.readLine();
  }

  /* -------------------- ADMIN --------------------------------*/

  /** Auxiliary function to display all Users and All admins */
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

  /** Auxiliary function to delete an User */
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

  /** Auxiliary function to create an Admin */
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

  /** Auxiliary function to delete an Admin */
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

  /* ---------------------------- SEPARATOR -----------------------*/

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

  /** Auxiliary Function for us to clean the terminal when we call it */
  private final static void clean(){
    if (console != null) System.out.print("\u001b[2J" + "\u001b[H");
  }

  /*--------------------- APPLICATION STATE -----------------------*/

  /** Save the program state to a file name 'geocaching' */
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