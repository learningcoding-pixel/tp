package seedu.address.model.team.session;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Session's Location in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Location {
    public static final int LOCATION_MAXIMUM_LENGTH = 255;
    public static final String MESSAGE_CONSTRAINTS =
            "Addresses may contain letters, numbers, spaces, commas (,), periods (.), hyphens (-), "
            + "apostrophes ('), slashes (/), ampersands (&), hash (#), semicolons (;), and parentheses ( ). "
            + "It must not be blank and must be at most " + LOCATION_MAXIMUM_LENGTH + " characters.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX =
            "^(?=\\S)(?=.*\\S)[\\p{L}\\p{M}0-9 .,'\\-&/#();]{1," + LOCATION_MAXIMUM_LENGTH + "}$";

    public final String value;

    /**
     * Constructs a {@code location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        value = location;
    }

    public static boolean isValidLocation(String test) {
        return test.matches(VALIDATION_REGEX);
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
        return value.equalsIgnoreCase(otherLocation.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
