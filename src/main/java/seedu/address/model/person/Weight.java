package seedu.address.model.person;

/**
 * Represents a Person's weight in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Weight {
    public static final String MESSAGE_CONSTRAINTS = "test";
    private final String weight;

    public Weight(String weight) {
        this.weight = weight;
    }

    public static boolean isValidWeight(String test) {
        return true;
    }

    @Override
    public String toString() {
        return this.weight;
    }
}
