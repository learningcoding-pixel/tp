package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.TeamName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddTeamCommandParser implements Parser<AddTeamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TEAM_NAME, PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_TEAM_NAME, PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTeamCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAM_NAME, PREFIX_INDEX);
        TeamName teamName = ParserUtil.parseTeamName(argMultimap.getValue(PREFIX_TEAM_NAME).get());
        Set<Index> indexes = ParserUtil.parseIndexes(argMultimap.getAllValues(PREFIX_INDEX));

        return new AddTeamCommand(teamName, indexes);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
