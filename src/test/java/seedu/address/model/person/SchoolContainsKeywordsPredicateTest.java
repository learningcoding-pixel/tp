package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class SchoolContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SchoolContainsKeywordsPredicate firstPredicate = new SchoolContainsKeywordsPredicate(firstPredicateKeywordList);
        SchoolContainsKeywordsPredicate secondPredicate = new SchoolContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SchoolContainsKeywordsPredicate firstPredicateCopy = new SchoolContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_schoolContainsKeywords_returnsTrue() {
        // One keyword
        SchoolContainsKeywordsPredicate predicate = new SchoolContainsKeywordsPredicate(
                Collections.singletonList("Jurong"));
        assertTrue(predicate.test(new PersonBuilder().withSchool("Jurong High").build()));

        // Multiple keywords
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("Jurong", "High"));
        assertTrue(predicate.test(new PersonBuilder().withSchool("Jurong High").build()));

        // Only one matching keyword
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("High", "Other"));
        assertTrue(predicate.test(new PersonBuilder().withSchool("Jurong High").build()));

        // Mixed-case keywords
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("jUrOnG", "hIGh"));
        assertTrue(predicate.test(new PersonBuilder().withSchool("Jurong High").build()));
    }

    @Test
    public void test_schoolDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SchoolContainsKeywordsPredicate predicate = new SchoolContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSchool("Jurong High").build()));

        // Non-matching keyword
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("NUS"));
        assertFalse(predicate.test(new PersonBuilder().withSchool("Jurong High").build()));

        // Keywords match phone, email and address, but does not match school
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withSchool("Some School").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        SchoolContainsKeywordsPredicate predicate = new SchoolContainsKeywordsPredicate(keywords);

        String expected = SchoolContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
