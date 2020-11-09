package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.CountryNote;
import seedu.address.model.tag.Tag;

/**
 * Edits a CountryNote.
 */
public class CountryNoteEditCommand extends Command {

    public static final String COMMAND_WORD = "country note edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the country note at the given index in the last viewed country note list panel.\n"
            + "Parameters: COUNTRY_NOTE_INDEX "
            + "(" + PREFIX_NOTE + "NOTE_STRING )"
            + " (" + PREFIX_TAG + "TAG)...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_NOTE
            + "better government stability in recent months";
    public static final String MESSAGE_SUCCESS = "Edited country note at index %1$s: %2$s";
    public static final String MESSAGE_COUNTRY_NOTES_NOT_VISIBLE = "Country notes are not "
            + "currently being displayed, thus this command will not be executed "
            + "so as to prevent accidental modification of country notes.\n"
            + "Please use the " + CountryNoteViewCommand.COMMAND_WORD + " command before using this command.";
    public static final String MESSAGE_DUPLICATE_COUNTRY_NOTE_AFTER_EDIT = "The country note "
            + "after editing already exists in TBM!";
    private final Index targetIndex;
    private final CountryNote countryNote;
    private final Set<Tag> tags;

    /**
     * Initializes a CountryNoteEditCommand with the given {@code targetIndex} and {@code countryNote}.
     *
     * @param targetIndex The given targetIndex.
     * @param countryNote The country note that contains the new note content.
     */
    public CountryNoteEditCommand(Index targetIndex, CountryNote countryNote) {
        requireAllNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.countryNote = countryNote;
        this.tags = new HashSet<>();
    }

    /**
     * Initializes a CountryNoteEditCommand with the given {@code targetIndex} and {@code tags}.
     *
     * @param targetIndex The given targetIndex.
     * @param tags The new tags to add to the country note at the existing targetIndex.
     */
    public CountryNoteEditCommand(Index targetIndex, Set<Tag> tags) {
        requireAllNonNull(targetIndex, tags);
        this.targetIndex = targetIndex;
        this.countryNote = CountryNote.NULL_COUNTRY_NOTE;
        this.tags = tags;
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

        CountryNote countryNoteToEdit = lastShownList.get(targetIndex.getZeroBased());
        tags.addAll(countryNoteToEdit.getTags());
        tags.addAll(countryNote.getTags());

        // if previously note has UNTAGGED and now it has additional tags, remove UNTAGGED
        if (tags.size() > 1) {
            tags.remove(Tag.UNTAGGED);
        }

        CountryNote newCountryNote = new CountryNote(
                countryNote.equals(CountryNote.NULL_COUNTRY_NOTE)
                        ? countryNoteToEdit.getNoteContent() : countryNote.getNoteContent(),
                countryNoteToEdit.getCountry(), tags);

        if (model.hasCountryNote(newCountryNote)) {
            throw new CommandException(MESSAGE_DUPLICATE_COUNTRY_NOTE_AFTER_EDIT);
        }

        model.setCountryNote(countryNoteToEdit, newCountryNote);

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased(), newCountryNote));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CountryNoteEditCommand)) {
            return false;
        }

        // state check
        CountryNoteEditCommand c = (CountryNoteEditCommand) other;

        return targetIndex.equals(c.targetIndex) && countryNote.equals(c.countryNote) && tags.equals(c.tags);
    }

}
