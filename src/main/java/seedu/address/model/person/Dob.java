package seedu.address.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.commons.util.AppUtil;

/**
 * Represents a Person's Date of Birth (DOB).
 * Guarantees: immutable, valid date in YYYY-MM-DD format.
 */
public class Dob {

    public static final String MESSAGE_CONSTRAINTS =
            "Date of Birth must be a valid calendar date in the format YYYY-MM-DD.\n"
                    + "Must not be blank (only whitespaces), and must not be a future date.";

    private static final DateTimeFormatter DOB_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);

    public final String value;
    private final LocalDate dateValue;

    /**
     * Constructs a {@code Dob}.
     *
     * @param dob A valid date in YYYY-MM-DD format.
     */
    public Dob(String dob) {
        AppUtil.checkArgument(isValidDob(dob), MESSAGE_CONSTRAINTS);
        this.value = dob;
        this.dateValue = LocalDate.parse(dob, DOB_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDob(String test) {
        try {
            LocalDate parsedDate = LocalDate.parse(test, DOB_FORMATTER);
            return !parsedDate.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public LocalDate getDateValue() {
        return dateValue;
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
        return otherDob.dateValue.equals(this.dateValue);
    }

    @Override
    public int hashCode() {
        return dateValue.hashCode();
    }
}
