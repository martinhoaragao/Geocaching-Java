import java.io.Serializable;

/**
* This class represents the Treasures contained in every Caches (except in the Micro Cache that has none).
*/
public class Treasure implements Serializable {
    private String info;
    private String id;


    /**
     * Construtor without arguments
     */
    public Treasure(){
        this.info = "";
        this.id = "";
    }

    /**
     * Constructor of new Treasure
     * @param info String info of treasure
     * @param id String identifier of treasure
     */
    public Treasure(String info, String id){
        this.info = info;
        this.id = id;
    }

    /**
     * Construct a Treasure using another Treasure as reference
     * @param cache Cache
     */
    public Treasure(Treasure treasure){
        this.info = treasure.info;
        this.id= treasure.id;
    }

    // Getters

    /**
     * @return info of a Treasure
     */
    public String getInfo () {
        return this.info;
    }

    /**
     * @return ID of a Treasure
     */
    public String getId () {
        return this.id;
    }

    // Setters

    /**
     * Sets treasure info
     * @param info String cache's info
     */
    public void setInfo (String info) {
        this.info = info;
    }
    /**
     * Set treasure ID
     * @param id, String treasure identifier
     */
    public void setId (String id) {
        this.id = id;
    }


    // toString, equals and clone

    /**
     * Transform treasure info into a String
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();

        sb.append(this.id + " " + this.info + "\n");

        return sb.toString();
    }

    /**
     * Compares this object with another Treasure to check if they are equal
     * @param treasure Object to compare with
     */
    public boolean equals (Object treasure) {
        if (this == treasure) return true;

        if ((treasure == null) || (this.getClass() != treasure.getClass())) return false;

        Treasure aux = (Treasure) treasure;
        boolean comp = this.id.equals(aux.getId());
        comp = comp && (this.info.equals(aux.getInfo()));

        return comp;
    }

    /**
     * Create a clone of a treasure
     */
    public Treasure clone () {
        return new Treasure(this);
    }

}
