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

public class Admin {
    private String name;              // Admin username
    private String pass;              // Admin Password
    private Double id;                // Admin id
    private Integer permi;            // Permission level (0 - lowest or >0)

    /**
     * Constructor without arguments
     */
    public Admin () {
        this.name = new String();
        this.pass = new String();
        this.id = 0.0;
        this.permi = 0;
    }

    /**
     * Construtor with arguments
     * @param name  Admin username
     * @param pass  Admin password
     * @param id    Admin id
     * @param permi Admin permissions
     */
    public Admin (String name, String pass, Double id, Integer permi) throws NullPointerException, IllegalStateException {
        this();

        if (pass == null)
            throw new NullPointerException("pass can't be null!");
        if (pass.trim() == "")
            throw new IllegalStateException("pass can't be empty!");
        this.pass = encryptPass(pass);

        if (name == null)
            throw new NullPointerException("name can't be null!");
        if (name.trim() == "")
            throw new IllegalStateException("name can't be empty!");
        this.name = name;

        this.id = id;

        if (permi < 0)
            throw new IllegalStateException("Illegal permission level!");
        this.permi = permi;
    }

    /**
     * Construct a new User with the same info as a given User
     * @param user User from which the information will be fetched
     */
    public Admin (Admin admin) throws NullPointerException {
        this();

        if (admin == null)
            throw new NullPointerException("user can't be null!");

        this.name = admin.getName();
        this.pass = admin.getPass();
        this.id = admin.getId();
        this.permi = admin.getPermi();
    }

    // Getters

    /**
     * @return Admin username
     */
    public String getName () {
        return this.name;
    }

    /**
     * @return Admin encrypted password
     */
    public String getPass () {
        return this.pass;
    }

    /**
     * @return Admin Id
     */
    public Double getId () {
        return this.id;
    }

    /**
     * @return Admin permission level
     */
    public Integer getPermi () {
        return this.permi;
    }

    //Setters

    /**
     * Chanches the Admin username
     */
    public void setName (String name) throws NullPointerException, IllegalStateException {
        if (name == null)
            throw new NullPointerException("name can't be null!");
        if (name.trim() == "")
            throw new IllegalStateException("name can't be empty");
        this.name = name;
    }

    /**
     * Set the password for the first time
     * @param passw Password for the admin
     */
    public void setPass (String passw) throws NullPointerException, IllegalStateException {
        if (passw == null)
            throw new NullPointerException("passw can't be null!");
        if (passw.trim() == "")
            throw new IllegalStateException("passw can't be empty!");

        this.pass = encryptPass(passw);
    }


    /**
     * Change the admin id
     * @param id New admin id
     */
    public void setId (Double id) throws NullPointerException, IllegalStateException {
        if (id == null)
          throw new NullPointerException("id can't be null!");
        if (id < 0)
          throw new IllegalStateException("id can't be negative!");

        this.id = id;
    }

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
        String day, month, year;

        sb.append("Name: " + this.name + "\n");
        sb.append("Admin id: " + this.id.intValue() + "\n");
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
        boolean comp = this.name.equals(aux.getName());
        comp = comp && (this.pass.equals(aux.getPass()));
        comp = comp && (this.permi.equals(aux.getPermi()));
        return comp;
    }

    /**
     * Create a clone of this object
     */
    public Admin clone () {
        return new Admin(this);
    }

    // Other methods

    /**
     * Confirm if the given password is equal to the one stored
     * @param pass
     */
    public boolean confirmPass (String pass) throws NullPointerException, IllegalStateException {
        if (pass == null)
            throw new NullPointerException("pass can't be null!");
        if (pass.trim() == "")
            throw new IllegalStateException("pass can't be empty!");

        if (this.getPass().equals(encryptPass(pass))) return true;
        else return false;
    }

    /**
     * Function to encrypt password when creating admin
     * @param pass Password to be encrypted
     */
    private String encryptPass (String pass) throws NullPointerException, IllegalStateException {
        if (pass == null)
            throw new NullPointerException("pass can't be null!");
        if (pass.trim() == "")
            throw new IllegalStateException("pass can't be empty!");

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());

            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < byteData.length; i++)
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

            return sb.toString();
        } catch (NoSuchAlgorithmException e){   // Unable to encrypt password
            return pass;
        }
    }
}
