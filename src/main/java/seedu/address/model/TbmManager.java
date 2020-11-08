package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.client.UniqueClientList;
import seedu.address.model.country.CountryNotesManager;
import seedu.address.model.note.CountryNote;

/**
 * Wraps all data at TbmManager level
 * Duplicates are not allowed (by .isSameClient comparison)
 */
public class TbmManager implements ReadOnlyTbmManager {

    private final UniqueClientList clients;
    private final CountryNotesManager countryNotesManager;
    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        clients = new UniqueClientList();
        countryNotesManager = new CountryNotesManager();
    }

    /**
     * Creates an empty TbmManager without any existing data.
     */
    public TbmManager() {}

    /**
     * Creates an TbmManager using the data in the {@code toBeCopied}
     */
    public TbmManager(ReadOnlyTbmManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the client list with {@code clients} and the contents of the tag set with
     * the union over all client tags, then update client tag sets with unique tags.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        this.clients.setClients(clients);
    }

    /**
     * Replaces all country notes in TbmManager with the given list of country notes.
     *
     * @param countryNotes The given list of country notes.
     */
    public void setCountryNotes(List<CountryNote> countryNotes) {
        countryNotesManager.clear();
        for (CountryNote countryNote: countryNotes) {
            countryNotesManager.addCountryNote(countryNote);
        }
    }

    /**
     * Resets the existing data of this {@code TbmManager} with {@code newData}.
     */
    public void resetData(ReadOnlyTbmManager newData) {
        requireNonNull(newData);
        setClients(newData.getClientList());
        setCountryNotes(newData.getCountryNoteList());
    }

    //// client-level operations

    /**
     * Returns true if a client with the same identity as {@code client} exists in TManager.
     */
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clients.contains(client);
    }

    /**
     * Adds the client to TbmManager.
     * The client must not already exist in TbmManager.
     */
    public void addClient(Client client) {
        clients.add(client);
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in TbmManager.
     * The client identity of {@code editedClient} must not be the same as another existing client in TbmManager.
     */
    public void setClient(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);
        clients.setClient(target, editedClient);
    }

    /**
     * Removes {@code key} from this {@code TbmManager}. {@code key} must exist in TbmManager.
     */
    public void removeClient(Client key) {
        clients.remove(key);
    }

    /**
     * Checks whether the given country has the given {@code countryNote}.
     *
     * @param countryNote The given country note.
     * @return True if the given country has the given {@code countryNote}.
     */
    public boolean hasCountryNote(CountryNote countryNote) {
        requireNonNull(countryNote);
        return countryNotesManager.hasCountryNote(countryNote);
    }

    /**
     * Adds the given {@code countryNote} into the notes manager for country.
     *
     * @param countryNote The given country note
     */
    public void addCountryNote(CountryNote countryNote) {
        requireNonNull(countryNote);
        countryNotesManager.addCountryNote(countryNote);
    }

    /**
     * Replaces the old country note with the new country note.
     *
     * @param oldCountryNote The old country note.
     * @param newCountryNote The new country note that replaces the old country note.
     */
    public void setCountryNote(CountryNote oldCountryNote, CountryNote newCountryNote) {
        requireAllNonNull(oldCountryNote, newCountryNote);
        countryNotesManager.setCountryNote(oldCountryNote, newCountryNote);
    }

    /**
     * Deletes the given country note.
     *
     * @param countryNoteToDelete The country note to delete.
     */
    public void deleteCountryNote(CountryNote countryNoteToDelete) {
        requireNonNull(countryNoteToDelete);
        countryNotesManager.deleteCountryNote(countryNoteToDelete);
    }

    @Override
    public String toString() {
        return clients.asUnmodifiableObservableList().size() + " clients";
    }

    @Override
    public ObservableList<Client> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    /**
     * Gets the list of country notes in TBM as an unmodifiable observable list.
     *
     * @return The unmodifiable observable list of country notes in TBM.
     */
    public ObservableList<CountryNote> getCountryNoteList() {
        return countryNotesManager.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TbmManager // instanceof handles nulls
                && clients.equals(((TbmManager) other).clients)
                && countryNotesManager.equals(((TbmManager) other).countryNotesManager)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(clients, countryNotesManager);
    }
}
