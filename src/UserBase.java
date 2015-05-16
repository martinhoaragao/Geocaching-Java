/**
 * Classe to save information of all the Users that have been created.
 *
 * @version 08/05/2015
 */

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserBase {
    private TreeMap<String, User> users;

    // Constructors

    /**
     * Constructor without arguments
     */
    public UserBase () {
        this.users = new TreeMap<String, User>();
    }

    /**
     * Construct a UserBase using another UserBase as reference
     * @arg userbase UserBase from where Users will be fetched
     */
    public UserBase (UserBase userbase) {
        TreeSet users = userbase.getUsers();
        Iterator it = users.iterator();

        while (it.hasNext()) {
            User aux = (User) it.next();
            this.users.put(aux.getMail(), aux);
        }
    }

    // Getters

    /**
     * Returns the user with the specified e-mail if passwords match
     * @arg mail User mail
     * @arg pass User password
     */
    public User getUser (String mail, String pass) {
        User aux = this.users.get(mail);

        if (aux == null) { return null; }   // No user with the given mail
        else {
            if (aux.confirmPass(pass)) return aux;
            else return null;
        }
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
    public TreeSet getUsers () {
        TreeSet<User> ts = new TreeSet<User>();

        for (User u : this.users.values())
            ts.add(u.clone());
        return ts;
    }

    

    // Other methods

    /**
     * Add a user to the data base
     * @arg user User to be added
     * -- nao é pa fazer user.clone() ?
     */
    public void addUser (User user) {
        this.users.put(user.getMail(), user);
    }

    /**
     * Check if a given e-mail already has a user associated
     * @arg mail E-mail to check if it's already in use
     */
    public boolean exists (String mail) {
        return this.users.containsKey(mail);
    }

    /**
     * Check if a given User is in the UserBase
     * @arg user User to be found in the UserBase
     */
    public boolean exists (User user) {
        for (User u : this.users.values())
            if (u.equals(user)) return true;
        return false;
    }
    
    // toString, equals and clone

    /**
     * Create a String with all the e-mails in the UserBase
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();

        sb.append(this.users.size() + " Users.\n");

        for (String mail : this.users.keySet())
            sb.append(mail + "\n");

        return sb.toString();
    }

    /**
     * Compare if two UserBase have the same e-mails and the same number of users,
     * should be modified to also check if the users are equal
     * @arg ubase UserBase to use for comparison
     */
    public boolean equals (Object ubase) {
        if (ubase == this) return true;

        if ((ubase == null) || (ubase.getClass() != this.getClass())) return false;

        UserBase aux = (UserBase) ubase;
        boolean result;
        result = (this.users.size() == aux.getNumOfUsers());
        // Should also check if Users are equal
        if (result) {
            for (User a : this.users.values())
                result = result && (aux.exists(a.getMail()));
        }
        return result;
    }

    /**
     * Create a clone of this UserBase
     */
    public UserBase clone () {
        return new UserBase(this);
    }
}