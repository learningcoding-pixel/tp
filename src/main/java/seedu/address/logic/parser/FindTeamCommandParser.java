package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamNameContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindTeamCommandParser implements Parser<FindTeamCommand>{

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public FindTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TEAM_NAME);

        // Checks only if prefix_name exists, else this is empty
        Optional<Predicate<Team>> namePredicate = argMultimap.getValue(PREFIX_TEAM_NAME)
                .filter(s -> !s.isBlank())
                .map(value -> new TeamNameContainsKeywordsPredicate(Arrays.asList(value.trim().split("\\s+"))));


        List<Predicate<Team>> predicates = new ArrayList<>();
        namePredicate.ifPresent(predicates::add);


        if (predicates.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTeamCommand.MESSAGE_USAGE));
        }

        Predicate<Team> combinedPredicate = predicates.get(0);

        return new FindTeamCommand((TeamNameContainsKeywordsPredicate) combinedPredicate);
    }


}
