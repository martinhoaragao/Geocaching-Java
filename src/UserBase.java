/**
 * Classe to save information of all the Users that have been created.
 * 
 * @version 08/05/2015
 */

import java.util.TreeMap;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserBase {
    private TreeMap<String, User> users;

    /**
     * Constructor withtout arguments
     */
    public UserBase () {
        this.users = new TreeMap<String, User>();
    }

    /**
     * Add a user to the data base
     * @arg user User to be added
     */
    public void addUser (User user) {
        this.users.put(user.getMail(), user);
    }

    /**
     * Get User given an e-mail and a password
     * @arg mail User mail
     * @arg pass User password
     */
    public User getUser (String mail, String pass) {
        User aux = this.users.get(mail);

        if (aux == null) { return null; }   // No user with the given mail
        else {
            if (encryptPass(pass).equals(aux.getPass()))
                return aux;
            else
                return null;
        }
    }

    /**
     * Encrypt a password the same way the User class does
     * @arg pass Password to be encrpyted
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

    /**
     * Check if a give e-mail already has a user associated
     * @arg mail
     */
    public boolean exists (String mail) {
        return this.users.containsKey(mail);
    }
}