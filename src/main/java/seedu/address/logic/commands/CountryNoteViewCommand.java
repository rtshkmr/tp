package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;

import seedu.address.model.Model;
import seedu.address.model.country.Country;
import seedu.address.ui.WidgetViewOption;

/**
 * Command to view all country notes of a given country.
 */
public class CountryNoteViewCommand extends Command {

    public static final String COMMAND_WORD = "country note view";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views all country notes that are associated with the given country.\n"
            + "Parameters: [" + PREFIX_COUNTRY + "COUNTRY_CODE]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_COUNTRY + "SG";
    public static final String MESSAGE_SUCCESS = "Showing country notes for %1$s";
    private final Country country;

    /**
     * Initializes a CountryNoteViewCommand.
     *
     * @param country The country which all displayed country notes belong to.
     */
    public CountryNoteViewCommand(Country country) {
        requireNonNull(country);
        this.country = country;
    }

    /**
     * Initializes a CountryNoteViewCommand that views all country notes.
     */
    public CountryNoteViewCommand() {
        this.country = Country.NULL_COUNTRY;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (country.equals(Country.NULL_COUNTRY)) {
            model.updateFilteredCountryNoteList(countryNote -> true);
        } else {
            model.updateFilteredCountryNoteList(countryNote -> countryNote.getCountry().equals(country));
        }

        String countryString = country.equals(Country.NULL_COUNTRY) ? "all countries" : country.toString();
        return new CommandResult(String.format(MESSAGE_SUCCESS, countryString), false,
            false, false, WidgetViewOption.generateCountryNoteWidgetOption(country));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CountryNoteViewCommand)) {
            return false;
        }

        // state check
        CountryNoteViewCommand c = (CountryNoteViewCommand) other;

        return country.equals(c.country);
    }
}
