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
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;

public class GeocachingPOO {
    private static Double id = 1.0;
    private static User user = null;           // User that is logged in
    private static UserBase userbase = null;   // User data base
    private static CacheBase cachebase = null; //Cache data base
    //Random main method/function to complete.
    public static void main(String[] args) {
        boolean running = true;     // Set the program as running
        int option = 0;
        userbase = new UserBase();  // Create new user base
        cachebase = new CacheBase(); //Create new cache base
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
        String name, pass, country, city, mail = "";
        String[] bdate_fields;
        String bbdate;
        GregorianCalendar bdate = null;
        User newuser = new User();
        int bdate_return;
        MailValidator mv = new MailValidator();
        boolean aux = true, gender;
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

        if (userbase.exists(mail)) {    // E-maill already in use
            System.out.println("E-mail already in use.");
            return;
        } else newuser.setMail(mail);

        System.out.print("Name: ");
        newuser.setName(sc.nextLine().replaceAll("[\n\r]", ""));

        System.out.print("Pass: ");
        newuser.setPass(sc.nextLine().replaceAll("[\n\r]",""));

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
            userbase.addUser(newuser);
            newuser.setId(id);
            id++;
            newuser = null;
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

        System.out.print("Current Password: ");
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
            System.out.print("Type new password: ");
            newpass = sc.nextLine().replaceAll("[\n\r]","");

            try {
                userbase.getUser(user.getMail(),currentpass).setPass(newpass);
            } catch (Exception e) {
                System.out.println("Error! Unable to change password!");
            }

        }
    }

    /** Auxiliary function to change User name */
    private static void changeName () {
        Scanner sc = new Scanner(System.in);

        System.out.print("Name: ");
        try {
            user.setName(sc.nextLine().replaceAll("[\n\r]", ""));
        } catch (Exception e) {
            System.out.println("Error! Unable to change name!");
        }

    }

    /** Auxiliary function to change User Address */
    private static void changeAddress () {
        Scanner sc = new Scanner(System.in);
        String city, country;

        System.out.print("City: ");
        city = sc.nextLine().replaceAll("[\n\r]", "");
        System.out.print("Country: ");
        country = sc.nextLine().replaceAll("[\n\r]", "");

        try { user.setAddress(city, country); }
        catch (Exception e) {
            System.out.println("Error! Unable to change Address!");
        }
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

        System.out.print("Gender (g for girl/b for boy): ");
        if (sc.nextLine().replaceAll("[\n\r]", "").equals("g"))
            user.setGender(true);
        else user.setGender(false);
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

        ArrayList<Double> list = user.getFriends();

        for (Double id : list) {
            u = userbase.getUserInfo(id);
            if (u != null)
                System.out.println(u.getName() + " - " + u.getMail());
        }
    }
    
    /**
     * Auxiliary function to report a cache
       @param double id The cache id to be reported
     */
     private static void UserReportCache(double id){
        Scanner sc = new Scanner(System.in);
        System.out.println("Are you sure you want to report this cache? [y/n]");
        if(!sc.nextLine().toUpperCase().contains("Y") ){
            //Não tem certeza de que quer reportar, logo nao introduziu y 
            return;
        }
        else{
            
            String email = user.getMail();
            System.out.println("Reasons why you want to report this cache: ");
            String message = sc.nextLine();
            
            Report rep = new Report(id, email, message);
            
            cachebase.addReport(rep);
            System.out.println("Sucessfuly reported cache with id number of" + " " + id);
            
            
        }
    }
    
    /**Auxiliary funtion: Show all reports*/
    
    private static void showAllReports(){
        TreeMap<Double,Report> reps = cachebase.getReportedCaches();
        
        for(Report r : reps.values()){
            System.out.println("| ID : " + r.getId() + "| " + "Msg: " + r.getMessage());
            }
            
    }
    
    /**Menu pints */
    public static int CacheMenuaux(){
                    Scanner sc = new Scanner(System.in);
                    System.out.println("1. Report one cache ");
                    System.out.println("2. See treasures of a cache");
                    System.out.println("3. Show registry book of a cache");
                    System.out.println("4. Show other details of a cache");
                    System.out.println("5. Leave cache menu");
                    System.out.println("0. Show all caches + menu");
                    return sc.nextInt();
    }
    
    /**Auxiliary function: Show all caches + menu 
     * Menu Option: Shows all caches to * report one already, * Show details   ** Report this cache ** return to show all caches 
     */
    private static void showAllCaches(){
        double n ;
        Scanner sc = new Scanner(System.in);
        TreeSet<Cache> caches = cachebase.getAllCaches();

       for(Cache c : caches){
                System.out.println("| ID : " + c.getId() + "| " + "Coords: " + c.getCoords().toString() + "Creator: " + c.getMail());
       }
       
       System.out.println("-------------------------");
       System.out.println("Cache Menu");
       System.out.println("-------------------------");
       
       CacheMenuaux();
       int o = CacheMenuaux();
       while(o!=0){
           
           switch (o) {
                    case 0:
                    for(Cache c : caches){
                        System.out.println("| ID : " + c.getId() + "| " + "Coords: " + c.getCoords().toString() + "Creator: " + c.getMail());
                    }
                    
                    CacheMenuaux();
                    o = sc.nextInt();
                    
                    case 1:
                    System.out.println("Type cache id to report");
                    double u = sc.nextDouble();
                    UserReportCache(u);
                     System.out.println("Type 0 to return to Cache menu");
                     if(sc.nextInt() == 0){
                        o = CacheMenuaux();
                     }
                    break;
                    
                    case 2: 
                    System.out.println("Type cache id to see all the treasures. (0 to leave)");
                    n = sc.nextDouble();
                    if(n == 0) break;
                    else{
                        Iterator it = caches.iterator();
                        Cache cache;
                        while(it.hasNext()){
                            cache = (Cache) it.next();
                            if(cache.getId() == n){
                                ArrayList<Treasure> treasures = cache.getTreasure();
                                for(Treasure t : treasures) System.out.println(t.toString());
                            }
                            else{
                                System.out.println("There isn't such cache with that id. Sorry.");
                            }
                        }
                    }
                    //Nao sei se fará sentido chamar o menu aqui outra vez... se tiver mts treasures, talvez?
                    //User ve os treasures todos e depois vê o menu outra vez para poder fazer outras coisas.. ? 
                    
                    System.out.println("Type 0 to return to Cache menu");
                    if(sc.nextInt() == 0){
                        o = CacheMenuaux();
                    }
                    
                    break;
                    
                    
                    case 3: 
                    System.out.println("Type the cache id to see registry book. (0 to leave)");
                    n = sc.nextDouble();
                    if(n == 0) break;
                    else{
                        Iterator it = caches.iterator();
                        Cache cache;
                        while(it.hasNext()){
                            cache = (Cache) it.next();
                            if(cache.getId() == n){
                                ArrayList<String> regs = cache.getRegistry();
                                for(String s : regs) System.out.println(s);
                            }
                            else{
                                System.out.println("There isn't such cache with that id. Sorry.");
                            }
                        }
                    }
                    
                    //
                    System.out.println("Type 0 to return to Cache menu");
                    if(sc.nextInt() == 0){
                        o = CacheMenuaux();
                    }
                    //
                    break;
                    
                    
                    case 4: 
                    System.out.println("Type the id of the cache to see more details. (0 to leave)");
                    n = sc.nextDouble();
                    if(n == 0) break;
                    else{
                        Iterator it = caches.iterator();
                        Cache cache;
                        while(it.hasNext()){
                            cache = (Cache) it.next();
                            if(cache.getId() == n){
                                ArrayList<String> info = cache.getInfo();
                                for(String s : info) System.out.println(s);
                            }
                            else{
                                System.out.println("There isn't such cache with that id. Sorry.");
                            }
                        }
                        
                        System.out.println("Report this cache? [y/n]");
                        if(!sc.nextLine().toUpperCase().contains("Y")){
                            break;
                        }
                        else{
                            UserReportCache(n);
                        }
                        
                        
                        
                    }
                    //
                    System.out.println("Type 0 to return to Cache menu");
                    if(sc.nextInt() == 0){
                        o = CacheMenuaux();
                    }
                    //
                    break;
                    
                    case 5: 
                    System.out.println("Leaving...");
                    break;
                    
                    default: 
                    break;
                }
        }
       
    }
    
  
    /**
     * Auxiliary function: Menu Option for Admin: Show all reported caches to perform menu options shown in the menuAdminReports()
     */
    
    private static void AdminReportsMenu(){
        double n; int u;
        Scanner sc = new Scanner(System.in);
        TreeMap<Double,Report> reps = cachebase.getReportedCaches();
        
        System.out.println("Want to display ID's and report messages? [y/n]");
        if(!sc.nextLine().toUpperCase().contains("Y") ){
            
            for(Report r : reps.values()){
            System.out.println("| ID : " + r.getId() + "| " );
            //only prints out the id's
            }
            
            u = menuAdminReports();
        }
        else{
            showAllReports(); //shows id and message
            
            u = menuAdminReports();
            
        }
        
        while(u != 4){ //4 is the menu option to leave
        
            switch (u){
                 case 1: 
                 System.out.println("Type the cache id to delete");
                 n = sc.nextDouble();
                 cachebase.delCache(n);
                 
                 System.out.println("Type 0 to return to Admin Reports Menu");
                 if(u==0)
                 u = menuAdminReports();
                 break;
                 
                 
                 case 2:
                 System.out.println("Type the report id to delete");
                 n = sc.nextDouble();
                  cachebase.delReport(n);
                 
                 System.out.println("Type 0 to return to Admin Reports Menu");
                 if(u==0)
                 u = menuAdminReports();
                 break;
                 
                 case 3:
                 
                 System.out.println("Type 0 to return to Admin Reports Menu");
                 if(u==0)
                 u = menuAdminReports();
                 break;
                 
                 default: 
                 break;
            }
            
            /*
             *  * 3. Show details of a cache.
         * (Sub-Menu)
           * 3.1 Delete this cache.
             * 3.2 Detele this report and maintain this cache.
               * 3.3 Return to show all reported caches 
               * 
               * 
               * faltam estes menus mas nao acho que sejam mesmo necessários...
               * ele vê os ids e as mensagens, para que quer saber de quem as fez ou que?
               * é-lhe igual... 
               * e iso de mostrar mais detalhes do report só ia acrescentar ao email.
             */
        }
        
        
        
        System.out.println("Type the ID of the cache to see details: (0 to leave)");
        if((n = sc.nextDouble()) != 0){
            System.out.println(reps.get(n).getMessage());
        }
    }
    
    /**
     * Aux menu prints
     * 1. Delete one cache.
     * 2. Delete one report.
     * 3. Show details of a cache.
     * (Sub-Menu)
     * 3.1 Delete this cache.
     * 3.2 Detele this report and maintain this cache.
     * 3.3 Return to show all reported caches 
     */
    private static int menuAdminReports(){
        Scanner sc = new Scanner(System.in);
        System.out.println("----------");
        System.out.println("1. Approve report and delete cache");
        System.out.println("2. Delete report and mantain cache");
        System.out.println("3. Show details of a report");
        System.out.println("4. Leave");
        return sc.nextInt();
    }
}
