package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SESSION_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteSessionCommand object
 */
public class DeleteSessionCommandParser implements Parser<DeleteSessionCommand> {

    @Override
    public DeleteSessionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_SESSION_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_SESSION_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, DeleteSessionCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_SESSION_INDEX);

        Index teamIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        Index sessionIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_SESSION_INDEX).get());

        return new DeleteSessionCommand(teamIndex, sessionIndex);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap m, Prefix... prefixes) {
        for (Prefix p : prefixes) {
            if (m.getValue(p).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
