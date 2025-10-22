package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import seedu.address.model.team.session.Session;

public class SessionListPanel extends UiPart<VBox> {
    private static final String FXML = "SessionListPanel.fxml";

    @FXML private ListView<Session> sessionListView;

    public SessionListPanel(ObservableList<Session> sessions) {
        super(FXML);
        sessionListView.setItems(sessions);
        sessionListView.setCellFactory(listView -> new ListCell<>() {
            @Override protected void updateItem(Session s, boolean empty) {
                super.updateItem(s, empty);
                setGraphic((empty || s == null) ? null : new SessionCard(s, getIndex() + 1).getRoot());
                setText(null);
            }
        });
    }
}