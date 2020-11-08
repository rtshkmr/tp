package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.ClientViewCommand.MESSAGE_VIEW_CLIENT_SUCCESS;
import static seedu.address.ui.HelpWindow.USERGUIDE_URL;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxToolkit;

import guitests.guihandles.HelpWindowHandle;
import guitests.guihandles.MainWindowHandle;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.ClientViewCommand;
import seedu.address.logic.commands.CountryNoteAddCommand;
import seedu.address.model.ModelManager;
import seedu.address.storage.JsonTbmManagerStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * This test class does not conduct a unit test.
 */
public class MainWindowTest extends GuiUnitTest {
    @TempDir
    public Path temporaryFolder;
    private MainWindow mainWindow;
    private MainWindowHandle mainWindowHandle;
    private Stage stage;
    private Logic logic;

    @BeforeEach
    public void setup() throws Exception {
        JsonTbmManagerStorage jsonTbmManagerStorage =
                new JsonTbmManagerStorage(temporaryFolder.resolve("tbmManager.json"));
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storageManager = new StorageManager(jsonTbmManagerStorage, jsonUserPrefsStorage);
        logic = new LogicManager(new ModelManager(), storageManager);
        logic.setGuiSettings(new GuiSettings(100000000.0, 20000000.0, -200, -300));
        FxToolkit.setupStage(stage -> {
            this.stage = stage;
            mainWindow = new MainWindow(stage, logic, new MainApp());
            mainWindow.fillInnerParts();
            mainWindowHandle = new MainWindowHandle(stage);
            mainWindowHandle.focus();
        });
        FxToolkit.showStage();
    }

    @Test
    public void setWindowDefaultSize_windowTruncatedProperly() {
        int screenHeight = (int) Screen.getPrimary().getVisualBounds().getHeight();
        int screenWidth = (int) Screen.getPrimary().getVisualBounds().getWidth();
        int minY = 0;
        int minX = 0;
        assertEquals(screenHeight, (int) mainWindow.getPrimaryStage().getHeight());
        assertEquals(screenWidth, (int) mainWindow.getPrimaryStage().getWidth());
        assertEquals(minX, (int) mainWindow.getPrimaryStage().getX());
        assertEquals(minY, (int) mainWindow.getPrimaryStage().getY());
    }

    @Test
    public void getPrimaryStage_returnsCorrectStage() {
        assertEquals(stage, mainWindow.getPrimaryStage());
    }

    @Test
    public void main() throws Exception {
        guiRobot.pauseForHuman();
        assertTrue(mainWindowHandle.isShowing());
        guiRobot.pauseForHuman();
        guiRobot.clickOn("#commandTextField");
        InteractionTerminal terminal = new InteractionTerminal(guiRobot.lookup("#commandTextField")
                .queryTextInputControl());
        terminal.inputCommand("clear");

        // help window tests
        guiRobot.clickOn("#help");
        guiRobot.sleep(200);
        guiRobot.clickOn("#helpMenuItem");
        guiRobot.pauseForHuman();
        assertTrue(guiRobot.isWindowShown(HelpWindowHandle.HELP_WINDOW_TITLE));
        guiRobot.clickOn("#copyButton");
        if (!guiRobot.isHeadlessMode()) {
            // checks the interaction of copy url and url is correct
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            assertEquals(USERGUIDE_URL, clipboard.getData(DataFlavor.stringFlavor));
            guiRobot.push(KeyCode.ESCAPE);
            assertFalse(guiRobot.isWindowShown(HelpWindowHandle.HELP_WINDOW_TITLE));

            // checks if F1 opens the help window
            guiRobot.push(KeyCode.F1);
            assertTrue(guiRobot.isWindowShown(HelpWindowHandle.HELP_WINDOW_TITLE));
            guiRobot.pauseForHuman();
            guiRobot.push(KeyCode.ESCAPE);
            guiRobot.pauseForHuman();
            assertFalse(guiRobot.isWindowShown(HelpWindowHandle.HELP_WINDOW_TITLE));

            // checks if help command opens the help window
            terminal.inputCommand("help");
            assertTrue(guiRobot.isWindowShown(HelpWindowHandle.HELP_WINDOW_TITLE));
        }
        guiRobot.pauseForHuman();
        guiRobot.push(KeyCode.ESCAPE);
        assertFalse(guiRobot.isWindowShown(HelpWindowHandle.HELP_WINDOW_TITLE));

        // command execution by robot
        terminal.inputCommand("client add n/Lim p/18002345 e/lim@gmail.com a/Yishun c/SG tz/UTC+08:00");
        terminal.inputCommand("client add n/Kim p/18002346 e/kim@gmail.com a/Kishun c/SG tz/UTC+08:00");
        terminal.inputCommand("client add n/Sim p/18002347 e/sim@gmail.com a/Sishun c/SG tz/UTC+08:00");
        terminal.inputCommand("client edit 1 n/Jim");
        terminal.inputCommand("client note add 1 t/reminder nt/birthday tmr");
        terminal.inputCommand("client note add 1 t/reminder nt/party tmr");
        terminal.inputCommand("client note add 1 t/reminder nt/takeout tmr");
        terminal.inputCommand("client note add 2 t/reminder nt/homework submission");
        terminal.inputCommand("country note add c/SG nt/shamaladingdong");
        terminal.inputCommand("country note add c/SG nt/chingchangchongdingdong");

        // skips the rest if headless: resultDisplay and view box does not update in headless.
        assumeFalse(guiRobot.isHeadlessMode());

        // unknown command
        terminal.inputCommand("client viewf");
        String unknownCommandMessage = getResultMessage();
        assertEquals(MESSAGE_UNKNOWN_COMMAND, unknownCommandMessage);

        // some invalid command usage
        terminal.inputCommand("client view");
        String resultMessage = getResultMessage();
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClientViewCommand.MESSAGE_USAGE);
        assertEquals(expectedMessage, resultMessage);

        // some valid command usage
        terminal.inputCommand("country note add");
        String resultMessage2 = getResultMessage();
        String expectedMessage2 = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CountryNoteAddCommand.MESSAGE_USAGE);
        assertEquals(expectedMessage2, resultMessage2);

        terminal.inputCommand("client view 1");
        String resultMessage3 = getResultMessage();
        String expectedMessage3 = String.format(MESSAGE_VIEW_CLIENT_SUCCESS, "Jim");
        assertEquals(expectedMessage3, resultMessage3);

        // viewing clients
        terminal.inputCommand("client view 1");
        checkLabel("#name", "Jim");
        terminal.inputCommand("client view 2");
        checkLabel("#name", "Kim");
        terminal.inputCommand("client view 3");
        checkLabel("#name", "Sim");

        // clearing display panel using list command
        terminal.inputCommand("client list");
        checkLabel("#name", "");
        checkLabel("#address", "");

        // viewing country
        terminal.inputCommand("country note view");
        //TODO
        pauseToEyeball();

        terminal.inputCommand("exit");
        assertEquals(guiRobot.listWindows().size(), 0);
    }

    private void checkLabel(String id, String value) {
        guiRobot.sleep(50);
        assertEquals(value, guiRobot.lookup(id).queryText().getText());
    }

    private String getResultMessage() {
        return guiRobot.lookup("#resultDisplay").queryTextInputControl().getText();
    }

    private void pauseToEyeball() {
        guiRobot.pauseForHuman();
        guiRobot.pauseForHuman();
        guiRobot.pauseForHuman();
    }

    /**
     * For gui interaction.
     */
    private class InteractionTerminal {
        private final TextInputControl textInputControl;

        public InteractionTerminal(TextInputControl textInputControl) {
            this.textInputControl = textInputControl;
        }

        public void inputCommand(String command) {
            textInputControl.setText(command);
            guiRobot.pauseForHuman();
            guiRobot.interact(() -> guiRobot.push(KeyCode.ENTER));
            guiRobot.pauseForHuman();
        }
    }
}
