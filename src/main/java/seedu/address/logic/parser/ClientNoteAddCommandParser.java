package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClientNoteAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.Note;
import seedu.address.model.note.TagNoteMap;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new ClientNoteAddCommand object.
 */
public class ClientNoteAddCommandParser implements Parser<ClientNoteAddCommand> {

    private final TagNoteMap tagNoteMap;

    /**
     * Initializes a {@code ClientNoteAddCommandParser} with the supplied {@code tagNoteMap} object.
     */
    public ClientNoteAddCommandParser(TagNoteMap tagNoteMap) {
        requireNonNull(tagNoteMap);
        this.tagNoteMap = tagNoteMap;
    }

    /**
     * Parses the given {@code args} in the context of the ClientNoteAddCommand and returns a ClientNoteAddCommand
     * object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public ClientNoteAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_NOTE);
        if (!arePrefixesPresent(argMultimap, PREFIX_NOTE) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClientNoteAddCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClientNoteAddCommand.MESSAGE_USAGE), pe);
        }

        Set<Tag> tags = tagNoteMap.getUniqueTags(argMultimap.getAllValues(PREFIX_TAG));

        Note clientNote = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE)
                .orElseThrow(() -> new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ClientNoteAddCommand.MESSAGE_USAGE))));
        clientNote.setTags(tags);
        return new ClientNoteAddCommand(index, clientNote);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
