package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final int NAME_MAXIMUM_LENGTH = 80;
    public static final String MESSAGE_CONSTRAINTS =
            "Names can only contain letters, spaces, hyphens (-), apostrophes ('), periods (.), slashes (/), "
            + "commas (,), and parentheses ( ).\n" + "Must be 1–" + NAME_MAXIMUM_LENGTH + " characters long.\n"
            + "Refer to the User Guide for full details on slash (/) usage.";

    /*
     * Name can only contain letters, spaces, hyphens (-), apostrophes ('), periods (.), slashes (/), commas (,),
     * and parentheses (). It must not be blank and must be at most 80 characters long.
     */
    public static final String VALIDATION_REGEX = "^(?=.*\\S)[\\p{L}\\p{M} .',()/-]{1," + NAME_MAXIMUM_LENGTH + "}$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equalsIgnoreCase(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.toLowerCase().hashCode();
    }

}
