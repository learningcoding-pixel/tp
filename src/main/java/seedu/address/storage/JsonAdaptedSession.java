package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.team.session.Location;
import seedu.address.model.team.session.Session;

/**
 * Jackson-friendly version of {@link Session}.
 * Stores a rudimentary representation as a string.
 */
class JsonAdaptedSession {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team session data is missing!";

    private final String sessionLocation;
    private final String sessionStartDate;
    private final String sessionEndDate;

    /**
     * Constructs a {@code JsonAdaptedTeamSession} with the given session details.
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("sessionLocation") String sessionLocation,
                              @JsonProperty("sessionStartDate") String sessionStartDate,
                              @JsonProperty("sessionEndDate") String sessionEndDate) {
        this.sessionLocation = sessionLocation;
        this.sessionStartDate = sessionStartDate;
        this.sessionEndDate = sessionEndDate;
    }

    /**
     * Converts a given {@code Session} into this class for Jackson use.
     */
    public JsonAdaptedSession(Session source) {
        this.sessionLocation = source.getLocation().value;
        this.sessionStartDate = source.getStartDate().toString();
        this.sessionEndDate = source.getEndDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted session object into the model's {@code Session} object.
     *
     * @throws IllegalValueException if any session field is null
     */
    public Session toModelType() throws IllegalValueException {
        // location
        if (sessionLocation == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        if (!Location.isValidLocation(sessionLocation)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(sessionLocation);

        //start date
        if (sessionStartDate == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        if (!LocalDateTime.parse(sessionStartDate).isBefore(LocalDateTime.parse(sessionEndDate))) {
            throw new IllegalValueException(Session.MESSAGE_CONSTRAINTS);
        }
        final LocalDateTime modelStartDate = LocalDateTime.parse(sessionStartDate);

        //end date
        if (sessionEndDate == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        if (!LocalDateTime.parse(sessionEndDate).isAfter(LocalDateTime.parse(sessionStartDate))) {
            throw new IllegalValueException(Session.MESSAGE_CONSTRAINTS);
        }
        final LocalDateTime modelEndDate = LocalDateTime.parse(sessionEndDate);

        //check if start date is after end date
        if (modelStartDate.isAfter(modelEndDate)) {
            throw new IllegalValueException(Session.MESSAGE_CONSTRAINTS);
        }

        return new Session(modelLocation, modelStartDate, modelEndDate);
    }
}
