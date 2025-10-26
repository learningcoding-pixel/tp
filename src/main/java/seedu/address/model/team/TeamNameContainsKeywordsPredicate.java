package seedu.address.model.team;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Team}'s {@code name} matches any of the keywords given.
 */
public class TeamNameContainsKeywordsPredicate implements Predicate<Team> {
    private final List<String> keywords;

    public TeamNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Team team) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(team.getName().fullTeamName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TeamNameContainsKeywordsPredicate)) {
            return false;
        }

        TeamNameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (TeamNameContainsKeywordsPredicate) other;
        return keywords.equals(TeamNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
