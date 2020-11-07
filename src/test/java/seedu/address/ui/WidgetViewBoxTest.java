package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TestUtil.basicEqualsTests;
import static seedu.address.testutil.TypicalClients.BENSON;

import org.junit.jupiter.api.Test;

import guitests.guihandles.WidgetViewBoxHandle;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.note.Note;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.TypicalClients;

/**
 * GUI unit test for WidgetViewBox. Test done to ensure the integrity of content displayed does not regress in future
 * refactoring.
 */
public class WidgetViewBoxTest extends GuiUnitTest {

    private static final Client AMY = TypicalClients.AMY;

    /**
     * This test checks your time, country and timezone.
     * The main usage of this test is as a manual eyeballing test.
     */
    @Test
    public void displayDefault() {
        WidgetViewBox widgetViewBox = WidgetViewBox.init();

        uiPartExtension.setUiPart(widgetViewBox);

        /*
         * Check if the country is correct via eyeballing.
         * A bug raised here https://bugs.java.com/bugdatabase/view_bug.do?bug_id=7082429.
         */
        guiRobot.pauseForHuman();
        assertDoesNotThrow(() -> guiRobot.lookup("#tbmLogo").query());
    }

    @Test
    public void updateContentToEmptyObject_updatesProperly_newObjectIsSuccessfullyUpdated() {
        // This is primarily testing that the textclock does not show itself during an update.
        WidgetViewBox widgetViewBox = WidgetViewBox.init();
        widgetViewBox.updateClientDisplay(AMY);
        Client benson = new ClientBuilder(BENSON).build();
        widgetViewBox.updateClientDisplay(benson);
        uiPartExtension.setUiPart(widgetViewBox);

        assertViewBoxDisplay(widgetViewBox, benson);
    }

    @Test
    public void observableListEditClient_updatesViewBoxDisplayToEditedClient() {
        Client benson = new ClientBuilder(BENSON).build();
        ObservableList<Client> clientList = FXCollections.observableArrayList(benson);
        WidgetViewBox widgetViewBox = new WidgetViewBox(clientList);
        widgetViewBox.updateClientDisplay(benson);
        Client editedBenson = new ClientBuilder(benson).withName("newName").withEmail("new@email.com").build();
        clientList.set(0, editedBenson);
        uiPartExtension.setUiPart(widgetViewBox);
        assertViewBoxDisplay(widgetViewBox, editedBenson);
    }

    @Test
    public void observableListAddClientNote_updatesViewBoxDisplayToReflectAddedNote() {
        Client benson = new ClientBuilder(BENSON).build();
        ObservableList<Client> clientList = FXCollections.observableArrayList(client ->
                new Observable[] { client.getClientNotesAsObservableList() });
        clientList.add(benson);
        WidgetViewBox widgetViewBox = new WidgetViewBox(clientList);
        widgetViewBox.updateClientDisplay(benson);
        benson.addClientNote(new Note("new note"));
        benson.addClientNote(new Note("another new note"));
        uiPartExtension.setUiPart(widgetViewBox);
        guiRobot.pauseForHuman();
        assertViewBoxDisplay(widgetViewBox, benson);
    }

    @Test
    public void observableListDeleteClient_resetsViewBoxDisplayToDefault() {
        Client benson = new ClientBuilder(BENSON).build();
        ObservableList<Client> clientList = FXCollections.observableArrayList(benson);
        WidgetViewBox widgetViewBox = new WidgetViewBox(clientList);
        widgetViewBox.updateClientDisplay(benson);
        clientList.remove(benson);
        uiPartExtension.setUiPart(widgetViewBox);
        assertDoesNotThrow(() -> guiRobot.lookup("#tbmLogo").query());
    }

    @Test
    public void observableListAddClient_noChangeToViewBoxDisplay() {
        Client benson = new ClientBuilder(BENSON).build();
        ObservableList<Client> clientList = FXCollections.observableArrayList(benson);
        WidgetViewBox widgetViewBox = new WidgetViewBox(clientList);
        widgetViewBox.updateClientDisplay(benson);
        clientList.add(0, new ClientBuilder().withName("another client").build());
        uiPartExtension.setUiPart(widgetViewBox);
        assertViewBoxDisplay(widgetViewBox, benson);
    }

    @Test
    public void observableListPermutate_noChangeToViewBoxDisplay() {
        Client benson = new ClientBuilder(BENSON).build();
        ObservableList<Client> clientList = FXCollections.observableArrayList(benson);
        WidgetViewBox widgetViewBox = new WidgetViewBox(clientList);
        widgetViewBox.updateClientDisplay(benson);
        clientList.add(0, new ClientBuilder().withName("another client").build());
        clientList.add(new ClientBuilder().withName("third client").build());
        FXCollections.reverse(clientList);
        uiPartExtension.setUiPart(widgetViewBox);
        assertViewBoxDisplay(widgetViewBox, benson);
    }

    @Test
    public void equals() {
        WidgetViewBox obj1 = WidgetViewBox.init();
        obj1.updateClientDisplay(AMY);
        WidgetViewBox obj2 = WidgetViewBox.init();
        obj2.updateClientDisplay(AMY);
        WidgetViewBox obj3 = WidgetViewBox.init();
        obj3.updateClientDisplay(BENSON);

        // basic equals tests
        basicEqualsTests(obj1);

        // same values -> returns true
        assertTrue(obj1.equals(obj2));

        // different values -> returns false
        assertFalse(obj1.equals(obj3));
    }

    private void assertViewBoxDisplay(WidgetViewBox widgetViewBox, Client expectedClient) {
        guiRobot.pauseForHuman();

        WidgetViewBoxHandle widgetViewBoxHandle = new WidgetViewBoxHandle(widgetViewBox.getRoot());

        // Testing using the equals method of handler.
        assertTrue(widgetViewBoxHandle.equals(expectedClient));
    }

}
