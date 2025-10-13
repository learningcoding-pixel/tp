package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
        Person alicetest = new PersonBuilder().withName("Alice").withSchool("test").build();
        Person bobtest = new PersonBuilder().withName("Bob").withSchool("test").build();
        Person aliceCS = new PersonBuilder().withName("Alice").withSchool("CS").build();
        Person bobMath = new PersonBuilder().withName("Bob").withSchool("Math").build();
        Person charlietest = new PersonBuilder().withName("Charlie").withSchool("test").build();
        Person jamesScience = new PersonBuilder().withName("James").withSchool("Science").build();
        // TODO: to implement once Person is updated with School

        // Predicate logic tests
        assertTrue(command.getPredicate().test(alicetest)); // matches name and school
        assertTrue(command.getPredicate().test(bobtest)); // matches name and school

        assertFalse(command.getPredicate().test(aliceCS)); // matches name
        assertFalse(command.getPredicate().test(bobMath)); // matches name
        assertFalse(command.getPredicate().test(charlietest)); // matches school
        assertFalse(command.getPredicate().test(jamesScience)); // no matches

    }

    // to add tests for parsing multiple args at once i.e. school and name
}
