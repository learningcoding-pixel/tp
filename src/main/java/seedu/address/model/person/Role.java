package seedu.address.model.person;

/**
 * Represents a Person's role in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Role {
    public static final String MESSAGE_CONSTRAINTS = "Role must be a valid role";
    public final String value;
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

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
