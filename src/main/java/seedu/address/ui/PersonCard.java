package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    public final Person person;

    @FXML private HBox cardPane;
    @FXML private Label name;
    @FXML private Label dob;
    @FXML private Label id;
    @FXML private Label phone;
    @FXML private Label address;
    @FXML private Label email;
    @FXML private FlowPane tags;
    @FXML private Label school;
    @FXML private Label role;
    @FXML private Label height;
    @FXML private Label weight;

    @FXML private HBox dobBox;
    @FXML private HBox phoneBox;
    @FXML private HBox addressBox;
    @FXML private HBox emailBox;
    @FXML private HBox schoolBox;
    @FXML private HBox roleBox;
    @FXML private HBox heightBox;
    @FXML private HBox weightBox;

    /**
     * A UI component that displays information of a {@code Person}.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        // bind the container HBoxes so hiding fields removes their layout space
        bindManagedToVisible(dobBox, phoneBox, addressBox, emailBox, schoolBox, roleBox, heightBox, weightBox, tags);

        // each container's visibility follows the inner label's visibility
        if (dobBox != null && dob != null) {
            dobBox.visibleProperty().bind(dob.visibleProperty());
        }
        if (phoneBox != null && phone != null) {
            phoneBox.visibleProperty().bind(phone.visibleProperty());
        }
        if (addressBox != null && address != null) {
            addressBox.visibleProperty().bind(address.visibleProperty());
        }
        if (emailBox != null && email != null) {
            emailBox.visibleProperty().bind(email.visibleProperty());
        }
        if (schoolBox != null && school != null) {
            schoolBox.visibleProperty().bind(school.visibleProperty());
        }
        if (roleBox != null && role != null) {
            roleBox.visibleProperty().bind(role.visibleProperty());
        }
        if (heightBox != null && height != null) {
            heightBox.visibleProperty().bind(height.visibleProperty());
        }
        if (weightBox != null && weight != null) {
            weightBox.visibleProperty().bind(weight.visibleProperty());
        }

        if (id != null) {
            id.managedProperty().bind(id.visibleProperty());
        }

        String rawDob = person.getDob().value;
        String formattedDob = formatDateOfBirth(rawDob);
        dob.setText(PersonField.DOB.getLabel() + formattedDob);
        phone.setText(PersonField.PHONE.getLabel() + person.getPhone().value);
        email.setText(PersonField.EMAIL.getLabel() + person.getEmail().value);
        address.setText(PersonField.ADDRESS.getLabel() + person.getAddress().value);
        school.setText(PersonField.SCHOOL.getLabel() + person.getSchool().value);
        role.setText(PersonField.ROLE.getLabel() + person.getRole().value);
        height.setText(PersonField.HEIGHT.getLabel() + person.getHeight().value);
        weight.setText(PersonField.WEIGHT.getLabel() + person.getWeight().value);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void bindManagedToVisible(Node... nodes) {
        for (Node n : nodes) {
            if (n != null) {
                n.managedProperty().bind(n.visibleProperty());
            }
        }
    }

    // Getters for testing
    public Label getId() {
        return id;
    }
    public Label getName() {
        return name;
    }
    public Label getDob() {
        return dob;
    }
    public Label getPhone() {
        return phone;
    }
    public Label getEmail() {
        return email;
    }
    public Label getAddress() {
        return address;
    }
    public Label getSchool() {
        return school;
    }
    public Label getRole() {
        return role;
    }
    public Label getHeight() {
        return height;
    }
    public Label getWeight() {
        return weight;
    }
    public FlowPane getTags() {
        return tags;
    }

    /**
     * Centralized fields with emoji code points.
     */
    public enum PersonField {
        DOB(0x1F4C5, "Date of Birth"),
        PHONE(0x1F4DE, "Phone"),
        ADDRESS(0x1F3E0, "Address"),
        EMAIL(0x1F4E7, "Email"),
        SCHOOL(0x1F4D6, "School"),
        ROLE(0x1F3AF, "Role"),
        HEIGHT(0x1F4CF, "Height (in cm)"),
        WEIGHT(0x2696, "Weight (in kg)");

        private final int codePoint;
        private final String text;

        PersonField(int codePoint, String text) {
            this.codePoint = codePoint;
            this.text = text;
        }

        public String emoji() {
            return new String(Character.toChars(codePoint));
        }

        public String getLabel() {
            return emoji() + " " + text + ": ";
        }
    }

    /**
     * Formats date from "2003-10-10" to "10 October 2003"
     */
    private String formatDateOfBirth(String rawDate) {
        try {
            LocalDate date = LocalDate.parse(rawDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
            return date.format(formatter);
        } catch (Exception e) {
            return rawDate;
        }
    }
}
