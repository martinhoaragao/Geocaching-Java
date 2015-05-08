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
                    case 1: user = null;
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
            System.out.println("1: Log Out");
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
    public void changePassword(){
        Scanner sc = new Scanner(System.in);
        String currentpassw, newpassw; int i=0; //3 trys to change password each time
        
        System.out.println("Type your current password: ");
        currentpassw = sc.nextLine();
        
        //While user doesn't type correct current password, he has 2 more trys.
        while(i<3 && encryptPass(currentpassw).equals(user.getPass())){
            System.out.println("Passwords don't match! Try again.");
            i++;
            System.out.println("Type your current password: ");
            currentpassw = sc.nextLine();
            if(i==3)System.out.println(" Passwords don't match! ");
        }
        
        //If User sucessfully types current password, he may change it.
        if(encryptPass(currentpassw).equals(user.getPass())){
            System.out.println("Type new password: ");
            newpassw = sc.nextLine();
            
            userbase.getUser(user.getMail(),currentpassw).setPass(newpassw);
          //user.setPass(newpassw
            
            
            /*//Removes the old and inserts the new one with same infos except for new password
            User newuser = new User(user.getMail(), newpassw, user.getName());
            
            userbase.values.remove(user.getMail());
            
            user = userbase.getUser(newuser.getMail, newpassw);
            //Now the user logged in is the same but with the new password fixed.
            userbase.addUser(user);
            /*Since we deleted the old user with the old password,
            we now add the one with the new password.
            And since we set user = newuser, we can add it using the user pointer
            allright? 
            remove method wont let me... userbase.remove(user.getMail()) wont work idk why
            */
            
            
            
        }
        

    }
    
    /**
     * Function to encrypt password when creating user
     * @arg pass Password to be encrypted
     */
    private String encryptPass (String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());

            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < byteData.length; i++)
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

            return sb.toString();
        } catch (NoSuchAlgorithmException e){   // Unable to ecnrypt password
            return pass;
        }
    }
}