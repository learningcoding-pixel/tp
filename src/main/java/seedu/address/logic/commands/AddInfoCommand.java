package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddInfoCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds additional information to the person. "
            + "Parameters: "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_HEIGHT + "HEIGHT "
            + PREFIX_WEIGHT + "WEIGHT "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ROLE + "Captain "
            + PREFIX_HEIGHT + "175 "
            + PREFIX_WEIGHT + "65 "
            + PREFIX_TAG + "kneeInjury " //keep tags as String with no spaces for now
            + PREFIX_TAG + "hasFlu";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_PERSON_DOES_NOT_EXIST = "This person does not exist.";

    private final Person toAddInfo;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddInfoCommand(Person person) {
        requireNonNull(person);
        toAddInfo = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPerson(toAddInfo)) {
            throw new CommandException(MESSAGE_PERSON_DOES_NOT_EXIST);
        }

        model.addPerson(toAddInfo);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAddInfo)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddInfoCommand)) {
            return false;
        }

        AddInfoCommand otherAddInfoCommand = (AddInfoCommand) other;
        return toAddInfo.equals(otherAddInfoCommand.toAddInfo);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAddInfo", toAddInfo)
                .toString();
    }
}
