/**
 * This class will represent the program in which a random user with an e-mail will login
 * and do all the available operations, with his account.
 * He will only have access to the functions of which he has power.
 * All the classes and all the methods created for User-Application will be avaiable here.
 */

import java.util.Scanner;

public class GeocachingPOO {
    private User user = null;           // User that is logged in
    private static UserBase userbase = null;   // User data base
    
    //Random main method/function to complete.
    public static int main() {
        boolean running = true;
        int option = 0;
        userbase = new UserBase();
        
        while (running) {
            option = menu();
            switch (option) {
                case 1: register(); break;
                case 2: running = false; break;
            }
        }
        
        return 0;
    }
    
    /**
     * Display the menu and return the user choice
     */
    private static int menu () {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("1: Register");
        System.out.println("2: Exit");
        
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
            System.out.println("E-mail already in use");
        } else {
            newuser = new User(mail, pass, name);
            userbase.addUser(newuser);
            newuser = null;
        }
    }
}