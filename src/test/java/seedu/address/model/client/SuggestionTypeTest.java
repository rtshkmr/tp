package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TestUtil.basicEqualsTests;

import org.junit.jupiter.api.Test;

public class SuggestionTypeTest {

    @Test
    public void constructor() {
        assertThrows(NullPointerException.class, () -> new SuggestionType(null));
        assertThrows(IllegalArgumentException.class, () -> new SuggestionType("availability"));
        assertThrows(IllegalArgumentException.class, () -> new SuggestionType("contractor"));
        assertThrows(IllegalArgumentException.class, () -> new SuggestionType("by/contract"));
        assertThrows(IllegalArgumentException.class, () -> new SuggestionType(""));
        assertDoesNotThrow(() -> new SuggestionType(SuggestionType.BY_FREQUENCY));
    }

    @Test
    public void getSuggestionPredicate_byAvailable_suggestAvailabilityPredicate() {
        SuggestionType available = new SuggestionType(SuggestionType.BY_AVAILABLE);
        assertTrue(available.getSuggestionPredicate() instanceof SuggestAvailabilityPredicate);
    }

    @Test
    public void getSuggestionPredicate_byContract_suggestContractPredicate() {
        SuggestionType contract = new SuggestionType(SuggestionType.BY_CONTRACT);
        assertTrue(contract.getSuggestionPredicate() instanceof SuggestContractPredicate);
    }

    @Test
    public void getDescription_returnsCorrectDescription() {
        assertEquals(new SuggestionType(SuggestionType.BY_CONTRACT).getDescription(),
                SuggestionType.CONTRACT_DESCRIPTION);
        assertEquals(new SuggestionType(SuggestionType.BY_FREQUENCY).getDescription(),
                SuggestionType.FREQUENCY_DESCRIPTION);
        assertEquals(new SuggestionType(SuggestionType.BY_AVAILABLE).getDescription(),
                SuggestionType.AVAILABLE_DESCRIPTION);
    }

    @Test
    public void isValidSuggestionType_validSuggestionType_returnsTrue() {
        assertTrue(SuggestionType.isValidSuggestionType(SuggestionType.BY_AVAILABLE));
        assertTrue(SuggestionType.isValidSuggestionType(SuggestionType.BY_CONTRACT));
        assertTrue(SuggestionType.isValidSuggestionType(SuggestionType.BY_FREQUENCY));
    }

    @Test
    public void isValidSuggestionType_invalidSuggestionType_returnsFalse() {
        assertFalse(SuggestionType.isValidSuggestionType("contractor"));
        assertFalse(SuggestionType.isValidSuggestionType("availability"));
        assertFalse(SuggestionType.isValidSuggestionType(""));
        assertThrows(NullPointerException.class, () -> SuggestionType.isValidSuggestionType(null));
    }

    @Test
    public void equals() {
        SuggestionType available = new SuggestionType(SuggestionType.BY_AVAILABLE);

        // basic equals tests
        basicEqualsTests(available);

        // same suggestion type -> returns true
        assertTrue(available.equals(new SuggestionType(SuggestionType.BY_AVAILABLE)));

        // different suggestion type -> returns false
        assertFalse(available.equals(new SuggestionType(SuggestionType.BY_CONTRACT)));
    }

    @Test
    public void hashCode_test() {
        SuggestionType available = new SuggestionType(SuggestionType.BY_AVAILABLE);
        // same suggestion type -> returns same hashcode
        assertEquals(available.hashCode(), new SuggestionType(SuggestionType.BY_AVAILABLE).hashCode());

        // different suggestion type -> returns false
        assertNotEquals(available.hashCode(), new SuggestionType(SuggestionType.BY_CONTRACT).hashCode());
    }

    @Test
    public void toString_test() {
        SuggestionType available = new SuggestionType(SuggestionType.BY_AVAILABLE);
        SuggestionType contract = new SuggestionType(SuggestionType.BY_CONTRACT);
        SuggestionType frequency = new SuggestionType(SuggestionType.BY_FREQUENCY);

        assertEquals(available.toString(), "[available]");
        assertEquals(contract.toString(), "[contract]");
        assertEquals(frequency.toString(), "[frequency]");
    }

}
