package seedu.address.model.person;

/**
 * Represents a Person's height in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Height {
    public static final String MESSAGE_CONSTRAINTS = "test";
    public final String value;

    public Height(String height) {
        this.value = height;
    }

    public static boolean isValidHeight(String test) {
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
        if (!(other instanceof Height)) {
            return false;
        }

        Height otherHeight = (Height) other;
        return value.equals(otherHeight.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
