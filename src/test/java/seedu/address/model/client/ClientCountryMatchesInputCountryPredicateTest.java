package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import seedu.address.model.country.Country;
import seedu.address.testutil.ClientBuilder;

public class ClientCountryMatchesInputCountryPredicateTest {

    private static final String[] COUNTRY_CODES = Locale.getISOCountries();

    @Test
    public void test_matchesCountry_returnTrue() {
        for (String countryCode : COUNTRY_CODES) {
            Country country = new Country(countryCode);
            ClientCountryMatchesInputCountryPredicate pred = new ClientCountryMatchesInputCountryPredicate(
                    country);
            assertTrue(pred.test(new ClientBuilder().withCountry(countryCode).build()));
        }
    }

    @Test
    public void test_notMatchCountry_returnFalse() {
        for (int i = 0; i < COUNTRY_CODES.length - 1; i++) {
            Country country = new Country(COUNTRY_CODES[i]);
            ClientCountryMatchesInputCountryPredicate pred = new ClientCountryMatchesInputCountryPredicate(
                    country);
            assertFalse(pred.test(new ClientBuilder().withCountry(COUNTRY_CODES[i + 1]).build()));
        }
    }

    @Test
    public void equals_sameObj_returnTrue() {
        ClientCountryMatchesInputCountryPredicate pred = new ClientCountryMatchesInputCountryPredicate(
                new Country("SG"));
        assertEquals(pred, pred);
    }

    @Test
    public void equals_notClientCountryMatchesInputCountryPredicate_returnFalse() {
        ClientCountryMatchesInputCountryPredicate pred = new ClientCountryMatchesInputCountryPredicate(
                new Country("SG"));
        Object obj = new Object();
        assertNotEquals(pred, obj);
    }

    @Test
    public void equals_diffCountry_returnFalse() {
        ClientCountryMatchesInputCountryPredicate predFirst = new ClientCountryMatchesInputCountryPredicate(
                new Country("SG"));
        ClientCountryMatchesInputCountryPredicate predSecond = new ClientCountryMatchesInputCountryPredicate(
                new Country("MY"));
        assertNotEquals(predFirst, predSecond);
        assertNotEquals(predSecond, predFirst);
    }

    @Test
    public void equals_sameCountry_returnTrue() {
        ClientCountryMatchesInputCountryPredicate predFirst = new ClientCountryMatchesInputCountryPredicate(
                new Country("SG"));
        ClientCountryMatchesInputCountryPredicate predSecond = new ClientCountryMatchesInputCountryPredicate(
                new Country("SG"));
        assertEquals(predFirst, predSecond);
        assertEquals(predSecond, predFirst);
    }
}
