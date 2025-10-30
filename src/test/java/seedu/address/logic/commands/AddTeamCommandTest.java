package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AddTeamCommand.MESSAGE_DUPLICATE_TEAM_NAME;
import static seedu.address.logic.commands.AddTeamCommand.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.commands.AddTeamCommand.MESSAGE_INVALID_TEAM_SIZE;
import static seedu.address.logic.commands.AddTeamCommand.MESSAGE_MEMBER_ALREADY_IN_TEAM;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;
import seedu.address.model.team.session.Session;
import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for {@link AddTeamCommand}.
 *
 * This test class follows the same structure and conventions as AddCommandTest:
 *  - Null checks in constructor
 *  - Successful execution path
 *  - Error paths (duplicate name, invalid index, invalid size, member already in team)
 *  - equals() and toStringMethod() tests
 *  - Model stubs with minimal overrides; all other methods throw AssertionError
 */
public class AddTeamCommandTest {

    @Test
    public void constructor_nullTeamName_throwsNullPointerException() {
        Set<Index> indexes = new HashSet<>();
        indexes.add(Index.fromOneBased(1));
        indexes.add(Index.fromOneBased(2));
        indexes.add(Index.fromOneBased(3));
        indexes.add(Index.fromOneBased(4));
        requireNonNull(indexes);

        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () ->
                new AddTeamCommand(null, indexes));
    }

    @Test
    public void constructor_nullIndexes_throwsNullPointerException() {
        TeamName name = new TeamName("Alpha");
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () ->
                new AddTeamCommand(name, null));
    }

    @Test
    public void execute_validInputs_addSuccessful() throws Exception {
        // Prepare model with 4 persons and no teams
        ModelStubAcceptingTeamAdded model = new ModelStubAcceptingTeamAdded();
        Person p1 = new PersonBuilder().withName("A1").build();
        Person p2 = new PersonBuilder().withName("A2").build();
        Person p3 = new PersonBuilder().withName("A3").build();
        Person p4 = new PersonBuilder().withName("A4").build();
        model.addPersonsToFilteredList(p1, p2, p3, p4);

        TeamName name = new TeamName("Alpha");
        Set<Index> memberIndexes = Set.of(Index.fromOneBased(1), Index.fromOneBased(2),
                Index.fromOneBased(3), Index.fromOneBased(4));

        AddTeamCommand command = new AddTeamCommand(name, memberIndexes);
        Team expectedTeam = new Team(name, Set.of(p1, p2, p3, p4));
        String expectedMessage = String.format(AddTeamCommand.MESSAGE_SUCCESS, Messages.format(expectedTeam));

        CommandResult result = command.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(model.teamsAdded.contains(expectedTeam));

        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
        assertTrue(result.isShowTeams());
    }

    @Test
    public void execute_duplicateTeamName_throwsCommandException() {
        // Existing team with same name
        TeamName name = new TeamName("Alpha");
        ModelStubWithExistingTeamName model = new ModelStubWithExistingTeamName(name);

        Person p1 = new PersonBuilder().withName("A1").build();
        Person p2 = new PersonBuilder().withName("A2").build();
        Person p3 = new PersonBuilder().withName("A3").build();
        Person p4 = new PersonBuilder().withName("A4").build();
        model.addPersonsToFilteredList(p1, p2, p3, p4);

        Set<Index> memberIndexes = Set.of(Index.fromOneBased(1), Index.fromOneBased(2),
                Index.fromOneBased(3), Index.fromOneBased(4));

        AddTeamCommand command = new AddTeamCommand(name, memberIndexes);
        org.junit.jupiter.api.Assertions.assertThrows(CommandException.class, () -> command.execute(model),
                MESSAGE_DUPLICATE_TEAM_NAME);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubAcceptingTeamAdded model = new ModelStubAcceptingTeamAdded();
        Person p1 = new PersonBuilder().withName("A1").build();
        model.addPersonsToFilteredList(p1);

        TeamName name = new TeamName("Alpha");

        Set<Index> memberIndexes = Set.of(Index.fromOneBased(1), Index.fromOneBased(2),
                Index.fromOneBased(3), Index.fromOneBased(4));

        AddTeamCommand command = new AddTeamCommand(name, memberIndexes);
        org.junit.jupiter.api.Assertions.assertThrows(CommandException.class, () -> command.execute(model),
                MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_invalidTeamSize_throwsCommandException() {
        ModelStubAcceptingTeamAdded model = new ModelStubAcceptingTeamAdded();
        Person p1 = new PersonBuilder().withName("A1").build();
        Person p2 = new PersonBuilder().withName("A2").build();
        Person p3 = new PersonBuilder().withName("A3").build();
        model.addPersonsToFilteredList(p1, p2, p3);

        TeamName name = new TeamName("Alpha");
        Set<Index> memberIndexes = Set.of(Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(3));

        AddTeamCommand command = new AddTeamCommand(name, memberIndexes);
        org.junit.jupiter.api.Assertions.assertThrows(CommandException.class, () -> command.execute(model),
                MESSAGE_INVALID_TEAM_SIZE);
    }

    @Test
    public void execute_memberAlreadyInTeam_throwsCommandException() {
        ModelStubWithMemberAlreadyInTeam model = new ModelStubWithMemberAlreadyInTeam();
        Person p1 = new PersonBuilder().withName("ExistingMember").build();
        Person p2 = new PersonBuilder().withName("A2").build();
        Person p3 = new PersonBuilder().withName("A3").build();
        Person p4 = new PersonBuilder().withName("A4").build();
        model.addPersonsToFilteredList(p1, p2, p3, p4);
        model.seedTeamWithMembers(Set.of(p1)); // existing team with p1

        TeamName name = new TeamName("Alpha");
        Set<Index> memberIndexes = Set.of(Index.fromOneBased(1), Index.fromOneBased(2),
                Index.fromOneBased(3), Index.fromOneBased(4));

        AddTeamCommand command = new AddTeamCommand(name, memberIndexes);
        org.junit.jupiter.api.Assertions.assertThrows(CommandException.class, () -> command.execute(model),
                MESSAGE_MEMBER_ALREADY_IN_TEAM);
    }

    @Test
    public void equals() {
        TeamName alpha = new TeamName("Alpha");
        TeamName beta = new TeamName("Beta");
        Set<Index> idx1234 = Set.of(Index.fromOneBased(1), Index.fromOneBased(2),
                Index.fromOneBased(3), Index.fromOneBased(4));
        Set<Index> idx2345 = Set.of(Index.fromOneBased(2), Index.fromOneBased(3),
                Index.fromOneBased(4), Index.fromOneBased(5));

        AddTeamCommand a1 = new AddTeamCommand(alpha, idx1234);
        AddTeamCommand a2 = new AddTeamCommand(alpha, idx1234);
        AddTeamCommand b = new AddTeamCommand(beta, idx1234);
        AddTeamCommand c = new AddTeamCommand(alpha, idx2345);

        // same values -> true
        assertTrue(a1.equals(a2));
        // same object -> true
        assertTrue(a1.equals(a1));
        // null -> false
        assertFalse(a1.equals(null));
        // different type -> false
        assertFalse(a1.equals(1));
        // different name -> false
        assertFalse(a1.equals(b));
        // different index set -> false
        assertFalse(a1.equals(c));
    }

    @Test
    public void toStringMethod() {
        TeamName alpha = new TeamName("Alpha");
        Set<Index> idx1234 = Set.of(Index.fromOneBased(1), Index.fromOneBased(2),
                Index.fromOneBased(3), Index.fromOneBased(4));
        AddTeamCommand command = new AddTeamCommand(alpha, idx1234);
        String expected = new ToStringBuilder(command)
                .add("teamName", alpha)
                .add("memberIndexes", idx1234)
                .toString();
        assertEquals(expected, command.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasIdenticalPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isPersonListEmpty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isTeamListEmpty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTeam(Team team) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTeam(Team target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTeam(Team target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTeam(Team target, Team editedTeam) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Team> getFilteredTeamList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTeamList(Predicate<Team> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Team getTeamOfPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSessionToTeam(Team team, Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSession(Team team, Session session) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always returns an existing team name conflict for hasTeam().
     */
    private static class ModelStubWithExistingTeamName extends ModelStub {
        private final TeamName existingName;
        private final List<Person> persons = new ArrayList<>();

        ModelStubWithExistingTeamName(TeamName existingName) {
            this.existingName = requireNonNull(existingName);
        }

        void addPersonsToFilteredList(Person... people) {
            persons.addAll(List.of(people));
        }

        @Override
        public boolean hasTeam(Team team) {
            return team.getName().equals(existingName);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(persons);
        }

        @Override
        public ObservableList<Team> getFilteredTeamList() {
            return FXCollections.observableArrayList();
        }
    }

    /**
     * A Model stub that accepts teams being added and exposes the list of added teams.
     */
    private static class ModelStubAcceptingTeamAdded extends ModelStub {
        final List<Person> persons = new ArrayList<>();
        final List<Team> teamsAdded = new ArrayList<>();
        final List<Team> existingTeams = new ArrayList<>();

        void addPersonsToFilteredList(Person... people) {
            persons.addAll(List.of(people));
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(persons);
        }

        @Override
        public ObservableList<Team> getFilteredTeamList() {
            return FXCollections.observableArrayList(existingTeams);
        }

        @Override
        public boolean hasTeam(Team team) {
            // no duplicate team names in this stub
            return false;
        }

        @Override
        public void addTeam(Team team) {
            teamsAdded.add(team);
        }
    }

    /**
     * A Model stub that already has a team with certain members.
     */
    private static class ModelStubWithMemberAlreadyInTeam extends ModelStub {
        final List<Person> persons = new ArrayList<>();
        final List<Team> existingTeams = new ArrayList<>();

        void addPersonsToFilteredList(Person... people) {
            persons.addAll(List.of(people));
        }

        void seedTeamWithMembers(Set<Person> members) {
            Set<Person> four = new HashSet<>(members);
            int counter = 1;
            while (four.size() < Team.TEAM_SIZE) {
                four.add(new PersonBuilder().withName("Pad" + counter++).build());
            }
            existingTeams.add(new Team(new TeamName("Existing"), four));
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(persons);
        }

        @Override
        public ObservableList<Team> getFilteredTeamList() {
            return FXCollections.observableArrayList(existingTeams);
        }

        @Override
        public boolean hasTeam(Team team) {
            // No duplicate by name here; conflict will be hit via existingTeams membership check
            return false;
        }

        @Override
        public void addTeam(Team team) {
            // not reached in the conflict test
            throw new AssertionError("This method should not be called.");
        }
    }
}
