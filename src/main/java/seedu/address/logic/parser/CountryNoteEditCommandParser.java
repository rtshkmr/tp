package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CountryNoteEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.country.Country;
import seedu.address.model.note.CountryNote;
import seedu.address.model.note.Note;
import seedu.address.model.note.TagNoteMap;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new CountryNoteEditCommand object.
 */
public class CountryNoteEditCommandParser implements Parser<CountryNoteEditCommand> {

    private final TagNoteMap tagNoteMap;

    /**
     * Initializes a {@code CountryNoteEditCommandParser} with the {@code tagNoteMap} object.
     */
    public CountryNoteEditCommandParser(TagNoteMap tagNoteMap) {
        requireNonNull(tagNoteMap);
        this.tagNoteMap = tagNoteMap;
    }

    /**
     * Parses the given {@code args} in the context of the CountryNoteEditCommand and returns a CountryNoteEditCommand
     * object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public CountryNoteEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NOTE, PREFIX_TAG);

        if ((argMultimap.getValue(PREFIX_NOTE).isEmpty() && argMultimap.getValue(PREFIX_TAG).isEmpty())
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CountryNoteEditCommand.MESSAGE_USAGE));
        }

        Index targetIndex = ParserUtil.parseIndex(argMultimap.getPreamble());

        Set<Tag> tags = new HashSet<>();
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            tags.addAll(tagNoteMap.getUniqueTags(argMultimap.getAllValues(PREFIX_TAG)));
        }

        if (argMultimap.getValue(PREFIX_NOTE).isEmpty()) {
            return new CountryNoteEditCommand(targetIndex, tags);
        }

        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        CountryNote countryNote = new CountryNote(note.getNoteContent(), Country.NULL_COUNTRY, tags);

        return new CountryNoteEditCommand(targetIndex, countryNote);
    }

}
