import java.util.ArrayList;
import java.util.GregorianCalendar;
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
    private GregorianCalendar beginTime;    // Date of event's start
    private GregorianCalendar finishTime;   // Date of event's end
    private ArrayList<Cache> caches;        // Caches related to this Event
    private ArrayList<NormalUser> users;    // Users registered in the Event

    // Constructors

    /**
     * Constructor without arguments
     */
    public Event () {
        this.id = 0.0;
        this.registTime = new GregorianCalendar();
        this.beginTime = new GregorianCalendar();
        this.finishTime = new GregorianCalendar();
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
        this.beginTime = (GregorianCalendar) beginTime.clone();
        this.finishTime = (GregorianCalendar) finishTime.clone();
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
        this.beginTime = event.getBegTime();
        this.finishTime = event.getFinTime();
        this.caches = event.getCaches();
        this.users = event.getUsers();
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
     * @return Date of event's start
     */
    public GregorianCalendar getBegTime () {
        return (GregorianCalendar) this.beginTime.clone();
    }

    /**
     * @return Date of event's end
     */
    public GregorianCalendar getFinTime () {
        return (GregorianCalendar) this.finishTime.clone();
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
     * Set time for the beggining of the event
     * @arg day
     * @arg month
     * @arg year
     * @arg hour
     * @arg minute
     */
    public void setBegTime (int day, int month, int year, int hour, int minute) {
        this.beginTime = new GregorianCalendar(year, month, day, hour, minute);
    }

    /**
     * Set time for the end of the event
     * @arg day
     * @arg month
     * @arg year
     * @arg hour
     * @arg minute
     */
    public void setFinTime (int day, int month, int year, int hour, int minute) {
        this.finishTime = new GregorianCalendar(year, month, day, hour, minute);
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
        String regYear, regMonth, regDay, regHour, regMinute,
               begYear, begMonth, begDay, begHour, begMinute,
               finYear, finMonth, finDay, finHour, finMinute;

       regYear = String.valueOf(registTime.get(GregorianCalendar.YEAR));
       regMonth = String.valueOf(registTime.get(GregorianCalendar.MONTH)+1);
       regDay = String.valueOf(registTime.get(GregorianCalendar.DAY_OF_MONTH));
       regHour = String.valueOf(registTime.get(GregorianCalendar.HOUR));
       regMinute = String.valueOf(registTime.get(GregorianCalendar.MINUTE));

       begYear = String.valueOf(beginTime.get(GregorianCalendar.YEAR));
       begMonth = String.valueOf(beginTime.get(GregorianCalendar.MONTH)+1);
       begDay = String.valueOf(beginTime.get(GregorianCalendar.DAY_OF_MONTH));
       begHour = String.valueOf(beginTime.get(GregorianCalendar.HOUR));
       begMinute = String.valueOf(beginTime.get(GregorianCalendar.MINUTE));

       finYear = String.valueOf(finishTime.get(GregorianCalendar.YEAR));
       finMonth = String.valueOf(finishTime.get(GregorianCalendar.MONTH)+1);
       finDay = String.valueOf(finishTime.get(GregorianCalendar.DAY_OF_MONTH));
       finHour = String.valueOf(finishTime.get(GregorianCalendar.HOUR));
       finMinute = String.valueOf(finishTime.get(GregorianCalendar.MINUTE));

       sb.append("Event begins at " + begMinute + ":" + begHour + " of " + begDay + "/" + begMonth + "/" + begYear + "\n");
       sb.append("Event ends at " + finMinute + ":" + finHour + " of " + finDay + "/" + finMonth + "/" + finYear + "\n");

       sb.append("So far there are: " + this.users.size() + " users registered ");
       sb.append("and " + this.caches.size() + " caches to find\n\n");
       sb.append("You have until " + regMinute + ":" + regHour + " of " + regDay + "/" + regMonth + "/" + regYear + " to register\n");
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
       comp = comp && (aux.beginTime.equals(this.beginTime));
       comp = comp && (aux.finishTime.equals(this.finishTime));
       comp = comp && (aux.users.size() == this.users.size());
       comp = comp && (aux.caches.size() == this.caches.size());

       return comp;
    }

    /* TODO clone */

    // Other methods

    /*
     * Register user in an event
     * @arg user, NormalUser to be registered
     */
    public void registerUser (NormalUser user) throws UserAlreadyRegisteredException {
        if (this.users.contains(user))
            throw new UserAlreadyRegisteredException("You are already registered in this event");
        this.users.add(user);
    }

    /*
     * Remove user from an event
     * @arg user, NormalUser whose registration will be cancelled
     */
    public void removeUser (NormalUser user){
        this.users.remove(user);
    }

    /*
     * Checks if a user is registered to an event
     * @arg user, NormalUser to be compared
     * @return boolean true if user is registered
     */
    public boolean containsUser (NormalUser user) {
        return this.users.contains(user);
    }

    /*
     * Add cache to event
     * @arg cache, cache to be inserted
     */
    public void addCache (Cache cache) throws CacheAlreadyAddedException {
        if (this.caches.contains(cache))
            throw new CacheAlreadyAddedException("Cache has already been added");
        this.caches.add(cache);
    }
}
