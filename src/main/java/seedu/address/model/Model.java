package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.client.Client;
import seedu.address.model.note.CountryNote;
import seedu.address.model.note.Note;
import seedu.address.model.note.TagNoteMap;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluates to true.
     */
    Predicate<Client> PREDICATE_SHOW_ALL_CLIENTS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' TBM Manager file path.
     */
    Path getTbmManagerFilePath();

    /**
     * Sets the user prefs' TBM Manager file path.
     */
    void setTbmManagerFilePath(Path tbmManagerFilePath);

    /**
     * Returns the TbmManager
     */
    ReadOnlyTbmManager getTbmManager();

    /**
     * Replaces TBM Manager data with the data in {@code tbmManager}.
     */
    void setTbmManager(ReadOnlyTbmManager tbmManager);

    /**
     * Returns true if a client with the same identity as {@code client} exists in TBM Manager.
     */
    boolean hasClient(Client client);

    /**
     * Deletes the given client. The client must exist in TBM Manager.
     */
    void deleteClient(Client target);

    /**
     * Sets the widget box contents to the given client.
     */
    void setWidgetClient(Client client);

    /**
     * Retrieves the client to be displayed in the widget.
     *
     * @return Client.
     */
    Client getWidgetClient();

    /**
     * Adds the given {@code client}.
     * The client must not already exist in TBM Manager.
     */
    void addClient(Client client);

    /**
     * Replaces the given client {@code target} with {@code editedClient}. {@code target} must exist in the
     * TBM Manager. The client identity of {@code editedClient} must not be the same as another existing
     * client in TBM Manager.
     */
    void setClient(Client target, Client editedClient);

    /**
     * Returns true if the {@code countryNote} already exists in TBM.
     */
    boolean hasCountryNote(CountryNote countryNote);

    /**
     * Adds the given country note to TBM.
     */
    void addCountryNote(CountryNote countryNote);

    /**
     * Deletes the given country note in TBM.
     */
    void deleteCountryNote(CountryNote countryNoteToDelete);

    /**
     * Replaces the given old country note with the given new country note.
     */
    void setCountryNote(CountryNote oldCountryNote, CountryNote newCountryNote);

    /**
     * Returns true if {@code client} contains the {@code clientNote} specified.
     */
    boolean hasClientNote(Client client, Note clientNote);

    /**
     * Adds the given client note to the given client, updating relevant maps while at it.
     */
    void addClientNote(Client client, Note clientNote);

    /**
     * Deletes a note associated to a particular client.
     *
     * @param associatedClient Client that the note belongs to.
     * @param noteToDelete     Note to be deleted.
     */
    void deleteClientNote(Client associatedClient, Note noteToDelete);

    /**
     * Edits a note associated to a particular client.
     *
     * @param associatedClient Client that the note belongs to.
     * @param noteToEdit       Note to be edited.
     * @param  newNote         New note to be used.
     */
    void editClientNote(Client associatedClient, Note noteToEdit, Note newNote);

    /**
     * Returns an unmodifiable view of the filtered client list.
     */
    ObservableList<Client> getSortedFilteredClientList();

    /**
     * Returns an unmodifiable view of the filtered client notes list.
     */
    ObservableList<Note> getSortedFilteredClientNotesList();

    /**
     * Updates the filter of the filtered client list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredClientList(Predicate<Client> predicate);

    /**
     * Updates the order of the filtered client list using the given {@code comparator}.
     *
     * @throws NullPointerException if {@code comparator} is null.
     */
    void updateSortedFilteredClientList(Comparator<Client> comparator);

    /**
     * Resets the order of the filtered client list to the default order.
     */
    void resetSortedFilteredClientListOrder();

    /**
     * Refreshes the order of the filtered client list to the existing order.
     * Used in client note add/edit/delete where updates are mutable.
     */
    void refreshSortedFilteredClientListOrder();


    /**
     * Returns an unmodifiable view of the filtered country notes list.
     */
    ObservableList<CountryNote> getSortedFilteredCountryNoteList();

    /**
     * Updates the filter of the filtered country notes list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCountryNoteList(Predicate<CountryNote> predicate);

    /**
     * Initialises {@code TagNoteMap} from client notes and country notes.
     */
    void initialiseTagNoteMap();

    /**
     * Returns the tag note map.
     */
    TagNoteMap getTagNoteMap();

    /**
     * Updates its {@code TagNoteMap} to map a note with a new set of tags.
     *
     * @param newTags The tags to associate with a particular note.
     * @param note    The note to associate the tag with.
     */
    void updateTagNoteMapWithNote(Set<Tag> newTags, Note note);

    /**
     * Sets the boolean that corresponds to whether the country notes list panel is currently visible.
     */
    void setCountryNotesListPanelIsVisible(boolean isVisible);

    /**
     * Returns true if the country notes list panel is currently visible, false otherwise.
     */
    boolean getCountryNotesListPanelIsVisible();
}
