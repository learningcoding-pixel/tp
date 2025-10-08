package seedu.address.model.person;

/**
 * Represents a Person's height in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Height {
    public static final String MESSAGE_CONSTRAINTS = "test";
    private final String height;

    public Height(String height) {
        this.height = height;
    }

    public static boolean isValidHeight(String test) {
        return true;
    }

    @Override
    public String toString() {
        return this.height;
    }
}
