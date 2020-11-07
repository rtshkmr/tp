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
     * Parses the given {@code args} in the context of the CountryFilterCommand and returns a CountryFilterCommand
     * object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public CountryFilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COUNTRY);

        if (argMultimap.getValue(PREFIX_COUNTRY).isEmpty() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CountryFilterCommand.MESSAGE_USAGE));
        }
        String countryCode = argMultimap.getValue(PREFIX_COUNTRY).get();

        Country country = ParserUtil.parseCountry(countryCode);
        return new CountryFilterCommand(new ClientCountryMatchesInputCountryPredicate(country));
    }
}
