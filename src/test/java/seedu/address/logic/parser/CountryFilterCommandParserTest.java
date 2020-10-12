package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CountryFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.ClientCountryMatchesInputCountryPredicate;
import seedu.address.model.country.Country;

public class CountryFilterCommandParserTest {

    private final CountryFilterCommandParser parser = new CountryFilterCommandParser();

    @Test
    public void parse_noCountry_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" random string"));
    }

    @Test
    public void parse_invalidCountryCode_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" c/ZZ"));
        assertThrows(ParseException.class, () -> parser.parse(" c/123"));
        assertThrows(ParseException.class, () -> parser.parse(" c/a b"));
        assertThrows(ParseException.class, () -> parser.parse(" c/c/ "));
    }

    @Test
    public void parse_validCountryCode_returnsExpected() {
        try {
            CountryFilterCommand expected = new CountryFilterCommand(
                    new ClientCountryMatchesInputCountryPredicate(new Country("SG")));
            assertEquals(expected, parser.parse(" c/SG"));
        } catch (ParseException e) {
            fail();
        }
    }
}
