/**
 * Class that has the main method to run Geocaching
 *
 * @author jfc
 * @version 25/05/2015
 */

import java.util.Scanner;
import java.util.GregorianCalendar;
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
          case 1:   printUserInfo(); break;
          case 10:  logged = false; break;
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
    clean();
    System.out.println("1: Send Friend Request");
    System.out.println("2: Show Friend Requests");
    System.out.println("3: Accept Friend Request");
    System.out.println("4: Friends List");
    System.out.println("5: Main Menu");
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

  /* ---------------- SEPARATOR --------------------------------*/

  /** Auxiliary function to display user information */
  private static void printUserInfo () {
    String info = gc.getUserInfo();

    clean();
    System.out.println(info);
    if (console != null) console.readLine();
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

  /** Auxiliary Function for us to clean the terminal when we call it */
  private final static void clean(){
    if (console != null) System.out.print("\u001b[2J" + "\u001b[H");
  }
}