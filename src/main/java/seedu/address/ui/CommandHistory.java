package seedu.address.ui;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * A class to encapsulate the history of commands entered by the user.
 */
public class CommandHistory {

    private static final Logger logger = LogsCenter.getLogger(CommandHistory.class);
    private final List<String> history;
    private int pointer;
    private String temp;

    /**
     * Constructor for the history of commands entered.
     */
    public CommandHistory() {
        this.history = new ArrayList<>();
        pointer = 0;
    }

    /**
     * Adds the given command to the history of commands.
     *
     * @param command A command to be added to the history.
     */
    public void add(String command) {
        requireAllNonNull(command);
        logger.info(history.toString());

        history.add(command);
        pointer = history.size();
        clearTemp();
    }

    /**
     * Gets the next command in history.
     *
     * @return The next command.
     * @throws NoSuchElementException if there is no next command.
     */
    public String getNext() {
        if (hasNext()) {
            return history.get(moveForward());
        } else if (hasIncompletelyTypedCommand()) {
            return getIncompletelyTypedCommand();
        }
        throw new NoSuchElementException();
    }

    /**
     * Gets the previous command in history.
     *
     * @return The previous command.
     * @throws NoSuchElementException if there is no previous command.
     */
    public String getPrevious(String command) {
        addIncompletelyTypedCommandToTempHistory(command);

        if (hasPrevious()) {
            return history.get(moveBack());
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Checks if there is any command next.
     *
     * @return True if there is another command after the current one.
     */
    public boolean hasNext() {
        return pointer + 1 < history.size();
    }

    /**
     * Checks if there is a incompletely typed command that is currently in the temporary history.
     *
     * @return True if there is a incompletely typed command, false otherwise.
     */
    public boolean hasIncompletelyTypedCommand() {
        return temp != null;
    }

    /**
     * Checks if there is any command previously.
     *
     * @return True if there is another command previous to the current one.
     */
    public boolean hasPrevious() {
        return pointer - 1 >= 0;
    }

    /**
     * Moves the pointer backwards.
     *
     * @return Decremented pointer,
     */
    private int moveBack() {
        assert pointer > 0;
        return --pointer;
    }

    /**
     * Moves the pointer forward.
     *
     * @return Incremented pointer.
     */
    private int moveForward() {
        assert pointer < history.size() - 1;
        return ++pointer;
    }

    /**
     * Adds the current incompletely typed command to a temporary history.
     */
    private void addIncompletelyTypedCommandToTempHistory(String command) {
        if (pointer == history.size()) {
            temp = command;
        }
    }

    /**
     * Gets the incompletely typed command.
     */
    private String getIncompletelyTypedCommand() {
        pointer = history.size();
        return temp;
    }

    /**
     * Clears the temporary history.
     */
    private void clearTemp() {
        temp = null;
    }

}
