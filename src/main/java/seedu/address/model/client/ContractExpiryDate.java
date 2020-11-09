package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Objects;

/**
 * Represents a contract expiry date.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class ContractExpiryDate implements Comparable<ContractExpiryDate> {

    public static final String MESSAGE_CONSTRAINTS = "ContractExpiryDate should be in the format day-month-year."
            + " Please input a valid date. Invalid dates like 29/02/2019 will not work.";
    /*
     * ContractExpiryDate should be in the format day-month-year separating the digits.
     * Day should be in the range 1-31, month should be in the range 1-12, and year can be from 0000-9999.
     */
    public static final String VALIDATION_REGEX =
            "^([0]?[1-9]|[1|2][0-9]|[3][0|1])[-]([0]?[1-9]|[1][0-2])[-]([0-9]{4}|[0-9]{2})$";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d-M-[uuuu][uu]")
            .withResolverStyle(ResolverStyle.STRICT);
    public static final String DEFAULT_DATE_FORMAT = "d-M-uuuu";
    public static final String DISPLAY_DATE_FORMAT = "d MMM uuuu";
    public static final ContractExpiryDate NULL_DATE = new ContractExpiryDate();
    public final boolean isNullDate;
    public final String value;
    public final String displayValue;
    private final LocalDate date;

    /**
     * Constructs a null date object.
     */
    private ContractExpiryDate() {
        this.value = "";
        this.displayValue = "";
        this.date = null;
        this.isNullDate = true;
    }

    /**
     * Constructs a date object.
     */
    public ContractExpiryDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date, DATE_FORMATTER);
        this.value = this.date.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
        this.displayValue = this.date.format(DateTimeFormatter.ofPattern(DISPLAY_DATE_FORMAT));
        this.isNullDate = false;
    }

    /**
     * Returns true if the String follows the correct date format and is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (test.matches(VALIDATION_REGEX)) {
            try {
                LocalDate.parse(test, DATE_FORMATTER);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContractExpiryDate // instanceof handles nulls
                && date.equals(((ContractExpiryDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int compareTo(ContractExpiryDate other) {
        return date.compareTo(other.date);
    }
}
