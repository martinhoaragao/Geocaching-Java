import java.util.GregorianCalendar;
import java.util.ArrayList;

/**
 * Classe geral para Caches. SuperClasse para todos os tipos de cache. Estes vão ser subclasses desta, 
 * public class CacheMini extends Cache
 */
public class Cache
{
    private Coordinates coord;          // Cache coordinates
    private ArrayList<String> registo;  // Cache register
    private ArrayList<Object> tesouro;  // Cache treasure
    private ArrayList<String> infos;    // Cache info
    private String mail;                // Cache owner mail
}
