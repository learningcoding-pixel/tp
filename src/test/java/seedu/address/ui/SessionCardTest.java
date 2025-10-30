package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel; // initializes JavaFX toolkit in headless mode
import seedu.address.model.team.session.Location;
import seedu.address.model.team.session.Session;

/**
 * Non-GUI tests for {@link SessionCard}.
 */
public class SessionCardTest {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    @BeforeAll
    public static void initJavaFx() {
        new JFXPanel();
    }

    @Test
    public void construct_displaysLocationAndFormattedTime() {
        Session s = makeSession("2025-10-21T07:00", "2025-10-21T08:00", "Track");
        SessionCard card = new SessionCard(s, 1);

        String expectedLocation = SessionCard.SessionField.LOCATION.getLabel() + "Track";
        String expectedTime = SessionCard.SessionField.TIME.getLabel()
                + s.getStartDate().format(FMT) + " → " + s.getEndDate().format(FMT);

        assertEquals(expectedLocation, card.getLocation().getText());
        assertEquals(expectedTime, card.getSessionTimeRange().getText());
    }

    @Test
    public void construct_differentTimes_updatesFormatting() {
        Session s = makeSession("2026-01-05T18:30", "2026-01-05T20:00", "Gym");
        SessionCard card = new SessionCard(s, 2);

        String expectedLocation = SessionCard.SessionField.LOCATION.getLabel() + "Gym";
        String expectedTime = SessionCard.SessionField.TIME.getLabel()
                + s.getStartDate().format(FMT) + " → " + s.getEndDate().format(FMT);

        assertEquals(expectedLocation, card.getLocation().getText());
        assertEquals(expectedTime, card.getSessionTimeRange().getText());

        assertNotNull(card.getLocation());
        assertNotNull(card.getSessionTimeRange());
    }

    private static Session makeSession(String startIso, String endIso, String loc) {
        LocalDateTime start = LocalDateTime.parse(startIso);
        LocalDateTime end = LocalDateTime.parse(endIso);
        return new Session(new Location(loc), start, end);
    }
}
