package seedu.address.model.team.session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;




/**
 * Represents a Team's session in the RelayCoach app.
 * Guarantees: immutable
 */
public class Session {

    public static final java.util.Comparator<Session> SESSION_ORDER = Comparator
            .comparing(Session::getStartDate)
            .thenComparing(Session::getEndDate)
            .thenComparing(s -> s.getLocation().toString());

    public static final String MESSAGE_CONSTRAINTS = "Session must have a valid location and start and end date";

    protected LocalDateTime startDate;
    protected LocalDateTime endDate;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
    private final Location location;

    /**
     * Represents a Session in the Team.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */
    public Session(Location location, LocalDateTime startDate, LocalDateTime endDate) {
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
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

    /**
     * Returns true if both sessions have the same identity and data fields.
     * This defines a stronger notion of equality between two sessions.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Session)) {
            return false;
        }

        Session otherSession = (Session) other;
        return location.equals(otherSession.location)
                && startDate.equals(otherSession.startDate)
                && endDate.equals(otherSession.endDate);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(location, startDate, endDate);
    }


    @Override
    public String toString() {
        return String.format("startDate: %s, endDate: %s, location: %s",
                startDate.format(formatter),
                endDate.format(formatter),
                location);
    }
}
