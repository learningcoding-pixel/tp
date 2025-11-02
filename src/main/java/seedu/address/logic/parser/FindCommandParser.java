package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.parseName;
import static seedu.address.logic.parser.ParserUtil.parseRole;
import static seedu.address.logic.parser.ParserUtil.parseSchool;
import static seedu.address.logic.parser.ParserUtil.parseTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.School;
import seedu.address.model.person.SchoolContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

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
        Optional<Predicate<Person>> namePredicate = Optional.empty();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String value = argMultimap.getValue(PREFIX_NAME).get();
            if (!value.isBlank()) {
                List<String> keywords = Arrays.asList(value.trim().split("\\s+"));
                for (String keyword : keywords) {
                    Name name = parseName(keyword); // validate name
                }
                namePredicate = Optional.of(new NameContainsKeywordsPredicate(keywords));
            }
        }
        Optional<Predicate<Person>> schoolPredicate = Optional.empty();
        if (argMultimap.getValue(PREFIX_SCHOOL).isPresent()) {
            String value = argMultimap.getValue(PREFIX_SCHOOL).get();
            if (!value.isBlank()) {
                List<String> keywords = Arrays.asList(value.trim().split("\\s+"));
                for (String keyword : keywords) {
                    School school = parseSchool(keyword); // validate school
                }
                schoolPredicate = Optional.of(new SchoolContainsKeywordsPredicate(keywords));
            }
        }
        Optional<Predicate<Person>> rolePredicate = Optional.empty();
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            String value = argMultimap.getValue(PREFIX_ROLE).get();
            if (!value.isBlank()) {
                List<String> keywords = Arrays.asList(value.trim().split("\\s+"));
                for (String keyword : keywords) {
                    Role role = parseRole(keyword); // validate role
                }
                rolePredicate = Optional.of(new RoleContainsKeywordsPredicate(keywords));
            }
        }
        Optional<Predicate<Person>> tagPredicate = Optional.empty();
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String value = argMultimap.getValue(PREFIX_TAG).get();
            if (!value.isBlank()) {
                List<String> keywords = Arrays.asList(value.trim().split("\\s+"));
                for (String keyword : keywords) {
                    Tag tag = parseTag(keyword); // validate tag
                }
                tagPredicate = Optional.of(new TagContainsKeywordsPredicate(keywords));
            }
        }

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
