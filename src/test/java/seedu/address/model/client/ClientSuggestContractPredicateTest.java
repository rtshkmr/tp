package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TestUtil.basicEqualsTests;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class ClientSuggestContractPredicateTest {

    private final ClientSuggestContractPredicate predicate = new ClientSuggestContractPredicate();

    @Test
    public void test_clientWithNullContractExpiryDate_returnsFalse() {
        Client client = new ClientBuilder().withContractExpiryDate(ContractExpiryDate.NULL_DATE).build();
        assertFalse(predicate.test(client));
    }

    @Test
    public void test_clientWithContractExpiryDate_returnsTrue() {
        Client client = new ClientBuilder().withContractExpiryDate("9-8-2029").build();
        assertTrue(predicate.test(client));
    }

    @Test
    public void equals() {
        // basic equals tests
        basicEqualsTests(predicate);

        // same values -> returns true
        ClientSuggestContractPredicate predicateCopy = new ClientSuggestContractPredicate();
        assertTrue(predicate.equals(predicateCopy));

        // different predicate -> returns false
        assertFalse(predicate.equals(new ClientSuggestAvailabilityPredicate()));
    }

    @Test
    public void hashCode_test() {
        ClientSuggestContractPredicate predicateCopy = new ClientSuggestContractPredicate();
        // same predicate -> same hashCode
        assertEquals(predicate.hashCode(), predicateCopy.hashCode());

        // different predicate -> different hashCode
        assertNotEquals(predicate.hashCode(), new ClientSuggestAvailabilityPredicate().hashCode());
    }

}
