package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.client.Client;
import seedu.address.model.note.CountryNote;
import seedu.address.model.note.Note;
import seedu.address.model.note.TagNoteMap;
import seedu.address.model.tag.Tag;
import seedu.address.model.widget.WidgetModel;

/**
 * Represents the in-memory model of the TbmManager data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TbmManager tbmManager;
    private final UserPrefs userPrefs;
    private final FilteredList<Client> filteredClients;
    private final SortedList<Client> sortedFilteredClients;
    private final Comparator<Client> defaultClientListOrder;
    private final FilteredList<CountryNote> filteredCountryNotes;
    private final SortedList<CountryNote> sortedFilteredCountryNotes;
    private final WidgetModel widget;
    private final TagNoteMap tagNoteMap;

    private boolean countryNotesListPanelIsVisible = false;

    /**
     * Initializes a ModelManager with the given tbmManager and userPrefs.
     */
    public ModelManager(ReadOnlyTbmManager tbmManager, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(tbmManager, userPrefs);

        logger.fine("Initializing with TBM Manager: " + tbmManager + " and user prefs " + userPrefs);

        this.tbmManager = new TbmManager(tbmManager);
        this.userPrefs = new UserPrefs(userPrefs);
        this.widget = WidgetModel.initWidget();
        filteredClients = new FilteredList<>(this.tbmManager.getClientList());
        sortedFilteredClients = new SortedList<>(filteredClients);
        defaultClientListOrder = Comparator.comparing(filteredClients::indexOf);
        filteredCountryNotes = new FilteredList<>(this.tbmManager.getCountryNoteList());
        sortedFilteredCountryNotes = new SortedList<>(filteredCountryNotes,
                Comparator.comparing(CountryNote::getCountry).thenComparingInt(filteredCountryNotes::indexOf));
        this.tagNoteMap = new TagNoteMap();
        this.initialiseTagNoteMap(); // init TagNoteMap upon construction of ModelManager
    }

    /**
     * Initializes a ModelManager with a new {@code TbmManager} and {@code UserPrefs}.
     */
    public ModelManager() {
        this(new TbmManager(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTbmManagerFilePath() {
        return userPrefs.getTbmManagerFilePath();
    }

    @Override
    public void setTbmManagerFilePath(Path tbmManagerFilePath) {
        requireNonNull(tbmManagerFilePath);
        userPrefs.setTbmManagerFilePath(tbmManagerFilePath);
    }

    //=========== TbmManager ================================================================================

    @Override
    public ReadOnlyTbmManager getTbmManager() {
        return tbmManager;
    }

    @Override
    public void setTbmManager(ReadOnlyTbmManager tbmManager) {
        this.tbmManager.resetData(tbmManager);
    }

    @Override
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return tbmManager.hasClient(client);
    }

    @Override
    public void deleteClient(Client target) {
        tbmManager.removeClient(target);
    }

    @Override
    public void setWidgetClient(Client client) {
        widget.setWidgetClient(client);
    }

    @Override
    public Client getWidgetClient() {
        return widget.getWidgetClient();
    }

    @Override
    public void addClient(Client client) {
        tbmManager.addClient(client);
        updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
        List<Note> clientNotes = new ArrayList<>(client.getClientNotesAsUnmodifiableList());
        for (Note note : clientNotes) {
            Set<Tag> tags = note.getTags();
            updateTagNoteMapWithNote(tags, note);
        }
    }

    @Override
    public void setClient(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);
        tbmManager.setClient(target, editedClient);
        initialiseTagNoteMap();
    }

    @Override
    public boolean hasCountryNote(CountryNote countryNote) {
        requireNonNull(countryNote);
        return tbmManager.hasCountryNote(countryNote);
    }

    @Override
    public boolean hasClientNote(Client target, Note clientNote) {
        requireAllNonNull(target, clientNote);
        assert tbmManager.hasClient(target) : "tbmManager not synced with modelManager; missing client";
        return target.hasClientNote(clientNote);
    }

    @Override
    public void addCountryNote(CountryNote countryNote) {
        requireNonNull(countryNote);
        Set<Tag> newTags = countryNote.getTags();
        updateTagNoteMapWithNote(newTags, countryNote);
        tbmManager.addCountryNote(countryNote);
    }

    @Override
    public void deleteCountryNote(CountryNote countryNoteToDelete) {
        requireNonNull(countryNoteToDelete);
        tagNoteMap.deleteNote(countryNoteToDelete);
        tbmManager.deleteCountryNote(countryNoteToDelete);
    }

    @Override
    public void setCountryNote(CountryNote oldCountryNote, CountryNote newCountryNote) {
        requireAllNonNull(oldCountryNote, newCountryNote);
        this.tagNoteMap.editNote(oldCountryNote, newCountryNote);
        tbmManager.setCountryNote(oldCountryNote, newCountryNote);
    }

    @Override
    public void addClientNote(Client target, Note clientNote) {
        requireAllNonNull(target, clientNote);
        target.addClientNote(clientNote);
        Set<Tag> newTags = clientNote.getTags();
        updateTagNoteMapWithNote(newTags, clientNote);
    }

    @Override
    public void updateTagNoteMapWithNote(Set<Tag> newTags, Note note) {
        this.tagNoteMap.addTagsForNote(newTags, note);
    }

    @Override
    public void initialiseTagNoteMap() {
        this.tagNoteMap.initTagNoteMapFromClients(this.tbmManager.getClientList());
        this.tagNoteMap.initTagNoteMapFromCountryNotes(new ArrayList<>(this.tbmManager.getCountryNoteList()));
    }

    public TagNoteMap getTagNoteMap() {
        return this.tagNoteMap;
    }

    @Override
    public void deleteClientNote(Client associatedClient, Note noteToDelete) {
        requireAllNonNull(associatedClient, noteToDelete);
        this.tagNoteMap.deleteNote(noteToDelete);
        associatedClient.deleteClientNote(noteToDelete);
    }

    @Override
    public void editClientNote(Client associatedClient, Note noteToEdit, Note newNote) {
        requireAllNonNull(associatedClient, noteToEdit);
        this.tagNoteMap.editNote(noteToEdit, newNote);
        associatedClient.editClientNote(noteToEdit, newNote);
    }

    //=========== Filtered Client List Accessors =============================================================

    @Override
    public ObservableList<Client> getSortedFilteredClientList() {
        return sortedFilteredClients;
    }

    @Override
    public ObservableList<Note> getSortedFilteredClientNotesList() {
        List<Note> clientNotes = new ArrayList<>();
        for (Client client : getSortedFilteredClientList()) {
            clientNotes.addAll(client.getClientNotesAsUnmodifiableList());
        }
        return FXCollections.observableList(clientNotes);
    }

    @Override
    public void updateFilteredClientList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredClients.setPredicate(predicate);
    }

    @Override
    public void updateSortedFilteredClientList(Comparator<Client> comparator) {
        requireNonNull(comparator);
        sortedFilteredClients.setComparator(comparator);
    }

    @Override
    public void resetSortedFilteredClientListOrder() {
        sortedFilteredClients.setComparator(defaultClientListOrder);
    }

    @Override
    public void refreshSortedFilteredClientListOrder() {
        Predicate<? super Client> predicate = filteredClients.getPredicate();
        Comparator<? super Client> comparator = sortedFilteredClients.getComparator();
        sortedFilteredClients.setComparator(defaultClientListOrder);

        filteredClients.setPredicate(predicate);
        sortedFilteredClients.setComparator(comparator);
    }

    @Override
    public ObservableList<CountryNote> getSortedFilteredCountryNoteList() {
        return sortedFilteredCountryNotes;
    }

    @Override
    public void updateFilteredCountryNoteList(Predicate<CountryNote> predicate) {
        requireNonNull(predicate);
        filteredCountryNotes.setPredicate(predicate);
    }

    @Override
    public void setCountryNotesListPanelIsVisible(boolean isVisible) {
        countryNotesListPanelIsVisible = isVisible;
    }

    @Override
    public boolean getCountryNotesListPanelIsVisible() {
        return countryNotesListPanelIsVisible;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return tbmManager.equals(other.tbmManager)
                && userPrefs.equals(other.userPrefs)
                && sortedFilteredClients.equals(other.sortedFilteredClients)
                && tagNoteMap.equals(other.tagNoteMap);
    }

}
