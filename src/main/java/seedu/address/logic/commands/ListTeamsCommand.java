package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEAMS;

import java.util.ArrayList;

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
        var teams = model.getFilteredTeamList();
        StringBuilder sb = new StringBuilder("Listed all teams\n");

        for (int i = 0; i < teams.size(); i++) {
            Team t = teams.get(i);
            sb.append(String.format("%d. Team name: %s\n", i + 1, t.getName()));

            var ordered = new ArrayList<>(t.getSessions());
            ordered.sort(Session.SESSION_ORDER);

            if (ordered.isEmpty()) {
                sb.append("   (no sessions)\n");
            } else {
                sb.append("   Sessions:\n");
                int cnt = 1;
                for (Session s : ordered) {
                    sb.append(String.format("     %d) %s\n", cnt++, s.toString()));
                }
            }
        }
        return new CommandResult(sb.toString(), false, false, true);
    }
}
