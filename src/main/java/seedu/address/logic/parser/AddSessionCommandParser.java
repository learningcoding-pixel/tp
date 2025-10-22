package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.AddSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.Location;
import seedu.address.model.team.Session;
import seedu.address.model.team.TeamName;

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

        // Use a simple split-based approach
        Map<String, String> arguments = parseArguments(trimmed);

        String teamNameStr = getArgument(arguments, "/tn", "Team name");
        String sessionName = getArgument(arguments, "/sn", "Session name");
        String locationStr = getArgument(arguments, "/l", "Location");
        String startStr = getArgument(arguments, "/s", "Start date/time");
        String endStr = getArgument(arguments, "/e", "End date/time");

        try {
            TeamName teamName = new TeamName(teamNameStr);
            Location location = new Location(locationStr);
            LocalDateTime start = LocalDateTime.parse(startStr);
            LocalDateTime end = LocalDateTime.parse(endStr);

            Session session = new Session(location, start, end);
            return new AddSessionCommand(teamName, session);
        } catch (DateTimeParseException dtpe) {
            throw new ParseException("Date/time must be ISO format: " + dtpe.getMessage());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ParseException("Failed to parse addsession arguments: " + e.getMessage()
                    + "\nUsage: " + AddSessionCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Simple argument parser that splits by prefixes
     */
    private Map<String, String> parseArguments(String args) throws ParseException {
        Map<String, String> arguments = new HashMap<>();

        // Split the string by the prefixes
        String[] parts = args.split("\\s+(?=/(tn|sn|l|s|e)\\s+)");

        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("/tn ")) {
                arguments.put("/tn", part.substring(4).trim());
            } else if (part.startsWith("/sn ")) {
                arguments.put("/sn", part.substring(4).trim());
            } else if (part.startsWith("/l ")) {
                arguments.put("/l", part.substring(3).trim());
            } else if (part.startsWith("/s ")) {
                arguments.put("/s", part.substring(3).trim());
            } else if (part.startsWith("/e ")) {
                arguments.put("/e", part.substring(3).trim());
            } else if (!part.isEmpty()) {
                throw new ParseException("Unknown argument: " + part);
            }
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
