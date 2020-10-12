package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;

import seedu.address.model.Model;
import seedu.address.model.client.ClientCountryMatchesInputCountryPredicate;

/**
 * Filters the client list based on their country.
 */
public class CountryFilterCommand extends Command {

    public static final String COMMAND_WORD = "country filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all clients whose country matches "
            + "the specified country-code and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_COUNTRY + "COUNTRY_CODE\n"
            + "Example: " + COMMAND_WORD + " c/SG";
    public static final String MESSAGE_FILTER_CLIENT_BY_COUNTRY_SUCCESS = "Showing %1$s clients that are from: %2$s";

    private final ClientCountryMatchesInputCountryPredicate predicate;

    /**
     * Initializes CountryFilterCommand with a predicate of Client.
     *
     * @param predicate The Country predicate to filter clients by.
     */
    public CountryFilterCommand(ClientCountryMatchesInputCountryPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredClientList(predicate);
        return new CommandResult(
                String.format(MESSAGE_FILTER_CLIENT_BY_COUNTRY_SUCCESS, model.getFilteredClientList().size(),
                        predicate.getInputCountryStringRepresentation()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CountryFilterCommand)) {
            return false;
        }

        // state check
        CountryFilterCommand c = (CountryFilterCommand) other;

        return predicate.equals(c.predicate);
    }
}
