import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Collections;
import Exceptions.*;

/**
 * Class to hold hosted events by the admin.
 * Limited number of participants, the caches are a copy from CacheBase.
 *
 * @version 11/05/2015
 */


public class Event {
    private Double id;                      // Identifier
    private GregorianCalendar registTime;   // Date of last opportunity to register
    private ArrayList<Cache> caches;        // Caches related to this Event
    private ArrayList<NormalUser> users;    // Users registered in the Event

    // Constructors

    /**
     * Constructor without arguments
     */
    public Event () {
        this.id = 0.0;
        this.registTime = new GregorianCalendar();
        this.caches = new ArrayList<Cache>();
        this.users = new ArrayList<NormalUser>();
    }

    /**
     * Construct an Event through all of it's arguments but users
     * @param event Event from where Events will be fetched
     */
    public Event (Double id, GregorianCalendar registTime, GregorianCalendar beginTime,
                  GregorianCalendar finishTime, ArrayList<Cache> caches) {
        this.id = id;
        this.registTime = (GregorianCalendar) registTime.clone();
        this.setCaches(caches);
        this.users = new ArrayList<NormalUser>();
    }

    /**
     * Construct an Event using another Event as reference
     * @param event Event from where Events will be fetched
     */
    public Event (Event event) {
        this.id = event.getId();
        this.registTime = event.getRegTime();
        this.caches = event.getCaches();
        ArrayList<NormalUser> list = new ArrayList<NormalUser>();

        for (NormalUser user : event.users)
            list.add(user.clone());
        this.users = list;
    }

    // Getters

    /**
     * @return ID of a Cache
     */
    public Double getId() {
        return this.id;
    }

    /**
     * @return Date of last opportunity to register
     */
    public GregorianCalendar getRegTime () {
        return (GregorianCalendar) this.registTime.clone();
    }

    /**
     * @return ArrayList with all the caches in the Event
     */
    public ArrayList<Cache> getCaches () {
        ArrayList<Cache> list = new ArrayList<Cache>();

        for (Cache cache : this.caches) {
            list.add(cache.clone());
        }

        return list;
    }

    /**
     * @return ArrayList with all the users in the Event
     */
    public ArrayList<NormalUser> getUsers () {
        ArrayList<NormalUser> list = new ArrayList<NormalUser>();

        for (NormalUser user : this.users) {
            list.add(user.clone());
        }

        return list;
    }

    // Setters

    /**
     * Set cache ID
     * @arg id, String cache identifier
     */
    public void setId (Double id) {
        this.id = id;
    }

    /**
     * Set time for the end of the event
     * @arg day
     * @arg month
     * @arg year
     * @arg hour
     * @arg minute
     */
    public void setRegTime (int day, int month, int year, int hour, int minute) {
        this.registTime = new GregorianCalendar(year, month, day, hour, minute);
    }

    /**
     * Set list of caches
     * @arg caches, ArrayList of Caches of this event
     */
    public void setCaches (ArrayList<Cache> caches) {
        ArrayList<Cache> newCaches = new ArrayList<Cache>();

        for(Cache aux : caches)
            newCaches.add(aux);

        this.caches = newCaches;
    }

    /**
     * Set list of users
     * @arg users, ArrayList of Users of this event
     */
    public void setUsers (ArrayList<NormalUser> users) {
        ArrayList<NormalUser> newUsers = new ArrayList<NormalUser>();

        for(NormalUser aux : users)
            newUsers.add(aux);

        this.users = newUsers;
    }

    // toString, equals and clone

    /**
     * Convert the info of this event into a string
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();
        String regYear, regMonth, regDay, regHour, regMinute;

       regYear = String.valueOf(registTime.get(GregorianCalendar.YEAR));
       regMonth = String.valueOf(registTime.get(GregorianCalendar.MONTH)+1);
       regDay = String.valueOf(registTime.get(GregorianCalendar.DAY_OF_MONTH));
       regHour = String.valueOf(registTime.get(GregorianCalendar.HOUR));
       regMinute = String.valueOf(registTime.get(GregorianCalendar.MINUTE));

       sb.append("So far there are: " + this.users.size() + " users registered ");
       sb.append("and " + this.caches.size() + " caches to find\n\n");
       sb.append("You have until " + regMinute + ":" + regHour + " of " + regDay + "/" + regMonth + "/" + regYear + " to register\n");
       return sb.toString();
    }
    /**
     * Compare this Event to another to check if they are equal.
     * TODO Only compares times and user and cache size.
     * @arg event Object to use for comparison
     */
    public boolean equals (Object event) {
       if (this == event) return true;

       if ((event == null) || (event.getClass() != this.getClass())) return false;

       Event aux = (Event) event;
       boolean comp = (aux.id == this.id);
       comp = comp && (aux.registTime.equals(this.registTime));
       comp = comp && (aux.users.size() == this.users.size());
       comp = comp && (aux.caches.size() == this.caches.size());

       return comp;
    }

    /* TODO clone */

    // Other methods

    /**
     * Register user in an event
     * @arg user, NormalUser to be registered
     */
    public void registerUser (NormalUser user) throws UserAlreadyRegisteredException {
        if (this.users.contains(user))
            throw new UserAlreadyRegisteredException("You are already registered in this event");
        this.users.add(user.clone());
    }

    /**
     * Remove user from an event
     * @arg user, NormalUser whose registration will be cancelled
     */
    public void removeUser (NormalUser user){
        this.users.remove(user);
    }

    /**
     * Checks if a user is registered to an event
     * @arg user, NormalUser to be compared
     * @return boolean true if user is registered
     */
    public boolean containsUser (NormalUser user) {
        return this.users.contains(user);
    }

    /**
     * Add cache to event
     * @arg cache, cache to be inserted
     */
    public void addCache (Cache cache) throws CacheAlreadyAddedException {
        if (this.caches.contains(cache))
            throw new CacheAlreadyAddedException("Cache has already been added");
        this.caches.add(cache);
    }

    /**
     * Auxiliary function to sum the doubles of an array
     * @arg avg, the avg numbers we want to sum
     * @return sum, the total sum of the values
     */
    private double sumArray(double[] avg) {
        double sum  = 0;
        for (int i = 0; i < 4; i++)
            sum += avg[i];

        return sum;
    }

    /**
     * Function to simulate a user's event, adds activities based on his past experience
     * @arg finishTime, last day of the event, will do a time period based on register
     * @ currentUser, user on which we are doing the simulation
     */
    public void simulateUserEvent (GregorianCalendar finishTime, NormalUser currentUser) {
        Random random = new Random();
        ArrayList<Cache> caches = this.caches;

        /* How many days long this simulation is */
        double diffTime = (finishTime.getTime().getTime() - this.registTime.getTime().getTime()) / (1000 * 60 * 60 * 24);

        int currentYear = currentUser.getCurrentYear();
        double[] avgNumberCaches = currentUser.averageActPerDay(currentYear);
        double[] numberCachesToFind = new double[4];

        /* Number of Caches to be found */
        for (int i = 0; i < 4; i++) {
            avgNumberCaches[i] *= random.nextDouble() * 1.5;
            numberCachesToFind[i] = avgNumberCaches[i] * diffTime;
        }
        /* Randomise caches */
        long seed = System.nanoTime();
        Collections.shuffle(caches, new Random(seed));

        /* Add first caches of randomised array */
        int daysPassed;
        for (int i = 0; i < this.caches.size() && sumArray(numberCachesToFind) > 0; i++) {
            Cache cache = caches.get(i);
            GregorianCalendar currentDate = (GregorianCalendar) this.registTime.clone();
            if (cache instanceof MicroCache && numberCachesToFind[0] > 0 ) {
                numberCachesToFind[0]--;
                daysPassed =(int) ( numberCachesToFind[0] / avgNumberCaches[0]); /* Check how many days to go */
                daysPassed =(int)  (diffTime - daysPassed); /* It's day (daysPassed) of the event */
                currentDate.add((GregorianCalendar.DAY_OF_MONTH), daysPassed);
                currentUser.addActivity(cache, currentDate);
            }
            else if (cache instanceof MultiCache && numberCachesToFind[1] > 0) {
                numberCachesToFind[1]--;
                daysPassed =(int) ( numberCachesToFind[1] / avgNumberCaches[1]); /* Check how many days to go */
                daysPassed =(int)  (diffTime - daysPassed); /* It's day (daysPassed) of the event */
                currentDate.add((GregorianCalendar.DAY_OF_MONTH), daysPassed);
                currentUser.addActivity(cache, currentDate);
            }
            else if (cache instanceof TraditionalCache && numberCachesToFind[2] > 0) {
                numberCachesToFind[2]--;
                daysPassed =(int) ( numberCachesToFind[2] / avgNumberCaches[2]); /* Check how many days to go */
                daysPassed =(int)  (diffTime - daysPassed); /* It's day (daysPassed) of the event */
                currentDate.add((GregorianCalendar.DAY_OF_MONTH), daysPassed);
                currentUser.addActivity(cache, currentDate);
            }
            else if (cache instanceof MysteryCache && numberCachesToFind[3] > 0) {
                numberCachesToFind[3]--;
                daysPassed =(int) ( numberCachesToFind[3] / avgNumberCaches[3]); /* Check how many days to go */
                daysPassed =(int)  (diffTime - daysPassed); /* It's day (daysPassed) of the event */
                currentDate.add((GregorianCalendar.DAY_OF_MONTH), daysPassed);
                currentUser.addActivity(cache, currentDate);
            }
        }
    }

    /**
     * Simulate entire event for list of users
     * @arg finishTime, last day of the event, will do a time period based on register
     */
    public void simulateEvent (GregorianCalendar finishTime) {

    }
}
