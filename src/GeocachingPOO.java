/**
 * This class will represent the program in which a random user with an e-mail will login
 * and do all the available operations, with his account.
 * He will only have access to the functions of which he has power.
 * All the classes and all the methods created for User-Application will be avaiable here.
 */

import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class GeocachingPOO {
    private static User user = null;           // User that is logged in
    private static UserBase userbase = null;   // User data base

    //Random main method/function to complete.
    public static int main() {
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
                    case 1: changePassword(); break;
                    case 2: user = null; break;
                    default: break;
                }
            }
        }
        return 0;
    }

    /**
     * Display the menu and return the user choice
     */
    private static int menu () {
        Scanner sc = new Scanner(System.in);

        if (user == null) { // No user logged in
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit");
        } else {
            System.out.println("Logged in as: " + user.getName());
            System.out.println("1: Change password");
            System.out.println("2: Log Out");
        }

        return sc.nextInt();
    }

    /**
     * Function to register new user
     */
    private static void register () {
        Scanner sc = new Scanner(System.in);
        String mail, pass, name;
        User newuser;

        System.out.print("E-mail: ");
        mail = sc.nextLine();
        System.out.print("Name: ");
        name = sc.nextLine();
        System.out.print("Password: ");
        pass = sc.nextLine();

        if (userbase.exists(mail)) {    // E-maill already in use
            System.out.println("E-mail already in use.");
        } else {
            newuser = new User(mail, pass, name);
            userbase.addUser(newuser);
            newuser = null;
        }

        sc.close();
    }

    /**
     * Login auxiliary function
     */
    private static void login () {
        Scanner sc = new Scanner(System.in);
        String mail, pass;

        System.out.print("E-mail: ");
        mail = sc.nextLine();
        System.out.print("Pass: ");
        pass = sc.nextLine();

        if (!userbase.exists(mail))
            System.out.println("There is no user with the given e-mail.");
        else {
            user = userbase.getUser(mail, pass);
            if (user == null)
                System.out.println("Wrong password!");
        }
    }
    
    /**
     * Method to change current password
     */
    private static void changePassword() {
        Scanner sc = new Scanner(System.in);
        String currentpass, newpass;
        int i = 0; //3 trys to change password each time
        
        System.out.println("Type your current password: ");
        currentpass = sc.nextLine();
        
        // Give user 3 tries to inser current password
        while(i<3 && !user.confirmPass(currentpass)) {
            System.out.println("Passwords don't match! Try again.");
            i++;
            System.out.println("Type your current password: ");
            currentpass = sc.nextLine();
            if(i==3)System.out.println(" Passwords don't match! ");
        }
        
        //If User sucessfully types current password, he may change it
        if(user.confirmPass(currentpass)){
            System.out.println("Type new password: ");
            newpass = sc.nextLine();
            
            userbase.getUser(user.getMail(),currentpass).setPass(newpass);
        }
    }
}