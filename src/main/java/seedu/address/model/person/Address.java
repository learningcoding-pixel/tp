package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final int ADDRESS_MAXIMUM_LENGTH = 255;
    public static final String MESSAGE_CONSTRAINTS =
            "Addresses may contain letters, numbers, spaces, commas (,), periods (.), hyphens (-), "
            + "apostrophes ('), slashes (/), ampersands (&), hash (#), semicolons (;), and parentheses ( ). "
            + "It must not be blank and must be at most " + ADDRESS_MAXIMUM_LENGTH + " characters.";

    /*
     * The first character of the address must not be a whitespace,
     * and the total length must be between 1 and 255 characters.
     */
    public static final String VALIDATION_REGEX =
            "^(?=\\S)(?=.*\\S)[\\p{L}\\p{M}0-9 .,'\\-&/#();]{1," + ADDRESS_MAXIMUM_LENGTH + "}$";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
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

        // instanceof handles nulls
        if (!(other instanceof Address)) {
            return false;
        }

        Address otherAddress = (Address) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
