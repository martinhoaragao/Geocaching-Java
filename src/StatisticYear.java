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
    private TreeMap<Integer, Statistic>  statsyear; // Visited Caches for each month. Comparable by date.
    
    
    /**
     * Constructor without arguments
     */
    public StatisticYear(){
       int i;
       statsyear = new TreeMap<Integer,Statistic>();
       
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
    public TreeMap< Integer, Statistic > getStatsyear(){
        TreeMap<Integer,  Statistic> aux = new TreeMap<>();
        int year;
        for(Statistic stats : this.statsyear.values()){
            year = stats.getYearStatsMonth();
            /*firstKey()  Returns the first (lowest) key currently in this map. */
            aux.put(year, stats.clone());
        }
        return aux;
    }

    /**
     * Methods
     */
    /**
     * Add an Activity in the StatisticYear.
     * @param Activity a.
     * TODO verificar que nao precida de fazer put depois de inserir ... ? 
     * tirar o boolean e mete exception...
     */
    public boolean addActY(Activity a){
       int month = a.getMonth();
       int year = a.getYear();
       
       //o map substitui, portanto, ele atualiza com os novos e adiciona
       
       Statistic aux = this.statsyear.get(year); //devolve-me estatistica deste ano
       if(aux==null) aux = new Statistic();
       aux.setYearStatsMonth(year);
      boolean inseriu =  aux.addAct(a); //Adiciona nova à estatistica deste ano.
       //A outra class Statistic trata de a inserir no mÊs correto.
       //Ela lá já faz clone.
       
       if(inseriu){
       this.statsyear.put(year, aux); //Ao fazer isto substitui com o novo adicionado.
       //O anterior apenas adiciona, este susbtitui com o aux que supostamente vai ter mais 1 atividade que a antiga.
       return true;
      }
       else{
           System.out.println("Não inseriu... nao é o ano correto!");
           //Isto é o que deve ter no geocaching. Nao apagar, só quando se meter no geocaching é que se apaga aqui.
           return false;
        }
    }
    
    
    /**
     * Removes an Activity
     * 
     * @param Activity a.
     */
    public void removeActY(Activity a){
        int year = a.getYear();
        int month = a.getMonth();
        
        if( !this.statsyear.containsKey(year) )
            return; //Nao existe o ano neste map logo nao existe esta atividade no map
        else{
            Statistic stats = this.statsyear.get(year); 
            //Dá me as estatisticas no mÊs desse ano.
            //É impossivel ser null agora pois já passou pelo teste acima.
            stats.removeAct(a); //Remove da estatistica desse ano
            //Atualiza este no map, logo subtitui o antigo por este novo onde já foi removida esta atividade. Correto?
            this.statsyear.put(year, stats);
        }
    
    }

    /**
     * Get the Statistic of Activities by a given year.
     * @param int year.
     */
    public Statistic getStatistic(int year){
        return this.statsyear.get(year);
    }
    
    /**
     * Get the Set of ativities done in a year.
     * 
     * This method addes them in a new set.
     */
    public TreeSet<Activity> getSetOfYear(){
        TreeSet<Activity> novo = new TreeSet<>();
        TreeSet<Activity> aux;
        for(Statistic stats : this.statsyear.values()){
            //Para todas as estatisticas que foram mapeadas dado um ano
            for(int i=0; i<12; i++){ //Para todos os meses.
                TreeSet<Activity> este = stats.getStats().get(i);
                //Estou no mês 0, 1, ..... 11
                //Para cada set do mês que está nessa estatistica desse ano
                 novo.addAll(este); 
                 //Adiciona as atividades todas do set para este em vez de add uma a uma.
            }
           }
        return novo;
        }
        
    /**
     * Returns in an auxiliary array.
     * New version
     */    
    public int[] getNCachesArray(){
        int[] caches = new int[4];
        int micro=0, multi=0, trad=0, mystery=0, virtual = 0, event = 0;
        
        for(Statistic stats : this.statsyear.values()){
            int[] cachesaux = stats.getNumberCaches();
            micro += cachesaux[0]; multi +=cachesaux[1]; 
            trad+= cachesaux[2]; mystery += cachesaux[3];
            
        }
        caches[0] = micro;
        caches[1] = multi;
        caches[2] = trad;
        caches[3] = mystery;
        return caches;
    }
    
    /**
     * @param String with information of how many caches of different type user has.
     */
    public String getinfoNumberCachesY(){
        int[] caches = getNCachesArray();
        StringBuilder sb = new StringBuilder();
        
        sb.append("Micro :"); sb.append(Integer.toString(caches[0]) + ".");
        sb.append("\n");
        sb.append("Multi :"); sb.append(Integer.toString(caches[1])+ ".");
        sb.append("\n");
        sb.append("Tradicional :"); sb.append(Integer.toString(caches[2])+ ".");
        sb.append("\n");
        sb.append("Mystery :"); sb.append(Integer.toString(caches[3])+ ".");
        sb.append("\n");
        System.out.println(sb.toString());
        return sb.toString();
    }

   /**
    * Returns the current month.
    * @return current month.
    * 
    * TEM NA STATISTIC.. NAO VEJO QUAL UTILIDADE DE TER AQUI 
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
     * 
     * TA NO STATISTIC MAS NEM NO STATISTIC É PRECISO PORQUE ISTO
     * TA NO USER CERTO?
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
    public Cache getCacheidYear(double id){
        Cache x = null;
        for(Statistic stats : this.statsyear.values()){
            x = stats.getCacheid(id);
            if(x != null) return x;
        }
        return x;
    }

    /**
     * Removes a Cache given the id
     * TODO  tenho duvida nisto agora... manteinho o put ou tiro?
     */
    public boolean removeCacheY(double id){
      boolean removeu = false;
      for(Statistic stats : this.statsyear.values()){
           removeu = stats.removeCache(id);
           if(removeu){
               statsyear.put(stats.getYearStatsMonth(), stats);
               //Se removeu atualiza o map com ela removida.
               //Ou seja, mapeia o Statistic que tem a cache removida.
               //Nestas estatisticas, chamar Cache ou Atividade assumimos que diz respeito à mesma coisa.
               return true;
            }
      }
      return removeu;
    }
   
    /**
     * Method that sums all points.
     * @return points : Total of points of this User's Statistic.
     */
    public int getSumPointsY(){
       int r=0;
       for(Statistic stats : this.statsyear.values()){
           r+=stats.getSumPoints();
        }
        return r;
    }
    
    /**
     * Method that sums all points of a given year.
     * @return int year Sum of points.
     */
    public int getSumPointsY(int year){
        int sum=0;
        Statistic stats = this.statsyear.get(year);
        sum+=stats.getSumPoints();
        return sum;
    }
    
    /**
     * Method that sums all kilometers of a given year.
     * @return double Total kms of a year.
     */
     public double getSumkmsY(int year){
        double sum=0;
        
        Statistic stats = this.statsyear.get(year);
        sum+=stats.getSumKms();
        
        return sum;
    }
  
    /**
     * Method that sums all kms.
     * @return int kms : Total of kms of this User's Statistic.
     */
    public double getSumKms(){
        double r=0;
       for(Statistic stats : this.statsyear.values()){
           r+=stats.getSumKms();
           //This calls the method created in the Statistic.
        }
        return r;
    }

    /**
     * Method that sums all Caches / Activities.
     * @return int Total of caches of this User's Statistic.
     */
    public int getSumTotalCachesAY(){
        int r=0;
        for(Statistic stats : this.statsyear.values()){
            r+=stats.getTotalCaches();
        }
        return r;
    }
    
    /**
     * Method that sums all activities / caches of a given year.
     * 
     * @param year
     */
    public int getActivitiesY(int year){
        Statistic stats = this.statsyear.get(year);
        return stats.getTotalCaches();
    }
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
    public boolean equals(Object o){
        int i;
        if (this == o) return true;
        if(o.getClass() != this.getClass()) return false;
        
        StatisticYear a = (StatisticYear) o;
        if(a.statsyear.size() != this.statsyear.size()) return false;
        
        
        
        for ( Statistic stats : a.statsyear.values() ){
            if(!this.statsyear.containsValue(stats)) return false;
        }
        
        //TODO verificar que duas sao iguais
        return true;
    }

    /**
     * Convert the info of this Statistic into a string
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        for(Statistic stats : this.statsyear.values()){
            int year = stats.getYearStatsMonth();
            sb.append("Year: " + year);
            sb.append(" #Caches: " + this.getActivitiesY(year) + ".");
            sb.append("\n");
            sb.append(" #Points: " + this.getSumPointsY(year)+ ".");
            sb.append("\n");
            sb.append(" #Kms travelled: " + this.getSumkmsY(year) + ".");
            sb.append("\n");
            
            
        }
        sb.append(this.getinfoNumberCachesY());
        System.out.println(sb.toString());
        return sb.toString();
    }
   
    
}
