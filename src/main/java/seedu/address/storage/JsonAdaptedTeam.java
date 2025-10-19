package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private final List<Integer> memberIndexes = new ArrayList<>(); // store indices (1-based)

    /**
     * Constructs a {@code JsonAdaptedTeam} with the given team details.
     */
    @JsonCreator
    public JsonAdaptedTeam(@JsonProperty("name") String name,
                           @JsonProperty("memberIndexes") List<Integer> memberIndexes) {
        this.name = name;
        if (memberIndexes != null) {
            this.memberIndexes.addAll(memberIndexes);
        }
    }

    /**
     * Converts a given {@code Team} into this class for Jackson use.
     * Member indices should be calculated relative to the AddressBook's person list elsewhere.
     */
    public JsonAdaptedTeam(Team source, List<Person> personReferenceList) {
        name = source.getName().fullTeamName;
        // Convert members to indices (1-based)
        for (Person member : source.getMembers()) {
            int index = personReferenceList.indexOf(member);
            if (index == -1) {
                throw new IllegalArgumentException("Member not found in reference person list");
            }
            memberIndexes.add(index + 1); // store as 1-based index
        }
    }

    /**
     * Converts this Jackson-friendly adapted team object into the model's {@code Team} object.
     *
     * @param personsReferenceList list of persons to resolve member indices
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Team toModelType(List<Person> personsReferenceList) throws IllegalValueException {
        // TeamName
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TeamName.class.getSimpleName()));
        }
        if (!TeamName.isValidName(name)) {
            throw new IllegalValueException(TeamName.MESSAGE_CONSTRAINTS);
        }
        final TeamName modelName = new TeamName(name);

        // Members
        final Set<Person> modelMembers = new HashSet<>();
        for (Integer idx : memberIndexes) {
            if (idx < 1 || idx > personsReferenceList.size()) {
                throw new IllegalValueException("Invalid member index for team: " + idx);
            }
            modelMembers.add(personsReferenceList.get(idx - 1));
        }

        if (modelMembers.size() != Team.TEAM_SIZE) {
            throw new IllegalValueException("A team must have exactly " + Team.TEAM_SIZE + " members.");
        }

        return new Team(modelName, modelMembers);
    }
}
