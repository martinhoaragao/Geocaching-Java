/**
 * This class will represent the program in which a random user with an e-mail will login
 * and do all the available operations, with his account.
 * He will only have access to the functions of which he has power.
 * All the classes and all the methods created for User-Application will be avaiable here.
 * 
 *  @version 08/05/2015
 */

import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class GeocachingPOO {
    private static User user = null;           // User that is logged in
    private static UserBase userbase = null;   // User data base

    //Random main method/function to complete.
    public static void main() {
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
                    case 2: System.out.println(user.toString()); break;
                    case 3: user = null; break;
                    default: break;
                }
            }
        }
        return;
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
            System.out.println("2: Display Personal Info");
            System.out.println("3: Log Out");
        }

        return sc.nextInt();
    }

    /**
     * Function to register new user
     */
    private static void register () {
        Scanner sc = new Scanner(System.in);
        String mail, pass, name, country, city, gender, bdate;
        User newuser;
        int i=0;
        System.out.print("E-mail: ");
        mail = sc.nextLine();
        
        while (userbase.exists(mail)) {    // E-maill already in use
            System.out.println("E-mail already in use.");
            i++;
            System.out.print("E-mail: ");
            mail = sc.nextLine();
            if(i==2) return;
        }
        
        System.out.print("Name: ");
        name = sc.nextLine();
        System.out.print("Password: ");
        pass = sc.nextLine();

            newuser = new User(mail, pass, name);
            
            System.out.print("Country: ");
            country = sc.nextLine();
            System.out.print("City: "); city = sc.nextLine();
            newuser.setAddress(city,country);
            System.out.print("Gender (g for girl, b for boy): "); gender = sc.nextLine();
            System.out.print("Birthday date DD/MM/YY format: "); bdate = sc.nextLine();
            newuser.setGender(gender); newuser.setBDate(bdate);
            
            userbase.addUser(newuser);
            System.out.println("Your account has been successfully created: Name " + name + ". E-Mail: " + mail + "!");
            newuser = null;
        

        sc.close();
    }

    /**
     * Login auxiliary function
     */
    private static void login () {
        Scanner sc = new Scanner(System.in);
        String mail, pass;
           
        int i=0;
        
        System.out.print("E-mail: ");
        mail = sc.nextLine();
        
        System.out.print("Pass: ");
        pass = sc.nextLine();
        
           
            user = userbase.getUser(mail, pass);
        
            while (!userbase.exists(mail) || user == null){ //Allow user to try again pass 2 more times
                i++;
                if(i==3) return;
                
                System.out.println("Email or password are incorrect. Please try again.");

                System.out.print("E-mail: ");
                mail = sc.nextLine();
                System.out.print("Pass: ");
                pass = sc.nextLine();
                user = userbase.getUser(mail, pass);
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
            
            if(i==2){
                System.out.println(" Passwords don't match! ");
                return;
            }
            i++;
            System.out.println("Passwords don't match! Try again.");
            
            System.out.println("Type your current password: ");
            currentpass = sc.nextLine();
            
        }
        
        //If User sucessfully types current password, he may change it
        if(user.confirmPass(currentpass)){
            System.out.println("Type new password: ");
            newpass = sc.nextLine();
            
            userbase.getUser(user.getMail(),currentpass).setPass(newpass);
        }
    }
}