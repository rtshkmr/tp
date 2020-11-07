package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX = "The client index provided is invalid";
    public static final String MESSAGE_INVALID_COUNTRY_NOTE_DISPLAYED_INDEX =
            "The country note index provided is invalid";
    public static final String MESSAGE_CLIENTS_LISTED_OVERVIEW = "%1$d client%2$s listed!";
    public static final String MESSAGE_INVALID_CLIENT_NOTE_DISPLAYED_INDEX = "The client note index provided is "
            + "invalid";

    /**
     * Returns grammatically-correct character based on an input value.
     * @param value Non-negative integer value.
     * @return String "s" if it's plural, else returns empty String.
     */
    public static String appendPluralChar(int value) {
        assert value >= 0 : "negative value is being passed in";
        return value == 1 ? "" : "s";
    }


}
