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
import seedu.address.model.client.SuggestionType;

/**
 * Suggests a list of clients based on the specified suggestion types
 */
public class SuggestCommand extends Command {

    public static final String COMMAND_WORD = "suggest";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Suggests a list of all clients based on the "
            + "suggestion types specified in the arguments.\n"
            + "Parameters: " + PREFIX_SUGGEST + "SUGGESTION_TYPE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SUGGEST + SuggestionType.BY_FREQUENCY + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SUGGEST + SuggestionType.BY_AVAILABLE + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SUGGEST + SuggestionType.BY_CONTRACT;
    public static final String MESSAGE_SUGGEST_SUCCESS = "Showing clients based on the following "
            + "suggestion criteria:\n";

    public final String suggestTypeDescriptions;

    private final Set<SuggestionType> suggestionTypeOrderedSet;
    private final Predicate<Client> suggestionTypePredicate;
    private final Comparator<Client> suggestionTypeCombinedComparator;

    /**
     * Initializes SuggestCommand with a SuggestionType
     */
    public SuggestCommand(Set<SuggestionType> suggestionTypeOrderedSet) {
        requireNonNull(suggestionTypeOrderedSet);
        this.suggestionTypeOrderedSet = suggestionTypeOrderedSet;
        suggestTypeDescriptions = suggestionTypeOrderedSet.stream().map(SuggestionType::getDescription)
                .collect(Collectors.joining("\n"));
        this.suggestionTypePredicate = getCombinedPredicate(suggestionTypeOrderedSet);
        this.suggestionTypeCombinedComparator = getCombinedComparator(suggestionTypeOrderedSet);
    }

    /**
     * Given an ordered set of suggestion types, returns a combined predicate that returns true only if the client
     * passed in passes all the suggestion types' predicates.
     */
    private static Predicate<Client> getCombinedPredicate(Set<SuggestionType> suggestionTypeOrderedSet) {
        return suggestionTypeOrderedSet.stream()
                .map(SuggestionType::getSuggestionPredicate)
                .reduce(client -> true, (p1, p2) -> client -> p1.test(client) && p2.test(client));
    }

    /**
     * Given an ordered set of suggestion types, returns a combined comparator that combines the suggestion types'
     * comparators. Ordering of comparisons will be the same as the ordering of the ordered set passed in.
     */
    private static Comparator<Client> getCombinedComparator(Set<SuggestionType> suggestionTypeOrderedSet) {
        List<Comparator<Client>> suggestionTypeComparatorList = suggestionTypeOrderedSet.stream()
                .map(SuggestionType::getSuggestionComparator).collect(Collectors.toList());
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
        model.updateFilteredClientList(suggestionTypePredicate);
        model.updateSortedFilteredClientList(suggestionTypeCombinedComparator);
        return new CommandResult(MESSAGE_SUGGEST_SUCCESS + suggestTypeDescriptions, true, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SuggestCommand // instanceof handles nulls
            && suggestionTypeOrderedSet.equals(((SuggestCommand) other).suggestionTypeOrderedSet)); // state check
    }
}
