package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.Team;
import seedu.address.model.team.Session;

/**
 * Deletes a session from a specified team in the RelayCoach app.
 */
public class DeleteSessionCommand extends Command {

    public static final String COMMAND_WORD = "deletesession";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the session identified by the session index (ss/) "
            + "from the team identified by the team index (i/) in the displayed team list.\n"
            + "Parameters: i/TEAM_INDEX ss/SESSION_INDEX (both must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " i/1 ss/2";

    public static final String MESSAGE_DELETE_SESSION_SUCCESS = "Deleted session: %1$s\nFrom team: %2$s";
    public static final String MESSAGE_INVALID_SESSION_DISPLAYED_INDEX = "The session index provided is invalid.";

    private final Index teamIndex;
    private final Index sessionIndex;

    /**
     * Creates a DeleteSessionCommand to delete a session from a specific team.
     */
    public DeleteSessionCommand(Index teamIndex, Index sessionIndex) {
        this.teamIndex = teamIndex;
        this.sessionIndex = sessionIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get all teams
        List<Team> lastShownTeamList = model.getFilteredTeamList();
        if (teamIndex.getZeroBased() >= lastShownTeamList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        Team teamToEdit = lastShownTeamList.get(teamIndex.getZeroBased());

        // Get the team's session list
        List<Session> sessionList;
        try {
            sessionList = model.getFilteredSessionList(teamToEdit);
        } catch (Exception e) {
            sessionList = teamToEdit.getSessionList(); // fallback
        }

        if (sessionIndex.getZeroBased() >= sessionList.size()) {
            throw new CommandException(MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        }

        Session sessionToDelete = sessionList.get(sessionIndex.getZeroBased());

        // Attempt deletion
        try {
            model.deleteSession(teamToEdit, sessionToDelete);
        } catch (Exception e) {
            model.deleteSession(sessionToDelete); // fallback
        }

        return new CommandResult(
                String.format(MESSAGE_DELETE_SESSION_SUCCESS,
                        Messages.format(sessionToDelete),
                        Messages.format(teamToEdit))
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteSessionCommand otherDeleteSessionCommand)) {
            return false;
        }

        return teamIndex.equals(otherDeleteSessionCommand.teamIndex)
                && sessionIndex.equals(otherDeleteSessionCommand.sessionIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("teamIndex", teamIndex)
                .add("sessionIndex", sessionIndex)
                .toString();
    }
}