package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalTbmManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.testutil.ClientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code ClientAddCommand}.
 */
public class ClientAddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTbmManager(), new UserPrefs());
    }

    @Test
    public void execute_newClient_success() {
        Client validClient = new ClientBuilder().build();
        Model expectedModel = new ModelManager(model.getTbmManager(), new UserPrefs());
        expectedModel.addClient(validClient);
        assertCommandSuccess(new ClientAddCommand(validClient), model,
                String.format(ClientAddCommand.MESSAGE_SUCCESS, validClient), expectedModel);
    }

    @Test
    public void execute_duplicateClient_throwsCommandException() {
        Client clientInList = model.getTbmManager().getClientList().get(0);
        assertCommandFailure(new ClientAddCommand(clientInList), model, ClientAddCommand.MESSAGE_DUPLICATE_CLIENT);
    }

}
