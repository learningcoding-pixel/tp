package seedu.address.model.person;

/**
 * Represents a Person's school in the RelayCoach app.
 * Guarantees: immutable}
 */
public class School {
    public static final String MESSAGE_CONSTRAINTS = "test";
    private final String school;

    public School(String school) {
        this.school = school;
    }

    public static boolean isValidSchool(String test) {
        return true;
    }

    @Override
    public String toString() {
        return this.school;
    }
}
