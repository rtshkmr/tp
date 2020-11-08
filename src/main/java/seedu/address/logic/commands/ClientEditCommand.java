package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTRACT_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEZONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.ContractExpiryDate;
import seedu.address.model.client.Email;
import seedu.address.model.client.LastModifiedInstant;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Timezone;
import seedu.address.model.country.Country;
import seedu.address.model.note.Note;

/**
 * Edits the details of an existing client in the address book.
 */
public class ClientEditCommand extends Command {

    public static final String COMMAND_WORD = "client edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the client identified "
            + "by the index number used in the displayed client list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: CLIENT_INDEX (must be a positive integer) "
            + "(" + PREFIX_NAME + "NAME) "
            + "(" + PREFIX_PHONE + "PHONE) "
            + "(" + PREFIX_EMAIL + "EMAIL) "
            + "(" + PREFIX_ADDRESS + "ADDRESS) "
            + "(" + PREFIX_COUNTRY + "COUNTRY_CODE) "
            + "(" + PREFIX_TIMEZONE + "TIMEZONE) "
            + "(" + PREFIX_CONTRACT_EXPIRY_DATE + "CONTRACT_EXPIRY_DATE)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_CLIENT_SUCCESS = "Edited Client: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CLIENT = "This client already exists!";

    private final Index clientIndex;
    private final EditClientDescriptor editClientDescriptor;

    /**
     * Creates a ClientEditCommand to edit the client at the specified {@code clientIndex} in the displayed client list,
     * using details from the supplied {@code editClientDescriptor}.
     *
     * @param clientIndex of the client in the displayed client list to edit
     * @param editClientDescriptor details to edit the client with
     */
    public ClientEditCommand(Index clientIndex, EditClientDescriptor editClientDescriptor) {
        requireNonNull(clientIndex);
        requireNonNull(editClientDescriptor);

        this.clientIndex = clientIndex;
        this.editClientDescriptor = new EditClientDescriptor(editClientDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getSortedFilteredClientList();

        if (clientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(clientIndex.getZeroBased());
        Client editedClient = createEditedClient(clientToEdit, editClientDescriptor);

        if (!clientToEdit.isSameClient(editedClient) && model.hasClient(editedClient)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }

        model.setClient(clientToEdit, editedClient);
        model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_CLIENT_SUCCESS, editedClient));
    }

    /**
     * Creates and returns a {@code Client} with the details of {@code clientToEdit}
     * edited with {@code editClientDescriptor}.
     */
    private static Client createEditedClient(Client clientToEdit, EditClientDescriptor editClientDescriptor) {
        assert clientToEdit != null;

        // Update the changed client fields, and transfer over the unchanged ones
        Name updatedName = editClientDescriptor.getName().orElse(clientToEdit.getName());
        Phone updatedPhone = editClientDescriptor.getPhone().orElse(clientToEdit.getPhone());
        Email updatedEmail = editClientDescriptor.getEmail().orElse(clientToEdit.getEmail());
        Address updatedAddress = editClientDescriptor.getAddress().orElse(clientToEdit.getAddress());
        Country updatedCountry = editClientDescriptor.getCountry().orElse(clientToEdit.getCountry());
        Timezone updatedTimezone = editClientDescriptor.getTimezone().orElse(clientToEdit.getTimezone());
        ContractExpiryDate updatedContractExpiryDate =
                editClientDescriptor.getContractExpiryDate().orElse(clientToEdit.getContractExpiryDate());
        LastModifiedInstant updatedLastModifiedInstant = new LastModifiedInstant();
        ArrayList<Note> retainedClientNotes = new ArrayList<>(clientToEdit.getClientNotesAsUnmodifiableList());

        // Create the new, edited client
        Client newClient = new Client(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedCountry,
                updatedTimezone, updatedContractExpiryDate, updatedLastModifiedInstant);
        retainedClientNotes.forEach(newClient::addClientNote);

        return newClient;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientEditCommand)) {
            return false;
        }

        // state check
        ClientEditCommand e = (ClientEditCommand) other;
        return clientIndex.equals(e.clientIndex)
                && editClientDescriptor.equals(e.editClientDescriptor);
    }

    /**
     * Stores the details to edit the client with. Each non-empty field value will replace the
     * corresponding field value of the client.
     */
    public static class EditClientDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Country country;
        private Timezone timezone;
        private ContractExpiryDate contractExpiryDate;

        /**
         * Constructs an EditClientDescriptor. It is an empty constructor that takes in no arguments.
         */
        public EditClientDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditClientDescriptor(EditClientDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setCountry(toCopy.country);
            setTimezone(toCopy.timezone);
            setContractExpiryDate(toCopy.contractExpiryDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, country, timezone, contractExpiryDate);
        }

        /**
         * Sets the name of this {@code EditClientDescriptor} to the supplied {@code name}.
         */
        public void setName(Name name) {
            this.name = name;
        }

        /**
         * Returns the name of this {@code EditClientDescriptor}.
         */
        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets the phone number of this {@code EditClientDescriptor} to the supplied {@code phone}.
         */
        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        /**
         * Returns the phone number of this {@code EditClientDescriptor}.
         */
        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        /**
         * Sets the email address of this {@code EditClientDescriptor} to the supplied {@code email}.
         */
        public void setEmail(Email email) {
            this.email = email;
        }

        /**
         * Returns the email address of this {@code EditClientDescriptor}.
         */
        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        /**
         * Sets the address of this {@code EditClientDescriptor} to the supplied {@code address}.
         */
        public void setAddress(Address address) {
            this.address = address;
        }

        /**
         * Returns the address of this {@code EditClientDescriptor}.
         */
        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets the country of this {@code EditClientDescriptor} to the supplied {@code country}.
         */
        public void setCountry(Country country) {
            this.country = country;
        }

        /**
         * Returns the country of this {@code EditClientDescriptor}.
         */
        public Optional<Country> getCountry() {
            return Optional.ofNullable(country);
        }

        /**
         * Sets the timezone of this {@code EditClientDescriptor} to the supplied {@code timezone}.
         */
        public void setTimezone(Timezone timezone) {
            this.timezone = timezone;
        }

        /**
         * Returns the timezone of this {@code EditClientDescriptor}.
         */
        public Optional<Timezone> getTimezone() {
            return Optional.ofNullable(timezone);
        }

        /**
         * Sets the contract expiry date of this {@code EditClientDescriptor} to the supplied
         * {@code contractExpiryDate}.
         */
        public void setContractExpiryDate(ContractExpiryDate contractExpiryDate) {
            this.contractExpiryDate = contractExpiryDate;
        }

        /**
         * Returns the contract expiry date of this {@code EditClientDescriptor}.
         */
        public Optional<ContractExpiryDate> getContractExpiryDate() {
            return Optional.ofNullable(contractExpiryDate);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditClientDescriptor)) {
                return false;
            }

            // state check
            EditClientDescriptor e = (EditClientDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getCountry().equals(e.getCountry())
                    && getTimezone().equals(e.getTimezone())
                    && getContractExpiryDate().equals(e.getContractExpiryDate());
        }
    }
}
