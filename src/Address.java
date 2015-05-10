/**
 * Class to represent a simple Address with a country and a city
 *
 * @version 08/05/2015
 */

public class Address {
    private String city;
    private String country;

    /**
     * Constructor with arguments
     * @arg city City name
     * @arg country Country name
     */
    public Address (String city, String country) {
        if ((city == null) || (country == null))
            throw new NullPointerException("City or country can't be null");
        if ((city.trim() == "") || (country.trim() == ""))
            throw new IllegalStateException("String can't be null");

        this.city = city; this.country = country;
    }

    // Getters

    /**
     * @return City in the address
     */
    public String getCity () {
        return this.city;
    }

    /**
     * @return Country in the address
     */
    public String getCountry () {
        return this.country;
    }

    // Setters

    /**
     * Change city in the address
     * @arg city New city for the address
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
     * @arg country New country for the adress
     */
    public void setCountry (String country) {
        if (country == null)
            throw new NullPointerException("Country can't be null");
        if (country.trim() == "")
            throw new IllegalStateException("Country must have content");

        this.country = country;
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