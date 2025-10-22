package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.team.session.Location;
import seedu.address.model.team.session.Session;

import java.time.LocalDateTime;

/**
 * Jackson-friendly version of {@link Session}.
 * Stores a rudimentary representation as a string.
 */
class JsonAdaptedSession {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team session data is missing!";

    private final Location sessionLocation;
    private final LocalDateTime sessionStartDate;
    private final LocalDateTime sessionEndDate;

    /**
     * Constructs a {@code JsonAdaptedTeamSession} with the given session details.
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("sessionLocation") Location sessionLocation,
                              @JsonProperty("sessionStartDate") LocalDateTime sessionStartDate,
                              @JsonProperty("sessionEndDate") LocalDateTime sessionEndDate) {
        this.sessionLocation = sessionLocation;
        this.sessionStartDate = sessionStartDate;
        this.sessionEndDate = sessionEndDate;
    }

    /**
     * Converts a given {@code Session} into this class for Jackson use.
     */
    public JsonAdaptedSession(Session source) {
        this.sessionLocation = source.getLocation();
        this.sessionStartDate = source.getStartDate();
        this.sessionEndDate = source.getEndDate();
    }

    /**
     * Converts this Jackson-friendly adapted session object into the model's {@code Session} object.
     *
     * @throws IllegalValueException if any session field is null
     */
    public Session toModelType() throws IllegalValueException {
        if (sessionLocation == null || sessionStartDate == null || sessionEndDate == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        return new Session(sessionLocation, sessionStartDate, sessionEndDate);
    }
}
