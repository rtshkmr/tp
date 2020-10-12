package seedu.address.model.country;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import seedu.address.model.note.Note;

/**
 * A high-level class responsible for mapping ISO3166 2-letter country codes to countries.
 */
public class CountryManager {

    //TODO: Should include checking for 3-letter Country Code?
    public static final String MESSAGE_CONSTRAINTS = "Country code must be a valid 2-letter ISO3166 country code";
    private static final String[] COUNTRY_CODES = Locale.getISOCountries();
    private final Map<String, Country> countryCodeMap;

    /**
     * Initializes a CountryManager with a Map that maps ISO3166 2-letter country codes to countries.
     */
    public CountryManager() {
        countryCodeMap = initCountryCodeMap();
    }

    /**
     * Checks if countryCode is a valid ISO3166 code.
     *
     * @param countryCode The country code.
     * @return Whether countryCode is a valid ISO3166 code.
     */
    public static boolean isValidCountryCode(String countryCode) {
        for (int i = 0; i < COUNTRY_CODES.length; i++) {
            if (COUNTRY_CODES[i].equals(countryCode)) {
                return true;
            }
        }
        return false;
    }

    private static Map<String, Country> initCountryCodeMap() {
        Map<String, Country> countryCodeMap = new HashMap<>();
        for (String countryCode : COUNTRY_CODES) {
            countryCodeMap.put(countryCode, new Country(countryCode));
        }
        return countryCodeMap;
    }

    /**
     * Checks if {@code country} contains {@code countryNote}.
     *
     * @param country     The country to be checked.
     * @param countryNote The note to be checked.
     * @return Whether {@code country} contains {@code countryNote}.
     */
    public boolean hasCountryNote(Country country, Note countryNote) {
        if (!isValidCountryCode(country.getCountryCode())) {
            return false;
        }
        return countryCodeMap.get(country.getCountryCode()).hasCountryNote(countryNote);
    }

    /**
     * Adds the {@code countryNote} to the {@code country}.
     *
     * @param country     The country where the countryNote will be added.
     * @param countryNote The country note to be added.
     */
    public void addCountryNote(Country country, Note countryNote) {
        if (isValidCountryCode(country.getCountryCode())) {
            countryCodeMap.get(country.getCountryCode()).addCountryNote(countryNote);
        }
    }

    /**
     * Returns the country that is identified by the given country code.
     *
     * @param countryCode The ISO3166 2-letter country code of the particular country.
     * @return The country that is identified by the given country code.
     */
    public Country getCountryFromCode(String countryCode) {
        if (isValidCountryCode(countryCode)) {
            return countryCodeMap.get(countryCode);
        } else {
            throw new NullPointerException();
        }
    }
}
