package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class CommandBoxTest extends GuiUnitTest {

    private static final String[] COMMANDS_THAT_SUCCEED = { "client list", "client view 1", "client view 2" };
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    private CommandBoxHandle commandBoxHandle;

    @BeforeEach
    public void setUp() {
        CommandBox commandBox = new CommandBox(commandText -> {
            if (Arrays.asList(COMMANDS_THAT_SUCCEED).contains(commandText)) {
                return new CommandResult("Command successful");
            }
            throw new CommandException("Command failed");
        });
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartExtension.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_handleKeyPush() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    @Test
    public void commandBox_correctHistory() {
        String viewCommand1 = "client view 1";
        String viewCommand2 = "client view 2";
        commandBoxHandle.run(viewCommand1);
        commandBoxHandle.run(viewCommand2);
        guiRobot.pauseForHuman();

        commandBoxHandle.pressUp();
        assertEquals(commandBoxHandle.getInput(), viewCommand2);

        commandBoxHandle.pressUp();
        assertEquals(commandBoxHandle.getInput(), viewCommand1);

        commandBoxHandle.pressUp();
        assertEquals(commandBoxHandle.getInput(), viewCommand1);

        commandBoxHandle.pressDown();
        assertEquals(commandBoxHandle.getInput(), viewCommand2);

        commandBoxHandle.pressDown();
        assertEquals(commandBoxHandle.getInput(), "");
    }

    /**
     * Runs a command that fails, then verifies that <br>
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMANDS_THAT_SUCCEED[0]);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

}
