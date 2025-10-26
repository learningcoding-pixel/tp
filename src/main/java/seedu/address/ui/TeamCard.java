package seedu.address.ui;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.team.Team;
import seedu.address.model.team.session.Session;

/**
 * An UI component that displays information of a {@code Team}.
 */
public class TeamCard extends UiPart<Region> {
    private static final String FXML = "TeamListCard.fxml";

    public final Team team;

    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private VBox memberCards;
    @FXML
    private VBox sessionCards;

    /**
     * Creates a {@code TeamCard} with the given {@code Team} and index to display.
     */
    public TeamCard(Team team, int displayedIndex) {
        super(FXML);
        this.team = team;

        id.setText(displayedIndex + ". ");
        name.setText(team.getName().toString());

        team.getMembers().stream()
                .sorted(Comparator.comparing(p -> p.getName().fullName))
                .forEach(person -> {
                    PersonCard smallCard = new PersonCard(person, -1);
                    smallCard.getId().setVisible(false);
                    smallCard.getDob().setVisible(true);
                    smallCard.getPhone().setVisible(false);
                    smallCard.getEmail().setVisible(false);
                    smallCard.getAddress().setVisible(false);
                    smallCard.getSchool().setVisible(true);
                    smallCard.getRole().setVisible(true);
                    smallCard.getHeight().setVisible(false);
                    smallCard.getWeight().setVisible(false);
                    smallCard.getTags().setVisible(true);

                    memberCards.getChildren().add(smallCard.getRoot());
                });

        // Check if there are any sessions
        if (team.getSessions().isEmpty()) {
            // Display "No sessions yet" message
            Label noSessionsLabel = new Label("No sessions yet");
            noSessionsLabel.setStyle("-fx-text-fill: #888; -fx-font-style: italic; -fx-padding: 5;");
            sessionCards.getChildren().add(noSessionsLabel);
        } else {
            AtomicInteger index = new AtomicInteger(1);
            team.getSessions().stream()
                    .sorted(Comparator.comparing(
                            Session::getStartDate,
                            Comparator.nullsLast(Comparator.naturalOrder())
                    ))
                    .forEach(session -> {
                        SessionCard smallCard = new SessionCard(session, index.getAndIncrement());
                        smallCard.getLocation().setVisible(true);
                        smallCard.getSessionTimeRange().setVisible(true);
                        sessionCards.getChildren().add(smallCard.getRoot());
                    });
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TeamCard
                && id.getText().equals(((TeamCard) other).id.getText())
                && team.equals(((TeamCard) other).team));
    }
}
