package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Address;
import seedu.address.model.client.ClientSuggestionType;
import seedu.address.model.client.ContractExpiryDate;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Timezone;
import seedu.address.model.country.Country;
import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "99";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_COUNTRY_CODE = "ZZ";
    private static final String INVALID_TIMEZONE = "GT+8";
    private static final String INVALID_NOTE_STRING = " ";
    private static final String INVALID_CLIENT_SUGGESTION_TYPE = "name";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_COUNTRY_CODE = "SG";
    private static final String VALID_TIMEZONE = "UTC+08:00";
    private static final String VALID_NOTE_STRING = "likes cats";
    private static final String VALID_CLIENT_SUGGESTION_TYPE_1 = "available";
    private static final String VALID_CLIENT_SUGGESTION_TYPE_2 = "frequency";
    private static final String VALID_CLIENT_SUGGESTION_TYPE_3 = "contract";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () ->
                ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_CLIENT, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_CLIENT, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone(null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress(null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail(null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseCountry_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCountry(null));
    }

    @Test
    public void parseCountry_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCountry(INVALID_COUNTRY_CODE));
    }

    @Test
    public void parseCountry_validValueWithoutWhitespace_returnsCountry() throws Exception {
        Country expectedCountry = new Country(VALID_COUNTRY_CODE);
        assertEquals(expectedCountry, ParserUtil.parseCountry(VALID_COUNTRY_CODE));
    }

    @Test
    public void parseCountry_validValueWithWhitespace_returnsTrimmedCountry() throws Exception {
        String countryWithWhitespace = WHITESPACE + VALID_COUNTRY_CODE + WHITESPACE;
        Country expectedCountry = new Country(VALID_COUNTRY_CODE);
        assertEquals(expectedCountry, ParserUtil.parseCountry(countryWithWhitespace));
    }

    @Test
    public void parseTimezone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTimezone(null));
    }

    @Test
    public void parseTimezone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTimezone(INVALID_TIMEZONE));
    }

    @Test
    public void parseTimezone_validValueWithoutWhitespace_returnsTimezone() throws Exception {
        Timezone expectedTimezone = new Timezone(VALID_TIMEZONE);
        assertEquals(expectedTimezone, ParserUtil.parseTimezone(VALID_TIMEZONE));
    }

    @Test
    public void parseTimezone_validValueWithWhitespace_returnsTrimmedTimezone() throws Exception {
        String timezoneWithWhitespace = WHITESPACE + VALID_TIMEZONE + WHITESPACE;
        Timezone expectedTimezone = new Timezone(VALID_TIMEZONE);
        assertEquals(expectedTimezone, ParserUtil.parseTimezone(timezoneWithWhitespace));
    }

    @Test
    public void parseNote_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNote(null));
    }

    @Test
    public void parseNote_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNote(INVALID_NOTE_STRING));
    }

    @Test
    public void parseNote_validValueWithoutWhitespace_returnsNote() throws Exception {
        Note expectedNote = new Note(VALID_NOTE_STRING);
        assertEquals(expectedNote, ParserUtil.parseNote(VALID_NOTE_STRING));
    }

    @Test
    public void parseNote_validValueWithWhitespace_returnsTrimmedNote() throws Exception {
        String noteWithWhitespace = WHITESPACE + VALID_NOTE_STRING + WHITESPACE;
        Note expectedNote = new Note(VALID_NOTE_STRING);
        assertEquals(expectedNote, ParserUtil.parseNote(noteWithWhitespace));
    }

    @Test
    public void parseSuggestionType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClientSuggestionType(null));
    }

    @Test
    public void parseSuggestionType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseClientSuggestionType(INVALID_CLIENT_SUGGESTION_TYPE));
    }

    @Test
    public void parseSuggestionType_validValueWithoutWhitespace_returnsSuggestionType() throws Exception {
        ClientSuggestionType expectedClientSuggestionType = new ClientSuggestionType(VALID_CLIENT_SUGGESTION_TYPE_1);
        assertEquals(expectedClientSuggestionType,
                ParserUtil.parseClientSuggestionType(VALID_CLIENT_SUGGESTION_TYPE_1));
    }

    @Test
    public void parseSuggestionType_validValueWithWhitespace_returnsTrimmedSuggestionType() throws Exception {
        String suggestionTypeWithWhitespace = WHITESPACE + VALID_CLIENT_SUGGESTION_TYPE_1 + WHITESPACE;
        ClientSuggestionType expectedClientSuggestionType = new ClientSuggestionType(VALID_CLIENT_SUGGESTION_TYPE_1);
        assertEquals(expectedClientSuggestionType, ParserUtil.parseClientSuggestionType(suggestionTypeWithWhitespace));
    }

    @Test
    public void parseSuggestionTypes_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClientSuggestionTypes(null));
    }

    @Test
    public void parseSuggestionTypes_collectionWithInvalidSuggestionTypes_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseClientSuggestionTypes(Arrays.asList(VALID_CLIENT_SUGGESTION_TYPE_1,
                INVALID_CLIENT_SUGGESTION_TYPE)));
    }

    @Test
    public void parseSuggestionTypes_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseClientSuggestionTypes(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseSuggestionTypes_collectionWithValidSuggestionTypes_returnsSuggestionTypeSet() throws Exception {
        Set<ClientSuggestionType> actualClientSuggestionTypeSet = ParserUtil.parseClientSuggestionTypes(Arrays.asList(
                VALID_CLIENT_SUGGESTION_TYPE_1, VALID_CLIENT_SUGGESTION_TYPE_2, VALID_CLIENT_SUGGESTION_TYPE_3));
        Set<ClientSuggestionType> expectedClientSuggestionTypeSet = new HashSet<>(Arrays.asList(
                new ClientSuggestionType(VALID_CLIENT_SUGGESTION_TYPE_1),
                new ClientSuggestionType(VALID_CLIENT_SUGGESTION_TYPE_2),
                new ClientSuggestionType((VALID_CLIENT_SUGGESTION_TYPE_3))));

        assertEquals(expectedClientSuggestionTypeSet, actualClientSuggestionTypeSet);
    }

    @Test
    public void parseContractExpiryDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseContractExpiryDate(null));
    }

    @Test
    public void parseContractExpiryDate_blankString_returnsNullDate() throws ParseException {
        assertEquals(ParserUtil.parseContractExpiryDate(""), ContractExpiryDate.NULL_DATE);
        assertEquals(ParserUtil.parseContractExpiryDate("  "), ContractExpiryDate.NULL_DATE);
    }

    @Test
    public void parseContractExpiryDate_invalidDateString_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseContractExpiryDate("2-2-100"));
        assertThrows(ParseException.class, () -> ParserUtil.parseContractExpiryDate("3/2/2020"));
        assertThrows(ParseException.class, () -> ParserUtil.parseContractExpiryDate("29/2/2021"));
        assertThrows(ParseException.class, () -> ParserUtil.parseContractExpiryDate("20/13/2022"));
        assertThrows(ParseException.class, () -> ParserUtil.parseContractExpiryDate("31/1/2025"));
    }

    @Test
    public void parseContractExpiryDate_validDateString_returns() throws ParseException {
        String validDate1 = "2-3-2020";
        String validDate2 = "30-10-20";
        String minDate = "1-1-0000";
        String maxDate = "31-12-9999";
        assertEquals(ParserUtil.parseContractExpiryDate(validDate1), new ContractExpiryDate(validDate1));
        assertEquals(ParserUtil.parseContractExpiryDate(validDate2), new ContractExpiryDate(validDate2));
        assertEquals(ParserUtil.parseContractExpiryDate(minDate), new ContractExpiryDate(minDate));
        assertEquals(ParserUtil.parseContractExpiryDate(maxDate), new ContractExpiryDate(maxDate));
    }

}
