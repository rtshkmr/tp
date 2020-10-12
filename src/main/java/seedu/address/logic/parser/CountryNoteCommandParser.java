package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

import seedu.address.logic.commands.CountryNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.country.Country;
import seedu.address.model.note.Note;

/**
 * Parses input arguments and creates a new CountryNoteCommand object.
 */
public class CountryNoteCommandParser implements Parser<CountryNoteCommand> {

    /**
     * Parses the given {@code arg} of arguments in the context of the CountryNoteCommand and returns a
     * CountryNoteCommand object for execution.
     *
     * @param args The user input string.
     * @return A CountryNoteCommand object which corresponds to the user input string.
     * @throws ParseException If user input string is invalid.
     */
    public CountryNoteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COUNTRY, PREFIX_NOTE);

        if (argMultimap.getValue(PREFIX_COUNTRY).isEmpty() || argMultimap.getValue(PREFIX_NOTE).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CountryNoteCommand.MESSAGE_USAGE));
        }
        Country country = ParserUtil.parseCountry(argMultimap.getValue(PREFIX_COUNTRY).get());
        Note countryNote = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());

        return new CountryNoteCommand(country, countryNote);
    }
}
