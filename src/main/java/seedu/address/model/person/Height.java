package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's height in the RelayCoach app.
 * Guarantees: immutable; is valid as declared in {@link #isValidHeight(String)}
 */
public class Height {

    public static final String MESSAGE_CONSTRAINTS =
            "Heights should be a positive number between 50 and 300 cm, and may include up to one decimal place."
            + "\nHeight must not be blank (only whitespaces).";
    /*
     * The height must be:
     * - A number between 50 and 300 (inclusive)
     * - Can have at most one decimal place
     * The regex allows either an integer or a decimal with one decimal digit.
     */
    public static final String VALIDATION_REGEX =
            "^(?:[5-9]\\d(?:\\.\\d)?|1\\d{2}(?:\\.\\d)?|2\\d{2}(?:\\.\\d)?|300(?:\\.0)?)$";

    public final String value;

    /**
     * Constructs a {@code Height}.
     *
     * @param height A valid height.
     */
    public Height(String height) {
        requireNonNull(height);
        checkArgument(isValidHeight(height), MESSAGE_CONSTRAINTS);
        this.value = String.format("%.1f", Float.parseFloat(height)); //ensure only 1 decimal place
    }

    /**
     * Returns true if a given string is a valid height.
     */
    public static boolean isValidHeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value + " cm";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Height // instanceof handles nulls
                && value.equals(((Height) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
