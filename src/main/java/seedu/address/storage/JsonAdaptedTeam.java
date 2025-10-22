package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * Jackson-friendly version of {@link Team}.
 */
class JsonAdaptedTeam {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedPerson> members = new ArrayList<>();
    private final List<JsonAdaptedSession> sessions = new ArrayList<>(); // optional sessions class

    /**
     * Constructs a {@code JsonAdaptedTeam} with the given team details.
     */
    @JsonCreator
    public JsonAdaptedTeam(@JsonProperty("name") String name,
                           @JsonProperty("members") List<JsonAdaptedPerson> members,
                           @JsonProperty("sessions") List<JsonAdaptedSession> sessions) {
        this.name = name;
        if (members != null) {
            this.members.addAll(members);
        }
        if (sessions != null) {
            this.sessions.addAll(sessions);
        }
    }

    /**
     * Converts a given {@code Team} into this class for Jackson use.
     */
    public JsonAdaptedTeam(Team source) {
        name = source.getName().fullTeamName;
        // Convert members to JsonAdaptedPerson
        members.addAll(source.getMembers().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));

        // Convert sessions to JsonAdaptedSession
        sessions.addAll(source.getSessions().stream()
                .map(JsonAdaptedSession::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted team object into the model's {@code Team} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Team toModelType() throws IllegalValueException {
        // TeamName
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TeamName.class.getSimpleName()));
        }
        if (!TeamName.isValidName(name)) {
            throw new IllegalValueException(TeamName.MESSAGE_CONSTRAINTS);
        }
        final TeamName modelName = new TeamName(name);

        // Members
        final Set<Person> modelMembers = new HashSet<>();
        for (JsonAdaptedPerson member : members) {
            modelMembers.add(member.toModelType());
        }

        if (modelMembers.size() != Team.TEAM_SIZE) {
            throw new IllegalValueException("A team must have exactly " + Team.TEAM_SIZE + " members.");
        }

        final Set<Team.Session> modelSessions = new HashSet<>();
        for (JsonAdaptedSession session : sessions) {
            modelSessions.add(session.toModelType());
        }

        return new Team(modelName, modelMembers, modelSessions);
    }
}
