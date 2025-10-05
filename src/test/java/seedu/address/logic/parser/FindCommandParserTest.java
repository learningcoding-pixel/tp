package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.SchoolContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_namePredicate_returnsFindCommand() {
        // Single predicate: Name only
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        FindCommand expectedFindCommand = new FindCommand(namePredicate);

        // No space immediately after prefix
        assertParseSuccess(parser, "find n/ Alice Bob", expectedFindCommand);
    }

    @Test
    public void parse_schoolPredicate_returnsFindCommand() {
        SchoolContainsKeywordsPredicate schoolPredicate =
                new SchoolContainsKeywordsPredicate(Arrays.asList("test"));
        FindCommand expectedFindCommandName = new FindCommand(schoolPredicate);
        assertParseSuccess(parser, "find s/ test ", expectedFindCommandName);

    }

    @Test
    public void parse_combinedNameAndSchoolPredicates_matchesCorrectPersons() throws Exception {
        // Parse the command
        FindCommand command = parser.parse("find n/Alice Bob s/test");

        // Persons to test
        Person alice = new PersonBuilder().withName("Alice").withSchool("CS").build();
        Person bob = new PersonBuilder().withName("Bob").withSchool("Math").build();
        Person testSchool = new PersonBuilder().withName("Charlie").withSchool("test").build();
        // Person unrelated = new PersonBuilder().withName("X").build();
        // TODO: to implement once Person is updated with School

        // Predicate logic tests
        assertTrue(command.getPredicate().test(alice)); // matches name
        assertTrue(command.getPredicate().test(bob)); // matches name
        assertTrue(command.getPredicate().test(testSchool)); // matches school
        // assertFalse(command.getPredicate().test(unrelated)); // matches neither
    }

    // to add tests for parsing multiple args at once i.e. school and name
}
