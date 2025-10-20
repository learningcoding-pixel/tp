package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * Forms a new team of 4 athletes.
 */
public class AddTeamCommand extends Command {

    public static final String COMMAND_WORD = "team";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Forms a team of 4 athletes. "
            + "Parameters: "
            + PREFIX_TEAM_NAME + "TEAM_NAME "
            + PREFIX_INDEX + "ATHLETE_INDEX... (exactly 4 athletes)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TEAM_NAME + "StarTeam "
            + PREFIX_INDEX + "1 " + PREFIX_INDEX + "2 " + PREFIX_INDEX + "3 " + PREFIX_INDEX + "4";

    public static final String MESSAGE_SUCCESS = "New team added: %1$s";
    public static final String MESSAGE_DUPLICATE_TEAM = "This team already exists.";
    public static final String MESSAGE_INVALID_INDEX = "One or more athlete indexes are invalid.";
    public static final String MESSAGE_INVALID_TEAM_SIZE = "A team must have exactly 4 distinct members.";

    private final TeamName teamName;
    private final Set<Index> memberIndexes;

    /**
     * Creates an AddTeamCommand with the given team name and member indexes.
     */
    public AddTeamCommand(TeamName teamName, Set<Index> memberIndexes) {
        requireNonNull(teamName);
        requireNonNull(memberIndexes);
        this.teamName = teamName;
        this.memberIndexes = memberIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        Set<Person> members = new HashSet<>();

        for (Index index : memberIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEX);
            }
            members.add(lastShownList.get(index.getZeroBased()));
        }

        if (members.size() != Team.TEAM_SIZE) {
            throw new CommandException(MESSAGE_INVALID_TEAM_SIZE);
        }

        Team newTeam = new Team(teamName, members);

        if (model.hasTeam(newTeam)) {
            throw new CommandException(MESSAGE_DUPLICATE_TEAM);
        }

        model.addTeam(newTeam);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(newTeam)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddTeamCommand)) {
            return false;
        }
        AddTeamCommand otherCommand = (AddTeamCommand) other;
        return teamName.equals(otherCommand.teamName)
                && memberIndexes.equals(otherCommand.memberIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("teamName", teamName)
                .add("memberIndexes", memberIndexes)
                .toString();
    }
}
