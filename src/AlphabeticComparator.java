import java.util.Comparator;

/**
 * Compares two Users depending on their names.
 */
class AlphabeticComparator implements Comparator<User> {
    /**
     * Compare two user's names
     * @param a First User
     * @param b Second User
     */
    @Override
    public int compare (User a, User b) {
        return a.getName().compareTo(b.getName());
    }
}