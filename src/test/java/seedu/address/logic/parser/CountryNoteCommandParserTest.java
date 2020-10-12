package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CountryNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.country.Country;
import seedu.address.model.country.NoteStub;

public class CountryNoteCommandParserTest {

    private final CountryNoteCommandParser parser = new CountryNoteCommandParser();

    @Test
    public void parse_noCountryNoNote_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" random string"));
    }

    @Test
    public void parse_hasCountryNoNote_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" c/SG random string"));
    }

    @Test
    public void parse_invalidCountryHasNote_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" c/Z Z nt/random string"));
    }

    @Test
    public void parse_validCountryNoNote_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" c/MY "));
    }

    @Test
    public void parse_validCountryHasNote_equalsExpected() {
        try {
            CountryNoteCommand expected = new CountryNoteCommand(new Country("SG"),
                    new NoteStub("random string"));
            assertEquals(expected, parser.parse(" c/SG nt/random string"));
        } catch (ParseException e) {
            fail();
        }
    }

}
