package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEAMS;

import seedu.address.logic.Messages;
import seedu.address.model.Model;

/**
 * Lists all teams in the address book to the user.
 */
public class ListTeamsCommand extends Command {

    public static final String COMMAND_WORD = "listteams";

    public static final String MESSAGE_SUCCESS = "Listed all teams";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.isTeamListEmpty()) {
            return new CommandResult(Messages.MESSAGE_NO_TEAMS_IN_LIST, false, false, true);
        }
        model.updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
