package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.client.UniqueClientList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagSet;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameClient comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueClientList clients;
    private final UniqueTagSet tags;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        clients = new UniqueClientList();
        tags = new UniqueTagSet();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Clients in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the client list with {@code clients} and the contents of the tag set with
     * the union over all client tags, then update client tag sets with unique tags.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        this.clients.setClients(clients);

        Set<Tag> allClientTags = new HashSet<>();
        for (Client client : clients) {
            allClientTags.addAll(client.getTags());
        }
        this.tags.setTags(allClientTags);
        for (Client client : clients) {
            replaceClientTagSet(client);
        }
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setClients(newData.getClientList());
    }

    //// client-level operations

    /**
     * Returns true if a client with the same identity as {@code client} exists in the address book.
     */
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clients.contains(client);
    }

    /**
     * Replaces the client tag set with a set of matching unique tag objects.
     */
    private void replaceClientTagSet(Client client) {
        Set<Tag> clientLocalTags = client.getTags();
        tags.addAll(clientLocalTags);
        client.replaceTags(tags.getTags(clientLocalTags));
    }

    /**
     * Replaces client tag set and adds the client to the address book.
     * The client must not already exist in the address book.
     */
    public void addClient(Client client) {
        replaceClientTagSet(client);
        clients.add(client);
    }

    /**
     * Replaces {@code editedClient} tag set and then replaces the given client {@code target} in the list
     * with {@code editedClient}.
     * {@code target} must exist in the address book.
     * The client identity of {@code editedClient} must not be the same as another existing client in the address book.
     */
    public void setClient(Client target, Client editedClient) {
        requireNonNull(editedClient);
        replaceClientTagSet(editedClient);
        clients.setClient(target, editedClient);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeClient(Client key) {
        clients.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return clients.asUnmodifiableObservableList().size() + " clients";
        // TODO: refine later
    }

    @Override
    public ObservableList<Client> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && clients.equals(((AddressBook) other).clients));
    }

    @Override
    public int hashCode() {
        return clients.hashCode();
    }
}
