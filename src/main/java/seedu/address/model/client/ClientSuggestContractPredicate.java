package seedu.address.model.client;

import java.util.function.Predicate;

/**
 * Predicate to check if client contains a contract expiry date.
 */
public class ClientSuggestContractPredicate implements Predicate<Client> {
    private static final int SUGGEST_CONTRACT_PREDICATE_HASHCODE = 2;

    @Override
    public boolean test(Client client) {
        return !client.getContractExpiryDate().isNullDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClientSuggestContractPredicate); // instanceof handles nulls
    }

    @Override
    public int hashCode() {
        return SUGGEST_CONTRACT_PREDICATE_HASHCODE;
    }
}
