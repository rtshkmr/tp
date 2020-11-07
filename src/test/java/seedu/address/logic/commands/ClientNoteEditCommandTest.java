package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CLIENT_NOTE_DISPLAYED_INDEX;
import static seedu.address.logic.commands.ClientNoteEditCommand.MESSAGE_EDIT_CLIENT_NOTE_SUCCESS;
import static seedu.address.logic.commands.ClientNoteEditCommand.MESSAGE_NOT_REAL_EDIT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TestUtil.basicEqualsTests;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.client.Client;
import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ClientBuilder;

class ClientNoteEditCommandTest {

    private static final String NOTE_CONTENT_1 = "client note content 1";
    private static final String NOTE_CONTENT_2 = "client note content 2";

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void constructor_nulls_throwsNullPointerException() {
        Index clientIdx = Index.fromOneBased(1);
        Index clientNoteIdx = Index.fromOneBased(1);
        Note clientNote = new Note(NOTE_CONTENT_1);
        // make sure all args are valid before doing negative tests
        assertDoesNotThrow(() -> new ClientNoteEditCommand(clientIdx, clientNoteIdx, clientNote));
        // change args to null one by one
        assertThrows(NullPointerException.class, () -> new ClientNoteEditCommand(null, clientNoteIdx, clientNote));
        assertThrows(NullPointerException.class, () -> new ClientNoteEditCommand(clientIdx, null, clientNote));
        assertThrows(NullPointerException.class, () -> new ClientNoteEditCommand(clientIdx, clientNoteIdx, null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Index clientIdx = Index.fromOneBased(1);
        Index clientNoteIdx = Index.fromOneBased(1);
        Note clientNote = new Note(NOTE_CONTENT_1);
        ClientNoteEditCommand clientNoteEditCommand = new ClientNoteEditCommand(clientIdx, clientNoteIdx, clientNote);
        assertThrows(NullPointerException.class, () -> clientNoteEditCommand.execute(null));
    }

    @Test
    public void execute_indicesOutOfRange_commandFailure() {
        Index invalidClientIdx = Index.fromOneBased(99);
        Index validClientIdx = Index.fromOneBased(1);
        Index invalidClientNoteIdx = Index.fromOneBased(99);
        Index validClientNoteIdx = Index.fromOneBased(1);
        Note clientNote1 = new Note(NOTE_CONTENT_1);
        Note clientNote2 = new Note(NOTE_CONTENT_2);
        Note newEditNote = new Note("dummy note");
        Client client1 = new ClientBuilder().withName("client1").build();
        model.addClient(client1);
        model.addClientNote(client1, clientNote1);
        model.addClientNote(client1, clientNote2);
        ClientNoteEditCommand invalidClientNoteEditCommand1 = new ClientNoteEditCommand(invalidClientIdx,
                validClientNoteIdx, newEditNote);
        ClientNoteEditCommand invalidClientNoteEditCommand2 = new ClientNoteEditCommand(validClientIdx,
                invalidClientNoteIdx, newEditNote);
        assertCommandFailure(invalidClientNoteEditCommand1, model, MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        assertCommandFailure(invalidClientNoteEditCommand2, model, MESSAGE_INVALID_CLIENT_NOTE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validClientIdxValidNoteIdx_generatesClientNoteEditCommandSuccessfully() {
        Index clientIdx = Index.fromOneBased(1);
        Index clientNoteIdx = Index.fromOneBased(1);
        Note clientNote1 = new Note(NOTE_CONTENT_1);
        Note clientNote2 = new Note(NOTE_CONTENT_2);
        Note newEditNote = new Note("dummy note to edit previous note");
        Client client1 = new ClientBuilder().withName("client1").build();
        model.addClient(client1);
        model.addClientNote(client1, clientNote1);
        model.addClientNote(client1, clientNote2);

        Model expectedModel = new ModelManager();
        Client client1Copy = new ClientBuilder().withName("client1").build();
        expectedModel.addClient(client1Copy);
        expectedModel.addClientNote(client1Copy, newEditNote);
        expectedModel.addClientNote(client1Copy, clientNote2);

        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_EDIT_CLIENT_NOTE_SUCCESS,
                client1.getName(), clientNote1, newEditNote));
        ClientNoteEditCommand clientNoteEditCommand = new ClientNoteEditCommand(clientIdx, clientNoteIdx, newEditNote);
        assertCommandSuccess(clientNoteEditCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_onlyEditTags_commandSuccess() {
        Index clientIdx = Index.fromOneBased(1);
        Index clientNoteIdx = Index.fromOneBased(1);
        Note clientNote1 = new Note(NOTE_CONTENT_1);
        Client client1 = new ClientBuilder().withName("client1").build();
        model.addClient(client1);
        model.addClientNote(client1, clientNote1);

        Note parsedNote = new Note("");
        Note expectedNote = new Note(NOTE_CONTENT_1);
        Tag testTag = new Tag("testTag");
        Set<Tag> expectedTagSet = new HashSet<>();
        expectedTagSet.add(testTag);

        parsedNote.setTags(expectedTagSet);
        expectedNote.setTags(expectedTagSet);
        Model expectedModel = new ModelManager();
        Client client1Copy = new ClientBuilder().withName("client1").build();
        expectedModel.addClient(client1Copy);
        expectedModel.addClientNote(client1Copy, expectedNote);

        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_EDIT_CLIENT_NOTE_SUCCESS,
                client1.getName(), clientNote1, expectedNote));
        ClientNoteEditCommand clientNoteEditCommand = new ClientNoteEditCommand(clientIdx, clientNoteIdx, parsedNote);
        assertCommandSuccess(clientNoteEditCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_notLogicallyARealEdit_commandFailure() {
        Tag testTag = new Tag("testTag");
        Set<Tag> testTagSet = new HashSet<>();
        testTagSet.add(testTag);
        Index clientIdx = Index.fromOneBased(1);
        Index clientNoteIdx = Index.fromOneBased(1);
        Note clientNote1 = new Note(NOTE_CONTENT_1);
        clientNote1.setTags(testTagSet);
        Client client1 = new ClientBuilder().withName("client1").build();
        model.addClient(client1);
        model.addClientNote(client1, clientNote1);
        Note parsedNote = new Note(NOTE_CONTENT_1);
        ClientNoteEditCommand clientNoteEditCommand = new ClientNoteEditCommand(clientIdx, clientNoteIdx, parsedNote);
        assertCommandFailure(clientNoteEditCommand, model, MESSAGE_NOT_REAL_EDIT);
    }

    @Test
    public void execute_editedClientNoteAlreadyInModel_commandFailure() {
        Index clientIdx = Index.fromOneBased(1);
        Index clientNoteIdx = Index.fromOneBased(1);
        Note clientNote1 = new Note(NOTE_CONTENT_1);
        Client client1 = new ClientBuilder().withName("client1").build();
        model.addClient(client1);
        model.addClientNote(client1, clientNote1);
        Note clientNote2 = new Note(NOTE_CONTENT_2);
        model.addClientNote(client1, clientNote2);
        ClientNoteEditCommand clientNoteEditCommand = new ClientNoteEditCommand(clientIdx, clientNoteIdx, clientNote2);
        assertCommandFailure(clientNoteEditCommand, model,
                ClientNoteEditCommand.MESSAGE_DUPLICATE_CLIENT_NOTE_AFTER_EDIT);
    }

    @Test
    public void execute_editExistingUntaggedNoteToAddTag_discardsDefaultUntaggedTag() {
        Tag oldTag = Tag.UNTAGGED;
        Set<Tag> oldTagSet = new HashSet<>();
        oldTagSet.add(oldTag);
        Note oldClientNote = new Note(NOTE_CONTENT_1);
        oldClientNote.setTags(oldTagSet);

        Tag newTag = new Tag("FreshTag");
        Set<Tag> newTagSet = new HashSet<>();
        newTagSet.add(newTag);
        Note newClientNote = new Note(NOTE_CONTENT_2);
        newClientNote.setTags(newTagSet);

        Client client = new ClientBuilder().withName("client1").build();
        model.addClient(client);
        model.addClientNote(client, oldClientNote);

        Model expectedModel = new ModelManager();
        Client clientCopy = new ClientBuilder().withName("client1").build();
        expectedModel.addClient(clientCopy);
        expectedModel.addClientNote(clientCopy, newClientNote);

        Index clientIdx = Index.fromOneBased(1);
        Index clientNoteIdx = Index.fromOneBased(1);
        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_EDIT_CLIENT_NOTE_SUCCESS,
                client.getName(), oldClientNote, newClientNote));
        ClientNoteEditCommand clientNoteEditCommand = new ClientNoteEditCommand(clientIdx,
                clientNoteIdx, newClientNote);
        assertCommandSuccess(clientNoteEditCommand, model, expectedResult, expectedModel);
        assert model.hasClientNote(client, newClientNote);
        Note resultNote = model.getSortedFilteredClientNotesList().get(0);
        assertFalse(resultNote.getTags().contains(Tag.UNTAGGED)); // default tag has been removed successfully
    }

    @Test
    public void execute_validClientIdxValidNoteIdxNewTaggedNote_preservesTagHistory() {
        Tag oldTag = new Tag("historicalTag");
        Tag newTag = new Tag("newTag");
        Set<Tag> newTagSet = new HashSet<>();
        newTagSet.add(newTag);
        Set<Tag> oldTagSet = new HashSet<>();
        oldTagSet.add(oldTag);
        Set<Tag> expectedTags = new HashSet<>(oldTagSet); // expected to preserve old tags and have new tags
        expectedTags.add(newTag);
        Note oldClientNote = new Note(NOTE_CONTENT_1);
        oldClientNote.setTags(oldTagSet);
        Note clientNote2 = new Note(NOTE_CONTENT_2);
        Note newClientNote = new Note("dummy note to edit previous note");
        newClientNote.setTags(newTagSet);
        Client client1 = new ClientBuilder().withName("client1").build();
        model.addClient(client1);
        model.addClientNote(client1, oldClientNote);
        model.addClientNote(client1, clientNote2);
        Model expectedModel = new ModelManager();
        Client client1Copy = new ClientBuilder().withName("client1").build();
        expectedModel.addClient(client1Copy);
        newClientNote.setTags(expectedTags); // containing old and new tags
        expectedModel.addClientNote(client1Copy, newClientNote);
        expectedModel.addClientNote(client1Copy, clientNote2);

        Index clientIdx = Index.fromOneBased(1);
        Index clientNoteIdx = Index.fromOneBased(1);
        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_EDIT_CLIENT_NOTE_SUCCESS,
                client1.getName(), oldClientNote, newClientNote));
        ClientNoteEditCommand clientNoteEditCommand = new ClientNoteEditCommand(clientIdx,
                clientNoteIdx, newClientNote);
        assertCommandSuccess(clientNoteEditCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void testEquals() {
        Note testNote = new Note("need for speed");
        ClientNoteEditCommand clientNoteEditCommand1 = new ClientNoteEditCommand(Index.fromOneBased(1),
                Index.fromOneBased(1), testNote);
        ClientNoteEditCommand clientNoteEditCommand1Duplicate = new ClientNoteEditCommand(Index.fromOneBased(1),
                Index.fromOneBased(1), testNote);
        ClientNoteEditCommand clientNoteEditCommandClient2 = new ClientNoteEditCommand(Index.fromOneBased(2),
                Index.fromOneBased(1), testNote);
        ClientNoteEditCommand clientNoteEditCommand1ClientNote2 = new ClientNoteEditCommand(Index.fromOneBased(1),
                Index.fromOneBased(2), testNote);

        // basic equals tests
        basicEqualsTests(clientNoteEditCommand1);

        // same values -> returns true
        assertTrue(clientNoteEditCommand1.equals(clientNoteEditCommand1Duplicate));

        // diff values (diff client) --> returns false
        assertFalse(clientNoteEditCommand1.equals(clientNoteEditCommandClient2));

        // diff values (diff client note) --> returns false
        assertFalse(clientNoteEditCommand1.equals(clientNoteEditCommand1ClientNote2));
    }

}
