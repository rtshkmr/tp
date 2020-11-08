package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTRACT_EXPIRY_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COUNTRY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LAST_MODIFIED_INSTANT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TestUtil.basicEqualsTests;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.BOB;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.note.Note;
import seedu.address.testutil.ClientBuilder;

public class ClientTest {

    @Test
    public void isSameClient() {
        // same object -> returns true
        assertTrue(ALICE.isSameClient(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameClient(null));

        // different phone and email -> returns false
        Client editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameClient(editedAlice));

        // different name -> returns false
        editedAlice = new ClientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameClient(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ClientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withCountry(VALID_COUNTRY_BOB).build();
        assertTrue(ALICE.isSameClient(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withCountry(VALID_COUNTRY_BOB).build();
        assertTrue(ALICE.isSameClient(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withCountry(VALID_COUNTRY_BOB)
                .withLastModifiedInstant(VALID_LAST_MODIFIED_INSTANT_BOB).build();
        assertTrue(ALICE.isSameClient(editedAlice));

        // TODO: Add tests with modified notes
    }

    @Test
    public void equals() {
        // basic equals tests
        basicEqualsTests(ALICE);

        // same values -> returns true
        Client aliceCopy = new ClientBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // different client -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Client editedAlice = new ClientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ClientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different country -> returns false
        editedAlice = new ClientBuilder(ALICE).withCountry(VALID_COUNTRY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different contract expiry date -> returns false
        editedAlice = new ClientBuilder(ALICE).withContractExpiryDate(VALID_CONTRACT_EXPIRY_DATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different last modified instant -> returns true
        editedAlice = new ClientBuilder(ALICE).withLastModifiedInstant(VALID_LAST_MODIFIED_INSTANT_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // TODO: Add tests with modified notes
    }

    @Test
    public void hashCode_test() {
        // same values -> hashCode is same
        Client aliceCopy = new ClientBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());

        // same object -> hashCode is same
        assertEquals(ALICE.hashCode(), ALICE.hashCode());

        // different client -> hashCode is different
        assertNotEquals(ALICE.hashCode(), BOB.hashCode());

        // different name -> hashCode is different
        Client editedAlice = new ClientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertNotEquals(ALICE.hashCode(), editedAlice.hashCode());

        // different phone -> hashCode is different
        editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertNotEquals(ALICE.hashCode(), editedAlice.hashCode());

        // different email -> hashCode is different
        editedAlice = new ClientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertNotEquals(ALICE.hashCode(), editedAlice.hashCode());

        // different address -> hashCode is different
        editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertNotEquals(ALICE.hashCode(), editedAlice.hashCode());

        // different country -> hashCode is different
        editedAlice = new ClientBuilder(ALICE).withCountry(VALID_COUNTRY_BOB).build();
        assertNotEquals(ALICE.hashCode(), editedAlice.hashCode());

        // different contract expiry date -> hashCode is different
        editedAlice = new ClientBuilder(ALICE).withContractExpiryDate(VALID_CONTRACT_EXPIRY_DATE_BOB).build();
        assertNotEquals(ALICE.hashCode(), editedAlice.hashCode());

        // different last modified instant -> hashCode is same
        editedAlice = new ClientBuilder(ALICE).withLastModifiedInstant(VALID_LAST_MODIFIED_INSTANT_BOB).build();
        assertEquals(ALICE.hashCode(), editedAlice.hashCode());

        // TODO: Add tests with modified notes
    }

    @Test
    public void getClientNotes_modifyUnmodifiableSet_throwsException() {
        Client client = new ClientBuilder(ALICE).build();
        Note clientNote = new Note("hell yes");
        client.addClientNote(clientNote);
        assertTrue(client.hasClientNote(clientNote));
        List<Note> currentNotes = client.getClientNotesAsUnmodifiableList();
        assertThrows(UnsupportedOperationException.class, () -> currentNotes.add(new Note("nice lahh")));
    }

    @Test
    public void getClientNotesAsList_modifyUnmodifiableList_throwsException() {
        Client client = new ClientBuilder(ALICE).build();
        client.addClientNote(new Note("hell yes"));
        List<Note> currentNotes = client.getClientNotesAsUnmodifiableList();
        assertThrows(UnsupportedOperationException.class, () -> currentNotes.add(new Note("nice lahh")));
    }

    @Test
    public void deleteClientNote_deleteExistingNote_noteIsDeletedWithoutException() {
        Client client = new ClientBuilder(ALICE).build();
        Note clientNote = new Note("hell yes");
        client.addClientNote(clientNote);
        assertTrue(client.hasClientNote(clientNote));
        assertDoesNotThrow(() -> client.deleteClientNote(clientNote));
        client.deleteClientNote(clientNote);
        assertFalse(client.hasClientNote(clientNote));
    }

    @Test
    public void editClientNote_editExistingNote_noteIsEditedWithoutException() {
        Client client = new ClientBuilder(ALICE).build();
        Note oldNote = new Note("hell yes");
        Note newNote = new Note("this be a new note");
        client.addClientNote(oldNote);
        assertTrue(client.hasClientNote(oldNote));
        assertFalse(client.hasClientNote(newNote));
        assertDoesNotThrow(() -> client.editClientNote(oldNote, newNote));
        assertFalse(client.hasClientNote(oldNote));
    }

    @Test
    public void toString_positiveTest_correctStringDisplayed() {
        Client client = new ClientBuilder(ALICE).build();
        Note clientNote = new Note("hell yes");
        client.addClientNote(clientNote);
        String expected = "Alice Pauline Phone: 94351253 Email: alice@example.com Address: 123, Jurong West Ave 6,"
                + " #08-111 Country: Singapore (SG) Timezone: UTC+08:00 Contract Expiry Date: 1-4-2021";
        assertEquals(expected, client.toString());
    }


}
