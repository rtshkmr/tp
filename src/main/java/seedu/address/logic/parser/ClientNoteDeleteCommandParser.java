package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClientNoteDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClientNoteDeleteCommand object.
 */
public class ClientNoteDeleteCommandParser implements Parser<ClientNoteDeleteCommand> {

    /**
     * Parses the given {@code args} in the context of the ClientNoteDeleteCommand and returns a ClientNoteDeleteCommand
     * object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public ClientNoteDeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args); // no prefix needed
        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClientNoteDeleteCommand.MESSAGE_USAGE));
        }
        Index targetClientIndex;
        Index targetClientNoteIndex;
        try {
            String[] splitPreamble = argMultimap.getPreamble().split(" ");
            if (splitPreamble.length != 2) { // args: 1 1 all space delimited ==> 2 elems only
                throw new ParseException("");
            }
            targetClientIndex = ParserUtil.parseIndex(splitPreamble[splitPreamble.length - 2]);
            targetClientNoteIndex = ParserUtil.parseIndex(splitPreamble[splitPreamble.length - 1]);
            return new ClientNoteDeleteCommand(targetClientIndex, targetClientNoteIndex);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClientNoteDeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
