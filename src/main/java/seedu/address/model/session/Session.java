package seedu.address.model.session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;


/**
 * Represents a Person's session in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Session {

    protected LocalDateTime startDate;
    protected LocalDateTime endDate;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
    private final String session;
    private final String location;

    /**
     * Represents a Session in the address book.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */
    public Session(String session, String location, LocalDateTime startDate, LocalDateTime EndDate) {
        this.session = session;
        this.location = location;
        this.startDate = startDate;
        this.endDate = EndDate;
    }

    public String getSession() {
        return session;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Returns true if both sessions have the same session name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameSession(Session otherSession) {
        if (otherSession == this) {
            return true;
        }

        return otherSession != null
                && otherSession.getSession().equals(getSession());
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
        return session.equals(otherSession.session)
                && location.equals(otherSession.location)
                && startDate.equals(otherSession.startDate)
                && endDate.equals(otherSession.endDate);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(session, location, startDate, endDate);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Session", session)
                .add("Location",location)
                .add("StartDate", startDate.format(formatter))
                .add("EndDate", endDate.format(formatter))
                .toString();
    }
}
