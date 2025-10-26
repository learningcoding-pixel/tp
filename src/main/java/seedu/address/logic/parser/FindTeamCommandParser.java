package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.TeamNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindTeamCommandParser implements Parser<FindTeamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTeamCommand
     * and returns a FindTeamCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTeamCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTeamCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTeamCommand(new TeamNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
