/**
 * Class to represent a simple Address with a country and a city
 *
 * @version 11/05/2015
 */

import java.io.Serializable;

public class Address implements Serializable {
    private String city;
    private String country;

    /**
     * Constructor with arguments
     * @param city City name
     * @param country Country name
     */
    public Address (String city, String country) {
        if ((city == null) || (country == null))
            throw new NullPointerException("City or country can't be null");
        if ((city.trim() == "") || (country.trim() == ""))
            throw new IllegalStateException("String can't be null");

        this.city = city; this.country = country;
    }

    /**
     * Empty Constructor
     */
    public Address(){
        this.country = "Portugal";
        this.city="Braga";
    }

    /**
     * Copy Constructor
     */
    public Address(Address a ){
        this.country = a.country;
        this.city = a.city;
    }

    // Getters

    /**
     * Method that returns the city of this Address.
     * @return City in the address
     */
    public String getCity () {
        return this.city;
    }

    /**
     * Method that returns the Country of this Address.
     * @return Country in the address
     */
    public String getCountry () {
        return this.country;
    }

    // Setters

    /**
     * Change city in the address
     * @param city New city for the address
     */
    public void setCity (String city) {
        if (city == null)
            throw new NullPointerException("City can't be null");
        if (city.trim() == "")
            throw new IllegalStateException("City must have content");

        this.city = city;
    }

    /**
     * Change country in the address
     * @param country New country for the adress
     */
    public void setCountry (String country) {
        if (country == null)
            throw new NullPointerException("Country can't be null");
        if (country.trim() == "")
            throw new IllegalStateException("Country must have content");

        this.country = country;
    }


    /**
     * Compare this Address to another to check if they are equal
     * @param ad Address to use for comparison
     */
    public boolean equals(Address ad){
        if(this == ad) return true;
        if(this.getClass() != ad.getClass()) return false;

        Address aux = (Address) ad;
        return aux.getCity().equals(this.getCity()) && aux.getCountry().equals(this.getCountry());
    }


    /**
     * Return Address info as String
     */
    public String toString () {
        StringBuilder sb = new StringBuilder();

        sb.append( city + ", " + country + "\n");
        return sb.toString();
    }

    /**
     * Create a clone of this object
     */
    public Address clone () {
        Address aux = new Address(this.city, this.country);
        return aux;
    }
}