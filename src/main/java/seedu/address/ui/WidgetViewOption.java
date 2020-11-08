package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import seedu.address.model.country.Country;

/**
 * Represents the display option for the Widget View.
 */
public class WidgetViewOption {
    private static final String CLIENT = "CLIENT";
    private static final String COUNTRY_NOTE = "COUNTRY_NOTE";
    private static final String NONE = "NONE";

    private final boolean isClientView;
    private final boolean isCountryNoteView;
    private final Country country;

    /**
     * Initializes a WidgetViewOption.
     *
     * @param isClientView True if displaying client view.
     * @param isCountryNoteView True if displaying country notes view.
     * @param country The country of the country notes to view.
     */
    private WidgetViewOption(boolean isClientView, boolean isCountryNoteView, Country country) {
        assert !(isClientView && isCountryNoteView) : "isClientView and isCountryNoteView cannot be both true";
        requireNonNull(country);
        this.isClientView = isClientView;
        this.isCountryNoteView = isCountryNoteView;
        this.country = country;
    }

    /**
     * Generates a client widget option that represents intent to view client.
     *
     * @return A client widget option that represents intent to view client.
     */
    public static WidgetViewOption generateClientWidgetOption() {
        return new WidgetViewOption(true, false, Country.NULL_COUNTRY);
    }

    /**
     * Generates a country note widget option that represents intent to view country notes.
     *
     * @return A country note widget option that represents intent to view country notes.
     */
    public static WidgetViewOption generateCountryNoteWidgetOption(Country country) {
        return new WidgetViewOption(false, true, country);
    }

    /**
     * Generates a null widget option.
     *
     * @return A null widget option.
     */
    public static WidgetViewOption generateNullWidgetOption() {
        return new WidgetViewOption(false, false, Country.NULL_COUNTRY);
    }

    /**
     * Returns whether UI should display client view.
     *
     * @return True if UI should display client view.
     */
    public boolean isClientView() {
        return isClientView;
    }

    /**
     * Returns whether UI should display country notes view.
     *
     * @return True if UI should display country notes view.
     */
    public boolean isCountryNoteView() {
        return isCountryNoteView;
    }

    /**
     * Returns the country of the country notes.
     *
     * @return The country of the country notes.
     */
    public Country getCountry() {
        return country;
    }

    @Override
    public String toString() {
        if (isClientView) {
            return CLIENT;
        } else if (isCountryNoteView) {
            return COUNTRY_NOTE;
        } else {
            return NONE;
        }
    }
}
