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
    private TreeSet<User> users;

    // Constructors

    /**
     * Constructor without arguments
     */
    public UserBase () {
        this.users = new TreeSet<User>(new AlphabeticComparator());
    }

    /**
     * Construct a UserBase using another UserBase as reference
     * @param userbase UserBase from where Users will be fetched
     */
    public UserBase (UserBase userbase) {
        TreeSet users = userbase.getUsers();
        Iterator it = users.iterator();

        while (it.hasNext()) {
            User aux = (User) it.next();
            this.users.add(aux);
        }
    }

    // Getters

    /**
     * Returns the user with the specified e-mail if passwords match
     * @param mail User mail
     * @param pass User password
     */
    public User getUser (String mail, String pass) {
        Iterator it = this.users.iterator();
        boolean found = false;
        User aux = null;

        while (it.hasNext() && !found) {
          aux = (User) it.next();
          if (aux.confirmPass(pass)) found = true;
        }

        if (found) return aux;
        else return null;
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
    public TreeSet<User> getUsers () {
        return (TreeSet<User>) this.users.clone();
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
        result = (this.users.size() == aux.getNumOfUsers());
        // Should also check if Users are equal
        if (result) {
            Iterator it = this.users.iterator();
            while (it.hasNext()) {
              User a = (User) it.next();
              result = result && (aux.exists(a.getMail()));
            }
        }
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
    public User getUserInfo (int id) {
        Iterator it = this.users.iterator();
        boolean found = false;
        User u = null;

        while (it.hasNext() && !found) {
            u = (User) it.next();
            if (u.getId() == id) { found = true; u = u.clone(); u.setPass(""); }
        }
        if (!found) u = null;

        return u;
    }

    // Other methods

    /**
     * Add a user to the data base
     * @param user User to be added
     */
    public void addUser (User user) {
        this.users.add(user);
    }

    /**
     * Check if a given e-mail already has a user associated
     * @param mail E-mail to check if it's already in use
     */
    public boolean exists (String mail) {
        Iterator it = this.users.iterator();
        boolean found = false;
        while (it.hasNext() && !found) {
          User aux = (User) it.next();
          if (aux.getMail().equals(mail)) found = true;
        }
        return found;
    }

    /**
     * Check if a given User is in the UserBase
     * @param user User to be found in the UserBase
     */
    public boolean exists (User user) {
        boolean found = false;
        Iterator it = this.users.iterator();

        while (it.hasNext() && !found) {
            User u = (User) it.next();
            if (u.equals(user)) return true;
        }

        return found;
    }
}
