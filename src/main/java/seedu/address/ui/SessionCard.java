package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.team.session.Session;

/**
 * An UI component that displays information of a {@code Session}.
 */
public class SessionCard extends UiPart<HBox> {
    private static final String FXML = "SessionCard.fxml";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    @FXML private Label index;
    @FXML private Label locationLabel;
    @FXML private Label timeRange;

    /**
     * Creates a {@code SessionCard} with the given {@code Session} and index to display.
     */
    public SessionCard(Session session, int displayedIndex) {
        super(FXML);
        index.setText(displayedIndex + ".");

        String formattedStart = session.getStartDate().format(DATE_FORMATTER);
        String formattedEnd = session.getEndDate().format(DATE_FORMATTER);

        locationLabel.setText("Location: " + session.getLocation().toString());
        timeRange.setText("Time: " + formattedStart + " → " + formattedEnd);
    }

    public Label getLocation() {
        return locationLabel;
    }

    public Label getSessionTimeRange() {
        return timeRange;
    }
}
