package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.CountryNoteAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.country.Country;
import seedu.address.model.note.CountryNote;
import seedu.address.model.note.Note;
import seedu.address.model.note.TagNoteMap;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new CountryNoteAddCommand object.
 */
public class CountryNoteAddCommandParser implements Parser<CountryNoteAddCommand> {
    private final TagNoteMap tagNoteMap;

    /**
     * Initializes a {@code CountryNoteAddCommandParser} with the {@code tagNoteMap} object.
     */
    public CountryNoteAddCommandParser(TagNoteMap tagNoteMap) {
        requireNonNull(tagNoteMap);
        this.tagNoteMap = tagNoteMap;
    }

    /**
     * Parses the given {@code args} in the context of the CountryNoteAddCommand and returns a CountryNoteAddCommand
     * object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public CountryNoteAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COUNTRY, PREFIX_NOTE, PREFIX_TAG);

        if (argMultimap.getValue(PREFIX_COUNTRY).isEmpty() || argMultimap.getValue(PREFIX_NOTE).isEmpty()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CountryNoteAddCommand.MESSAGE_USAGE));
        }
        Set<Tag> tags = tagNoteMap.getUniqueTags(argMultimap.getAllValues(PREFIX_TAG));

        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        Country country = ParserUtil.parseCountry(argMultimap.getValue(PREFIX_COUNTRY).get());
        CountryNote countryNote = new CountryNote(note.getNoteContent(), country);
        countryNote.setTags(tags);

        return new CountryNoteAddCommand(countryNote);
    }
}
