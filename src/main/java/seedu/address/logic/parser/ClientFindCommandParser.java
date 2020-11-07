package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ClientFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ClientFindCommand object
 */
public class ClientFindCommandParser implements Parser<ClientFindCommand> {

    /**
     * Parses the given {@code args} in the context of the ClientFindCommand and returns a ClientFindCommand
     * object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public ClientFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClientFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new ClientFindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
