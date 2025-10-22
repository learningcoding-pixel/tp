package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddSessionCommand.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.Team;
import seedu.address.model.team.session.Session;

/**
 * Deletes a session identified using it's displayed index from a team.
 */
public class DeleteSessionCommand extends Command {

    public static final String COMMAND_WORD = "deletesession";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a session from a team.\n"
            + "Parameters: i/TEAM_INDEX si/SESSION_INDEX\n"
            + "Example: " + COMMAND_WORD + " i/1 si/2";

    public static final String MESSAGE_DELETE_SESSION_SUCCESS =
            "Deleted session: %1$s\nFrom team: %2$s";

    public static final String MESSAGE_INVALID_SESSION_INDEX =
            "The session index provided is invalid.";

    private final Index teamIndex;
    private final Index sessionIndex;

    /**
     * Creates a DeleteSessionCommand to delete the {@code Session}
     * at {@code sessionIndex} from the {@code Team} at {@code teamIndex}.
     */
    public DeleteSessionCommand(Index teamIndex, Index sessionIndex) {
        this.teamIndex = teamIndex;
        this.sessionIndex = sessionIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Team> teams = model.getFilteredTeamList();
        if (teamIndex.getZeroBased() >= teams.size()) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        Team team = teams.get(teamIndex.getZeroBased());

        ArrayList<Session> ordered = new ArrayList<>(team.getSessions());
        ordered.sort(Session.SESSION_ORDER);

        if (ordered.isEmpty() || sessionIndex.getZeroBased() >= ordered.size()) {
            throw new CommandException(MESSAGE_INVALID_SESSION_INDEX);
        }

        Session toDelete = ordered.get(sessionIndex.getZeroBased());
        model.deleteSession(team, toDelete);

        return new CommandResult(String.format(
                MESSAGE_DELETE_SESSION_SUCCESS,
                toDelete.toString(),
                team.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteSessionCommand o
                && teamIndex.equals(o.teamIndex)
                && sessionIndex.equals(o.sessionIndex));
    }
}
