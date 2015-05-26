/**
 * Class that has the main method to run Geocaching
 *
 * @author jfc
 * @version 25/05/2015
 */

import java.util.Scanner;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.io.Console;

public class Main {
  private static Console console = System.console();
  private static GeocachingPOO gc;
  private static boolean logged;      /* Control if a user is logged in */

  public static void main (String args[]) {
    Scanner sc = new Scanner(System.in);
    boolean running = true;

    /* Create GeocachingPOO Object */
    gc = new GeocachingPOO();

    while (running) {
      if (!logged) {  /* No user logged in */
        mainMenu();

        switch (sc.nextInt()) {
          case 1: register(); break;
          case 2: login("user"); break;
          case 3: break;
          case 4: running = false; break;
          default: break;
        }
      }else {    /* User logged in */
        userMenu();

        switch (sc.nextInt()) {
          case 1: infoMenu();                   break;
          case 2: createCache();                break;
          case 3: friendsMenu();                break;
          case 8: displayUserCaches();          break;
          case 10: gc.logout(); logged = false; break;
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
    System.out.println("4: Exit");
  }

  /** Auxiliary function to display the User Menu */
  private static void userMenu () {
    clean();
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
    System.out.println("10: Log Out");
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
   * @param type User type
   */
  private static void login (String type) {
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
      logged = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      if (console != null) console.readLine();
    }
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

/* ---------------- SEPARATOR --------------------------------*/

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
}