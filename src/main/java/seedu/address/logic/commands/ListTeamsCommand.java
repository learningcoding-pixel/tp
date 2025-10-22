package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEAMS;

import java.util.List;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.team.Team;
import seedu.address.model.team.session.Session;

/**
 * Lists all teams in the address book to the user.
 */
public class ListTeamsCommand extends Command {

    public static final String COMMAND_WORD = "listteams";

    public static final String MESSAGE_SUCCESS = "Listed all teams";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
        List<Team> teams = model.getFilteredTeamList();
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_SUCCESS).append("\n");
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            sb.append(i + 1).append(". Team name: ").append(team.getName()).append("\n");
            Set<Session> sessions = team.getSessions();
            if (sessions == null || sessions.isEmpty()) {
                sb.append("   (no sessions)\n");
            } else {
                sb.append("   Sessions:\n");
                int count = 1;
                for (Session s : sessions) {
                    sb.append("     ").append(count++).append(") ").append(s.toString()).append("\n");
                }
            }
        }
        String builtMessage = sb.toString().trim();
        return new CommandResult(builtMessage, false, false , true);
    }
}
