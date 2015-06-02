package Exceptions;

public class NoAdminLoggedInException extends Exception {
  public NoAdminLoggedInException () { super(); }
  public NoAdminLoggedInException (String s) { super(s); }
}