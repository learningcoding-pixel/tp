package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.Team;

/**
 * Adds a person to the address book.
 */
public class AddTeamCommand extends Command {

    public static final String COMMAND_WORD = "team";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Forms a team of 4 athletes. "
            + "Parameters: "
            + PREFIX_NAME + "TEAM_NAME "
            + "[" + PREFIX_INDEX + "ATHLETE_INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "StarTeam "
            + PREFIX_INDEX + "1 2 3 4";

    public static final String MESSAGE_SUCCESS = "New team added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This team already exists in the address book";

    private final Team toAdd;

    /**
     * Creates an AddTeamCommand to add the specified {@code Team}
     */
    public AddTeamCommand(Team team) {
        requireNonNull(team);
        toAdd = team;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTeam(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addTeam(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTeamCommand)) {
            return false;
        }

        AddTeamCommand otherAddTeamCommand = (AddTeamCommand) other;
        return toAdd.equals(otherAddTeamCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
