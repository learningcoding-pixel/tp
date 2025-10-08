package seedu.address.model.person;

/**
 * Represents a Person's role in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Role {
    public static final String MESSAGE_CONSTRAINTS = "test";
    private final String role;

    public Role(String role) {
        this.role = role;
    }

    public static boolean isValidRole(String test) {
        return true;
    }

    @Override
    public String toString() {
        return this.role;
    }
}
