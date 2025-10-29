package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.SchoolContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SCHOOL, PREFIX_ROLE, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_SCHOOL, PREFIX_ROLE, PREFIX_TAG);

        // Create predicates for each field if the prefix is present and non-empty
        Optional<Predicate<Person>> namePredicate = argMultimap.getValue(PREFIX_NAME)
                .filter(s -> !s.isBlank())
                .map(value -> new NameContainsKeywordsPredicate(Arrays.asList(value.trim().split("\\s+"))));

        Optional<Predicate<Person>> schoolPredicate = argMultimap.getValue(PREFIX_SCHOOL)
                .filter(s -> !s.isBlank())
                .map(value -> new SchoolContainsKeywordsPredicate(Arrays.asList(value.trim().split("\\s+"))));

        Optional<Predicate<Person>> rolePredicate = argMultimap.getValue(PREFIX_ROLE)
                .filter(s -> !s.isBlank())
                .map(value -> new RoleContainsKeywordsPredicate(Arrays.asList(value.trim().split("\\s+"))));

        Optional<Predicate<Person>> tagPredicate = argMultimap.getValue(PREFIX_TAG)
                .filter(s -> !s.isBlank())
                .map(value -> new TagContainsKeywordsPredicate(Arrays.asList(value.trim().split("\\s+"))));

        List<Predicate<Person>> predicates = new ArrayList<>();
        namePredicate.ifPresent(predicates::add);
        schoolPredicate.ifPresent(predicates::add);
        rolePredicate.ifPresent(predicates::add);
        tagPredicate.ifPresent(predicates::add);

        if (predicates.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        assert !predicates.isEmpty() : "Predicates list should not be empty at this point";

        Predicate<Person> combinedPredicate = predicates.get(0);

        for (int i = 1; i < predicates.size(); i++) {
            combinedPredicate = combinedPredicate.and(predicates.get(i));
        }

        return new FindCommand(combinedPredicate);
    }

}
