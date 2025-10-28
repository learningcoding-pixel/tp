package seedu.address.model.team.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class SessionTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

    @Test
    public void getters_returnCorrectValues() {
        LocalDateTime start = LocalDateTime.of(2025, 10, 21, 7, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 21, 8, 0);
        Location loc = new Location("Track");

        Session s = new Session(loc, start, end);

        assertEquals(loc, s.getLocation());
        assertEquals(start, s.getStartDate());
        assertEquals(end, s.getEndDate());
    }

    @Test
    public void equals_sameAndDifferentValues_behaviour() {
        LocalDateTime start = LocalDateTime.of(2025, 10, 21, 7, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 21, 8, 0);
        Location loc = new Location("Track");

        Session s1 = new Session(loc, start, end);
        Session s2 = new Session(loc, start, end);

        // same values
        assertTrue(s1.equals(s2));
        assertTrue(s1.equals(s1));
        assertFalse(s1.equals(null));
        assertFalse(s1.equals("not a session"));

        // different location
        Session diffLoc = new Session(new Location("Field"), start, end);
        assertFalse(s1.equals(diffLoc));

        // different start
        Session diffStart = new Session(loc, LocalDateTime.of(2025, 10, 21, 6, 0), end);
        assertFalse(s1.equals(diffStart));

        // different end
        Session diffEnd = new Session(loc, start, LocalDateTime.of(2025, 10, 21, 9, 0));
        assertFalse(s1.equals(diffEnd));
    }

    @Test
    public void sessionOrder_comparatorOrdersByStartThenEndThenLocation() {
        // sEarly starts earlier than sLater
        Session sEarly = new Session(new Location("A"),
                LocalDateTime.of(2025, 10, 20, 7, 0), LocalDateTime.of(2025, 10, 20, 8, 0));
        Session sLater = new Session(new Location("A"),
                LocalDateTime.of(2025, 10, 21, 7, 0), LocalDateTime.of(2025, 10, 21, 8, 0));

        assertTrue(Session.SESSION_ORDER.compare(sEarly, sLater) < 0);

        // same start, earlier end comes first
        Session sSameStartEarlyEnd = new Session(new Location("A"),
                LocalDateTime.of(2025, 10, 21, 7, 0), LocalDateTime.of(2025, 10, 21, 7, 30));
        Session sSameStartLaterEnd = new Session(new Location("A"),
                LocalDateTime.of(2025, 10, 21, 7, 0), LocalDateTime.of(2025, 10, 21, 8, 0));

        assertTrue(Session.SESSION_ORDER.compare(sSameStartEarlyEnd, sSameStartLaterEnd) < 0);

        // same start and end, location alphabetical
        Session locA = new Session(new Location("A"),
                LocalDateTime.of(2025, 10, 21, 7, 0), LocalDateTime.of(2025, 10, 21, 8, 0));
        Session locB = new Session(new Location("B"),
                LocalDateTime.of(2025, 10, 21, 7, 0), LocalDateTime.of(2025, 10, 21, 8, 0));

        assertTrue(Session.SESSION_ORDER.compare(locA, locB) < 0);
    }

    @Test
    public void toString_containsLocationAndFormattedDates() {
        LocalDateTime start = LocalDateTime.of(2025, 10, 21, 7, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 21, 8, 0);
        Location loc = new Location("Track");

        Session s = new Session(loc, start, end);
        String str = s.toString();

        // toString uses formatter "dd MMMM yyyy HH:mm" for dates
        String startStr = start.format(formatter);
        String endStr = end.format(formatter);

        assertTrue(str.contains(loc.toString()));
        assertTrue(str.contains(startStr));
        assertTrue(str.contains(endStr));
    }
}
