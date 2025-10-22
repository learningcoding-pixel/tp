package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

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
        requireNonNull(args);
        String trimmed = args.trim();

        if (trimmed.isEmpty()) {
            throw new ParseException("Empty command\nUsage: " + AddSessionCommand.MESSAGE_USAGE);
        }

        // Use a token-based parser for new prefixes
        Map<String, String> arguments = parseArguments(trimmed);

        String indexStr = getArgument(arguments, "i/", "Team Index");
        String locationStr = getArgument(arguments, "l/", "Location");
        String startStr = getArgument(arguments, "sdt/", "Start date/time");
        String endStr = getArgument(arguments, "edt/", "End date/time");

        try {
            Index teamIndex = Index.fromOneBased(Integer.parseInt(indexStr));
            Location location = new Location(locationStr);
            LocalDateTime start = LocalDateTime.parse(startStr);
            LocalDateTime end = LocalDateTime.parse(endStr);

            Session session = new Session(location, start, end);
            return new AddSessionCommand(teamIndex, session);
        } catch (DateTimeParseException dtpe) {
            throw new ParseException("Date/time must be ISO format: " + dtpe.getMessage());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ParseException("Failed to parse addsession arguments: " + e.getMessage()
                    + "\nUsage: " + AddSessionCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Token-based argument parser that supports prefixes: i/, l/, sdt/, edt/
     */
    private Map<String, String> parseArguments(String args) throws ParseException {
        Map<String, String> arguments = new HashMap<>();
        String[] tokens = args.split("\\s+");

        String currentPrefix = null;
        StringBuilder currentValue = new StringBuilder();

        for (String token : tokens) {
            if (token.startsWith("i/") || token.startsWith("l/") || token.startsWith("sdt/") || token.startsWith("edt/")) {
                // save previous prefix and value if any
                if (currentPrefix != null) {
                    arguments.put(currentPrefix, currentValue.toString().trim());
                }
                // start new prefix
                int prefixLength = token.indexOf('/') + 1;
                currentPrefix = token.substring(0, prefixLength);
                currentValue = new StringBuilder(token.substring(prefixLength));
            } else {
                if (currentPrefix == null) {
                    throw new ParseException("Unknown argument or missing prefix: " + token);
                }
                currentValue.append(" ").append(token);
            }
        }
        if (currentPrefix != null) {
            arguments.put(currentPrefix, currentValue.toString().trim());
        }

        return arguments;
    }

    private String getArgument(Map<String, String> arguments, String prefix, String description) throws ParseException {
        String value = arguments.get(prefix);
        if (value == null || value.isEmpty()) {
            throw new ParseException("Missing " + description + " (" + prefix + ")\nUsage: " + AddSessionCommand.MESSAGE_USAGE);
        }
        return value;
    }
}
