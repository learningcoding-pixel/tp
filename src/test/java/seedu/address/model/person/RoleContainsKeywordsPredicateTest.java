package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RoleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoleContainsKeywordsPredicate firstPredicateCopy = new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_roleContainsKeywords_returnsTrue() {
        // One keyword
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Collections.singletonList("member"));
        assertTrue(predicate.test(new PersonBuilder().withRole("member").build()));

        // Multiple keywords
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("member", "anchor"));
        assertTrue(predicate.test(new PersonBuilder().withRole("anchor").build()));

        // Only one matching keyword
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("anchor", "coach"));
        assertTrue(predicate.test(new PersonBuilder().withRole("anchor").build()));

        // Mixed-case keywords
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("MeMbEr"));
        assertTrue(predicate.test(new PersonBuilder().withRole("member").build()));
    }

    @Test
    public void test_roleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRole("member").build()));

        // Non-matching keyword
        predicate = new RoleContainsKeywordsPredicate(Collections.singletonList("coach"));
        assertFalse(predicate.test(new PersonBuilder().withRole("member").build()));

        // Keywords match other fields but not role
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("12345", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(keywords);

        String expected = RoleContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
