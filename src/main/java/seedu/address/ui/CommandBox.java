package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private final CommandHistory history;
    private final CommandExecutor commandExecutor;
    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.history = new CommandHistory();
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the UP and DOWN key code pressed event for navigating through the {@code CommandHistory}.
     */
    @FXML
    private void handleKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.UP) {
            getPreviousCommand();
        } else if (keyEvent.getCode() == KeyCode.DOWN) {
            getNextCommand();
        }
    }

    /**
     * Retrieves the previous command relative to the current point in the {@code CommandHistory}
     * and replaces the text in {@code commandTextField} with the command.
     */
    private void getPreviousCommand() {
        if (history.hasPrevious()) {
            String currentCommand = commandTextField.getText();
            String prevCommand = history.getPrevious(currentCommand);
            commandTextField.setText(prevCommand);
        }
        commandTextField.end();
    }

    /**
     * Retrieves the next command relative to the current point in the {@code CommandHistory}
     * and replaces the text in {@code commandTextField} with the command.
     */
    private void getNextCommand() {
        if (history.hasNext() || history.hasIncompletelyTypedCommand()) {
            String nextCommand = history.getNext();
            commandTextField.setText(nextCommand);
            commandTextField.end();
        }
    }

    /**
     * Handles the Enter button pressed event and begins the execution of the command based on user input.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            String command = commandTextField.getText();
            commandExecutor.execute(command);
            history.add(command);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
