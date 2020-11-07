package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTRACT_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEZONE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClientEditCommand;
import seedu.address.logic.commands.ClientEditCommand.EditClientDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClientEditCommand object
 */
public class ClientEditCommandParser implements Parser<ClientEditCommand> {

    /**
     * Parses the given {@code args} in the context of the ClientEditCommand and returns a ClientEditCommand
     * object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public ClientEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_COUNTRY, PREFIX_TIMEZONE, PREFIX_CONTRACT_EXPIRY_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClientEditCommand.MESSAGE_USAGE), pe);
        }

        EditClientDescriptor editClientDescriptor = new EditClientDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editClientDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editClientDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editClientDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editClientDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_COUNTRY).isPresent()) {
            editClientDescriptor.setCountry(ParserUtil.parseCountry(argMultimap.getValue(PREFIX_COUNTRY).get()));
        }
        if (argMultimap.getValue(PREFIX_TIMEZONE).isPresent()) {
            editClientDescriptor.setTimezone(ParserUtil.parseTimezone(argMultimap.getValue(PREFIX_TIMEZONE).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTRACT_EXPIRY_DATE).isPresent()) {
            editClientDescriptor.setContractExpiryDate(
                    ParserUtil.parseContractExpiryDate(argMultimap.getValue(PREFIX_CONTRACT_EXPIRY_DATE).get()));
        }

        if (!editClientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ClientEditCommand.MESSAGE_NOT_EDITED);
        }

        return new ClientEditCommand(index, editClientDescriptor);
    }
}
