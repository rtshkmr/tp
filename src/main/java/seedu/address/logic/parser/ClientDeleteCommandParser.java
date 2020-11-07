package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClientDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClientDeleteCommand object.
 */
public class ClientDeleteCommandParser implements Parser<ClientDeleteCommand> {

    /**
     * Parses the given {@code args} in the context of the ClientDeleteCommand and returns a ClientDeleteCommand
     * object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public ClientDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ClientDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClientDeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
