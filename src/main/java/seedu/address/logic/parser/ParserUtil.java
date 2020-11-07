package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Address;
import seedu.address.model.client.ClientSuggestionType;
import seedu.address.model.client.ContractExpiryDate;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Timezone;
import seedu.address.model.country.Country;
import seedu.address.model.country.CountryCodeVerifier;
import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String rawCountryCode} into a {@code Country}.
     *
     * @param rawCountryCode The raw countryCode.
     * @return The country associated with the input country code.
     * @throws ParseException If countryCode is invalid.
     */
    public static Country parseCountry(String rawCountryCode) throws ParseException {
        requireNonNull(rawCountryCode);
        String trimmedCountryCode = rawCountryCode.trim();
        if (!CountryCodeVerifier.isValidCountryCode(trimmedCountryCode)) {
            throw new ParseException(CountryCodeVerifier.MESSAGE_CONSTRAINTS);
        }
        return new Country(trimmedCountryCode);
    }

    /**
     * Parses {@code String timezone} into a {@code Timezone}.
     *
     * @param timezone String to be parsed into timezone.
     * @return The timezone associated with the input string.
     * @throws ParseException If timezone is invalid.
     */
    public static Timezone parseTimezone(String timezone) throws ParseException {
        requireNonNull(timezone);
        String trimmedTimezone = timezone.trim().toUpperCase();
        if (!Timezone.isValidTimezone(trimmedTimezone)) {
            throw new ParseException(Timezone.MESSAGE_CONSTRAINTS);
        }
        return new Timezone(trimmedTimezone);
    }

    /**
     * Parses {@code String noteString} into a {@code Note}.
     *
     * @param noteString The contents of the Note.
     * @return The Note representing the input noteString.
     * @throws ParseException If noteString is empty or null string.
     */
    public static Note parseNote(String noteString) throws ParseException {
        requireNonNull(noteString);
        String trimmedNoteString = noteString.trim();
        if (!Note.isValidNote(trimmedNoteString)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }

        return new Note(trimmedNoteString);
    }

    /**
     * Parses a {@code String clientSuggestionType} into a {@code ClientSuggestionType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code clientSuggestionType} is invalid.
     */
    public static ClientSuggestionType parseClientSuggestionType(String clientSuggestionType) throws ParseException {
        requireNonNull(clientSuggestionType);
        String trimmedSuggestionType = clientSuggestionType.trim();
        if (!ClientSuggestionType.isValidClientSuggestionType(trimmedSuggestionType)) {
            throw new ParseException(ClientSuggestionType.MESSAGE_CONSTRAINTS);
        }
        return new ClientSuggestionType(trimmedSuggestionType);
    }


    /**
     * Parses {@code Collection<String> clientSuggestionTypes} into a {@code Set<ClientSuggestionType>}.
     */
    public static Set<ClientSuggestionType> parseClientSuggestionTypes(Collection<String> clientSuggestionTypes)
            throws ParseException {
        requireNonNull(clientSuggestionTypes);
        final Set<ClientSuggestionType> suggestionSet = new LinkedHashSet<>();
        for (String suggestionType : clientSuggestionTypes) {
            suggestionSet.add(parseClientSuggestionType(suggestionType));
        }
        return suggestionSet;
    }

    /**
     * Parses a {@code String dateString} into a {@code ContractExpiryDate}.
     */
    public static ContractExpiryDate parseContractExpiryDate(String dateString) throws ParseException {
        requireNonNull(dateString);
        String trimmedDateString = dateString.trim();
        if (trimmedDateString.isEmpty()) {
            return ContractExpiryDate.NULL_DATE;
        }
        if (!ContractExpiryDate.isValidDate(trimmedDateString)) {
            throw new ParseException(ContractExpiryDate.MESSAGE_CONSTRAINTS);
        }
        return new ContractExpiryDate(trimmedDateString);
    }
}
