package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Headless sanity tests for {@link MainWindow}.
 *
 * These tests verify that MainWindow can be constructed with a stub Logic,
 * that its inner parts can be filled, and that list toggling methods run without exceptions.
 */
public class MainWindowTest {

    @BeforeAll
    public static void initJavaFx() {
        // Boot JavaFX toolkit for headless tests
        new JFXPanel();
    }

    @Test
    public void constructAndFillInnerParts_doesNotThrow() throws Exception {
        runOnFxThread(() -> {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(new javafx.scene.layout.StackPane()));
            stage.show();

            Logic logic = new LogicStub();
            MainWindow window = new MainWindow(stage, logic);

            assertDoesNotThrow(window::fillInnerParts);

            // exercise help/show/hide paths to ensure no exceptions
            assertDoesNotThrow(window::showTeamList);
            assertDoesNotThrow(window::showPersonList);

            stage.hide();
        });
    }

    @Test
    public void toggleTeamAndPersonList_doesNotThrow() throws Exception {
        runOnFxThread(() -> {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(new javafx.scene.layout.StackPane()));
            stage.show();

            Logic logic = new LogicStub();
            MainWindow window = new MainWindow(stage, logic);
            window.fillInnerParts();

            assertDoesNotThrow(window::showTeamList);
            assertDoesNotThrow(window::showPersonList);

            stage.hide();
        });
    }

    // ---------------------- Helpers ----------------------

    private static void runOnFxThread(Runnable r) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try { r.run(); } finally { latch.countDown(); }
        });
        if (!latch.await(10, TimeUnit.SECONDS)) {
            throw new AssertionError("FX task timed out");
        }
    }

    private static class LogicStub implements Logic {
        @Override public CommandResult execute(String commandText) throws CommandException, ParseException {
            return new CommandResult("stub", false, false, false);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }

        @Override public ObservableList<Team> getFilteredTeamList() {
            return FXCollections.observableArrayList();
        }

        @Override public Path getAddressBookFilePath() {
            return Path.of("/dev/null");
        }

        @Override public GuiSettings getGuiSettings() {
            return new GuiSettings(800, 600, 0, 0);
        }
        @Override public void setGuiSettings(GuiSettings guiSettings) {}
    }
}
