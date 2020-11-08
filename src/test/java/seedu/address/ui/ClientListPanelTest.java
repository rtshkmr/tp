package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalClients.getTypicalClients;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysClient;

import org.junit.jupiter.api.Test;

import guitests.guihandles.ClientCardHandle;
import guitests.guihandles.ClientListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.testutil.ClientBuilder;

public class ClientListPanelTest extends GuiUnitTest {

    private static final ObservableList<Client> TYPICAL_CLIENTS = FXCollections.observableList(getTypicalClients());
    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private ClientListPanelHandle clientListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_CLIENTS);

        for (int i = 0; i < TYPICAL_CLIENTS.size(); i++) {
            clientListPanelHandle.navigateToCard(TYPICAL_CLIENTS.get(i));
            Client expectedClient = TYPICAL_CLIENTS.get(i);
            ClientCardHandle actualCard = clientListPanelHandle.getClientCardHandle(i);

            assertCardDisplaysClient(expectedClient, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    /*
     * This is probably useless as TBM has almost no significant interaction with the ListView.
     * However the this test is a good benchmark should we refactor the ListView. According to
     * changes suggested in displaying notes, more performance test may be needed for this specific ListView.
     */
    /**
     * Verifies that creating and deleting large number of persons in {@code PersonListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Client> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code PersonListPanel}.
     */
    private ObservableList<Client> createBackingList(int clientCount) {
        ObservableList<Client> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < clientCount; i++) {
            Client client = new ClientBuilder().withName(i + "Kim Lim").build();
            backingList.add(client);
        }
        return backingList;
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Client> backingList) {
        ClientListPanel clientListPanel =
                new ClientListPanel(backingList);
        uiPartExtension.setUiPart(clientListPanel);

        clientListPanelHandle = new ClientListPanelHandle(getChildNode(clientListPanel.getRoot(),
                ClientListPanelHandle.CLIENT_LIST_VIEW_ID));
    }

}
