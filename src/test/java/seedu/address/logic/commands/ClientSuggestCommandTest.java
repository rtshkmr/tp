package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TestUtil.basicEqualsTests;
import static seedu.address.testutil.TypicalClients.getTypicalTbmManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientSuggestionType;
import seedu.address.model.client.ContractExpiryDate;
import seedu.address.testutil.ClientBuilder;

public class ClientSuggestCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTbmManager(), new UserPrefs());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClientSuggestCommand(null));
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClientSuggestCommand(Collections.emptySet()).execute(null));
    }

    @Test
    public void execute_emptySet_success() {
        List<Client> beforeClientList = new ArrayList<>(model.getSortedFilteredClientList());
        ClientSuggestCommand clientSuggestCommand = new ClientSuggestCommand(Collections.emptySet());
        CommandResult expectedResult = new CommandResult(ClientSuggestCommand.MESSAGE_SUGGEST_SUCCESS,
                true, false, false);
        assertEquals(clientSuggestCommand.execute(model), expectedResult);
        List<Client> afterClientList = new ArrayList<>(model.getSortedFilteredClientList());
        assertEquals(beforeClientList, afterClientList);
    }

    @Test
    public void execute_filterPredicates_correctClientList() {
        Model newModel = new ModelManager();
        Client client1 = new ClientBuilder().withName("client1")
                .withContractExpiryDate(ContractExpiryDate.NULL_DATE).build();
        Client client2 = new ClientBuilder().withName("client2")
                .withContractExpiryDate("2-3-2020").withLastModifiedInstant("2020-10-11T15:18:35.617617Z").build();
        Client client3 = new ClientBuilder().withName("client3")
                .withContractExpiryDate("1-3-2020").withLastModifiedInstant("2020-10-10T16:18:35.617617Z").build();
        Client client4 = new ClientBuilder().withName("client4")
                .withContractExpiryDate("10-3-2020").withLastModifiedInstant("2020-10-11T15:18:35.617617Z").build();
        newModel.addClient(client1);
        newModel.addClient(client2);
        newModel.addClient(client3);
        newModel.addClient(client4);
        assertEquals(newModel.getSortedFilteredClientList().size(), 4);

        Set<ClientSuggestionType> clientSuggestionTypes = new LinkedHashSet<>();
        clientSuggestionTypes.add(new ClientSuggestionType(ClientSuggestionType.BY_CONTRACT));
        ClientSuggestCommand clientSuggestCommand1 = new ClientSuggestCommand(clientSuggestionTypes);
        CommandResult expectedResult1 = new CommandResult(ClientSuggestCommand.MESSAGE_SUGGEST_SUCCESS
                + ClientSuggestionType.CONTRACT_DESCRIPTION, true, false, false);
        assertEquals(clientSuggestCommand1.execute(newModel), expectedResult1);
        assertEquals(newModel.getSortedFilteredClientList().size(), 3);
        assertEquals(newModel.getSortedFilteredClientList().get(0), client3);

        clientSuggestionTypes = new LinkedHashSet<>();
        clientSuggestionTypes.add(new ClientSuggestionType(ClientSuggestionType.BY_FREQUENCY));
        clientSuggestionTypes.add(new ClientSuggestionType(ClientSuggestionType.BY_CONTRACT));
        ClientSuggestCommand clientSuggestCommand2 = new ClientSuggestCommand(clientSuggestionTypes);
        CommandResult expectedResult2 = new CommandResult(ClientSuggestCommand.MESSAGE_SUGGEST_SUCCESS
                + ClientSuggestionType.FREQUENCY_DESCRIPTION + "\n" + ClientSuggestionType.CONTRACT_DESCRIPTION,
                true, false, false);
        assertEquals(clientSuggestCommand2.execute(newModel), expectedResult2);
        assertEquals(newModel.getSortedFilteredClientList().size(), 3);
        assertEquals(newModel.getSortedFilteredClientList().get(0), client2);
    }

    @Test
    public void equals() {
        Set<ClientSuggestionType> clientSuggestionTypeSet = Set.of(
                new ClientSuggestionType(ClientSuggestionType.BY_FREQUENCY),
                new ClientSuggestionType(ClientSuggestionType.BY_CONTRACT),
                new ClientSuggestionType(ClientSuggestionType.BY_AVAILABLE));
        ClientSuggestCommand clientSuggestCommand = new ClientSuggestCommand(clientSuggestionTypeSet);

        // basic equals tests
        basicEqualsTests(clientSuggestCommand);

        // same values -> returns true
        ClientSuggestCommand clientSuggestCommandCopy = new ClientSuggestCommand(clientSuggestionTypeSet);
        assertTrue(clientSuggestCommand.equals(clientSuggestCommandCopy));

        // different suggestion type set -> returns false
        assertFalse(clientSuggestCommand.equals(new ClientSuggestCommand(Collections.emptySet())));
    }

}
