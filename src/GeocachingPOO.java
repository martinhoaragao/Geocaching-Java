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
            if (user == null) { // No user logged in
                option = mainMenu();
                switch (option) {
                    case 1: register(); break;
                    case 2: login(); break;
                    case 3: running = false; break;
                    default: break;
                }
            } else {    // User logged in
                option = userMenu();
                switch (option) {
                    case 1: printInfo(); break;
                    case 2: changePassword(); break;
                    case 3: changeName(); break;
                    case 4: changeAddress(); break;
                    case 5: changeBDate(); break;
                    case 6: changeGender(); break;
                    case 7: addFriend(); break;
                    case 8: showFriends(); break;
                    case 9: user = null; break;
                    default: break;
                }
            }
        }
    }

    /** Display the main menu and return the user choice */
    private static int mainMenu () {
        Scanner sc = new Scanner(System.in);

        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("3: Exit");

        return sc.nextInt();
    }

    /** Auxiliary function to display user menu */
    private static int userMenu () {
        Scanner sc = new Scanner(System.in);

        System.out.println("Logged in as: " + user.getName() + " | " + user.getPoints() + " points");
        System.out.println("1: Personal Information");
        System.out.println("2: Change Password");
        System.out.println("3: Change Name");
        System.out.println("4: Change Address");
        System.out.println("5: Change Birthdate");
        System.out.println("6: Change Gender");
        System.out.println("7: Add Friend");
        System.out.println("8: Show Friends");
        System.out.println("9: Log Out");

        return sc.nextInt();
    }

    /** Auxiliary function to register new user */
    private static void register () {
        Scanner sc = new Scanner(System.in);
        String name, pass, country, city, gender, mail = "";
        String[] bdate_fields;
        String bbdate;
        GregorianCalendar bdate = null;
        User newuser = new User();
        int bdate_return;
        MailValidator mv = new MailValidator();
        boolean aux = true;
        int i=0; //Tries for the user. After 3rd try, menu to register closes. (Avoiding infinite loop).
        int d,m,y,yy;
        do {

            if(i==3){ System.out.println("Invalid e-mail!"); return; }
            System.out.print("E-mail: ");
            mail = sc.nextLine().replaceAll("[\n\r]","");
            aux = mv.validate(mail);
            if (!aux) System.out.println("Invalid e-mail! Please try a valid one.");
            i++;
        } while (!aux);

        // Check if e-mail is in use
        if (userbase.exists(mail)) {
            System.out.println("E-mail already in use!");
            return;
        } else newuser.setMail(mail);

        // Get User Name
        System.out.print("Name: ");
        newuser.setName(sc.nextLine().replaceAll("[\n\r]", ""));

        // Get User BDate
        do {
            System.out.print("Birthdate (Day/Month/Year): ");
            bdate_return = newuser.setBDate(sc.nextLine().replaceAll("[\n\r]",""));

            if (bdate_return == 1)
                System.out.println("Invalid day!");
            else if (bdate_return == 2)
                System.out.println("Invalid month!");
            else if (bdate_return == 3)
                System.out.println("Invalid year");
        } while ( bdate_return != 0 );

        System.out.print("Pass: ");
        pass = sc.nextLine().replaceAll("[\n\r]","");

        // Get User Password
        newuser.setPass(sc.nextLine().replaceAll("[\n\r]",""));

        // Get User Address
        System.out.print("Country: ");
        country = sc.nextLine().replaceAll("[\n\r]","");
        System.out.print("City: ");
        city = sc.nextLine().replaceAll("[\n\r]","");
        newuser.setAddress(city, country);

        // Get User Gender
        System.out.print("Gender (g for girl, b for boy): ");
        gender = sc.nextLine().replaceAll("[\n\r]","");
        newuser.setGender(gender);

        if (userbase.exists(mail)) {    // E-maill already in use
            System.out.println("E-mail already in use.");
        } else {                        // New e-mail
            userbase.addUser(newuser);
            newuser.setGender(gender);
            newuser = null; id++;
            System.out.println("User sucessfuly created!");
        }
            sc.close();
    }

    /** Auxiliary login function */
    private static void login () {
        Scanner sc = new Scanner(System.in);
        String mail, pass;
        int i = 0;

        do {
            i++;
            System.out.print("E-mail: ");
            mail = sc.nextLine().replaceAll("[\n\r]","");
            System.out.print("Password: ");
            pass = sc.nextLine().replaceAll("[\n\r]","");

            user = userbase.getUser(mail, pass);

            if(user == null && i==3){
                System.out.println("E-mail or password were incorrect.");
                sc.close();
                return;
            }
            if(user == null ){
                System.out.println("E-mail or password incorrect. Please try again.");
            }

        } while ((user == null) );

        sc.close();
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
               sc.close();
               return bbbdate;
               }
        //when numm == 0 everything is ok.
        }while( aux );
        return null;
    }

    /** Auxiliary function to print user information */
    private static void printInfo () {
        System.out.println(user.toString());
    }

    /** Auxiliary function to change user password */
    private static void changePassword() {
        String currentpass, newpass;
        Scanner sc = new Scanner(System.in);
        int i = 0; //3 trys to change password each time

        System.out.println("Current Password: ");
        currentpass = sc.nextLine().replaceAll("[\n\r]","");

        // Give user 3 tries to inser current password
        while(i<3 && !user.confirmPass(currentpass)) {

            if(i==2){
                System.out.println(" Passwords don't match! ");
                return;
            }
            i++;
            System.out.println("Incorret! Type Current Password: ");
            currentpass = sc.nextLine().replaceAll("[\n\r]","");
        }

        //If User sucessfully types current password, he may change it
        if(user.confirmPass(currentpass)){
            System.out.println("Type new password: ");
            newpass = sc.nextLine().replaceAll("[\n\r]","");

            userbase.getUser(user.getMail(),currentpass).setPass(newpass);
        }
    }

    /** Auxiliary function to change User name */
    private static void changeName () {
        Scanner sc = new Scanner(System.in);

        System.out.print("Name: ");
        user.setName(sc.nextLine().replaceAll("[\n\r]", ""));
    }

    /** Auxiliary function to change User Address */
    private static void changeAddress () {
        Scanner sc = new Scanner(System.in);
        String city, country;

        System.out.print("City: ");
        city = sc.nextLine().replaceAll("[\n\r]", "");
        System.out.print("Country: ");
        country = sc.nextLine().replaceAll("[\n\r]", "");
        user.setAddress(city, country);
    }

    /** Auxiliary function to change User birthdate */
    private static void changeBDate () {
        GregorianCalendar bb = typebdate();
        if(bb!=null) user.setBDate(bb);
        /*Scanner sc = new Scanner(System.in);
        String[] bdate;
        System.out.print("Birthdate (Day/Month/Year): ");



        user.setBDate(sc.nextLine().replaceAll("[\n\r]", ""));
        */

    }

    /** Auxiliary function to change User gender */
    private static void changeGender () {
        Scanner sc = new Scanner(System.in);

        System.out.print("Gender: ");
        user.setGender(sc.nextLine().replaceAll("[\n\r]", ""));
    }

    /** Auxiliary function to add a user as friend */
    private static void addFriend () {
        Scanner sc = new Scanner(System.in);
        User u;

        u = userbase.getUserInfo(sc.nextLine().replaceAll("[\n\r]", ""));
        if (u != null) {
            user.addFriend(u);
            System.out.println("Friend Added");
        } else {
            System.out.println("No User with such e-mail");
        }

    }

    /** Auxiliary function to show friends */
    private static void showFriends () {
        User u;

        ArrayList<Integer> list = user.getFriends();

        for (Integer id : list) {
            u = userbase.getUserInfo(id);
            if (u != null)
                System.out.println(u.getName() + " - " + u.getMail());
        }
    }
}