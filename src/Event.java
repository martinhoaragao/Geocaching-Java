import java.util.ArrayList;
import java.util.GregorianCalendar;


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

    public void setBegTime (int day, int month, int year, int hour, int minute) {
        this.beginTime = new GregorianCalendar(year, month, day, hour, minute);
    }

    public void setDate (int day, int month, int year, int hour, int minute) {
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

}
