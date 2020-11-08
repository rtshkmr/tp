package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTRACT_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEZONE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;

/**
 * Adds a client to the address book.
 */
public class ClientAddCommand extends Command {

    public static final String COMMAND_WORD = "client add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a client. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_COUNTRY + "COUNTRY_CODE "
            + PREFIX_TIMEZONE + "TIMEZONE "
            + "[" + PREFIX_CONTRACT_EXPIRY_DATE + "CONTRACT_EXPIRY_DATE" + "]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_COUNTRY + "SG "
            + PREFIX_TIMEZONE + "UTC+08:00 "
            + PREFIX_CONTRACT_EXPIRY_DATE + "30-1-2023 ";

    public static final String MESSAGE_SUCCESS = "New client added: %1$s";
    public static final String MESSAGE_DUPLICATE_CLIENT = "This client already exists!";

    private final Client toAdd;

    /**
     * Creates a ClientAddCommand to add the specified {@code Client}.
     */
    public ClientAddCommand(Client client) {
        requireNonNull(client);
        toAdd = client;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasClient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }

        model.addClient(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClientAddCommand // instanceof handles nulls
                && toAdd.equals(((ClientAddCommand) other).toAdd));
    }
}
