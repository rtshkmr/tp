package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.note.Note;

/**
 * Deletes a Client-specific Note from a list of notes associated with a client.
 */
public class ClientNoteDeleteCommand extends Command {

    public static final String COMMAND_WORD = "client note delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a note associated to a client "
            + "by the index number used in the displayed client list "
            + "and the index number for client note in the displayed client notes list. "
            + "Parameters: CLIENT_INDEX CLIENT_NOTE_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 2";
    public static final String MESSAGE_DELETED_CLIENT_NOTE_SUCCESS = "Successfully deleted client note";
    private static final String MESSAGE_INVALID_CLIENT_NOTE_DISPLAYED_INDEX = "The client note index provided is "
            + "invalid";
    private final Index targetClientIndex;
    private final Index targetClientNoteIndex;

    /**
     * Initializes a ClientNoteDeleteCommand.
     *
     * @param targetClientIndex The index of the client whom the clientNote is associated to.
     * @param targetClientNoteIndex  The index of the clientNote to be deleted.
     */
    public ClientNoteDeleteCommand(Index targetClientIndex, Index targetClientNoteIndex) {
        requireAllNonNull(targetClientIndex, targetClientNoteIndex);
        this.targetClientIndex = targetClientIndex;
        this.targetClientNoteIndex = targetClientNoteIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownClientList = model.getSortedFilteredClientList();
        if (targetClientIndex.getZeroBased() >= lastShownClientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }
        List<Note> notesList = lastShownClientList.get(targetClientIndex.getZeroBased())
                .getClientNotesAsUnmodifiableList();
        if (targetClientNoteIndex.getZeroBased() >= notesList.size()) {
            throw new CommandException(MESSAGE_INVALID_CLIENT_NOTE_DISPLAYED_INDEX);
        }
        Client associatedClient = lastShownClientList.get(targetClientIndex.getZeroBased());
        Note noteToDelete = associatedClient.getClientNotesAsUnmodifiableList()
                .get(targetClientNoteIndex.getZeroBased());
        assert associatedClient.hasClientNote(noteToDelete) : "attempting to delete client note that doesn't exist";
        model.deleteClientNote(associatedClient, noteToDelete);
        model.refreshSortedFilteredClientListOrder();
        return new CommandResult(MESSAGE_DELETED_CLIENT_NOTE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientNoteDeleteCommand)) {
            return false;
        }

        // state check
        ClientNoteDeleteCommand c = (ClientNoteDeleteCommand) other;

        return this.targetClientIndex.equals(c.targetClientIndex)
                && this.targetClientNoteIndex.equals(c.targetClientNoteIndex);
    }
}
