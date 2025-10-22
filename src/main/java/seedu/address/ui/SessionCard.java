package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.team.session.Session;

public class SessionCard extends UiPart<HBox> {
    private static final String FXML = "SessionCard.fxml";

    @FXML private Label index;
    @FXML private Label location;
    @FXML private Label timeRange;

    public SessionCard(Session session, int displayedIndex) {
        super(FXML);
        index.setText(displayedIndex + ".");
        location.setText("Location: " + session.getLocation().toString());
        timeRange.setText(session.getStartDate() + " → " + session.getEndDate());
    }
}