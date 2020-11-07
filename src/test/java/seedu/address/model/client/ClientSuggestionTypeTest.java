package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TestUtil.basicEqualsTests;

import org.junit.jupiter.api.Test;

public class ClientSuggestionTypeTest {

    @Test
    public void constructor() {
        assertThrows(NullPointerException.class, () -> new ClientSuggestionType(null));
        assertThrows(IllegalArgumentException.class, () -> new ClientSuggestionType("availability"));
        assertThrows(IllegalArgumentException.class, () -> new ClientSuggestionType("contractor"));
        assertThrows(IllegalArgumentException.class, () -> new ClientSuggestionType("by/contract"));
        assertThrows(IllegalArgumentException.class, () -> new ClientSuggestionType(""));
        assertDoesNotThrow(() -> new ClientSuggestionType(ClientSuggestionType.BY_FREQUENCY));
    }

    @Test
    public void getSuggestionPredicate_byAvailable_suggestAvailabilityPredicate() {
        ClientSuggestionType available = new ClientSuggestionType(ClientSuggestionType.BY_AVAILABLE);
        assertTrue(available.getClientSuggestionPredicate() instanceof ClientSuggestAvailabilityPredicate);
    }

    @Test
    public void getSuggestionPredicate_byContract_suggestContractPredicate() {
        ClientSuggestionType contract = new ClientSuggestionType(ClientSuggestionType.BY_CONTRACT);
        assertTrue(contract.getClientSuggestionPredicate() instanceof ClientSuggestContractPredicate);
    }

    @Test
    public void getDescription_returnsCorrectDescription() {
        assertEquals(new ClientSuggestionType(ClientSuggestionType.BY_CONTRACT).getDescription(),
                ClientSuggestionType.CONTRACT_DESCRIPTION);
        assertEquals(new ClientSuggestionType(ClientSuggestionType.BY_FREQUENCY).getDescription(),
                ClientSuggestionType.FREQUENCY_DESCRIPTION);
        assertEquals(new ClientSuggestionType(ClientSuggestionType.BY_AVAILABLE).getDescription(),
                ClientSuggestionType.AVAILABLE_DESCRIPTION);
    }

    @Test
    public void isValidSuggestionType_validSuggestionType_returnsTrue() {
        assertTrue(ClientSuggestionType.isValidClientSuggestionType(ClientSuggestionType.BY_AVAILABLE));
        assertTrue(ClientSuggestionType.isValidClientSuggestionType(ClientSuggestionType.BY_CONTRACT));
        assertTrue(ClientSuggestionType.isValidClientSuggestionType(ClientSuggestionType.BY_FREQUENCY));
    }

    @Test
    public void isValidSuggestionType_invalidSuggestionType_returnsFalse() {
        assertFalse(ClientSuggestionType.isValidClientSuggestionType("contractor"));
        assertFalse(ClientSuggestionType.isValidClientSuggestionType("availability"));
        assertFalse(ClientSuggestionType.isValidClientSuggestionType(""));
        assertThrows(NullPointerException.class, () -> ClientSuggestionType.isValidClientSuggestionType(null));
    }

    @Test
    public void equals() {
        ClientSuggestionType available = new ClientSuggestionType(ClientSuggestionType.BY_AVAILABLE);

        // basic equals tests
        basicEqualsTests(available);

        // same suggestion type -> returns true
        assertTrue(available.equals(new ClientSuggestionType(ClientSuggestionType.BY_AVAILABLE)));

        // different suggestion type -> returns false
        assertFalse(available.equals(new ClientSuggestionType(ClientSuggestionType.BY_CONTRACT)));
    }

    @Test
    public void hashCode_test() {
        ClientSuggestionType available = new ClientSuggestionType(ClientSuggestionType.BY_AVAILABLE);
        // same suggestion type -> returns same hashcode
        assertEquals(available.hashCode(), new ClientSuggestionType(ClientSuggestionType.BY_AVAILABLE).hashCode());

        // different suggestion type -> returns false
        assertNotEquals(available.hashCode(), new ClientSuggestionType(ClientSuggestionType.BY_CONTRACT).hashCode());
    }

    @Test
    public void toString_test() {
        ClientSuggestionType available = new ClientSuggestionType(ClientSuggestionType.BY_AVAILABLE);
        ClientSuggestionType contract = new ClientSuggestionType(ClientSuggestionType.BY_CONTRACT);
        ClientSuggestionType frequency = new ClientSuggestionType(ClientSuggestionType.BY_FREQUENCY);

        assertEquals(available.toString(), "[available]");
        assertEquals(contract.toString(), "[contract]");
        assertEquals(frequency.toString(), "[frequency]");
    }

}
