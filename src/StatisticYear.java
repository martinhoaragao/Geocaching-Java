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
import java.io.Serializable;
import Exceptions.*;

public class StatisticYear implements Serializable
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
    public void addAct(Activity a) throws NotAddedActivityYearIncorrectException{
       int month = a.getMonth();
       int year = a.getYear();

       //o map substitui, portanto, ele atualiza com os novos e adiciona

       Statistic aux = this.statsyear.get(year); //devolve-me estatistica deste ano
       if(aux==null) aux = new Statistic();
       aux.setYearStatsMonth(year);

       aux.addAct(a);
       //Nao precisa de fazer put porque ele já atualiza logo, tem o get emcima.
       this.statsyear.put(year, aux);
    }


    /**
     * Removes an Activity
     *
     * @param Activity a.
     */
    public void removeAct(Activity a) throws NullPointerException{
        int year = a.getYear();
        int month = a.getMonth();

        if( !this.statsyear.containsKey(year) )
            //return; //Nao existe o ano neste map logo nao existe esta atividade no map
            throw new NullPointerException( "Activity does not exist " );
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
    public int[] getNumberCaches(){
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
    public String getinfoNCaches(){
        int[] caches = getNumberCaches();
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
     * Find a cache with a given id
     */
    public Cache getCacheid(double id){
        Cache x = null;
        for(Statistic stats : this.statsyear.values()){
            x = stats.getCacheid(id);
            if(x != null) return x;
        }
        return x;
    }

    /**
     * Removes a Cache given the id
     * 
     */
    //TODO testar sem o statsyear.put();
    public boolean removeCache(double id){
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
    public int getSumPoints(){
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
    public int getSumPoints(int year){
        int sum=0;
        Statistic stats = this.statsyear.get(year);
        sum+=stats.getSumPoints();
        return sum;
    }

    /**
     * Method that sums all kilometers of a given year.
     * @return double Total kms of a year.
     */
     public double getSumKms(int year){
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
    public int getTotalCaches(){
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
    public int getAtivities(int year) throws NullPointerException{
        Statistic stats = this.statsyear.get(year);
        if(stats == null) throw new NullPointerException( "Activities don't exist " );
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
            sb.append(" #Caches: " + this.getAtivities(year) + ".");
            sb.append("\n");
            sb.append(" #Points: " + this.getSumPoints(year)+ ".");
            sb.append("\n");
            sb.append(" #Kms travelled: " + this.getSumKms(year) + ".");
            sb.append("\n");


        }
        sb.append(this.getinfoNCaches());
        //System.out.println(sb.toString());
        return sb.toString();
    }


}
