package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CountryNoteDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * A class that parses the user input and returns a CountryNoteDeleteCommand.
 */
public class CountryNoteDeleteCommandParser implements Parser<CountryNoteDeleteCommand> {

    /**
     * Parses the given {@code args} in the context of the CountryNoteDeleteCommand and returns a
     * CountryNoteDeleteCommand object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public CountryNoteDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CountryNoteDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CountryNoteDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
