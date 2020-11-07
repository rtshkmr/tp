package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClientNoteEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.Note;
import seedu.address.model.note.TagNoteMap;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new ClientNoteEditCommandParser object.
 */
public class ClientNoteEditCommandParser implements Parser<ClientNoteEditCommand> {

    private final TagNoteMap tagNoteMap;

    /**
     * Initializes a {@code ClientNoteEditCommandParser} with the {@code tagNoteMap} object.
     */
    public ClientNoteEditCommandParser(TagNoteMap tagNoteMap) {
        requireNonNull(tagNoteMap);
        this.tagNoteMap = tagNoteMap;
    }

    /**
     * Parses the given {@code args} in the context of the ClientNoteEditCommand and returns a ClientNoteEditCommand
     * object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public ClientNoteEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NOTE, PREFIX_TAG);
        // NEED TO PASS IN NOTE STRING AT LEAST, TAGS ARE OPTIONAL
        if (argMultimap.getPreamble().isEmpty() || !anyPrefixesPresent(argMultimap, PREFIX_NOTE, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClientNoteEditCommand.MESSAGE_USAGE));
        }
        Index targetClientIndex;
        Index targetClientNoteIndex;
        Set<Tag> tags = new HashSet<>();
        Note newNote = new Note("");
        try {
            String[] splitPreamble = argMultimap.getPreamble().split(" ");
            if (splitPreamble.length != 2) { // args: 1 1 all space delimited ==> 2 elems only
                throw new ParseException(""); // empty string, will be caught in the catch block
            }
            targetClientIndex = ParserUtil.parseIndex(splitPreamble[splitPreamble.length - 2]);
            targetClientNoteIndex = ParserUtil.parseIndex(splitPreamble[splitPreamble.length - 1]);
            if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
                tags = tagNoteMap.getUniqueTags(argMultimap.getAllValues(PREFIX_TAG));
            }
            if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
                newNote = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).orElse(""));
            }
            if (tags.isEmpty()) {
                tags.add(Tag.UNTAGGED);
            }
            newNote.setTags(tags);
            return new ClientNoteEditCommand(targetClientIndex, targetClientNoteIndex, newNote);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClientNoteEditCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
