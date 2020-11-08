package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TestUtil.basicEqualsTests;
import static seedu.address.testutil.TypicalClients.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTbmManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.TbmManager;
import seedu.address.model.client.Client;
import seedu.address.model.note.CountryNote;
import seedu.address.model.note.Note;
import seedu.address.model.note.TagNoteMap;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ClientBuilder;

public class ClientAddCommandTest {

    private static final Index HUGE_OUT_OF_INDEX_VALUE = Index.fromOneBased(10000000);

    @Test
    public void constructor_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClientAddCommand(null));
    }

    @Test
    public void execute_clientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingClientAdded modelStub = new ModelStubAcceptingClientAdded();
        Client validClient = new ClientBuilder().build();

        CommandResult commandResult = new ClientAddCommand(validClient).execute(modelStub);

        assertEquals(String.format(ClientAddCommand.MESSAGE_SUCCESS, validClient), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validClient), modelStub.clientsAdded);
    }

    @Test
    public void execute_duplicateClient_throwsCommandException() {
        Client validClient = new ClientBuilder().build();
        ClientAddCommand addCommand = new ClientAddCommand(validClient);
        ModelStub modelStub = new ModelStubWithClient(validClient);

        assertThrows(CommandException.class,
                ClientAddCommand.MESSAGE_DUPLICATE_CLIENT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidClientIndex_throwsCommandException() {
        Note clientNote = new Note("dummyNote");
        ClientNoteAddCommand failingCommand = new ClientNoteAddCommand(HUGE_OUT_OF_INDEX_VALUE, clientNote);
        assertThrows(CommandException.class, () -> failingCommand.execute(new ModelManager()));
    }

    @Test
    public void execute_duplicateClientNote_throwsCommandException() throws CommandException {
        Note clientNote = new Note("dummyNote");
        ModelManager modelManager = new ModelManager();
        Client client = new ClientBuilder(ALICE).build();
        client.addClientNote(clientNote);
        modelManager.addClient(client);
        modelManager.addClientNote(client, clientNote);
        ClientNoteAddCommand command = new ClientNoteAddCommand(Index.fromOneBased(1), clientNote);
        assertThrows(CommandException.class, () -> command.execute(modelManager));
    }

    @Test
    public void equals() {
        Client alice = new ClientBuilder().withName("Alice").build();
        Client bob = new ClientBuilder().withName("Bob").build();
        ClientAddCommand addAliceCommand = new ClientAddCommand(alice);
        ClientAddCommand addBobCommand = new ClientAddCommand(bob);

        // basic equals tests
        basicEqualsTests(addAliceCommand);

        // same client -> returns true
        ClientAddCommand addAliceCommandCopy = new ClientAddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different client -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateTagNoteMapWithNote(Set<Tag> newTags, Note note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCountryNotesListPanelIsVisible(boolean isVisible) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean getCountryNotesListPanelIsVisible() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTbmManagerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTbmManagerFilePath(Path tbmManagerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTbmManager(ReadOnlyTbmManager tbmManager) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTbmManager getTbmManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteClient(Client target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWidgetClient(Client client) {
            throw new AssertionError("this method should not be called.");
        }

        @Override
        public Client getWidgetClient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClient(Client target, Client editedClient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCountryNote(CountryNote countryNote) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClientNote(Client client, Note clientNote) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCountryNote(CountryNote countryNote) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCountryNote(CountryNote oldCountryNote, CountryNote newCountryNote) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClientNote(Client client, Note clientNote) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteClientNote(Client associatedClient, Note noteToDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editClientNote(Client associatedClient, Note noteToEdit, Note newNote) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCountryNote(CountryNote countryNoteToDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getSortedFilteredClientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Note> getSortedFilteredClientNotesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredClientList(Predicate<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedFilteredClientList(Comparator<Client> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetSortedFilteredClientListOrder() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void refreshSortedFilteredClientListOrder() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<CountryNote> getSortedFilteredCountryNoteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCountryNoteList(Predicate<CountryNote> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void initialiseTagNoteMap() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TagNoteMap getTagNoteMap() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single client.
     */
    private class ModelStubWithClient extends ModelStub {
        private final Client client;

        ModelStubWithClient(Client client) {
            requireNonNull(client);
            this.client = client;
        }

        @Override
        public boolean hasClient(Client client) {
            requireNonNull(client);
            return this.client.isSameClient(client);
        }
    }

    /**
     * A Model stub that always accept the client being added.
     */
    private class ModelStubAcceptingClientAdded extends ModelStub {
        final ArrayList<Client> clientsAdded = new ArrayList<>();

        @Override
        public boolean hasClient(Client client) {
            requireNonNull(client);
            return clientsAdded.stream().anyMatch(client::isSameClient);
        }

        @Override
        public void addClient(Client client) {
            requireNonNull(client);
            clientsAdded.add(client);
        }

        @Override
        public ReadOnlyTbmManager getTbmManager() {
            return new TbmManager();
        }
    }

}
