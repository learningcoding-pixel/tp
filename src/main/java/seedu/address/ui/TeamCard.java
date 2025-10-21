package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.team.Team;

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
                    smallCard.getPhone().setVisible(true);
                    smallCard.getEmail().setVisible(true);
                    smallCard.getAddress().setVisible(true);
                    smallCard.getSchool().setVisible(true);
                    smallCard.getRole().setVisible(true);
                    smallCard.getHeight().setVisible(true);
                    smallCard.getWeight().setVisible(true);
                    smallCard.getTags().setVisible(true);

                    memberCards.getChildren().add(smallCard.getRoot());
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TeamCard
                && id.getText().equals(((TeamCard) other).id.getText())
                && team.equals(((TeamCard) other).team));
    }
}
