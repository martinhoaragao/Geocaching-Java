/**
 * Classe to save information of all the Users that have been created.
 *
 * @version 08/05/2015
 */

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserBase {
    private ArrayList<User> users;          /* Array to store users */
    private TreeMap<String, Double> mails;  /* Map between mails and ids */

    // Constructors

    /**
     * Constructor without arguments
     */
    public UserBase () {
        this.users = new ArrayList<User>();
        this.mails = new TreeMap<String, Double>(new MailComparator());
    }

    /**
     * Construct a UserBase using another UserBase as reference
     * @param userbase UserBase from where Users will be fetched
     */
    public UserBase (UserBase userbase) {
        this.users = userbase.getUsers();
        this.mails = userbase.getMails();
    }

    // Getters

    /** Function to be used only by the user base to get a user
     * @param id User id
     */
    private User getUser (Double id) {
        if (id < users.size())
            return users.get(id.intValue() - 1);
        else
            return null;
    }

    /** Function to be used inly by the user base to get a user
     * @param mail User e-mail
     */
    private User getUser (String mail) {
        return this.users.get(this.mails.get(mail).intValue() - 1);
    }

    /**
     * Returns the user with the specified e-mail if passwords match
     * @param mail User mail
     * @param pass User password
     */
    public User getUser (String mail, String pass) {
        /* Get the user id */
        Double id = this.mails.get(mail);

        if (id != null) {
            User user = this.users.get(id.intValue() - 1);
            if (user.confirmPass(pass)) return user;
        }
        return null;
    }

    /**
     * @return Number of users in the user data base
     */
    public int getNumOfUsers () {
        return this.users.size();
    }

    /**
     * @return TreeSet with all the users in the UserBase
     */
    @SuppressWarnings("unchecked")
    public ArrayList<User> getUsers () {
        ArrayList<User> list = new ArrayList<User>();
        Iterator it = users.iterator();

        for (User user : this.users) {
            list.add(user.clone());
        }

        return list;
    }

    /** @return TreeMap with the mapping between e-mails and ids */
    @SuppressWarnings("unchecked")
    public TreeMap<String, Double> getMails () {
        TreeMap<String, Double> tm = new TreeMap<String, Double>();

        for (String mail : mails.keySet())
            tm.put(mail, mails.get(mail));

        return tm;
    }

    // toString, equals and clone

    /**
     * Create a String with all the e-mails in the UserBase
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();
        Iterator it = this.users.iterator();

        sb.append(this.users.size() + " Users.\n");

        while (it.hasNext()) {
          User u = (User) it.next();
          sb.append(u.getName() + " - " + u.getMail() + "\n");
        }

        return sb.toString();
    }

    /**
     * Compare if two UserBase have the same e-mails and the same number of users,
     * should be modified to also check if the users are equal
     * @param ubase UserBase to use for comparison
     */
    public boolean equals (Object ubase) {
        if (ubase == this) return true;

        if ((ubase == null) || (ubase.getClass() != this.getClass())) return false;

        UserBase aux = (UserBase) ubase;
        boolean result;
        /* Compare number of users */
        result = (this.users.size() == aux.getNumOfUsers());
        /* Compare all users */
        for (User user : aux.getUsers())
            result = result && (this.getUser(user.getId()).equals(user));
        return result;
    }

    /**
     * Create a clone of this UserBase
     */
    public UserBase clone () {
        return new UserBase(this);
    }

    /**
     * Create a clone of the user and eliminate the password
     * @param id User Id
     */
    public User getUserInfo (Double id) {
        User user = this.users.get(id.intValue() - 1);

        if (user != null) {
            user = user.clone();
            user.setPass("----");
        }

        return user;
    }

    /**
     * Create a clone of the user and eliminate the password
     * @param mail User e-mail
     */
    public User getUserInfo (String mail) {
        if (!mails.containsKey(mail))
            return null;
        int id = this.mails.get(mail).intValue();
        User user = this.users.get(id - 1).clone();
        user.setPass("----");
        return user;
    }

    // Other methods

    /**
     * Add a user to the data base
     * @param user User to be added
     */
    public void addUser (User user) {
        this.users.add(user);
        this.mails.put(user.getMail(), user.getId());
    }

    /**
     * Check if a given e-mail already has a user associated
     * @param mail E-mail to check if it's already in use
     */
    public boolean exists (String mail) {
        return (this.mails.get(mail) != null);
    }

    /**
     * Check if a given User is in the UserBase
     * @param user User to be found in the UserBase
     */
    public boolean exists (User user) {
        int id = user.getId().intValue();

        if (id < this.users.size())
            return (this.users.get(id - 1).equals(user));
        else
            return false;
    }

    /** Create friend request from a given user to another
     * @param id Id of the user sending the request
     * @param mail Mail of the user to whom the request will be sent
     */
    public void sendFriendRequest (Double id, String mail) throws IllegalArgumentException {
        if (!this.exists(mail)) /* User e-mail doesn't exist */
            throw new IllegalArgumentException("User doesn't exist.");
        if (id < 0)             /* Invalid id */
            throw new IllegalArgumentException("id has to be positive.");

        Double user_id = this.mails.get(mail);           /* Get user id */
        User u = this.users.get(user_id.intValue() - 1); /* Get user */

        if (u != null)
            try { u.addFriendRequest(id); }
            catch (Exception e) {
                /* User does not exist */;
            }
    }

    /** Accept a friend request
     * Add the id of the user that accepted to the list of friends of the user
     * that sent the request
     * @param id Id of the user that accepted the request
     * @param mail E-mail of the user that sent the request
     */
    public void acceptFriendRequest (Double id, String mail) throws NullPointerException {
        /* Add exceptions */
        if (mail == null)
            throw new NullPointerException("mail can't be null.");

        Double user_id = this.mails.get(mail);
        User u1, u2;
        u1 = this.users.get(id.intValue() - 1);
        u2 = this.users.get(user_id.intValue() - 1);

        u1.removeFriendRequest(user_id);  /* Remove friend request */
        u1.addFriend(user_id);            /* Add friend */
        u2.addFriend(id);          /* Add friend */
    }
}
