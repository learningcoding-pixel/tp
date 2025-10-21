package seedu.address.model.person;

/**
 * Represents a Person's school in the RelayCoach app.
 * Guarantees: immutable}
 */
public class School {
    public static final String MESSAGE_CONSTRAINTS = "School must be a valid school name";

    /*
     * The first character of the school must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} .'’]*";

    public final String value;

    public School(String school) {
        this.value = school;
    }

    public static boolean isValidSchool(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof School)) {
            return false;
        }

        School otherSchool = (School) other;
        return value.equals(otherSchool.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
