/**
 * Super class that represents the Users that will be using the application.
 *
 * @version 08/05/2015
 */

import java.util.Iterator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.Serializable;
import java.io.IOException;

public abstract class User implements Serializable {
    private String mail;              // User mail
    private String pass;              // User Password
    private String name;              // User name
    private Double id;                // User Id

    /**
     * Constructor without arguments
     */
    public User () {
        this.mail = "";
        this.pass = "";
        this.name = "";
        this.id = 0.0;
    }

    /**
     * Construtor with arguments
     * @param mail  User e-mail
     * @param pass  User password
     * @param name  User name
     * @param id    User id
     */
    public User (String mail, String pass, String name, Double id) throws NullPointerException, IllegalStateException {
        this();

        if (mail == null)
            throw new NullPointerException("mail can't be null!");
        if (mail.trim() == "")
            throw new IllegalStateException("mail can't be empty!");
        this.mail = mail;

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
    }

    /**
     * Construct a new User with the same info as a given User
     * @param user User from which the information will be fetched
     */
    public User (User user) throws NullPointerException {
        this();

        if (user == null)
            throw new NullPointerException("user can't be null!");

        this.mail = user.getMail(); this.pass = user.getPass();
        this.name = user.getName();
        this.id = user.getId();
    }

    // Getters

    /**
     * Gets the User's e-mail.
     * @return User mail
     */
    public String getMail () {
        return this.mail;
    }

    /**
     * Gets the User's name.
     * @return User name
     */
    public String getName () {
        return this.name;
    }

    /**
     * Gets the encrypted password of an User to be treated when logging in.
     * @return User encrypted password
     */
    public String getPass () {
        return this.pass;
    }

    /**
     * Gets the User Id.
     * @return The user Id
     */
    public Double getId () {
        return this.id;
    }

    //Setters

    /**
     * 
     * Changes the User name
     * @param name String username
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
     * @param passw Password for the user
     */
    public void setPass (String passw) throws NullPointerException, IllegalStateException {
        if (passw == null)
            throw new NullPointerException("passw can't be null!");
        if (passw.trim() == "")
            throw new IllegalStateException("passw can't be empty!");

        this.pass = encryptPass(passw);
    }

    /**
     * Change user e-mail
     * @param mail E-mail to be saved
     */
    public void setMail (String mail) throws NullPointerException, IllegalStateException {
        if (mail == null)
            throw new NullPointerException("mail can't be null!");
        if (mail.trim() == "")
            throw new IllegalStateException("mail can't be empty!");

        this.mail = mail;
    }

    /**
     * Change the user id
     * @param id New user id
     */
    public void setId (Double id) throws NullPointerException, IllegalStateException {
        if (id == null)
          throw new NullPointerException("id can't be null!");
        if (id < 0)
          throw new IllegalStateException("id can't be negative!");

        this.id = id;
    }

    // toString, equals and clone

    /**
     * Transform user info into a String
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();
        String day, month, year;

        sb.append("Mail: " + this.mail + "\n");
        sb.append("Name: " + this.name + "\n");
        sb.append("User id: " + this.id.intValue() + "\n");

        return sb.toString();
    }

    /**
     * Compares this object with another User to check if they are equal
     * @param user User to compare with
     */
    public boolean equals (Object user) throws NullPointerException {
        if (user == null)
            throw new NullPointerException("user can't be null!");

        if (this == user) return true;

        if ((user == null) || (this.getClass() != user.getClass())) return false;

        User aux = (User) user;
        boolean comp = this.mail.equals(aux.getMail());
        comp = comp && (this.pass.equals(aux.getPass()));
        return comp;
    }

    /**
     *  Create a clone of User, to be overridden.
     */
    public abstract User clone();


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
     * Function to encrypt password when creating user
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
