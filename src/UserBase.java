/**
 * Class that stores instances of NormalUser and Admin
 *
 * @version 06/06/2015
 */

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.Serializable;
import Exceptions.*;

public class UserBase implements Serializable {
    private ArrayList<Admin> admins;                /* Array to store admins */
    private ArrayList<NormalUser> users;            /* Array to store users */
    private TreeMap<String, Double> userMails;      /* Map between mails and ids */
    private TreeMap<String, Double> adminMails;     /* Map between mails and ids */


    /* Constructors */

    /**
     * Constructor without arguments, only the instance variables are initialized
     */
    public UserBase () {
        this.admins = new ArrayList<Admin>();
        this.users = new ArrayList<NormalUser>();
        this.adminMails = new TreeMap<String, Double>(new MailComparator());
        this.userMails = new TreeMap<String, Double>(new MailComparator());
    }

    /**
     * Construct a UserBase using another UserBase as reference
     * @param userbase UserBase from where Users will be fetched
     */
    public UserBase (UserBase userbase) {
        this.admins = userbase.getAdmins();
        this.users = userbase.getUsers();
        this.adminMails = userbase.getAdminMails();
        this.userMails = userbase.getUserMails();

    }

    /* Getters */

    /**
     * Get a Admin given its ID
     * @param id Admin ID
     * @return The Admin if it exists, otherwise null will be returned
     */
    private Admin getAdmin (Double id) {
        if (id < this.admins.size())
            return this.admins.get(id.intValue() - 1);
        else
            return null;
    }

    /**
     * Returns the admin with the specified username if passwords match
     * @param name Admin username
     * @param pass Admin password
     */
    public Admin getAdmin (String mail, String pass) throws IllegalArgumentException, WrongPasswordException {
        Double id = this.adminMails.get(mail);

        if (id != null) {
            Admin ad = this.admins.get(id.intValue() - 1);
            if (ad.confirmPass(pass)) return ad;
            else
                throw new WrongPasswordException("Wrong password!");
        } else
            throw new IllegalArgumentException("No Admin with the given mail.");
    }

    /**
     * Get a NormalUser given its ID
     * @param id User id
     */
    private NormalUser getUser (Double id) {
        if (id < users.size())
            return users.get(id.intValue() - 1);
        else
            return null;
    }

    /**
     * Function to be used only by the user base to get a user
     * @param mail User e-mail
     * @return The NormalUser if it exists, otherwise null will be returned
     */
    public NormalUser getUser (String mail) {
        return this.users.get(this.userMails.get(mail).intValue() - 1);
    }

    /**
     * Get instance of a NormalUser given its e-mail and password
     * @param mail User mail
     * @param pass User password
     */
    public NormalUser getUser (String mail, String pass) throws WrongPasswordException {
        /* Get the user id */
        Double id = this.userMails.get(mail);

        if (id != null) {
            NormalUser user = this.users.get(id.intValue() - 1);
            if (user.confirmPass(pass)) return user;
            else
                throw new WrongPasswordException("Wrong Password!");
        } else
        throw new IllegalArgumentException(mail + " has no user associated.");
    }

    /**
     * Calculate the number of Admin instances on the UserBase
     * @return Number of admins in the user data base
     */
    public int getNumOfAdmins () {
        return this.admins.size();
    }

    /**
     * Calculate the number of Normaluser instances on the UserBase
     * @return Number of users in the user data base
     */
    public int getNumOfUsers () {
        return this.users.size();
    }

    /**
     * Get the List with clones of all the instances of Admin in the UserBase
     * @return ArrayList with all the admins in the UserBase
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Admin> getAdmins () {
        ArrayList<Admin> list = new ArrayList<Admin>();

        for (Admin admin : this.admins) {
            list.add(admin.clone());
        }
        return list;
    }

    /**
     * Get list with clones of all the instances of NormalUser in the UserBase
     * @return ArrayList with all the users in the UserBase
     */
    @SuppressWarnings("unchecked")
    public ArrayList<NormalUser> getUsers () {
        ArrayList<NormalUser> list = new ArrayList<NormalUser>();

        for (NormalUser user : this.users) {
            list.add(user.clone());
        }

        return list;
    }

    /**
     * Get the mapping between admin's e-mails and IDs
     * @return TreeMap with the mapping between admin's e-mails and IDs
     */
    @SuppressWarnings("unchecked")
    public TreeMap<String, Double> getAdminMails () {
        TreeMap<String, Double> tm = new TreeMap<String, Double>();

        for (String mail : adminMails.keySet())
            tm.put(mail, adminMails.get(mail));

        return tm;
    }

    /**
     * Get the mapping between user's e-mails and IDs
     * @return TreeMap with the mapping between user's e-mails and IDs
     */
    @SuppressWarnings("unchecked")
    public TreeMap<String, Double> getUserMails () {
        TreeMap<String, Double> tm = new TreeMap<String, Double>();

        for (String mail : userMails.keySet())
            tm.put(mail, userMails.get(mail));

        return tm;
    }

    /**
     * Create a clone of the user and eliminate the password
     * @param id User Id
     */
    public NormalUser getUserInfo (Double id) {
        NormalUser user = this.users.get(id.intValue() - 1);

        if (user != null) {
            user = user.clone();
            user.setPass("----");
        }

        return user;
    }

    /**
     * Add an admin to the UserBase
     * @param adm Admin to be added
     */
    public void addAdmin (Admin adm) throws IllegalArgumentException, NullPointerException {
        Double id = this.adminMails.get(adm.getMail());
        if (id != null)
            throw new IllegalArgumentException("This e-mail is already taken.");
        if (adm == null)
            throw new NullPointerException("adm can't be null");
        this.admins.add(adm.getId().intValue() - 1, adm);
        this.adminMails.put(adm.getMail(), adm.getId());
    }

    /**
     * Add a user to the UserBase
     * @param user User to be added
     */
    public void addUser (NormalUser user) throws EmailAlreadyInUseException, IdAlreadyAssignedException {
        if (userExists(user.getMail()))
            throw new EmailAlreadyInUseException(user.getMail() + " is already in use.");
        else if (userExists(user.getId()))
            throw new IdAlreadyAssignedException(user.getId() + " is already assigned.");
        else {
            this.users.add(user);
            this.userMails.put(user.getMail(), user.getId());
        }
    }

    /**
     * Remove an admin from the UserBase
     *  @param mail E-mail of the admin to bew removed
     */
    public void removeAdmin (String mail) throws IllegalArgumentException {
        if (!this.adminExists(mail)) /* Admin e-mail doesn't exist */
            throw new IllegalArgumentException("Admin doesn't exist.");
        this.admins.remove(adminMails.get(mail).intValue() - 1);
        this.adminMails.remove(mail);
    }

    /**
     * Remove an user from the UserBase
     * @param mail E-mail of the user to be removed
     */
    public void removeUser (String mail) throws IllegalArgumentException {
        if (!this.userExists(mail)) /* User e-mail doesn't exist */
            throw new IllegalArgumentException("User doesn't exist.");
        this.users.remove(userMails.get(mail).intValue() - 1);
        this.userMails.remove(mail);
    }

    /**
     * Check if a given e-mail already has an admin associated
     * @param mail E-mail to check if it's already in use
     */
    public boolean adminExists (String mail) {
        return (this.adminMails.get(mail) != null);
    }

    /**
     * Check if a given e-mail already has a user associated
     * @param mail E-mail to check if it's already in use
     */
    public boolean userExists (String mail) {
        return (this.userMails.get(mail) != null);
    }

    /**
     * Check if a user exists given its ID
     * @param id The user ID
     * @return true if the user exists, false otherwise
     */
    public boolean userExists (Double id) {
        if (id <= this.users.size())
            return (this.users.get(id.intValue() - 1) != null);
        else return false;
    }

    /**
     * Create friend request from a given user to another
     * @param id Id of the user sending the request
     * @param mail Mail of the user to whom the request will be sent
     */
    public void sendFriendRequest (Double id, String mail) throws IllegalArgumentException {
        if (!this.userExists(mail)) /* User e-mail doesn't exist */
            throw new IllegalArgumentException("User doesn't exist.");
        if (id < 0)             /* Invalid id */
            throw new IllegalArgumentException("id has to be positive.");

        Double user_id = this.userMails.get(mail);           /* Get user id */
        NormalUser u = this.users.get(user_id.intValue() - 1); /* Get user */

        if (u != null)
            try { u.addFriendRequest(id); }
            catch (Exception e) {
                /* User does not exist */;
            }
    }

    /**
     * Accept a friend request
     * @param id Id of the user that accepted the request
     * @param mail E-mail of the user that sent the request
     */
    public void acceptFriendRequest (Double id, String mail) throws NullPointerException, IllegalArgumentException {
        NormalUser u1, u2;
        u1 = this.users.get(id.intValue() - 1);
        u2 = this.users.get(this.userMails.get(mail).intValue() - 1);

        /* Add exceptions */
        if (mail == null)
            throw new NullPointerException("mail can't be null.");
        if (!u1.getFriendRequests().contains(u2.getId()))
            throw new IllegalArgumentException("No friend request from this user.");

        u1.removeFriendRequest(u2.getId());    /* Remove friend request */
        u1.addFriend(u2.getId());              /* Add friend */
        u2.addFriend(id);                   /* Add friend */
    }

        /* toString, equals and clone */

    /**
     * Create a String with all the e-mails in the UserBase
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();

        sb.append("\nCurrent DataBase\n");

        sb.append(this.admins.size() + " Admins.\n");
        for(Admin admin : this.admins)
            sb.append(admin.getName() + " - Email: " + admin.getMail()  + " - Power: " + admin.getPermi() + "\n");

        sb.append(this.users.size() + " Users.\n");
        Iterator it = this.users.iterator();
        while (it.hasNext()) {
          User u = (User) it.next();
          sb.append(u.getName() + " - " + u.getMail() + "\n");
        }

        return sb.toString();
    }

    /**
     * Compare if two UserBase have the same e-mails and the same number of users
     * @param ubase UserBase to use for comparison
     */
    public boolean equals (Object ubase) {
        if (ubase == this) return true;

        if ((ubase == null) || (ubase.getClass() != this.getClass())) return false;

        UserBase aux = (UserBase) ubase;
        boolean result;

        /* Compare number of admins */
        result = (this.admins.size() == aux.getNumOfAdmins());
        /* Compare all admins */
        for (Admin admin : aux.getAdmins())
            result = result && (this.getAdmin(admin.getId()).equals(admin));

        /* Compare number of users */
        result = (this.users.size() == aux.getNumOfUsers());
        /* Compare all users */
        for (NormalUser user : aux.getUsers())
            result = result && (this.getUser(user.getId()).equals(user));
        return result;
    }

    /**
     * Create a clone of the UserBase
     * @return Instance of UserBase containing clones of the information of the UserBase
     */
    public UserBase clone () {
        return new UserBase(this);
    }
}
