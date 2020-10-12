package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClientAddCommand;
import seedu.address.logic.commands.ClientDeleteCommand;
import seedu.address.logic.commands.ClientEditCommand;
import seedu.address.logic.commands.ClientFindCommand;
import seedu.address.logic.commands.ClientNoteAddCommand;
import seedu.address.logic.commands.ClientViewCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CountryFilterCommand;
import seedu.address.logic.commands.CountryNoteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Different command type separators
     */
    private static final String CLIENT_TYPE = "client";
    private static final String COUNTRY_TYPE = "country";

    /**
     * Used for initial separation of command type and rest of command.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandType>\\S+)(?<restOfCommand>.*)");
    private static final Pattern SECONDARY_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandType = matcher.group("commandType");
        final String restOfCommand = matcher.group("restOfCommand");
        switch (commandType) {
        case CLIENT_TYPE:
            return parseClientCommands(restOfCommand);

        case COUNTRY_TYPE:
            return parseCountryCommands(restOfCommand);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }


    /**
     * Parses input given that command is of COUNTRY_TYPE (starts with "country")
     *
     * @param input user input with "client" stripped
     * @return command relating to client functions
     * @throws ParseException if input does not conform to expected format
     */
    private Command parseCountryCommands(String input) throws ParseException {
        final Matcher secondaryMatcher = SECONDARY_COMMAND_FORMAT.matcher(input.trim());
        if (!secondaryMatcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        String commandWord = secondaryMatcher.group("commandWord");
        final String arguments = secondaryMatcher.group("arguments");

        commandWord = COUNTRY_TYPE + " " + commandWord;
        switch (commandWord) {
        case CountryNoteCommand.COMMAND_WORD:
            return new CountryNoteCommandParser().parse(arguments);

        case CountryFilterCommand.COMMAND_WORD:
            return new CountryFilterCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses input given that command is of CLIENT_TYPE (starts with "client")
     *
     * @param input user input with "client" stripped
     * @return command relating to client functions
     * @throws ParseException if input does not conform to expected format
     */
    private Command parseClientCommands(String input) throws ParseException {
        final Matcher secondaryMatcher = SECONDARY_COMMAND_FORMAT.matcher(input.trim());
        if (!secondaryMatcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        String commandWord = secondaryMatcher.group("commandWord");
        String arguments = secondaryMatcher.group("arguments");
        commandWord = CLIENT_TYPE + " " + commandWord;
        // todo: abstract away this parsing logic or use better regex matcher
        StringTokenizer stringTokenizer = new StringTokenizer(arguments);
        String nextWord = stringTokenizer.nextToken();
        if (nextWord.equals("add")) {
            // gather rest of the arguments:
            StringBuilder stringBuilder = new StringBuilder();
            while (stringTokenizer.hasMoreTokens()) {
                stringBuilder.append(stringTokenizer.nextToken()).append(" ");
            }

            commandWord += (" " + nextWord);
            arguments = " " + stringBuilder.toString();
        }

        switch (commandWord) {
        case ClientNoteAddCommand.COMMAND_WORD:
            return new ClientNoteAddCommandParser().parse(arguments);
        case ClientAddCommand.COMMAND_WORD:
            return new ClientAddCommandParser().parse(arguments);

        case ClientEditCommand.COMMAND_WORD:
            return new ClientEditCommandParser().parse(arguments);

        case ClientDeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClientFindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ClientViewCommand.COMMAND_WORD:
            return new ClientViewCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
