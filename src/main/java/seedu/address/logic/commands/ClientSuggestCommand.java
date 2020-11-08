package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUGGEST;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientSuggestionType;

/**
 * Suggests a list of clients based on the specified client suggestion types.
 */
public class ClientSuggestCommand extends Command {

    public static final String COMMAND_WORD = "client suggest";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Suggests a list of all clients based on the "
            + "suggestion types specified in the arguments.\n"
            + "Parameters: " + PREFIX_SUGGEST + "SUGGESTION_TYPE "
            + "[" + PREFIX_SUGGEST + "SUGGESTION_TYPE]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SUGGEST + ClientSuggestionType.BY_FREQUENCY + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SUGGEST + ClientSuggestionType.BY_AVAILABLE + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SUGGEST + ClientSuggestionType.BY_CONTRACT;
    public static final String MESSAGE_SUGGEST_SUCCESS = "Showing clients based on the following "
            + "suggestion criteria:\n";

    public final String clientSuggestTypeDescriptions;

    private final Set<ClientSuggestionType> clientSuggestionTypeOrderedSet;
    private final Predicate<Client> clientSuggestionTypePredicate;
    private final Comparator<Client> clientSuggestionTypeCombinedComparator;

    /**
     * Initializes ClientSuggestCommand with an ordered set of client suggestion types.
     */
    public ClientSuggestCommand(Set<ClientSuggestionType> clientSuggestionTypeOrderedSet) {
        requireNonNull(clientSuggestionTypeOrderedSet);
        this.clientSuggestionTypeOrderedSet = clientSuggestionTypeOrderedSet;
        clientSuggestTypeDescriptions = clientSuggestionTypeOrderedSet.stream()
                .map(ClientSuggestionType::getDescription).collect(Collectors.joining("\n"));
        this.clientSuggestionTypePredicate = getCombinedPredicate(clientSuggestionTypeOrderedSet);
        this.clientSuggestionTypeCombinedComparator = getCombinedComparator(clientSuggestionTypeOrderedSet);
    }

    /**
     * Given an ordered set of client suggestion types, returns a combined predicate that returns true if and only if
     * the client passed in passes all the client suggestion types' predicates.
     */
    private static Predicate<Client> getCombinedPredicate(Set<ClientSuggestionType> clientSuggestionTypeOrderedSet) {
        return clientSuggestionTypeOrderedSet.stream()
                .map(ClientSuggestionType::getClientSuggestionPredicate)
                .reduce(client -> true, (p1, p2) -> client -> p1.test(client) && p2.test(client));
    }

    /**
     * Given an ordered set of client suggestion types, returns a combined comparator that combines the
     * client suggestion types' comparators.
     * Ordering of comparisons will be the same as the ordering of the ordered set passed in.
     */
    private static Comparator<Client> getCombinedComparator(Set<ClientSuggestionType> clientSuggestionTypeOrderedSet) {
        List<Comparator<Client>> suggestionTypeComparatorList = clientSuggestionTypeOrderedSet.stream()
                .map(ClientSuggestionType::getClientSuggestionComparator).collect(Collectors.toList());
        return (client1, client2) -> {
            for (Comparator<Client> comparator: suggestionTypeComparatorList) {
                int compareResult = comparator.compare(client1, client2);
                if (compareResult != 0) {
                    return compareResult;
                }
            }
            return 0;
        };
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredClientList(clientSuggestionTypePredicate);
        model.updateSortedFilteredClientList(clientSuggestionTypeCombinedComparator);
        return new CommandResult(MESSAGE_SUGGEST_SUCCESS + clientSuggestTypeDescriptions,
                true, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ClientSuggestCommand // instanceof handles nulls
            // state check
            && clientSuggestionTypeOrderedSet.equals(((ClientSuggestCommand) other).clientSuggestionTypeOrderedSet));
    }
}
