package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's weight in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Weight {
    public static final String MESSAGE_CONSTRAINTS =
            "Weight must be a positive number between 25 and 120 kg (inclusive), and may have up to one decimal place.";
    public static final String VALIDATION_REGEX =
            "^(?:2[5-9](?:\\.\\d)?|[3-9]\\d(?:\\.\\d)?|1[01]\\d(?:\\.\\d)?|120(?:\\.0)?)$";
    public final String value;

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A valid weight.
     */
    public Weight(String weight) {
        requireNonNull(weight);
        checkArgument(isValidWeight(weight), MESSAGE_CONSTRAINTS);
        this.value = weight;
    }

    /**
     * Returns true if a given string is a valid weight.
     */
    public static boolean isValidWeight(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.value + " kg";
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
