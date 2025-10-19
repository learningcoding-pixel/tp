package seedu.address.model.team;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.session.Session;

/**
 * Represents a Team in the RelayCoach app.
 * Guarantees: members size is always 4, details are present and not null, field values are validated, immutable.
 */
public class Team {

    public static final int TEAM_SIZE = 4;

    private final String name; // TODO: Create a TeamName class
    private final Set<Person> members = new HashSet<>();
    private final Set<Session> sessions = new HashSet<>();

    /**
     * Constructs a {@code Team}.
     *
     * @param name Name of the team
     * @param members Set of team members (must have 4 distinct members)
     */
    public Team(String name, Set<Person> members) {
        requireNonNull(name);
        requireNonNull(members);

        if (members.size() != TEAM_SIZE) {
            throw new IllegalArgumentException("A team must have exactly 4 members.");
        }

        this.name = name;
        this.members.addAll(members);
    }

    public String getName() {
        return name;
    }

    public Set<Person> getMembers() {
        return Set.copyOf(members);
    }

    public Set<Session> getSessions() {
        return Set.copyOf(sessions);
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Team)) {
            return false;
        }
        Team otherTeam = (Team) other;
        return name.equals(otherTeam.name)
                && members.equals(otherTeam.members)
                && sessions.equals(otherTeam.sessions);
    }

    /**
     * Returns true if both teams have the same name.
     * This defines a weaker notion of equality between two teams.
     */
    public boolean isSameTeam(Team otherTeam) {
        if (otherTeam == this) {
            return true;
        }
        return otherTeam != null
                && otherTeam.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, members, sessions);
    }

    @Override
    public String toString() {
        return String.format("Team %s: %s", name, members); // TODO: Configure toString format
    }
}

