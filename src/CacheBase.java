/**
 * CacheBase represents the Data-Base for all the Caches that have been created (by the server
 * or by users).
 *
 * @version 07/05/2015
 */

import java.util.TreeSet;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;

public class CacheBase implements Serializable {
    private ArrayList<Cache> caches;                    /* To store caches */
    private TreeMap<Double, ArrayList<Double>> owners;  /* Map between user id and cache id */
    private TreeMap<Double, ArrayList<Report>> reported_caches;    /* Reported caches */
    private TreeMap<Coordinates, Double> coords; /* Map between coordinates and caches ids */

    /** Unparameterized constructor */
    public CacheBase () {
        this.caches = new ArrayList<Cache>();
        this.owners = new TreeMap<Double, ArrayList<Double>>();
        this.reported_caches = new TreeMap<Double, ArrayList<Report>>();
        this.coords = new TreeMap<Coordinates, Double>();
    }

    /** Constructs a UserBase with the caches present on another UserBase
     * @param cbase CacheBase from where the caches will be copied
     */
    public CacheBase (CacheBase cbase) {
        this();
        this.caches = cbase.getAllCaches();
        this.owners = cbase.getAllOwners();
        this.reported_caches = cbase.getAllReports();
        this.coords = cbase.getCoords();
    }

    // Getters

    /** @return ArrayList with all the caches in the CacheBase */
    public ArrayList<Cache> getAllCaches () {
      ArrayList<Cache> ts = new ArrayList<Cache>();
      Iterator it = this.caches.iterator();

      while (it.hasNext()) {
        Cache cache = (Cache) it.next();
        ts.add(cache.clone());
      }

      return ts;
    }

    /** @return TreeMap with all the owners relations with caches ids */
    @SuppressWarnings("unchecked")
    public TreeMap<Double, ArrayList<Double>> getAllOwners () {
        return (TreeMap<Double, ArrayList<Double>>)this.owners.clone();
    }

    /** @return TreeMap with all the reports */
    @SuppressWarnings("unchecked")
    public TreeMap<Double, ArrayList<Report>> getAllReports () {
      TreeMap<Double, ArrayList<Report>> tm = new TreeMap<Double, ArrayList<Report>>();

      for (Double id : this.reported_caches.keySet()) {
        tm.put(id, new ArrayList<Report>());
        for (Report rep : this.reported_caches.get(id)) {
          tm.get(id).add(rep.clone());
        }
      }
      return tm;
    }

    /** @return TreeMap with all the Coordinates used */
    @SuppressWarnings("unchecked")
    public TreeMap<Coordinates, Double> getCoords () {
        /* TODO: Clone the coordinates */
        return (TreeMap<Coordinates, Double>) this.coords.clone();
    }

    /** Get caches given a user id
     * @param id User id
     */
    public ArrayList<Cache> getCaches (Double id) {
        ArrayList<Double> caches_ids = this.owners.get(id);
        ArrayList<Cache> user_caches = new ArrayList<Cache>();

        if (caches_ids == null)
            return null;
        else {
            for (Double c_id : caches_ids) {
                user_caches.add(caches.get(c_id.intValue() - 1));
            }
        }

        return user_caches;
    }

    /**
     * @return Number of caches stored in the CacheBase
     */
    public int getNumOfCaches () {
        return this.caches.size();
    }

    /** Add a cache to the CacheBase
     * @param id Id of the user creating the cache
     * @param cache Cache to be added
     */
    public void addCache (Double id, Cache cache) throws IllegalStateException, NullPointerException {
        ArrayList<Double> list;
        Coordinates cache_coords = cache.getCoords();

        /* Make sure there is no cache in the argument cache location */
        if ( this.coords.get(cache_coords) != null)
            throw new IllegalStateException("There is already a cache in that location.");
        if (cache == null)
          throw new NullPointerException("cache can't be null.");

        if ( (list = owners.get(id)) == null ) {
            /* First cache created by the user */
            list = new ArrayList<Double>();
            list.add(cache.getId());
            owners.put(id, list);
            coords.put(cache_coords, id);
        } else list.add(cache.getId());

        /* !!Should check if it is replacing a cache with same id */
        this.caches.add(cache.getId().intValue() - 1, cache);
    }

    /**
     * Check if a given Cache is present in the Cache data base
     * @arg cache
     */
    public boolean exists (Cache cache) {
        if (cache.getId().intValue() > this.caches.size())
            return false;
        else return (cache.equals(caches.get(cache.getId().intValue() -1 )));
    }

    /** Check if a given id has a cache associated
     * @param id Cache id
     */
    public boolean exists (Double id) {
        return (this.caches.size() > id.intValue());
    }

    /** Return a Cache with a given id
     * @param id The Cache id
     */
    public Cache getCache (Double id) throws IllegalArgumentException {
        if (id.intValue() > this.caches.size())
            throw new IllegalArgumentException("No cache with the given id!");
        else
            return this.caches.get(id.intValue() - 1);
    }

    // toString, equals and clone

    /**
     * Return CacheBase info in String
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();
        Iterator it = this.caches.iterator();

        sb.append(this.caches.size() + " caches are stored.\n");

        while (it.hasNext()) {
          Cache aux = (Cache) it.next();
          Coordinates coords = aux.getCoords();
          sb.append("Lat: " + coords.getLat());
          sb.append(" Lon: " + coords.getLon() + "\n");
        }

        while (it.hasNext()) {
            Cache aux = (Cache) it.next();
            Coordinates coords = (Coordinates) aux.getCoords();
            sb.append("Lat: " + coords.getLat());
            sb.append(" Lon: " + coords.getLon() + "\n");
        }

        return sb.toString();
    }

    /**
     * Compares this CacheBase to another CacheBase to check if they are equal
     * @arg cbase CacheBase to use for comparison
     */
    public boolean equals (Object cbase) {
        if (cbase == this) return true;

        if ((cbase == null) || (cbase.getClass() != this.getClass())) return false;

        CacheBase aux = (CacheBase) cbase;
        boolean comp = (this.caches.size() == aux.getNumOfCaches());

        if (comp) {
            Iterator it = this.caches.iterator();

            while (it.hasNext() && comp)
                comp = comp && (aux.exists((Cache) it.next()));
        }

        return comp;
    }

    // Code to invalidate a cache
    /** Invalidate (delete) a cache if the owner wants so
     * @param id Cache Id
     * @param user Owner of the cache
     */
    public void invalidateCache (Double id, User user) {
        if (this.getCache(id).getMail().equals(user.getMail()))
            delCache(id);
    }


    // Code for reported caches

    /** Add a report
     * @param report The report to be added}
     */
    public void addReport (Report report) throws NullPointerException {
        ArrayList<Report> list = reported_caches.get(report.getId());

        if (report == null)
            throw new NullPointerException("report can't be null!");

        /* Add report */
        if (list != null)
            list.add(report);
        else {
            list = new ArrayList<Report>();
            list.add(report);
            reported_caches.put(report.getId(), list);
        }
    }

    /** Delete a cache from the CacheBase, and from the reported caches
     * if any reports for the given cache are Present
     * @param id The Cache id
     */
    public void delCache (Double id) {
        Cache cache;

        if (id.intValue() <= this.caches.size())
            caches.add(id.intValue() - 1, null);
        this.reported_caches.remove(id);            /* Remove cache from reports */
    }

    /**
     * Delete a report only
     */
    public void delReport(Double id){
        this.reported_caches.remove(id);
    }
}
