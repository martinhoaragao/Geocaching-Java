/**
 * Class that represents the Statistics of a given User, in a given year
 * represented by an integer from 0 to 11. ????
 *
 * @version 20/05/2015
 */
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;

public class StatisticYear
{
    //Could I declare private int month and then TreeSet<month, a> , for instace?
    //my opinion is no, but what do you think?
    private TreeMap<Integer, TreeSet<Activity>>  statsyear; // Visited Caches for each month. Comparable by date.
    
    
    /**
     * Constructor without arguments
     */
    public StatisticYear(){
       int i;
       statsyear = new TreeMap<Integer,TreeSet<Activity>>();
       
    }

    /**
     * Copy Constructor (preserving encapsulation)
     *
     */
    public StatisticYear(StatisticYear stt){ //MUDAR
        
        this.statsyear = getStatsyear();

    }
    
    /**
     * Method to get the map?
     */
    public TreeMap< Integer, TreeSet<Activity>> getStatsyear(){
        TreeMap<Integer, TreeSet<Activity>> aux = new TreeMap<>();
        
        for( TreeSet<Activity> este : this.statsyear.values() ){
           
            aux.put( este.first().getYear(),cloneTreeSet(este));
        }
        return aux;
    }

    
    
    /**
     * Method to clone the set
     */
    public TreeSet<Activity> cloneTreeSet( TreeSet<Activity> ts){
        TreeSet<Activity> novo = new TreeSet<>(new AComparator());
        Iterator<Activity> it = ts.iterator();
        
        while(it.hasNext()){
            novo.add(it.next());
        }
        
        return novo;
    }
    

    /**
     * Methods
     */
    
 // COMEÇAR AQUI  
 //TODO
    /**
     * Add an Activity in the Statistic.
     * @param Activity a.
     */
  /*  public void addActY(Activity a){
       
    }
    */
    

    /**
     * Removes an Activity in the array of TreeSet<Activity>.
     * 
     * @param Activity a.
     */
  /*  public void removeActY(Activity a){

        
    }
*/
    /**
     * Get the Set of Activities by a given month.
     * @param int m month.
     */
  /*  public TreeSet<Activity> getTreeSetY(int m){
        
    }
*/
    /**
     * Get a Set of Activities that has a given type of Cache.
     * 
     * @param String with information of how many caches of different type user has.
     */
 /*   public String getinfoNumberCachesY(){
        StringBuilder sb = new StringBuilder();
        int micro=0, multi=0, trad=0, mystery=0, virtual = 0, event = 0,i;
        for(i=0;i<12;i++){
            for(Activity a : this.stats.get(i)){
                if (a.getCache() instanceof MicroCache) micro++;
                if(a.getCache() instanceof MultiCache) multi++;
                if(a.getCache() instanceof TraditionalCache) trad++;
                if(a.getCache() instanceof MysteryCache) mystery++;
                //if(a.getCache() instanceof Virtual) virtual++;
                //if(a.getCache() instanceof Event) event++;
            }
        }
        sb.append("Micro :"); sb.append(Integer.toString(micro) + ".");
        sb.append("\n");
        sb.append("Multi :"); sb.append(Integer.toString(multi)+ ".");
        sb.append("\n");
        sb.append("Tradicional :"); sb.append(Integer.toString(trad)+ ".");
        sb.append("\n");
        sb.append("Mystery :"); sb.append(Integer.toString(mystery)+ ".");
        sb.append("\n");
        System.out.println(sb.toString());
        return sb.toString();
    }
*/
   /**
    * Returns the current month.
    * @return current month.
    */
  /* public int getCMonthY(){
    GregorianCalendar gc = new GregorianCalendar();
        String monthh = String.valueOf(gc.get(GregorianCalendar.MONTH));
        return Integer.parseInt(monthh)+1;
    }
*/
    /**
     * Returns a List of the last 10 activities.
     * 
     * @return String with information. 
     * (Just for testing times).
     */
  /*  public String get10LastAYear(){
        int number = 0; boolean acabou = false;
        int mesatual = getCMonth()-1;
        int meses = 0;
        StringBuilder sb = new StringBuilder();
        while(number < 10 || !acabou){
            
            while(this.stats.get(mesatual).size() == 0){
                if(mesatual == 0) mesatual = 11; else mesatual--;
                meses++;
            } //Encontrou no array set que tem conteudo.
            
            for(Activity a : this.stats.get(mesatual)){
            sb.append(Integer.toString(number+1) + "º: ");
            sb.append(a.toString() + ".\n" );
            number++;
            if(number == 9) return sb.toString();
            }
            meses++;
            mesatual--; if(mesatual == 0) mesatual = 11;
            while(this.stats.get(mesatual).size() == 0){
                if(mesatual == 0) mesatual = 0;
                else mesatual--;
                meses++;
            } //Procura um set nao vazio no mês anterior a este para completar as 10.
            
            if(meses >=11) acabou = true;
            //call activity.toString();
        }
        return sb.toString();
    }
*/
    /**
     * Find a cache with a given id
     */
/*    public Cache getCacheidYear(double id){
        int i;
        for(i=0;i<12;i++){
            for(Activity a : this.stats.get(i)){
                if(a.getCache().getId() == id) 
                return a.getCache();
            }
        }
        return null;
    }
*/
    /**
     * Removes a Cache given the id
     */
 /*   public void removeCacheY(double id){
        int i;
        for(i=0;i<12;i++){
            for(Activity a : this.stats.get(i)){
                if(a.getCache().getId() == id) 
                this.stats.get(i).remove(id);
            }
        }
    }
  */  
    /**
     * Method that sums all points.
     * @return points : Total of points of this User's Statistic.
     */
 /*   public int getSumPointsY(){
        int i,r=0;
        for(i=0;i<12;i++){
            r+=getSumPointsM(i+1);
        }
        return r;
    }
  */  
    /**
     * Method that sums all points of a given year.
     * @return int Sum of points.
     */
  /*  public int getSumPointsY(int month){
        int sum=0;
        
    }
  */  
    //Começar a tratar tudo , nao mudei assinaturas a partir daqui agora
    /**
     * Method that sums all kilometers of a given year.
     * @return double Total kms of a month.
     */
  /*  public double getSumkmsM(int month){
        double sum=0;
        for(Activity a : this.stats.get(month-1)){
            sum+=a.getKms();
        }
        return sum;
    }
  */  
    /**
     * Method that sums all kms.
     * @return int kms : Total of kms of this User's Statistic.
     */
 /*   public double getSumKms(){
        int i; double r=0;
        for(i=0;i<12;i++){
            r+=getSumkmsM(i+1);
        }
        return r;
    }
   */ 
    /**
     * Method that sums all Caches.
     * @return int Total of caches of this User's Statistic.
     */
  /*  public int getSumTotalCaches(){
        int i,r=0;
        for(i=0;i<12;i++){
            r+=getSumAM(i+1);
        }
        return r;
    }
  */  
    /**
     * Method that sums all activities / caches of a given month.
     */
 /*   public int getSumAM(int month){
        int sum=0;
        for(Activity a : this.stats.get(month-1)){
            sum++;
        }
        return sum;
    }*/
    /**
     * Clone,toString and equals
     */
    /**
     * Create a clone of this object
     */
    public StatisticYear clone(){
        return new StatisticYear(this);
    }

    /**
     * Compare this Statistic to another to check if they are equal
     * @arg sa Statistic to use for comparison
     */
   /* public boolean equals(Object sa){
        int i;
        if (this == sa) return true;
        if(sa.getClass() != this.getClass()) return false;
        
        Statistic a = (Statistic) sa;
        for(i=0;i<12;i++){
            if(a.stats.get(i).size() != this.stats.get(i).size()) return false;
            for(Activity ac : a.stats.get(i)){
                if(!this.stats.get(i).contains(ac)) return false;
            }
        }
        return true;
    }
*/
    /**
     * Convert the info of this Statistic into a string
     */
 /*   public String toString(){
        StringBuilder sb = new StringBuilder();
        int i;
        for(i=0;i<12;i++){
            sb.append("Month: " + (i+1));
            sb.append(" #Caches: " + this.getSumAM(i+1) + ".");
            sb.append("\n");
            sb.append(" #Points: " + this.getSumPointsM(i+1)+ ".");
            sb.append("\n");
            sb.append(" #Kms travelled: " + this.getSumkmsM(i+1) + ".");
            sb.append("\n");
            
            
        }
        sb.append(this.getinfoNumberCaches());
        System.out.println(sb.toString());
        return sb.toString();
    }
   */ 
    
}
