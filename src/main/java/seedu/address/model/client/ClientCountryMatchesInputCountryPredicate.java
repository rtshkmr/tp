package seedu.address.model.client;

import java.util.function.Predicate;

import seedu.address.model.country.Country;

/**
 * Tests that a {@code Client}'s {@code Country} matches the given Country.
 */
public class ClientCountryMatchesInputCountryPredicate implements Predicate<Client> {

    private final Country country;

    /**
     * Initializes with the country to be matched.
     *
     * @param country The country to be matched
     */
    public ClientCountryMatchesInputCountryPredicate(Country country) {
        this.country = country;
    }

    @Override
    public boolean test(Client client) {
        return client.getCountry().equals(country);
    }

    /**
     * Returns string representation of user-input country.
     *
     * @return The string representation of the user-input country.
     */
    public String getInputCountryStringRepresentation() {
        return country.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClientCountryMatchesInputCountryPredicate // instanceof handles nulls
                && country.equals(((ClientCountryMatchesInputCountryPredicate) other).country)); // state check
    }

}
