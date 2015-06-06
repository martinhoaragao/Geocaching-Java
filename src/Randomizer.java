/**
 * Class to randomly create Users, caches and activities in GeocachingPOO. Will also
 * create relationships between users.
 * Before finishing a method is run to save the application state to and object file
 * name 'geocaching'
 *
 * @author jfc
 * @version 05/06/2015
 */

import java.util.Random;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Randomizer {
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Random rnd = new Random();
    private static GeocachingPOO gc;
    private static int limit = 10;
    private static ArrayList<String> user_names = new ArrayList<String>(50);
    private static ArrayList<String> passwords = new ArrayList<String>(50);
    private static ArrayList<String> mails = new ArrayList<String>(50);
    private static ArrayList<Boolean> gender = new ArrayList<Boolean>(50);
    private static ArrayList<Coordinates> coords = new ArrayList<Coordinates>(50);
    private static ArrayList<String> treasures = new ArrayList<String>(1);
    private static String aux, mail, pass;
    private static NormalUser user;
    private static Double user_id = 1.0;
    private static Double cache_id = 1.0;

    private static String[] names = {"Joao", "Pedro", "Carlos", "Miguel", "Afonso", "Ana", "Raquel", "Maria", "Beatriz", "Catarina"};
    private static String[] surnames = {"Costa", "Faria", "Oliveira", "Sampaio", "Ferreira", "Pereira", "Matos", "Henriques", "Antunes", "Moura"};

    public static void main (String args[]) {
        int a, b;
        gc = new GeocachingPOO();

        treasures.add("Chocolate");

        /* Create names, e-mails and passwords for users */
        for (int i = 0; i < 50; i++) {
            do {
                a = rnd.nextInt(limit);
                b = rnd.nextInt(limit);
                aux = names[a] + " " + surnames[b];
            } while (user_names.contains(aux));
            user_names.add(aux);
            pass = names[a].replaceAll("[\n\r]", "");
            passwords.add(pass);
            mail = (names[a] + surnames[b] + "@gmail.com").replaceAll("[\n\r]", "");
            mails.add(mail);
            if (a > 4) gender.add(true);
            else gender.add(false);
        }

        /* Create and register users */
        for (int i = 0; i < 50; i++) {
            user = new NormalUser(mails.get(i), passwords.get(i), user_names.get(i), user_id, new GregorianCalendar(1995, 11, 25));
            user.setGender(gender.get(i));

            System.out.println(user.toString());
            try {
                gc.register(user);
            } catch (Exception e) {
                System.out.println("Unable to register user.");
            }

            user_id++;
        }

        /* Create caches */
        for (int i = 0; i < 50; i++) {
            Coordinates cache_coords;
            ArrayList<Coordinates> multi_coords;
            Double lat, lon;
            TraditionalCache cache;

            try {
                gc.login(mails.get(i), passwords.get(i), false);

                do {
                    lat = (Double) (rnd.nextDouble() * 100);
                    lon = (Double) (rnd.nextDouble() * 100);
                    cache_coords = new Coordinates(lat, lon);
                } while (coords.contains(cache_coords));
                coords.add(cache_coords);

                switch (rnd.nextInt(2)) {
                    case 0: /* Traditional Cache */
                    System.out.println("Traditional.");
                    gc.createTraditionalCache(cache_coords, treasures, "");
                    break;
                    case 1: /* MultiCache */
                    System.out.println("MultiCache");
                    multi_coords = new ArrayList<Coordinates>(2);
                    multi_coords.add(cache_coords);
                    multi_coords.add(new Coordinates(cache_coords.getLat() + (rnd.nextDouble() * 5), cache_coords.getLon() + (rnd.nextDouble() * 5)));
                    gc.createMultiCache(multi_coords, treasures, "");
                }

                gc.createTraditionalCache(cache_coords, treasures, "");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        /* Establish relationships between the users */
        for (int i = 0; i < 50; i++) {
            try {
                gc.login(mails.get(i), passwords.get(i), false);

                do { a = rnd.nextInt(51); } while (a == i);
                gc.sendFriendRequest(mails.get(a));   /* Enviar o pedido */

                gc.login(mails.get(a), passwords.get(a), false);
                gc.acceptFriendRequest(mails.get(i));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        /* Create Activities */
        for (int i = 0; i < 50; i++) {

            Double cache_id;
            GregorianCalendar data = new GregorianCalendar(2015,8,25);
            try {
                gc.login(mails.get(i), passwords.get(i), false);
                cache_id = (Double) Math.ceil(rnd.nextDouble()  * 49);

                gc.addActivity(cache_id, data);
                System.out.println("Adicionada Actividade.");

            } catch (Exception e) {
                System.out.println(e.getClass() + e.getMessage());
            }
        }

        saveState();    /* Save the application state to a file named 'geocaching' */
    }

    /**
     * Save the current application state to a object file named 'geocaching'
     */
    private static void saveState () {
        try {
            FileOutputStream fos = new FileOutputStream("geocaching");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gc);
            oos.close();
            System.out.println("Guardado!");
        } catch (Exception e) {
            System.out.println(e.getClass() + e.getMessage());
        }
    }
}
