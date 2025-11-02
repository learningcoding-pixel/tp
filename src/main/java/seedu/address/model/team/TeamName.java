package seedu.address.model.team;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TeamName {
    public static final int TEAM_NAME_MAX_LENGTH = 80;
    public static final String MESSAGE_CONSTRAINTS =
            "Team names can only contain letters, numbers, spaces, hyphens (-), apostrophes ('), "
            + "periods (.), and parentheses ( ). "
            + "They must not be blank and must be 1–" + TEAM_NAME_MAX_LENGTH + " characters long.";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX =
            "^(?=.*\\S)[\\p{L}\\p{M}0-9 .'-()]{1," + TEAM_NAME_MAX_LENGTH + "}$";

    public final String fullTeamName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public TeamName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullTeamName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullTeamName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TeamName)) {
            return false;
        }

        TeamName otherTeamName = (TeamName) other;
        return fullTeamName.equals(otherTeamName.fullTeamName);
    }

    @Override
    public int hashCode() {
        return fullTeamName.hashCode();
    }

}
