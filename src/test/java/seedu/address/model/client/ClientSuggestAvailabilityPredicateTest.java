package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TestUtil.basicEqualsTests;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class ClientSuggestAvailabilityPredicateTest {

    @Test
    public void test_clientAt17_false() {
        TimezoneStub fivePm = new TimezoneStub("UTC+08:00", 17);
        Client client = new ClientBuilder().withTimezone(fivePm).build();
        ClientSuggestAvailabilityPredicate clientSuggestAvailabilityPredicate =
                new ClientSuggestAvailabilityPredicate();
        assertFalse(clientSuggestAvailabilityPredicate.test(client));
    }

    @Test
    public void test_clientAt18_true() {
        TimezoneStub sixPm = new TimezoneStub("UTC+08:00", 18);
        Client client = new ClientBuilder().withTimezone(sixPm).build();
        ClientSuggestAvailabilityPredicate clientSuggestAvailabilityPredicate =
                new ClientSuggestAvailabilityPredicate();
        assertTrue(clientSuggestAvailabilityPredicate.test(client));
    }

    @Test
    public void test_clientAt21_true() {
        TimezoneStub tenPm = new TimezoneStub("UTC+08:00", 21);
        Client client = new ClientBuilder().withTimezone(tenPm).build();
        ClientSuggestAvailabilityPredicate clientSuggestAvailabilityPredicate =
                new ClientSuggestAvailabilityPredicate();
        assertTrue(clientSuggestAvailabilityPredicate.test(client));
    }

    @Test
    public void test_clientAt22_false() {
        TimezoneStub elevenPm = new TimezoneStub("UTC+08:00", 22);
        Client client = new ClientBuilder().withTimezone(elevenPm).build();
        ClientSuggestAvailabilityPredicate clientSuggestAvailabilityPredicate =
                new ClientSuggestAvailabilityPredicate();
        assertFalse(clientSuggestAvailabilityPredicate.test(client));
    }

    @Test
    public void equals() {
        ClientSuggestAvailabilityPredicate clientSuggestAvailabilityPredicate =
                new ClientSuggestAvailabilityPredicate();

        // basic equals tests
        basicEqualsTests(clientSuggestAvailabilityPredicate);

        // different object, same fields -> true
        assertTrue(clientSuggestAvailabilityPredicate.equals(new ClientSuggestAvailabilityPredicate()));
    }

    private static class TimezoneStub extends Timezone {
        private final int testCurrHour;

        TimezoneStub(String val, int testCurrHour) {
            super(val);
            this.testCurrHour = testCurrHour;
        }

        @Override
        public int getCurrHourInTimezone() {
            return testCurrHour;
        }
    }
}
