package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClientSuggestCommand;
import seedu.address.model.client.ClientSuggestionType;

public class ClientClientSuggestCommandParserTest {

    private final ClientSuggestCommandParser parser = new ClientSuggestCommandParser();

    @Test
    public void parse_validArgs_returnsSuggestCommand() {
        Set<ClientSuggestionType> clientSuggestionTypes = new LinkedHashSet<>();
        clientSuggestionTypes.add(new ClientSuggestionType(ClientSuggestionType.BY_AVAILABLE));
        assertParseSuccess(parser, " by/available", new ClientSuggestCommand(clientSuggestionTypes));

        clientSuggestionTypes.add(new ClientSuggestionType(ClientSuggestionType.BY_CONTRACT));
        assertParseSuccess(parser, " by/available by/contract", new ClientSuggestCommand(clientSuggestionTypes));

        /* TODO: Add this back if frequency gets a predicate
        clientSuggestionTypes.add(new ClientSuggestionType(ClientSuggestionType.BY_FREQUENCY));
        assertParseSuccess(parser, " by/available by/contract by/frequency",
               new ClientSuggestCommand(clientSuggestionTypes));*/
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " by/availability", ClientSuggestionType.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " by/", ClientSuggestionType.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " available",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClientSuggestCommand.MESSAGE_USAGE));
    }

}
