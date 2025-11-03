package seedu.address.model.person;

/**
 * Represents a Person's role in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Role {
    public static final int ROLE_MAXIMUM_LENGTH = 150;
    public static final String MESSAGE_CONSTRAINTS =
            "Roles can only contain letters, numbers, spaces, hyphens (-), slashes (/), plus signs (+), "
            + "underscores (_), parentheses (), and apostrophes (').\n" + "Must be 1–" + ROLE_MAXIMUM_LENGTH
            + " characters long.\n" + "Roles must not be blank (only whitespaces).\n"
            + "Refer to the User Guide for full details on slash (/) usage.";
    /*
     * The first character of the role must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(?=.*\\S)[A-Za-z0-9 \\-/_+()']{1," + ROLE_MAXIMUM_LENGTH + "}$";

    public final String value;

    public Role(String role) {
        this.value = role;
    }

    public static boolean isValidRole(String test) {
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
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return value.equals(otherRole.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
