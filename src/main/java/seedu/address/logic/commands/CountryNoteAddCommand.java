package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.CountryNote;

/**
 * Adds a CountryNote.
 */
public class CountryNoteAddCommand extends Command {

    public static final String COMMAND_WORD = "country note add";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a note that is associated with the user input country.\n"
            + "Parameters: "
            + PREFIX_COUNTRY + "COUNTRY_CODE "
            + PREFIX_NOTE + "NOTE_STRING"
            + " [" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_COUNTRY + "SG "
            + PREFIX_NOTE + "has one of the lowest coporate taxes in the world "
            + PREFIX_TAG + "tax";
    private static final String MESSAGE_DUPLICATE_COUNTRY_NOTE = "This country note already exists in TBM";
    private static final String MESSAGE_SUCCESS = "Successfully added country note for %1$s: %2$s";

    private final CountryNote countryNote;

    /**
     * Initializes a CountryNoteCommand.
     *
     * @param countryNote The countryNote to be added.
     */
    public CountryNoteAddCommand(CountryNote countryNote) {
        requireNonNull(countryNote);
        this.countryNote = countryNote;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasCountryNote(countryNote)) {
            throw new CommandException(MESSAGE_DUPLICATE_COUNTRY_NOTE);
        }

        model.addCountryNote(countryNote);
        return new CommandResult(String.format(MESSAGE_SUCCESS, countryNote.getCountry(), countryNote));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CountryNoteAddCommand)) {
            return false;
        }

        // state check
        CountryNoteAddCommand c = (CountryNoteAddCommand) other;

        return countryNote.equals(c.countryNote);
    }
}
