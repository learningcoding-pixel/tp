package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.session.Location;
import seedu.address.model.team.session.Session;

/**
 * Parses input arguments and creates a new AddSessionCommand.
 */
public class AddSessionCommandParser implements Parser<AddSessionCommand> {

    @Override
    public AddSessionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_LOCATION,
                        PREFIX_START_DATETIME, PREFIX_END_DATETIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_LOCATION, PREFIX_START_DATETIME, PREFIX_END_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSessionCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_LOCATION,
                PREFIX_START_DATETIME, PREFIX_END_DATETIME);

        try {
            Index teamIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
            Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
            LocalDateTime start = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATETIME).get());
            LocalDateTime end = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATETIME).get());

            if (!start.isBefore(end)) {
                throw new ParseException(AddSessionCommand.MESSAGE_INVALID_DATES);
            }

            Session session = new Session(location, start, end);
            return new AddSessionCommand(teamIndex, session);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ParseException("Failed to parse addsession arguments: " + e.getMessage()
                    + "\nUsage: " + AddSessionCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Returns true if all the prefixes contain non-empty values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix ->
                argumentMultimap.getValue(prefix).isPresent());
    }
}
