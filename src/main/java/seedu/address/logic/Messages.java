package seedu.address.logic;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The athlete index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d athlete(s) listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    public static final String MESSAGE_TEAMS_LISTED_OVERVIEW = "%1$d team(s) listed!";
    public static final String MESSAGE_NO_ATHLETES_IN_LIST = "No athletes added yet!";
    public static final String MESSAGE_NO_MATCHING_ATHLETES = "The keywords does not seem to match any athletes.";
    public static final String MESSAGE_NO_TEAMS_IN_LIST = "No teams added yet!";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(person.getName())
                .append("; DOB: ")
                .append(person.getDob())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; School: ")
                .append(person.getSchool())
                .append("; Role: ")
                .append(person.getRole())
                .append("; Height: ")
                .append(person.getHeight())
                .append("; Weight: ")
                .append(person.getWeight())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code team} for display to the user.
     */
    public static String format(Team team) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n " + "Team name: ")
                .append(team.getName())
                .append("\n " + "Athletes: ");
        AtomicInteger counter = new AtomicInteger(1);

        String members = team.getMembers().stream()
                .map(person -> counter.getAndIncrement() + ". " + Messages.format(person))
                .collect(Collectors.joining("\n "));
        builder.append("\n ").append(members);
        return builder.toString();
    }

}
