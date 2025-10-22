package seedu.address.model.team;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.session.Location;

/**
 * Represents a Team in the RelayCoach app.
 * Guarantees: members size is always 4, details are present and not null, field values are validated, immutable.
 */
public class Team {

    public static final int TEAM_SIZE = 4;

    private final TeamName name;
    private final Set<Person> members = new HashSet<>();
    private final Set<Session> sessions = new HashSet<>();

    /**
     * Constructs a {@code Team}.
     *
     * @param name Name of the team
     * @param members Set of team members (must have 4 distinct members)
     */
    public Team(TeamName name, Set<Person> members) {
        requireNonNull(name);
        requireNonNull(members);

        if (members.size() != TEAM_SIZE) {
            throw new IllegalArgumentException("A team must have exactly 4 members.");
        }

        this.name = name;
        this.members.addAll(members);
    }

    /**
     * Constructs a {@code Team}.
     *
     * @param name Name of the team
     * @param members Set of team members (must have 4 distinct members)
     * @param sessions Set of sessions associated with the team
     */
    public Team(TeamName name, Set<Person> members, Set<Session> sessions) {
        requireNonNull(name);
        requireNonNull(members);
        requireNonNull(sessions);

        if (members.size() != TEAM_SIZE) {
            throw new IllegalArgumentException("A team must have exactly 4 members.");
        }

        this.name = name;
        this.members.addAll(members);
        this.sessions.addAll(sessions);
    }

    public TeamName getName() {
        return name;
    }



    public boolean hasMember(Person person) {
        return members.contains(person);
    }

    /**
     * Returns a new {@code Team} with the given member replaced by the edited person.
     */
    public Team updateTeamMember(Person oldMember, Person newMember) {
        Set<Person> updatedMembers = new HashSet<>(members);
        updatedMembers.remove(oldMember);
        updatedMembers.add(newMember);
        return new Team(this.name, updatedMembers);
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
        return String.format("Team %s: %s", name, members, sessions); // TODO: Configure toString format
    }

    public Set<Person> getMembers() {
        return members;
    }

    /**
     * Represents a Team's session in the RelayCoach app.
     * Guarantees: immutable
     */
    public static final class Session {

        private final String name;
        private final Location location;
        private final LocalDateTime startDate;
        private final LocalDateTime endDate;
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

        /**
         * Constructs a {@code Session}.
         *
         * @param name non-null session name
         * @param location non-null location
         * @param startDate non-null start date/time
         * @param endDate non-null end date/time; must be the same or after startDate
         */
        public Session(String name, Location location, LocalDateTime startDate, LocalDateTime endDate) {
            requireNonNull(name);
            requireNonNull(location);
            requireNonNull(startDate);
            requireNonNull(endDate);

            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("Start date/time must be before or equal to end date/time.");
            }

            this.name = name;
            this.location = location;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getName() {
            return name;
        }

        public Location getLocation() {
            return location;
        }

        public LocalDateTime getStartDate() {
            return startDate;
        }

        public LocalDateTime getEndDate() {
            return endDate;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Session)) {
                return false;
            }
            Session o = (Session) other;
            return Objects.equals(name, o.name)
                    && Objects.equals(location, o.location)
                    && Objects.equals(startDate, o.startDate)
                    && Objects.equals(endDate, o.endDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, location, startDate, endDate);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("Name", name)
                    .add("Location", location)
                    .add("StartDate", startDate.format(FORMATTER))
                    .add("EndDate", endDate.format(FORMATTER))
                    .toString();
        }
    }
}
