package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel; // initializes JavaFX toolkit in headless mode
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Non-GUI tests for {@link PersonCard}.
 *
 * We avoid GuiUnitTest and scene graph lookups. Instead, we construct the UiPart
 * directly (after booting JavaFX with JFXPanel) and assert its exposed labels.
 */
public class PersonCardTest {

    @BeforeAll
    public static void initJavaFx() {
        new JFXPanel();
    }

    @Test
    public void construct_displaysIndexAndName() {
        Person person = new PersonBuilder().build();
        int displayedIndex = 1;

        PersonCard card = new PersonCard(person, displayedIndex);

        assertEquals(displayedIndex + ". ", card.getId().getText());
        assertEquals(person.getName().fullName, card.getName().getText());

        assertNotNull(card.getDob());
        assertNotNull(card.getPhone());
        assertNotNull(card.getEmail());
        assertNotNull(card.getAddress());
        assertNotNull(card.getSchool());
        assertNotNull(card.getRole());
        assertNotNull(card.getHeight());
        assertNotNull(card.getWeight());
        assertNotNull(card.getTags());
    }

    @Test
    public void construct_withDifferentIndex_updatesIdLabel() {
        Person person = new PersonBuilder().build();

        PersonCard first = new PersonCard(person, 1);
        PersonCard second = new PersonCard(person, 2);

        assertEquals("1. ", first.getId().getText());
        assertEquals("2. ", second.getId().getText());
    }
}
