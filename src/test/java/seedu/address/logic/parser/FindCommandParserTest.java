package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.SchoolContainsKeywordsPredicate;

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

    // to add tests for parsing multiple args at once i.e. school and name
}
