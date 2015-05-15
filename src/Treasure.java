


public class Treasure{
    private String name;
    private String id;
    
    public Treasure(String name, String id){
        this.name = name;
        this.id= id;
    }
    
    public Treasure(){
        this.name = "Coffee capsula";
        this.id = "001";
    }
    
    /**
     * Getters and Setters
     */
    
    public String getName(){ return this.name; }
    public String getID(){return this.id;}
    
    public void setName(String name){this.name = name;}
    public void setID(String id){this.id = id;}
    
    
    /**
     * Clone, toString, equals
     */
    
}