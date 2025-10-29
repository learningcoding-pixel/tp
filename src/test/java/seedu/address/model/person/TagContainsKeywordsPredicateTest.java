package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple keywords, one matches
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("owesMoney", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple tags on person, keyword matches one of them
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("owesMoney"));
        assertTrue(predicate.test(new PersonBuilder().withTags("owesMoney", "friends").build()));

        // Mixed-case keyword matching tag
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("FrIends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("notatag"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Keywords match other fields but not tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("12345", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
