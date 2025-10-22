package seedu.address.model.team.session;

/**
 * Represents a Session's Location in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Location {
    public static final String MESSAGE_CONSTRAINTS = "test";
    public final String value;

    public Location(String location) {
        this.value = location;
    }

    public static boolean isValidLocation(String test) {
        return true;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Location)) {
            return false;
        }

        Location otherLocation = (Location) other;
        return value.equals(otherLocation.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
