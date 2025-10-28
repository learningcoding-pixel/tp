package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.HOON;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.team.session.Location;
import seedu.address.model.team.session.Session;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for {@link Team}.
 */
public class TeamTest {

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        Set<Person> members = new HashSet<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL));
        Assert.assertThrows(NullPointerException.class, () -> new Team(null, members));
        Assert.assertThrows(NullPointerException.class, () -> new Team(new TeamName("TeamA"), null));
    }

    @Test
    public void constructor_invalidTeamSize_throwsIllegalArgumentException() {
        // too few members
        Set<Person> threeMembers = new HashSet<>(Arrays.asList(ALICE, BENSON, CARL));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Team(new TeamName("TeamA"), threeMembers));

        // too many members
        Set<Person> fiveMembers = new HashSet<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, HOON));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Team(new TeamName("TeamA"), fiveMembers));
    }

    @Test
    public void isSameTeam() {
        Set<Person> members1 = new HashSet<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL));
        Set<Person> members2 = new HashSet<>(Arrays.asList(HOON, BENSON, CARL, DANIEL));

        Team teamA = new Team(new TeamName("TeamA"), members1);
        Team teamACopy = new Team(new TeamName("TeamA"), members2);

        // same object
        assertTrue(teamA.isSameTeam(teamA));
        // null
        assertFalse(teamA.isSameTeam(null));
        // same name -> true
        assertTrue(teamA.isSameTeam(teamACopy));
        // different name -> false
        Team teamB = new Team(new TeamName("TeamB"), members1);
        assertFalse(teamA.isSameTeam(teamB));
    }

    @Test
    public void equals() {
        Set<Person> members = new HashSet<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL));
        Team team = new Team(new TeamName("TeamX"), members);
        Team teamCopy = new Team(new TeamName("TeamX"), new HashSet<>(members));

        // same values -> true
        assertTrue(team.equals(teamCopy));
        // same object -> true
        assertTrue(team.equals(team));
        // null -> false
        assertFalse(team.equals(null));
        // different type -> false
        assertFalse(team.equals("not a team"));

        // different name -> false
        Team differentName = new Team(new TeamName("OtherName"), members);
        assertFalse(team.equals(differentName));

        // different members -> false
        Set<Person> otherMembers = new HashSet<>(Arrays.asList(HOON, BENSON, CARL, DANIEL));
        Team differentMembers = new Team(new TeamName("TeamX"), otherMembers);
        assertFalse(team.equals(differentMembers));
    }

    @Test
    public void hasMember() {
        Set<Person> members = new HashSet<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL));
        Team team = new Team(new TeamName("TeamHas"), members);

        assertTrue(team.hasMember(ALICE));
        assertFalse(team.hasMember(HOON));
    }

    @Test
    public void updateTeamMember_replacesMemberAndReturnsNewTeam() {
        Set<Person> members = new HashSet<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL));
        Team team = new Team(new TeamName("TeamUpdate"), members);

        Person newAlice = new PersonBuilder(ALICE).withName("Alice Changed").build();
        Team updated = team.updateTeamMember(ALICE, newAlice);

        // original unchanged
        assertTrue(team.hasMember(ALICE));
        assertFalse(team.hasMember(newAlice));

        // updated has new member and not old
        assertFalse(updated.hasMember(ALICE));
        assertTrue(updated.hasMember(newAlice));

        // other members preserved
        assertTrue(updated.hasMember(BENSON));
        assertTrue(updated.hasMember(CARL));
        assertTrue(updated.hasMember(DANIEL));
    }

    @Test
    public void getSessions_returnsUnmodifiableSet() {
        Set<Person> members = new HashSet<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL));
        Session session = new Session(new Location("Track"),
                LocalDateTime.of(2025, 10, 21, 7, 0), LocalDateTime.of(2025, 10, 21, 8, 0));
        Set<Session> sessions = new HashSet<>(Arrays.asList(session));

        Team team = new Team(new TeamName("TeamSess"), members, sessions);

        Set<Session> returned = team.getSessions();
        Assert.assertThrows(UnsupportedOperationException.class, () -> returned.remove(session));
    }
}
