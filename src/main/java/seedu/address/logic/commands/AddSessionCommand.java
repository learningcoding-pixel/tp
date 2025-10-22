package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.Team;
import seedu.address.model.team.session.Session;

/**
 * Adds a {@code Session} to a {@code Team}.
 */
public class AddSessionCommand extends Command {

    public static final String COMMAND_WORD = "addsession";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a session to the team identified by its index in the team list.\n"
            + "Format: addsession i/TEAM_INDEX sdt/START_DATETIME edt/END_DATETIME l/LOCATION\n"
            + "Example: addsession i/1 sdt/2025-10-21 0700 edt/2025-10-21 0800 l/Track";

    public static final String MESSAGE_SUCCESS = "Added session to %1$s: %2$s";
    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX = "The team index provided is invalid.";

    private final Index teamIndex;
    private final Session session;

    /**
     * Creates an AddSessionCommand to add the specified {@code Session}
     * to the {@code Team} at {@code teamIndex}.
     */
    public AddSessionCommand(Index teamIndex, Session session) {
        this.teamIndex = requireNonNull(teamIndex);
        this.session = requireNonNull(session);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Team> lastShownList = model.getFilteredTeamList();

        if (teamIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        Team targetTeam = lastShownList.get(teamIndex.getZeroBased());

        model.addSessionToTeam(targetTeam, session);

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetTeam.getName(), session));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddSessionCommand)) {
            return false;
        }
        AddSessionCommand otherCmd = (AddSessionCommand) other;
        return teamIndex.equals(otherCmd.teamIndex) && session.equals(otherCmd.session);
    }
}
