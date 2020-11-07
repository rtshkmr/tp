package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {

    /**
     * Parses {@code args} into a command and returns it.
     *
     * @throws ParseException If {@code args} does not conform to the expected format.
     */
    T parse(String args) throws ParseException;
}
