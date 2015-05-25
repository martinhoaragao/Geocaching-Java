public class EmailAlreadyInUseException extends Exception {
  public EmailAlreadyInUseException () { super(); }
  public EmailAlreadyInUseException (String s) { super(s); }
}