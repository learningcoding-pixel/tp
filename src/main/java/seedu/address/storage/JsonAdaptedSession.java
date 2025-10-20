package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.team.Session;

/**
 * Jackson-friendly version of {@link Session}.
 * Stores a rudimentary representation as a string.
 */
class JsonAdaptedSession {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team session data is missing!";

    private final String sessionString;

    /**
     * Constructs a {@code JsonAdaptedTeamSession} with the given session string.
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("sessionString") String sessionString) {
        this.sessionString = sessionString;
    }

    /**
     * Converts a given {@code TeamSession} into this class for Jackson use.
     */
    public JsonAdaptedSession(Session source) {
        this.sessionString = source.getSession(); // TODO: adjust after implementation of Session
    }

    /**
     * Converts this Jackson-friendly adapted session object into the model's {@code TeamSession} object.
     *
     * @throws IllegalValueException if session string is null
     */
    public Session toModelType() throws IllegalValueException {
        if (sessionString == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        return new Session(sessionString);
    }
}
