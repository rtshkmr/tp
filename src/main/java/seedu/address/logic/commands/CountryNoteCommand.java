package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.country.Country;
import seedu.address.model.note.Note;

/**
 * Adds a Country-specific Note.
 */
public class CountryNoteCommand extends Command {

    public static final String COMMAND_WORD = "country note";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a note that is associated with the user input country.\n"
            + "Parameters: "
            + PREFIX_COUNTRY + "COUNTRY_CODE "
            + PREFIX_NOTE + "NOTE_STRING\n"
            + "Example: " + COMMAND_WORD + " c/SG nt/likes laksa";
    private static final String MESSAGE_DUPLICATE_COUNTRY_NOTE = "This country note already exists in TBM";
    private static final String MESSAGE_SUCCESS = "Successfully added country note for %1$s: %2$s";

    private final Country country;
    private final Note countryNote;

    /**
     * Initializes a CountryNoteCommand.
     *
     * @param country     The country where the countryNote will be associated to.
     * @param countryNote The countryNote to be added.
     */
    public CountryNoteCommand(Country country, Note countryNote) {
        requireNonNull(country);
        requireNonNull(countryNote);
        this.country = country;
        this.countryNote = countryNote;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasCountryNote(country, countryNote)) {
            throw new CommandException(MESSAGE_DUPLICATE_COUNTRY_NOTE);
        }

        model.addCountryNote(country, countryNote);
        return new CommandResult(String.format(MESSAGE_SUCCESS, country, countryNote));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CountryNoteCommand)) {
            return false;
        }

        // state check
        CountryNoteCommand c = (CountryNoteCommand) other;

        return country.equals(c.country) && countryNote.equals(c.countryNote);
    }
}
