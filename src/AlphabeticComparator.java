import java.util.Comparator;

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