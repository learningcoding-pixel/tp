package seedu.address.model.session;

/**
 * Represents a Person's session in the RelayCoach app.
 * Guarantees: immutable}
 */
public class Session {
    private final String session;

    public Session(String session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return this.session;
    }
}
