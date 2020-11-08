package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.country.Country;
import seedu.address.model.note.Note;

/**
 * Represents a Client in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Country country;
    private final Timezone timezone;
    private final ContractExpiryDate contractExpiryDate;
    private final ObservableList<Note> clientNotes = FXCollections.observableArrayList(new ArrayList<>());

    // Metadata field for client suggestions (not exposed to user).
    // This field will be updated on every creation of a client instance.
    // This field will also be updated on every mutable note update.
    private LastModifiedInstant lastModifiedInstant;

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, Phone phone, Email email, Address address, Country country, Timezone timezone,
            ContractExpiryDate contractExpiryDate, LastModifiedInstant lastModifiedInstant) {
        requireAllNonNull(name, phone, email, address, country, timezone, contractExpiryDate);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.country = country;
        this.timezone = timezone;
        this.contractExpiryDate = contractExpiryDate;
        this.lastModifiedInstant = lastModifiedInstant;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Country getCountry() {
        return country;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public ContractExpiryDate getContractExpiryDate() {
        return contractExpiryDate;
    }

    public LastModifiedInstant getLastModifiedInstant() {
        return lastModifiedInstant;
    }

    /**
     * Gets the list of client notes associated with this client as an unmodifiable list.
     *
     * @return An unmodifiable list of client notes associated with this client.
     */
    public List<Note> getClientNotesAsUnmodifiableList() {
        return Collections.unmodifiableList(this.clientNotes);
    }

    /**
     * Gets the observable list of client notes associated with this client.
     *
     * @return The observable list of client notes associated with this client.
     */
    public ObservableList<Note> getClientNotesAsObservableList() {
        return clientNotes;
    }

    /**
     * Adds a client note for this client.
     *
     * @param clientNote The client note to be added.
     */
    public void addClientNote(Note clientNote) {
        requireNonNull(clientNote);
        this.clientNotes.add(clientNote);
        this.lastModifiedInstant = new LastModifiedInstant();
    }

    /**
     * Deletes a specific client note from associated notes for this client.
     *
     * @param clientNote the clientNote to be deleted.
     */
    public void deleteClientNote(Note clientNote) {
        requireNonNull(clientNote);
        this.clientNotes.remove(clientNote);
        this.lastModifiedInstant = new LastModifiedInstant();
    }

    /**
     * Edits a specific client note from associated notes for this client.
     *
     * @param clientNote the clientNote to be deleted.
     * @param newNote the new clientnote to replace the existing one.
     */
    public void editClientNote(Note clientNote, Note newNote) {
        requireNonNull(clientNote);
        int targetIdx = clientNotes.indexOf(clientNote);
        clientNotes.set(targetIdx, newNote);
        assert !clientNotes.contains(clientNote);
        this.lastModifiedInstant = new LastModifiedInstant();
    }

    /**
     * Checks whether the client has a given note in collection or not.
     *
     * @param clientNote The note, to be checked if client has it in collection.
     * @return True if Client has that note in the collection.
     */
    public boolean hasClientNote(Note clientNote) {
        return clientNotes.contains(clientNote);
    }

    /**
     * Returns true if both clients of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two clients.
     */
    public boolean isSameClient(Client otherClient) {
        if (otherClient == this) {
            return true;
        }

        return otherClient != null
                && otherClient.getName().equals(getName())
                && (otherClient.getPhone().equals(getPhone()) || otherClient.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both clients have the same identity and data fields.
     * This defines a stronger notion of equality between two clients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Client)) {
            return false;
        }

        Client otherClient = (Client) other;
        return otherClient.getName().equals(getName())
                && otherClient.getPhone().equals(getPhone())
                && otherClient.getEmail().equals(getEmail())
                && otherClient.getAddress().equals(getAddress())
                && otherClient.getCountry().equals(getCountry())
                && otherClient.getTimezone().equals(getTimezone())
                && otherClient.getContractExpiryDate().equals(getContractExpiryDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, country, timezone, contractExpiryDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Country: ")
                .append(getCountry())
                .append(" Timezone: ")
                .append(getTimezone())
                .append(" Contract Expiry Date: ")
                .append(getContractExpiryDate());

        return builder.toString();
    }

}
