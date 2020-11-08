package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTRACT_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEZONE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.TbmManager;
import seedu.address.model.client.Client;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditClientDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_COUNTRY_AMY = "SG";
    public static final String VALID_COUNTRY_BOB = "MY";
    public static final String VALID_TIMEZONE_AMY = "UTC+08:00";
    public static final String VALID_TIMEZONE_BOB = "UTC+07:00";
    public static final String VALID_CONTRACT_EXPIRY_DATE_AMY = "1-1-2022";
    public static final String VALID_CONTRACT_EXPIRY_DATE_BOB = "13-12-2021";
    public static final String VALID_LAST_MODIFIED_INSTANT_AMY = "2020-10-10T00:00:00.000000Z";
    public static final String VALID_LAST_MODIFIED_INSTANT_BOB = "2020-11-11T00:00:00.000000Z";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String COUNTRY_DESC_AMY = " " + PREFIX_COUNTRY + VALID_COUNTRY_AMY;
    public static final String COUNTRY_DESC_BOB = " " + PREFIX_COUNTRY + VALID_COUNTRY_BOB;
    public static final String TIMEZONE_DESC_AMY = " " + PREFIX_TIMEZONE + VALID_TIMEZONE_AMY;
    public static final String TIMEZONE_DESC_BOB = " " + PREFIX_TIMEZONE + VALID_TIMEZONE_BOB;
    public static final String CONTRACT_EXPIRY_DATE_DESC_AMY =
            " " + PREFIX_CONTRACT_EXPIRY_DATE + VALID_CONTRACT_EXPIRY_DATE_AMY;
    public static final String CONTRACT_EXPIRY_DATE_DESC_BOB =
            " " + PREFIX_CONTRACT_EXPIRY_DATE + VALID_CONTRACT_EXPIRY_DATE_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_COUNTRY_DESC = " " + PREFIX_COUNTRY + "ZZ"; // not a valid country code
    public static final String INVALID_TIMEZONE_DESC = " " + PREFIX_TIMEZONE + "GT+8"; // not a valid timezone input
    public static final String INVALID_CONTRACT_EXPIRY_DATE_DESC =
            " " + PREFIX_CONTRACT_EXPIRY_DATE + "31-2-2020"; // 31st of Feb is not a valid date

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";


    public static final ClientEditCommand.EditClientDescriptor DESC_AMY;
    public static final ClientEditCommand.EditClientDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditClientDescriptorBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withCountry(VALID_COUNTRY_AMY)
                .withTimezone(VALID_TIMEZONE_AMY).withContractExpiryDate(VALID_CONTRACT_EXPIRY_DATE_AMY)
                .build();
        DESC_BOB = new EditClientDescriptorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withCountry(VALID_COUNTRY_BOB)
                .withTimezone(VALID_TIMEZONE_BOB).withContractExpiryDate(VALID_CONTRACT_EXPIRY_DATE_BOB)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered client list and selected client in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TbmManager expectedTbmManager = new TbmManager(actualModel.getTbmManager());
        List<Client> expectedFilteredList = new ArrayList<>(actualModel.getSortedFilteredClientList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedTbmManager, actualModel.getTbmManager());
        assertEquals(expectedFilteredList, actualModel.getSortedFilteredClientList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the client at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showClientAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getSortedFilteredClientList().size());

        Client client = model.getSortedFilteredClientList().get(targetIndex.getZeroBased());
        final String[] splitName = client.getName().fullName.split("\\s+");
        model.updateFilteredClientList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getSortedFilteredClientList().size());
    }

}
