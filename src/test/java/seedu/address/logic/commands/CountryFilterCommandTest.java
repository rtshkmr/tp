package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientCountryMatchesInputCountryPredicate;
import seedu.address.model.country.Country;

public class CountryFilterCommandTest {

    private static final String[] COUNTRY_CODES = Locale.getISOCountries();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validCountries_matchesExpectedFilterResult() {
        List<Client> initialClientList = new ArrayList<>(model.getFilteredClientList());

        for (String countryCode : COUNTRY_CODES) {
            Country country = new Country(countryCode);
            ClientCountryMatchesInputCountryPredicate pred = new ClientCountryMatchesInputCountryPredicate(
                    country);
            CountryFilterCommand countryFilterCommand = new CountryFilterCommand(pred);
            countryFilterCommand.execute(model);

            List<Client> expectedFilteredClientList = initialClientList.stream().filter(pred)
                    .collect(Collectors.toList());

            int i = 0;
            for (Client client : model.getFilteredClientList()) {
                assertEquals(expectedFilteredClientList.get(i), client);
                i++;
            }
        }
    }

    @Test
    public void equals_sameObj_returnTrue() {
        Country country = new Country("SG");
        ClientCountryMatchesInputCountryPredicate pred = new ClientCountryMatchesInputCountryPredicate(
                country);
        CountryFilterCommand countryFilterCommand = new CountryFilterCommand(pred);
        assertEquals(countryFilterCommand, countryFilterCommand);
    }

    @Test
    public void equals_notCountryFilterCommand_returnFalse() {
        Country country = new Country("SG");
        ClientCountryMatchesInputCountryPredicate pred = new ClientCountryMatchesInputCountryPredicate(
                country);
        CountryFilterCommand countryFilterCommand = new CountryFilterCommand(pred);
        Object obj = new Object();
        assertNotEquals(countryFilterCommand, obj);
    }

    @Test
    public void equals_diffPred_returnFalse() {
        Country countryFirst = new Country("SG");
        Country countrySecond = new Country("MY");
        ClientCountryMatchesInputCountryPredicate predFirst = new ClientCountryMatchesInputCountryPredicate(
                countryFirst);
        ClientCountryMatchesInputCountryPredicate predSecond = new ClientCountryMatchesInputCountryPredicate(
                countrySecond);
        CountryFilterCommand countryFilterCommandFirst = new CountryFilterCommand(predFirst);
        CountryFilterCommand countryFilterCommandSecond = new CountryFilterCommand(predSecond);
        assertNotEquals(countryFilterCommandFirst, countryFilterCommandSecond);
        assertNotEquals(countryFilterCommandSecond, countryFilterCommandFirst);
    }

    @Test
    public void equals_samePred_returnFalse() {
        Country countryFirst = new Country("SG");
        Country countrySecond = new Country("SG");
        ClientCountryMatchesInputCountryPredicate predFirst = new ClientCountryMatchesInputCountryPredicate(
                countryFirst);
        ClientCountryMatchesInputCountryPredicate predSecond = new ClientCountryMatchesInputCountryPredicate(
                countrySecond);
        CountryFilterCommand countryFilterCommandFirst = new CountryFilterCommand(predFirst);
        CountryFilterCommand countryFilterCommandSecond = new CountryFilterCommand(predSecond);
        assertEquals(countryFilterCommandFirst, countryFilterCommandSecond);
        assertEquals(countryFilterCommandSecond, countryFilterCommandFirst);
    }

}
