package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CLIENT_NOTE_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;

/**
 * Edits a Client-specific Note from a list of notes associated with a client while preserving tagging history.
 */
public class ClientNoteEditCommand extends Command {

    public static final String COMMAND_WORD = "client note edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a note associated to a client "
            + "by the index number used in the displayed client list"
            + "and the index number for client note in the displayed client notes list.\n"
            + "Parameters: CLIENT_INDEX CLIENT_NOTE_INDEX " + PREFIX_NOTE + "NOTE_STRING [" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 1 " + PREFIX_NOTE + "client note newly edited content";
    public static final String MESSAGE_EDIT_CLIENT_NOTE_SUCCESS = "Successfully edited note for %1$s: \n Before: "
            + "%2$s\n After: %3$s";
    public static final String MESSAGE_NOT_REAL_EDIT = "New edit doesn't value-add anything!";
    public static final String MESSAGE_DUPLICATE_CLIENT_NOTE_AFTER_EDIT = "The client note after editing "
            + "already exists in TBM!";

    private final Index targetClientIndex;
    private final Index targetClientNoteIndex;
    private final Note parsedNewNote;

    /**
     * Initializes a ClientNoteEditCommand.
     *
     * @param targetClientIndex The index of the client whom the clientNote is associated to.
     * @param targetClientNoteIndex The index of the clientNote to be edited.
     * @param parsedNewNote The newly edited note.
     */
    public ClientNoteEditCommand(Index targetClientIndex, Index targetClientNoteIndex, Note parsedNewNote) {
        requireAllNonNull(targetClientIndex, targetClientNoteIndex, parsedNewNote);
        this.targetClientIndex = targetClientIndex;
        this.targetClientNoteIndex = targetClientNoteIndex;
        this.parsedNewNote = parsedNewNote;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownClientList = model.getSortedFilteredClientList();
        if (targetClientIndex.getZeroBased() >= lastShownClientList.size()) {
            throw new CommandException(MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }
        List<Note> notesList = lastShownClientList.get(targetClientIndex.getZeroBased())
                .getClientNotesAsUnmodifiableList();
        if (targetClientNoteIndex.getZeroBased() >= notesList.size()) {
            throw new CommandException(MESSAGE_INVALID_CLIENT_NOTE_DISPLAYED_INDEX);
        }

        Client associatedClient = lastShownClientList.get(targetClientIndex.getZeroBased());
        Note existingNote = associatedClient.getClientNotesAsUnmodifiableList()
                .get(targetClientNoteIndex.getZeroBased());
        assert associatedClient.hasClientNote(existingNote) : "attempting to edit client note that doesn't exist";
        String existingNoteContent = existingNote.getNoteContent();
        Set<Tag> accumulatedTags = new HashSet<>();
        Note editedNote;
        if (parsedNewNote.getNoteContent().equals("")) { // empty string implies only tags have been passed in
            editedNote = new Note(existingNoteContent);
        } else {
            editedNote = parsedNewNote;
        }

        // Add the previous tags, because we want to retain history of tags
        accumulatedTags.addAll(existingNote.getTags());
        accumulatedTags.addAll(parsedNewNote.getTags());

        // Because parser used tagNoteMap#getUniqueTags, it is okay for there to be duplicates in previous tags and
        // new Note's tags. Overwriting will keep one of the two duplicates, and they are the same object reference.
        if (accumulatedTags.size() > 1) {
            accumulatedTags.remove(Tag.UNTAGGED);
        }
        editedNote.setTags(accumulatedTags);
        if (existingNote.equals(editedNote)) {
            throw new CommandException(MESSAGE_NOT_REAL_EDIT);
        }

        if (model.hasClientNote(associatedClient, editedNote)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT_NOTE_AFTER_EDIT);
        }

        model.editClientNote(associatedClient, existingNote, editedNote);
        model.refreshSortedFilteredClientListOrder();
        return new CommandResult(String.format(MESSAGE_EDIT_CLIENT_NOTE_SUCCESS, associatedClient.getName(),
                existingNote, editedNote));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientNoteEditCommand)) {
            return false;
        }

        // state check
        ClientNoteEditCommand c = (ClientNoteEditCommand) other;

        return this.targetClientIndex.equals(c.targetClientIndex)
                && this.targetClientNoteIndex.equals(c.targetClientNoteIndex)
                && this.parsedNewNote.equals(c.parsedNewNote);
    }
}
