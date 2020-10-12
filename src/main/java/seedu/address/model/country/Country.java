package seedu.address.model.country;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import seedu.address.model.note.Note;

/**
 * A representation of a Country that can be identified by a 2-letter ISO3166 country-code or by its country
 * name. It contains a list of country notes.
 */
public class Country {

    private final String countryName;
    private final String countryCode;
    private final Set<Note> countryNotes;

    /**
     * Initializes a Country by its countryCode.
     *
     * @param countryCode The ISO3166 2-letter country code of the country to be initialized.
     */
    public Country(String countryCode) {
        this.countryCode = countryCode;
        this.countryName = new Locale("", countryCode).getDisplayName();
        this.countryNotes = new LinkedHashSet<>();
    }

    /**
     * Gets the list of country notes associated with this country.
     *
     * @return The list of country notes associated with this country.
     */
    public Set<Note> getCountryNotes() {
        return Collections.unmodifiableSet(this.countryNotes);
    }

    /**
     * Adds a country note for this country.
     * Method is protected as it is not intended for use outside of this package.
     *
     * @param countryNote The country note to be added.
     */
    protected void addCountryNote(Note countryNote) {
        requireNonNull(countryNote);
        this.countryNotes.add(countryNote);
    }

    /**
     * Checks whether given country note has been added for this country.
     *
     * @param countryNote The given country note.
     * @return True if given country note has been added for this country, false otherwise.
     */
    protected boolean hasCountryNote(Note countryNote) {
        return countryNotes.contains(countryNote);
    }

    /**
     * Gets the country name of this country.
     *
     * @return The country name of this country.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Gets the country code of this country.
     *
     * @return The country code of this country.
     */
    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Country)) {
            return false;
        }

        // state check
        Country c = (Country) other;

        return countryCode.equals(c.countryCode);
    }

    @Override
    public int hashCode() {
        return countryCode.hashCode();
    }

    @Override
    public String toString() {
        return countryName + " (" + countryCode + ")";
    }

}
