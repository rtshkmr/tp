package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.note.Note;

/**
 * Adds a Client-specific Note to list of notes associated with a client.
 */
public class ClientNoteAddCommand extends Command {

    public static final String COMMAND_WORD = "client note add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a note associated to a client "
            + "by the index number used in the displayed client list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: CLIENT_INDEX " + PREFIX_NOTE + "NOTE_STRING [" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_NOTE + "client note content";
    private static final String MESSAGE_DUPLICATE_CLIENT_NOTE = "The client note already exists";
    private static final String MESSAGE_SUCCESS = "Successfully added client note";
    private final Index targetIndex;
    private final Note clientNote;

    /**
     * Initializes a ClientNoteAddCommand.
     *
     * @param targetIndex The index of the client whom the clientNote will be associated to.
     * @param clientNote  The clientNote to be added.
     */
    public ClientNoteAddCommand(Index targetIndex, Note clientNote) {
        requireNonNull(targetIndex);
        requireNonNull(clientNote);
        this.targetIndex = targetIndex;
        this.clientNote = clientNote;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getSortedFilteredClientList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }
        Client clientToAddNoteTo = lastShownList.get(targetIndex.getZeroBased());
        if (model.hasClientNote(clientToAddNoteTo, clientNote)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT_NOTE);
        }
        model.addClientNote(clientToAddNoteTo, clientNote);
        model.refreshSortedFilteredClientListOrder();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientNoteAddCommand)) {
            return false;
        }

        // state check
        ClientNoteAddCommand c = (ClientNoteAddCommand) other;

        return this.targetIndex.equals(c.targetIndex) && this.clientNote.equals(c.clientNote);
    }
}
