package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel; // initializes JavaFX toolkit in headless mode
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;
import seedu.address.testutil.PersonBuilder;

/**
 * Non-GUI tests for {@link TeamCard}.
 *
 * We only verify the equality contract of TeamCard which is based on displayed index and team identity.
 */
public class TeamCardTest {

    @BeforeAll
    public static void initJavaFx() {
        new JFXPanel();
    }

    private static final int DISPLAYED_INDEX = 1;

    @Test
    public void equals() {
        Team team = makeTeam("Alpha");
        TeamCard card = new TeamCard(team, DISPLAYED_INDEX);
        TeamCard copy = new TeamCard(team, DISPLAYED_INDEX);

        // same values -> returns true
        assertTrue(card.equals(copy));

        // same object -> returns true
        assertTrue(card.equals(card));

        // null -> returns false
        assertFalse(card.equals(null));

        // different type -> returns false
        assertFalse(card.equals(1));

        // different displayed index -> returns false
        TeamCard differentIndex = new TeamCard(team, DISPLAYED_INDEX + 1);
        assertFalse(card.equals(differentIndex));

        // different team -> returns false
        Team otherTeam = makeTeam("Beta");
        TeamCard differentTeam = new TeamCard(otherTeam, DISPLAYED_INDEX);
        assertFalse(card.equals(differentTeam));
    }

    private static Team makeTeam(String name) {
        TeamName tn = new TeamName(name);
        Set<Person> members = new HashSet<>();
        members.add(new PersonBuilder().withName("A1").build());
        members.add(new PersonBuilder().withName("A2").build());
        members.add(new PersonBuilder().withName("A3").build());
        members.add(new PersonBuilder().withName("A4").build());
        return new Team(tn, members);
    }
}
