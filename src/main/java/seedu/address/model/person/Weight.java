package seedu.address.model.person;

/**
 * Represents a Person's weight in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Weight {
    public static final String MESSAGE_CONSTRAINTS = "test";
    public final String value;

    public Weight(String weight) {
        this.value = weight;
    }

    public static boolean isValidWeight(String test) {
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
        if (!(other instanceof Weight)) {
            return false;
        }

        Weight otherWeight = (Weight) other;
        return value.equals(otherWeight.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
