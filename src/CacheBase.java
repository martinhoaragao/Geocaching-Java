/**
 * CacheBase represents the Data-Base for all the Caches that have been created (by the server
 * or by users).
 *
 * @version 07/05/2015
 */

import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Iterator;

public class CacheBase {
    private TreeSet<Cache> caches;    // All the existing Caches.

    /**
     * Constructor without arguments
     */
    public CacheBase () {
      this.caches = new TreeSet<Cache>();
    }

    /**
     * Constructs a UserBase with the caches present on another UserBase
     * @arg cbase CacheBase from where the caches will be copied
     */
    public CacheBase (CacheBase cbase) {
      TreeSet<Cache> ts = cbase.getAllCaches();
      Iterator it = ts.iterator();

      while (it.hasNext())
        this.caches.add((Cache) it.next());
    }

    // Getters

    /**
     * @return TreeSet with all the caches stored on the CacheBase
     */
    public TreeSet<Cache> getAllCaches () {
      return (TreeSet<Cache>) this.caches.clone();
    }

    /**
     * @return ArrayList with all the caches owned by a given e-mail
     * @arg mail Owner e-mail
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
    public boolean exists(String id){
        for(Cache a: this.caches){
            if(a.getID().equals(id)) return true;
        }
        return false;
    }
    
    /**
     * Get this cache if the id is the right one
     * @arg id The id of that Cache
     */
    public Cache getCache(String id){
       for(Cache a : this.caches){
           if(a.getID().equals(id)) return a.clone();
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
}
