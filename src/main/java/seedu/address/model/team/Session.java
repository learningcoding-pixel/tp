package seedu.address.model.team;

/**
 * Represents a Person's session in the RelayCoach app.
 * Guarantees: immutable
 */
public class Session {
    private final String session;

    public Session(String session) {
        this.session = session;
    }

    public String getSession() {
        return this.session;
    }

    @Override
    public String toString() {
        return this.session;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Session)) {
            return false;
        }
        Session otherSession = (Session) other;
        return otherSession.session.equals(this.session);
    }
}
