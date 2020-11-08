package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.CountryNote;

/**
 * Deletes a CountryNote.
 */
public class CountryNoteDeleteCommand extends Command {

    public static final String COMMAND_WORD = "country note delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the country note at the given index in the currently viewed country note list panel.\n"
            + "Parameters: COUNTRY_NOTE_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Deleted country note at index %1$s: %2$s";
    public static final String MESSAGE_COUNTRY_NOTES_NOT_VISIBLE = "Country notes are not currently being displayed,"
            + " thus this command will not be executed so as to prevent accidental deletion of country notes.\n"
            + "Please use the " + CountryNoteViewCommand.COMMAND_WORD + " command before using this command.";
    private final Index targetIndex;

    /**
     * Initializes a CountryNoteDeleteCommand with the given {@code targetIndex}.
     *
     * @param targetIndex The given targetIndex.
     */
    public CountryNoteDeleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<CountryNote> lastShownList = model.getSortedFilteredCountryNoteList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COUNTRY_NOTE_DISPLAYED_INDEX);
        }

        if (!model.getCountryNotesListPanelIsVisible()) {
            throw new CommandException(MESSAGE_COUNTRY_NOTES_NOT_VISIBLE);
        }

        CountryNote countryNoteToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCountryNote(countryNoteToDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased(), countryNoteToDelete));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CountryNoteDeleteCommand)) {
            return false;
        }

        // state check
        CountryNoteDeleteCommand c = (CountryNoteDeleteCommand) other;

        return targetIndex.equals(c.targetIndex);
    }
}
