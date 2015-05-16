import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;

/**
 * Classe to save information of all Caches reported by an User.
 *
 * @version 16/05/2015
 */

public class ReportedCacheBase{
    private TreeMap<String,Cache> reports; //id -> Cache
    private static CacheBase cachebase; //TreeSet 
    
     /**
     * Constructor without arguments
     */
    public ReportedCacheBase(){
        reports = new TreeMap<>();
    }
    
     /**
     * Construct a ReportedCacheBase using another ReportedCacheBase as reference
     * @arg reportsCache ReportedCacheBase from where Users will be fetched
     */
    public ReportedCacheBase(ReportedCacheBase reportsCache){
        TreeSet caches = reportsCache.getCaches();
        Iterator it = caches.iterator();
        while(it.hasNext()){
            Cache aux = (Cache) it.next();
            this.reports.put(aux.getID(), aux);
        }
    }
    
    //Getters
    /**
     * Returns the cache with the specified id if it exists
     * @arg id Cache id
     * 
     */
    public Cache getCache(String id){
        Cache c = this.reports.get(id);
        if(c == null) return null;
        else{
            return c;
        }
    }
    
    
    /**
     * @return Number of caches in the reports data base
     */
    public int getNumRC(){
        return this.reports.size();
    }
    
    
    /**
     * @return TreeSet with all the caches in the ReportedCacheBase
     * A.CLONE tava a dar bue erros porque a parte das caches ta atrasada.
     * PORTANTO ARRANJAR ISSO DOS CLONES NA CLASSE CACHE SFFV.
     * FIX FIX FIX!!
     */
    public TreeSet getCaches(){
        TreeSet<Cache> cc = new TreeSet<>();
        
        for(Cache a : this.reports.values()){
            cc.add(a.clone());
            
        }
        return cc;
    }
    
    
    
    // Other methods

    /**
     * Add a cache to the reports
     * @arg ca Cache to be added
     */
     public void addCache (Cache ca){
       this.reports.put(ca.getID(), ca.clone());  
     }
    
    /**
     * Add a cache by the id
     */
    public void addCache(String id){
        //put(id, getCachebyIDfromtheCACHEBASE)
        if(cachebase.exists(id)) this.reports.put(id,cachebase.getCache(id) );
    }
     
    /**
     * Check if a given id already has a Cache associated
     * @arg id ID to check if it's already in use
     */
    public boolean exists(String id){
        return this.reports.containsKey(id);
    }
    
    /**
     * Check if a given Cache is in the ReportedCacheBase
     * @arg ca Cache to be found in the ReportedCacheBase
     */
    public boolean exists(Cache ca){
        return this.reports.containsValue(ca);
    }
    
    
    // toString, equals and clone

    /**
     * Create a String with all the id's in the ReportedCacheBase
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(this.reports.size() + " Caches.\n");

        for (String id : this.reports.keySet())
            sb.append(id + "\n");

        return sb.toString();
    }
    
    /**
     * Compare if two ReportedCacheBase have the same id's and the same number of caches,
     * should be modified to also check if the caches are equal
     * @arg rbase ReportedCacheBase to use for comparison
     */
    public boolean equals(Object rbase){
        if(rbase == this) return true;
        
        if(rbase.getClass() != this.getClass() || (rbase == null) ) return false;
        
        ReportedCacheBase a = (ReportedCacheBase) rbase;
        if(this.reports.size() != a.reports.size()) return false;
        
        for(Cache c : this.reports.values()){
            if(!a.exists(c.getID())) return false;
            
        }
        return true;
    }
    
    
    /**
     * Create a clone of this ReportedCacheBase
     */
    public ReportedCacheBase clone(){
        return new ReportedCacheBase(this);
    }
    
}