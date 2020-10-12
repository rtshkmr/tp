package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.country.Country;
import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;

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
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Note> clientNotes = new LinkedHashSet<>(); // todo: initialise this iff client has notes

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, Phone phone, Email email, Address address, Country country, Timezone timezone,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.country = country;
        this.timezone = timezone;
        this.tags.addAll(tags);
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Replace current tag objects with tag objects from {@code UniqueTagSet}.
     */
    public void replaceTags(Set<Tag> uniqueTags) {
        tags.clear();
        tags.addAll(uniqueTags);
    }

    /**
     * Gets the list of client notes associated with this client.
     *
     * @return The list of client notes associated with this client.
     */
    public Set<Note> getClientNotes() {
        return Collections.unmodifiableSet(this.clientNotes);
    }

    /**
     * Adds a client note for this client.
     *
     * @param clientNote The client note to be added.
     */
    public void addClientNote(Note clientNote) {
        requireNonNull(clientNote);
        this.clientNotes.add(clientNote);
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
                && otherClient.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, country, timezone, tags);
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
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
