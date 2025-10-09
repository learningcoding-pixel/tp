package seedu.address.model.person;

import seedu.address.commons.util.AppUtil;

/**
 * Represents a Person's Date of Birth (DOB).
 * Guarantees: immutable, valid date in YYYY-MM-DD format.
 */
public class Dob {

    public static final String MESSAGE_CONSTRAINTS =
            "Date of Birth should be in the format YYYY-MM-DD and should not be blank";

    public final String value;

    /**
     * Constructs a {@code Dob}.
     *
     * @param dob A valid date in YYYY-MM-DD format.
     */
    public Dob(String dob) {
        AppUtil.checkArgument(isValidDob(dob), MESSAGE_CONSTRAINTS);
        this.value = dob;
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDob(String test) {
        return test.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Dob)) {
            return false;
        }
        Dob otherDob = (Dob) other;
        return otherDob.value.equals(this.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}