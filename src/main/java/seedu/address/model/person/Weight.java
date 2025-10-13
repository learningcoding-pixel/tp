package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's weight in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Weight {
    public static final String MESSAGE_CONSTRAINTS =
            "Weight must be a non-negative number and cannot be blank.";
    public final String value;

    public Weight(String weight) {
        requireNonNull(weight);
        checkArgument(isValidWeight(weight), MESSAGE_CONSTRAINTS);
        this.value = weight;
    }

    public static boolean isValidWeight(String test) {
        if (test == null || test.isBlank()) {
            return false;
        }
        try {
            double value = Double.parseDouble(test);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
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
