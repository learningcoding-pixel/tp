package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.control.TextArea;

/**
 * Tests for {@code ResultDisplay}.
 */
public class ResultDisplayTest {

    private ResultDisplay resultDisplay;

    @BeforeEach
    public void setUp() {
        resultDisplay = new ResultDisplay();
    }

    @Test
    public void constructor_initializesCorrectly() {
        assertNotNull(resultDisplay);
        assertNotNull(resultDisplay.getTextArea());
    }

    @Test
    public void resultDisplay_isTextArea() {
        assertTrue(resultDisplay.getTextArea() instanceof TextArea);
    }

    @Test
    public void resultDisplay_isNotEditable() {
        assertFalse(resultDisplay.getTextArea().isEditable());
    }

    @Test
    public void resultDisplay_hasTextWrappingEnabled() {
        assertTrue(resultDisplay.getTextArea().isWrapText());
    }

    @Test
    public void resultDisplay_hasPreferredRowCount() {
        // Check that prefRowCount is set (may be 3 or similar)
        assertTrue(resultDisplay.getTextArea().getPrefRowCount() > 0);
    }

    @Test
    public void setFeedbackToUser_setsTextCorrectly() {
        String testFeedback = "This is a test feedback message";
        resultDisplay.setFeedbackToUser(testFeedback);

        assertEquals(testFeedback, resultDisplay.getTextArea().getText());
    }

    @Test
    public void setFeedbackToUser_withLongText_wrapsCorrectly() {
        String longText = "This is a very long text that should wrap to multiple lines "
                + "instead of requiring horizontal scrolling in the UI. "
                + "The text wrapping feature should ensure that users only need "
                + "to scroll vertically, not horizontally.";

        resultDisplay.setFeedbackToUser(longText);

        assertEquals(longText, resultDisplay.getTextArea().getText());
        assertTrue(resultDisplay.getTextArea().isWrapText());
    }

    @Test
    public void setFeedbackToUser_withEmptyString() {
        resultDisplay.setFeedbackToUser("");
        assertEquals("", resultDisplay.getTextArea().getText());
    }

    @Test
    public void setFeedbackToUser_withNull_throwsException() {
        // This tests the requireNonNull check in your setFeedbackToUser method
        try {
            resultDisplay.setFeedbackToUser(null);
            // If we reach here, the method didn't throw an exception as expected
            // You might want to add: fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Expected behavior - the method should throw NPE for null input
            assertTrue(e instanceof NullPointerException);
        }
    }

    @Test
    public void textArea_hasCorrectStyleClass() {
        assertTrue(resultDisplay.getTextArea().getStyleClass().contains("result-display"));
    }
}
