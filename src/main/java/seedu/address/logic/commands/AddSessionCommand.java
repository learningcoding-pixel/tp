package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * Adds a {@code Session} to a {@code Team}.
 */
public class AddSessionCommand extends Command {

    public static final String COMMAND_WORD = "addsession";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a session to the team identified by its name.\n"
            + "Format: addsession /tn TEAM_NAME /sn SESSION_NAME /l LOCATION /s START_ISO /e END_ISO\n"
            + "Example: addsession /tn TeamA /sn MorningRun /l Track /s 2025-10-21T07:00 /e 2025-10-21T08:00";

    public static final String MESSAGE_SUCCESS = "Added session to %1$s: %2$s";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team not found.";

    private final TeamName targetName;
    private final Team.Session session;

    public AddSessionCommand(TeamName targetName, Team.Session session) {
        this.targetName = requireNonNull(targetName);
        this.session = requireNonNull(session);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Team> maybeTeam = model.getFilteredTeamList().stream()
                .filter(t -> t.getName().equals(targetName))
                .findFirst();

        if (maybeTeam.isEmpty()) {
            throw new CommandException(MESSAGE_TEAM_NOT_FOUND);
        }

        Team targetTeam = maybeTeam.get();

        // Model should implement addSessionToTeam(Team, Session)
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
        AddSessionCommand o = (AddSessionCommand) other;
        return targetName.equals(o.targetName) && session.equals(o.session);
    }
}




