package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;

import seedu.address.logic.commands.CountryFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.ClientCountryMatchesInputCountryPredicate;
import seedu.address.model.country.Country;

/**
 * Parses input arguments and creates a new CountryFilterCommand object.
 */
public class CountryFilterCommandParser implements Parser<CountryFilterCommand> {

    /**
     * Parses the given {@code arg} of arguments in the context of the CountryFilterCommand and returns a
     * CountryFilterCommand object for execution.
     *
     * @param args The user input string.
     * @return A CountryFilterCommand object which corresponds to the user input string.
     * @throws ParseException If user input string is invalid.
     */
    public CountryFilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COUNTRY);

        if (argMultimap.getValue(PREFIX_COUNTRY).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CountryFilterCommand.MESSAGE_USAGE));
        }
        String countryCode = argMultimap.getValue(PREFIX_COUNTRY).get();

        Country country = ParserUtil.parseCountry(countryCode);
        return new CountryFilterCommand(new ClientCountryMatchesInputCountryPredicate(country));
    }
}
