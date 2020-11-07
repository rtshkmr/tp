package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUGGEST;

import java.util.Set;

import seedu.address.logic.commands.ClientSuggestCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.ClientSuggestionType;

/**
 * Parses input arguments and creates a new ClientSuggestCommand object.
 */
public class ClientSuggestCommandParser implements Parser<ClientSuggestCommand> {

    /**
     * Parses the given {@code args} in the context of the ClientSuggestCommand and returns a ClientSuggestCommand
     * object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public ClientSuggestCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUGGEST);

        if (argMultimap.getValue(PREFIX_SUGGEST).isEmpty() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClientSuggestCommand.MESSAGE_USAGE));
        }

        Set<ClientSuggestionType> clientSuggestionTypeOrderedSet =
                ParserUtil.parseClientSuggestionTypes(argMultimap.getAllValues(PREFIX_SUGGEST));

        return new ClientSuggestCommand(clientSuggestionTypeOrderedSet);
    }

}
