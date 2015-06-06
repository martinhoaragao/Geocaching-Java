/**
 * Class to represent the report of a cache
 *
 * @author Jc
 * @version 20/05/2015
 */

public class Report {
    private static Double id;      // The cache id
    private static String mail;     // E-mail of the user that reported
    private static String message;  // Report message

    /** Unparameterized constructor */
    public Report () {
        this.id = 0.0;
        this.mail = "";
        this.message = "";
    }

    /** 
    * Parameterized constructor
     * @param id The cache id
     * @param mail The user e-mail
     * @param message The report message
     */
    public Report (Double id, String mail, String message)
    throws NullPointerException, IllegalStateException {

        // Exceptions
        if (mail == null)
            throw new NullPointerException("mail can't be null!");
        if (mail.trim() == "")
            throw new IllegalStateException("mail can't be empty!");

        if (message == null)
            throw new NullPointerException("message can't be null!");
        if (message.trim() == "")
            throw new IllegalStateException("message can't be empty!");

        this.id = id;
        this.mail = new String(mail);
        this.message = new String(message);
    }

    // Getters

    /** 
    * Gets the id of the reported cache.
    *@return The cache id 
    */
    public Double getId () {
        return this.id;
    }

    /** 
    * Gets the e-mail of the person who has created this report.
    * @return The user e-mail */
    public String getMail () {
        return this.mail;
    }

    /** 
    * Gets the report message.
    * @return The report message 
    */
    public String getMessage () {
        return this.message;
    }

    // Setters

    /** 
    * Change the reported cache id
     * @param id The cache id
     */
    public void setId (double id) {
        this.id = id;
    }

    /** 
    * Change the mail of the user that reported
     * @param mail The user e-mail
     */
    public void setMail (String mail) throws NullPointerException, IllegalStateException {
        if (mail == null)
            throw new NullPointerException("mail can't be null!");
        if (mail.trim().equals(""))
            throw new IllegalStateException("mail can't be empty");

        this.mail = mail;
    }

    /** 
    * Change the report message
     * @param message New report message
     */
    public void setMessage (String message) throws NullPointerException, IllegalStateException {
        if (message == null)
            throw new NullPointerException("message can't be null!");
        if (message.trim().equals(""))
            throw new IllegalStateException("message can't be empty!");

        this.message = message;
    }

    // toString, clone & equals

    /** 
    * Create String with report info 
    */
    public String toString () {
        StringBuilder sb = new StringBuilder();

        sb.append("Cache id: " + id + "\n");
        sb.append("Reported by: " + mail + "\n");
        sb.append("Report message: " + message + "\n");

        return sb.toString();
    }

    /** 
    *Check if two Report are equals
     * @param report Report to use for comparison
     * @return true if they have the same e-mail and id, false otherwise
     */
    public boolean equals (Object report) {
        if (report == this) return true;

        if ((report == null) || ((report.getClass()) != (this.getClass())))
            return false;

        boolean comp = true;
        Report rep = (Report) report;
        comp = comp && (this.id == rep.getId());
        comp = comp && (this.mail.equals(rep.getMail()));
        return comp;
    }

    /** Create clone of the Report */
    public Report clone () {
        return new Report(id, mail, message);
    }
}
