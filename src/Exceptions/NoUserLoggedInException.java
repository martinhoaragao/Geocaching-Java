/** Class to represent an exception when there is no user logged in in
 *  GeocachingPOO
 *
 *  @version 27/05/2015
 *  @author jfc
 */

package Exceptions;

public class NoUserLoggedInException extends Exception {
  public NoUserLoggedInException () { super("No user logged in."); }
  public NoUserLoggedInException (String s) { super(s); }
}