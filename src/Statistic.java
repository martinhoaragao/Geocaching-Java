/**
 * Class that represents the Statistics of a given User, in a given month
 * represented by an integer from 0 to 11.
 * Name has to be different from statistics (already used in class User) so I called it "stats".
 *
 * @version 20/05/2015
 */
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Iterator;

public class Statistic
{
    //Could I declare private int month and then TreeSet<month, a> , for instace?
    //my opinion is no, but what do you think?
    private ArrayList< TreeSet<Activity>>  stats; // Visited Caches for each month. Comparable by date.
    
    
    /**
     * Constructor without arguments
     */
    public Statistic(){
       int i;
       stats = new ArrayList<TreeSet<Activity>>(12);
       
       for(i=0;i<12;i++){
           stats.add(new TreeSet<Activity>(new AComparator())); 
       }
    }

    /**
     * Copy Constructor (preserving encapsulation)
     *
     */
    public Statistic(Statistic stt){ //MUDAR
        this.stats = stt.getStats();
    }
    
    /**
     * Method to get the list
     */
    public ArrayList< TreeSet<Activity>> getStats(){
        ArrayList<TreeSet<Activity>> aux = new ArrayList<>();
        int i;
        for( TreeSet<Activity> este : this.stats ){
            aux.add(cloneTreeSet(este));
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
    /**
     * Add an Activity in the Statistic.
     * @param Activity a.
     */
    public void addAct(Activity a){
        int month = a.getMonth();
        this.stats.get(month-1).add(a.clone());
    }
    
    

    /**
     * Removes an Activity in the array of TreeSet<Activity>.
     * 
     * @param Activity a.
     */
    public void removeAct(Activity a){

        int month = a.getMonth();
        this.stats.get(month-1).remove(a);
    }

    /**
     * Get the Set of Activities by a given month.
     * @param int m month.
     */
    public TreeSet<Activity> getTreeSet(int m){
        //Array starts from 0 to 11. Receives 1 , returns stats[0].
        TreeSet<Activity> ret = this.stats.get(m-1);
        TreeSet<Activity> novo = new TreeSet<Activity>(new AComparator());
        for(Activity ac : ret){
            novo.add(ac.clone());
        }
        return novo;
    }

    /**
     * Get a Set of Activities that has a given type of Cache.
     * 
     * @param String with information of how many caches of different type user has.
     */
    public String getinfoNumberCaches(){
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

   /**
    * Returns the current month.
    * @return current month.
    */
   public int getCMonth(){
    GregorianCalendar gc = new GregorianCalendar();
        String monthh = String.valueOf(gc.get(GregorianCalendar.MONTH));
        return Integer.parseInt(monthh)+1;
    }

    /**
     * Returns a List of the last 10 activities.
     * 
     * @return String with information. 
     * (Just for testing times).
     */
    public String get10LastA(){
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

    /**
     * Find a cache with a given id
     */
    public Cache getCacheid(double id){
        int i;
        for(i=0;i<12;i++){
            for(Activity a : this.stats.get(i)){
                if(a.getCache().getId() == id) 
                return a.getCache();
            }
        }
        return null;
    }

    /**
     * Removes a Cache given the id
     */
    public void removeCache(double id){
        int i;
        for(i=0;i<12;i++){
            for(Activity a : this.stats.get(i)){
                if(a.getCache().getId() == id) 
                this.stats.get(i).remove(id);
            }
        }
    }
    
    /**
     * Method that sums all points.
     * @return points : Total of points of this User's Statistic.
     */
    public int getSumPoints(){
        int i,r=0;
        for(i=0;i<12;i++){
            r+=getSumPointsM(i+1);
        }
        return r;
    }
    
    /**
     * Method that sums all points of a given month.
     * @return int Sum of points.
     */
    public int getSumPointsM(int month){
        int sum=0;
        for(Activity a : this.stats.get(month-1)){
            sum+=a.getPoints();
        }
        return sum;
    }
    
    /**
     * Method that sums all kilometers of a given month.
     * @return double Total kms of a month.
     */
    public double getSumkmsM(int month){
        double sum=0;
        for(Activity a : this.stats.get(month-1)){
            sum+=a.getKms();
        }
        return sum;
    }
    
    /**
     * Method that sums all kms.
     * @return int kms : Total of kms of this User's Statistic.
     */
    public double getSumKms(){
        int i; double r=0;
        for(i=0;i<12;i++){
            r+=getSumkmsM(i+1);
        }
        return r;
    }
    
    /**
     * Method that sums all Caches.
     * @return int Total of caches of this User's Statistic.
     */
    public int getSumTotalCaches(){
        int i,r=0;
        for(i=0;i<12;i++){
            r+=getSumAM(i+1);
        }
        return r;
    }
    
    /**
     * Method that sums all activities / caches of a given month.
     */
    public int getSumAM(int month){
        int sum=0;
        for(Activity a : this.stats.get(month-1)){
            sum++;
        }
        return sum;
    }
    /**
     * Clone,toString and equals
     */
    /**
     * Create a clone of this object
     */
    public Statistic clone(){
        return new Statistic(this);
    }

    /**
     * Compare this Statistic to another to check if they are equal
     * @arg sa Statistic to use for comparison
     */
    public boolean equals(Object sa){
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

    /**
     * Convert the info of this Statistic into a string
     */
    public String toString(){
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
}
