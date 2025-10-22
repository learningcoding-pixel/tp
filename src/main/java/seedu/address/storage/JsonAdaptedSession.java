package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.session.Location;
import seedu.address.model.team.Team;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Jackson-friendly version of {@link Team.Session}.
 * Stores a rudimentary representation as a string.
 */
class JsonAdaptedSession {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team session data is missing!";

    private final String name;
    private final String location;
    private final String start;
    private final String end;

    /**
     * Constructs a {@code JsonAdaptedTeamSession} with the given session string.
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("name") String name,
                              @JsonProperty("location") String location,
                              @JsonProperty("start") String start,
                              @JsonProperty("end") String end) {
        this.name = name;
        this.location = location;
        this.start = start;
        this.end = end;
    }

    /**
     * Converts a given {@code TeamSession} into this class for Jackson use.
     */
    public JsonAdaptedSession(Team.Session source) {
        this.name = source.getName();
        this.location = source.getLocation().toString();
        this.start = source.getStartDate().toString(); // ISO format
        this.end = source.getEndDate().toString();     // ISO format
    }

    /**
     * Converts this Jackson-friendly adapted session object into the model's {@code TeamSession} object.
     *
     * @throws IllegalValueException if session string is null
     */
    public Team.Session toModelType() throws IllegalValueException {
        if (name == null || name.isBlank()) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT + " (name)");
        }
        if (location == null || location.isBlank()) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT + " (location)");
        }
        if (start == null || start.isBlank()) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT + " (start)");
        }
        if (end == null || end.isBlank()) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT + " (end)");
        }

        try {
            LocalDateTime startDate = LocalDateTime.parse(start);
            LocalDateTime endDate = LocalDateTime.parse(end);
            return new Team.Session(name, new Location(location), startDate, endDate);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException("Session date/time must be ISO format: " + dtpe.getMessage());
        } catch (IllegalArgumentException iae) {
            throw new IllegalValueException("Invalid session data: " + iae.getMessage());
        }
    }
}
