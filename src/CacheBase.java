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

public class CacheBase {
    private TreeSet<Cache> caches;    // All the existing Caches.
    private TreeMap<Double, Report> reported_caches;  // Reported caches

    /**
     * Constructor without arguments
     */
    public CacheBase () {
        this.caches = new TreeSet<Cache>();
        this.reported_caches = new TreeMap<Double, Report>();
    }

    /**
     * Constructs a UserBase with the caches present on another UserBase
     * @param cbase CacheBase from where the caches will be copied
     */
    public CacheBase (CacheBase cbase) {
        TreeSet<Cache> ts = cbase.getAllCaches();
        TreeMap<Double, Report> tm = new TreeMap<Double, Report>();
        Iterator it = ts.iterator();

        while (it.hasNext())
            this.caches.add((Cache) it.next());

        this.reported_caches = cbase.getReportedCaches();
    }

    // Getters

    /** @return TreeSet with all the caches in the CacheBase */
    public TreeSet<Cache> getAllCaches () {
      TreeSet<Cache> ts = new TreeSet<Cache>();
      Iterator it = this.caches.iterator();
      Cache cache;

      while (it.hasNext()) {
        cache = (Cache) it.next();
        ts.add(cache.clone());
      }

      return ts;
    }

    /**
     * @return ArrayList with all the caches owned by a given e-mail
     * @param mail Owner e-mail
     */
    public ArrayList<Cache> getCaches (String mail) {
        ArrayList<Cache> result = new ArrayList<Cache>();
        Iterator it = this.caches.iterator();

        while (it.hasNext()) {
            Cache aux = (Cache) it.next();
            if (aux.getMail().equals(mail)) result.add(aux);
        }

        return result;
    }

    /**
     * @return Number of caches stored in the CacheBase
     */
    public int getNumOfCaches () {
        return this.caches.size();
    }

    /**
     * Add a cache to the CacheBase
     * @arg cache Cache to be added
     */
    public void addCache (Cache cache) {
        this.caches.add(cache);
    }

    /**
     * Check if a given Cache is present in the Cache data base
     * @arg cache
     */
    public boolean exists (Cache cache) {
        Iterator it = this.caches.iterator();
        boolean found = false;

        while (it.hasNext() && !found)
            found = cache.equals((Cache) it.next());

        return found;
    }

    /**
     * Check it a given Cache is present in the Cache data base
     * @arg id Cache id
     */
    public boolean exists(double id){
        for(Cache a: this.caches){
            if(a.getId() == id) return true;
        }
        return false;
    }

    /**
     * Get this cache if the id is the right one
     * @arg id The id of that Cache
     */
    public Cache getCache(double id){
       for(Cache a : this.caches){
           //fazer clone cache
           if(a.getId() == id) return a;
        }
        return null;
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

    // Code for reported caches

    /** Add a report
     * @param report The report to be added}
     */
    public void addReport (Report report) throws NullPointerException {
      if (report == null)
        throw new NullPointerException("report can't be null!");

      reported_caches.put(report.getId(), report);
    }

    /** Delete a cache from the CacheBase, and from the reported caches
     * if any reports for the given cache are Present
     * @param id The Cache id
     */
    public void delCache (Double id) {
      Iterator it = this.caches.iterator();
      Cache cache;
      boolean done = false;

      while (it.hasNext() && !false) {
        cache = (Cache) it.next();
        if (cache.getId() == id) {
          this.caches.remove(cache);
          done = true;
        }
      }

      this.reported_caches.remove(id);
    }

    /** @return All reported caches */
    public TreeMap<Double, Report> getReportedCaches () {
      TreeMap<Double, Report> tm = new TreeMap<Double, Report>();

      for (Report rep : this.reported_caches.values()) {
        tm.put(rep.getId(), rep.clone());
      }

      return tm;
    }
}
