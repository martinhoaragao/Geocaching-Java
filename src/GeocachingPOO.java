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
import java.util.GregorianCalendar;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.Console;


public class GeocachingPOO {
    private static int id = 1;
    private static User user = null;           // User that is logged in
    private static UserBase userbase = null;   // User data base

    //Random main method/function to complete.
    public static void main(String[] args) {
        boolean running = true;     // Set the program as running
        int option = 0;
        userbase = new UserBase();  // Create new user base

        while (running) {
            option = menu();
            if (user == null) { // No user logged in
                switch (option) {
                    case 1: register(); break;
                    case 2: login(); break;
                    case 3: running = false; break;
                    default: break;
                }
            } else {    // User logged in
                switch (option) {
                    case 1: printInfo(); break;
                    case 2: changePassword(); break;
                    case 3: changeName(); break;
                    case 4: changeAddress(); break;
                    case 5: changeBDate(); break;
                    case 6: changeGender(); break;
                    case 7: user = null; break;
                    default: break;
                }
            }
        }
    }

    /**
     * Display the menu and return the user choice
     */
    private static int menu () {
        Scanner sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J");  // Clear terminal view

        if (user == null) { // No user logged in
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit");
        } else {
            System.out.println("Logged in as: " + user.getName() + " | " + user.getPoints() + " points");
            System.out.println("1: Personal Information");
            System.out.println("2: Change Password");
            System.out.println("3: Change Name");
            System.out.println("4: Change Address");
            System.out.println("5: Change Birthdate");
            System.out.println("6: Change Gender");
            System.out.println("7: Log Out");
        }

        return sc.nextInt();
    }

    /** Auxiliary function to register new user */
    private static void register () {
        System.out.print("\033[H\033[2J");  // Clear terminal view

        Scanner sc = new Scanner(System.in);
        Console console = System.console();
        String mail, name, pass;
        String[] bdate_fields;
        GregorianCalendar bdate = null;
        User newuser;

        System.out.print("E-mail: ");
        mail = sc.nextLine().replaceAll("[\n\r]", "");
        System.out.print("Name: ");
        name = sc.nextLine().replaceAll("[\n\r]", "");
        System.out.print("Birthdate (Day/Month/Year): ");
        bdate_fields = sc.nextLine().replaceAll("[\n\r]","").split("/");
        // Needs checking to make sure date was correctly passed
        bdate = new GregorianCalendar(Integer.parseInt(bdate_fields[2]),
            Integer.parseInt(bdate_fields[1]), Integer.parseInt(bdate_fields[0]));
        pass = new String(console.readPassword("Password: "));

        if (userbase.exists(mail)) {    // E-maill already in use
            System.out.println("E-mail already in use.");
        } else {
            newuser = new User(mail, pass, name, id, bdate);
            userbase.addUser(newuser);
            newuser = null; id++;
        }
    }

    /** Auxiliary login function */
    private static void login () {
        Scanner sc = new Scanner(System.in);
        Console console = System.console();
        String mail, pass;

        System.out.print("\033[H\033[2J");  // Clear terminal view
        System.out.print("E-mail: ");
        mail = sc.nextLine();
        pass = new String(console.readPassword("Password: "));

        if (!userbase.exists(mail))
            System.out.println("There is no user with the given e-mail.");
        else {
            user = userbase.getUser(mail, pass);
            if (user == null)
                System.out.println("Wrong password!");
        }
    }

    /** Auxiliary function to print user information */
    private static void printInfo () {
        Console c = System.console();

        System.out.print("\033[H\033[2J"); // Clear terminal
        System.out.println(user.toString());

        if (c != null) {
            c.readLine();
        }
    }

    /** Auxiliary function to change user password */
    private static void changePassword() {
        Console console = System.console();
        String currentpass, newpass;
        int i = 0; //3 trys to change password each time

        System.out.print("\033[H\033[2J");  // Clear terminal view
        currentpass = new String(console.readPassword("Current password: "));

        // Give user 3 tries to inser current password
        while(i<3 && !user.confirmPass(currentpass)) {
            System.out.println("Passwords don't match! Try again.");
            i++;
            currentpass = new String(console.readPassword("Current password: "));
            if(i==3)System.out.println(" Passwords don't match! ");
        }

        //If User sucessfully types current password, he may change it
        if(user.confirmPass(currentpass)){
            System.out.println("Type new password: ");
            newpass = new String(console.readPassword("New Password: "));

            userbase.getUser(user.getMail(),currentpass).setPass(newpass);
        }
    }

    /** Auxiliary function to change User name */
    private static void changeName () {
        Scanner sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J");  // Clear terminal view

        System.out.print("Name: ");
        user.setName(sc.nextLine().replaceAll("[\n\r]", ""));
    }

    /** Auxiliary function to change User Address */
    private static void changeAddress () {
        Scanner sc = new Scanner(System.in);
        String city, country;

        System.out.print("\033[H\033[2J");  // Clear terminal view
        System.out.print("City: ");
        city = sc.nextLine().replaceAll("[\n\r]", "");
        System.out.print("Country: ");
        country = sc.nextLine().replaceAll("[\n\r]", "");
        user.setAddress(city, country);
    }

    /** Auxiliary function to change User birthdate */
    private static void changeBDate () {
        Scanner sc = new Scanner(System.in);
        String[] bdate;

        System.out.print("\033[H\033[2J");  // Clear terminal view
        System.out.print("Birthdate (Day/Month/Year): ");
        user.setBDate(sc.nextLine().replaceAll("[\n\r]", ""));
    }

    /** Auxiliary function to change User gender */
    private static void changeGender () {
        Scanner sc = new Scanner(System.in);

        System.out.print("\033[H\033[2J");  // Clear terminal view
        System.out.print("Gender: ");
        user.setGender(sc.nextLine().replaceAll("[\n\r]", ""));
    }
}
