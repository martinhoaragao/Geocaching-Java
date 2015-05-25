/**
 * Class to represent a an admin
 *
 * @version 08/05/2015
 */

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Admin extends User{
    private Integer permi;            // Permission level (0 - lowest or >0)

    /**
     * Constructor without arguments
     */
    public Admin () {
        super();
        this.permi = 0;
    }

    /**
     * Construtor with arguments
     * @param mail  Admin e-mail
     * @param name  Admin username
     * @param pass  Admin password
     * @param id    Admin id
     * @param permi Admin permissions
     */
    public Admin (String email, String name, String pass, Double id, Integer permi) throws NullPointerException, IllegalStateException {
        super(email, pass, name, id);

        if (permi < 0)
            throw new IllegalStateException("Illegal permission level!");
        this.permi = permi;
    }

    /**
     * Construct a new User with the same info as a given User
     * @param user User from which the information will be fetched
     */
    public Admin (Admin admin) throws NullPointerException {
        super();
        this.permi = admin.getPermi();
    }

    // Getters

    /**
     * @return Admin permission level
     */
    public Integer getPermi () {
        return this.permi;
    }

    //Setters

    /**
     * Change the admin permission level
     * @param permi New admin permission
     */
    public void setPermi (Integer permi) throws NullPointerException, IllegalStateException {
        if (permi < 0)
          throw new IllegalStateException("id can't be negative!");

        this.permi = permi;
    }

    // toString, equals and clone

    /**
     * Transform Admin info into a String
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();

        sb.append(super.toString());
        sb.append("Admin has level: " + this.permi + "\n");

        return sb.toString();
    }

    /**
     * Compares this object with another Admin to check if they are equal
     * @param admin Admin to compare with
     */
    public boolean equals (Object admin) throws NullPointerException {
        if (admin == null)
            throw new NullPointerException("admin can't be null!");

        if (this == admin) return true;

        if ((admin == null) || (this.getClass() != admin.getClass())) return false;

        Admin aux = (Admin) admin;
        return this.getMail().equals(aux.getMail());
    }

    /**
     * Create a clone of this object
     */
    @Override
    public Admin clone () {
        return new Admin(this);
    }
}
