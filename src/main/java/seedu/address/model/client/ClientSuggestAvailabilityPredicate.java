package seedu.address.model.client;

import java.util.function.Predicate;

/**
 * Predicate to check for client availability in their timezone
 */
public class ClientSuggestAvailabilityPredicate implements Predicate<Client> {
    private static final int AVAILABLE_STARTING_HOUR = 18;
    private static final int AVAILABLE_ENDING_HOUR = 22;
    private static final int CLIENT_SUGGEST_AVAILABILITY_PREDICATE_HASHCODE = 1;

    @Override
    public boolean test(Client client) {
        int currHour = client.getTimezone().getCurrHourInTimezone();
        return (currHour >= AVAILABLE_STARTING_HOUR && currHour < AVAILABLE_ENDING_HOUR);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ClientSuggestAvailabilityPredicate); // instanceof handles nulls
    }

    @Override
    public int hashCode() {
        return CLIENT_SUGGEST_AVAILABILITY_PREDICATE_HASHCODE;
    }
}
