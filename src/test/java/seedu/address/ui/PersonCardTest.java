package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Tests for {@code PersonCard}.
 */
public class PersonCardTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PersonCard(null, 0));
    }

    @Test
    public void constructor_negativeIndex_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new PersonCard(ALICE, -1));
    }

    @Test
    public void constructor_validPerson_success() {
        PersonCard personCard = new PersonCard(ALICE, 1);
        assertNotNull(personCard);
    }

    @Test
    public void display_correctIdAndName() {
        PersonCard personCard = new PersonCard(ALICE, 1);

        // Verify ID is correctly set
        assertEquals("1. ", personCard.getId().getText());

        // Verify name is correctly set
        assertEquals(ALICE.getName().fullName, personCard.getName().getText());
    }

    @Test
    public void display_formatsDobCorrectly() {
        // Create a person with specific DOB
        Person personWithDob = new PersonBuilder()
                .withName("John Doe")
                .withPhone("98765432")
                .withEmail("john@example.com")
                .withAddress("123 Street")
                .withDob("2003-10-10")
                .withSchool("Test School")
                .withRole("Member")
                .withHeight("180")
                .withWeight("75")
                .build();

        PersonCard personCard = new PersonCard(personWithDob, 1);

        // Test that DOB is formatted from "2003-10-10" to "10 October 2003"
        String expectedFormattedDob = formatDobForTest("2003-10-10");
        assertEquals(expectedFormattedDob, personCard.getDob().getText());
    }

    @Test
    public void display_invalidDob_fallsBackToOriginal() {
        // Create a person with invalid DOB format
        Person personWithInvalidDob = new PersonBuilder(ALICE)
                .withDob("invalid-date-format")
                .build();

        PersonCard personCard = new PersonCard(personWithInvalidDob, 1);

        // Should fall back to original invalid date string
        assertEquals("invalid-date-format", personCard.getDob().getText());
    }

    @Test
    public void display_emptyDob_displaysEmpty() {
        // Create a person with empty DOB
        Person personWithEmptyDob = new PersonBuilder(ALICE)
                .withDob("")
                .build();

        PersonCard personCard = new PersonCard(personWithEmptyDob, 1);
        assertEquals("", personCard.getDob().getText());
    }

    @Test
    public void display_allFieldsPopulated() {
        PersonCard personCard = new PersonCard(ALICE, 1);

        // Verify all fields are populated with correct data
        assertEquals(ALICE.getPhone().value, personCard.getPhone().getText());
        assertEquals(ALICE.getEmail().value, personCard.getEmail().getText());
        assertEquals(ALICE.getAddress().value, personCard.getAddress().getText());
        assertEquals(ALICE.getSchool().value, personCard.getSchool().getText());
        assertEquals(ALICE.getRole().value, personCard.getRole().getText());
        assertEquals(ALICE.getHeight().value, personCard.getHeight().getText());
        assertEquals(ALICE.getWeight().value, personCard.getWeight().getText());
        assertEquals(ALICE.getDob().value, "2003-10-10"); // Verify DOB data is present
    }

    @Test
    public void display_tagsAreShown() {
        PersonCard personCard = new PersonCard(BENSON, 1);

        // Verify tags flowpane is populated (BENSON has tags)
        assertFalse(personCard.getTags().getChildren().isEmpty());
    }

    @Test
    public void display_noTags_emptyTagsFlowPane() {
        // Create a person without tags
        Person personWithoutTags = new PersonBuilder(ALICE)
                .withTags() // Clear all tags
                .build();

        PersonCard personCard = new PersonCard(personWithoutTags, 1);

        // Tags flowpane should be empty
        assertTrue(personCard.getTags().getChildren().isEmpty());
    }

    @Test
    public void display_differentIndex_showsCorrectId() {
        PersonCard personCard1 = new PersonCard(ALICE, 1);
        PersonCard personCard2 = new PersonCard(ALICE, 5);
        PersonCard personCard3 = new PersonCard(ALICE, 10);

        assertEquals("1. ", personCard1.getId().getText());
        assertEquals("5. ", personCard2.getId().getText());
        assertEquals("10. ", personCard3.getId().getText());
    }

    @Test
    public void addressAndEmail_haveTextWrapping() {
        PersonCard personCard = new PersonCard(ALICE, 1);

        // Verify address and email have text wrapping enabled
        assertTrue(personCard.getAddress().isWrapText());
        assertTrue(personCard.getEmail().isWrapText());
    }

    @Test
    public void name_isBold() {
        PersonCard personCard = new PersonCard(ALICE, 1);

        // Verify name has bold styling (check if style contains bold)
        String nameStyle = personCard.getName().getStyle();
        assertTrue(nameStyle.contains("-fx-font-weight: bold")
                || nameStyle.contains("bold"));
    }

    @Test
    public void variousDobFormats_formatCorrectly() {
        // Test different date formats
        testDobFormatting("1990-01-15", "15 January 1990");
        testDobFormatting("1985-12-31", "31 December 1985");
        testDobFormatting("2000-06-01", "1 June 2000");
        testDobFormatting("2003-10-10", "10 October 2003"); // ALICE's default DOB
    }

    private void testDobFormatting(String inputDob, String expectedOutput) {
        Person person = new PersonBuilder(ALICE)
                .withDob(inputDob)
                .build();

        PersonCard personCard = new PersonCard(person, 1);
        assertEquals(expectedOutput, personCard.getDob().getText());
    }

    @Test
    public void defaultPerson_hasFormattedDob() {
        // Test that the default person (ALICE) has formatted DOB
        PersonCard personCard = new PersonCard(ALICE, 1);
        String expectedFormattedDob = formatDobForTest("2003-10-10");
        assertEquals(expectedFormattedDob, personCard.getDob().getText());
    }

    /**
     * Helper method to format DOB the same way PersonCard does for testing
     */
    private String formatDobForTest(String rawDate) {
        try {
            LocalDate date = LocalDate.parse(rawDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
            return date.format(formatter);
        } catch (Exception e) {
            return rawDate;
        }
    }
}
