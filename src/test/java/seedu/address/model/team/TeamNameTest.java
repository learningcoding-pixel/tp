package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TeamNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TeamName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new TeamName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> TeamName.isValidName(null));

        // invalid name
        assertFalse(TeamName.isValidName("")); // empty string
        assertFalse(TeamName.isValidName(" ")); // spaces only
        assertFalse(TeamName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(TeamName.isValidName("team*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(TeamName.isValidName("team alpha")); // alphabets only
        assertTrue(TeamName.isValidName("12345")); // numbers only
        assertTrue(TeamName.isValidName("team 2nd")); // alphanumeric characters
        assertTrue(TeamName.isValidName("Capital Team")); // with capital letters
        assertTrue(TeamName.isValidName("Relay Team 1")); // mixed alphanumeric and spaces
    }

    @Test
    public void equals() {
        TeamName name = new TeamName("Valid Team");

        // same values -> returns true
        assertTrue(name.equals(new TeamName("Valid Team")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new TeamName("Other Valid Team")));
    }
}
