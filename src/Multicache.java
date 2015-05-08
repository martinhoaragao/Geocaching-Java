/**
 * Subclass of Cache which the main difference between all other types of caches is that
 * the User needs to visit more than one place to find the right coordinates for the
 * final Cache for his treasure.
 * 
 *  @version 08/05/2015
 */

import java.util.ArrayList;

public class Multicache extends Cache
{
    private ArrayList<Coordinates> locals;
    private int i; //Indicates the local where I am at the moment. Locals saved in fixed-size array.
    
}