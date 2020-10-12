package seedu.address.model.country;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.junit.jupiter.api.Test;

public class CountryManagerTest {

    //TODO: Add more tests if decide to include checking for 3-letter Country Code
    private static final String[] COUNTRY_CODES = Locale.getISOCountries();

    @Test
    public void isValidCode_validCode_returnTrue() {
        for (String countryCode : COUNTRY_CODES) {
            assertTrue(CountryManager.isValidCountryCode(countryCode));
        }
    }

    @Test
    public void isValidCode_invalidCode_returnFalse() {
        assertFalse(CountryManager.isValidCountryCode("ZZ"));
        assertFalse(CountryManager.isValidCountryCode("12"));
        assertFalse(CountryManager.isValidCountryCode("az"));
        assertFalse(CountryManager.isValidCountryCode("bd"));
        assertFalse(CountryManager.isValidCountryCode("bdc"));
    }

    @Test
    public void hasCountryNote_duplicateNote_returnTrue() {
        CountryManager countryManager = new CountryManager();
        for (String countryCode : COUNTRY_CODES) {
            NoteStub genericNote = new NoteStub("generic note");
            countryManager.getCountryFromCode(countryCode).addCountryNote(genericNote);
            assertTrue(countryManager.hasCountryNote(new Country(countryCode), genericNote));
        }
    }

    @Test
    public void hasCountryNote_notDuplicateNote_returnFalse() {
        CountryManager countryManager = new CountryManager();
        for (String countryCode : COUNTRY_CODES) {
            NoteStub genericNote = new NoteStub("generic note");
            assertFalse(countryManager.hasCountryNote(new Country(countryCode), genericNote));
        }
    }

    @Test
    public void addCountryNote_validNote_updatesCorrectly() {
        CountryManager countryManager = new CountryManager();
        for (String countryCode : COUNTRY_CODES) {
            Country country = countryManager.getCountryFromCode(countryCode);
            NoteStub genericNote = new NoteStub("generic note");
            assertFalse(country.hasCountryNote(genericNote));
            countryManager.addCountryNote(new Country(countryCode), genericNote);
            assertTrue(country.hasCountryNote(genericNote));
        }
    }

    @Test
    public void getCountryFromCode_validCode_validCountry() {
        CountryManager countryManager = new CountryManager();

        for (String countryCode : COUNTRY_CODES) {
            Country country = countryManager.getCountryFromCode(countryCode);
            assertNotNull(country);
        }
    }

    @Test
    public void getCountryFromCode_multipleRefSameCountry_reflectChanges() {
        CountryManager countryManager = new CountryManager();
        for (String countryCode : COUNTRY_CODES) {
            Country countryFirstRef = countryManager.getCountryFromCode(countryCode);
            Country countrySecondRef = countryManager.getCountryFromCode(countryCode);

            assertEquals(0, countryFirstRef.getCountryNotes().size());
            assertEquals(0, countrySecondRef.getCountryNotes().size());

            countryFirstRef.addCountryNote(new NoteStub("something1"));
            assertEquals(1, countryFirstRef.getCountryNotes().size());
            assertEquals(1, countrySecondRef.getCountryNotes().size());

            countrySecondRef.addCountryNote(new NoteStub("something2"));
            assertEquals(2, countryFirstRef.getCountryNotes().size());
            assertEquals(2, countrySecondRef.getCountryNotes().size());

            countryManager.addCountryNote(countryFirstRef, new NoteStub("something3"));
            assertEquals(3, countryFirstRef.getCountryNotes().size());
            assertEquals(3, countrySecondRef.getCountryNotes().size());
        }
    }

    @Test
    public void getCountryFromCode_invalidCode_throwsNullPointerException() {
        CountryManager countryManager = new CountryManager();
        assertThrows(NullPointerException.class, () -> countryManager.getCountryFromCode("1"));
        assertThrows(NullPointerException.class, () -> countryManager.getCountryFromCode("12"));
        assertThrows(NullPointerException.class, () -> countryManager.getCountryFromCode("123"));
        assertThrows(NullPointerException.class, () -> countryManager.getCountryFromCode("ab"));
        assertThrows(NullPointerException.class, () -> countryManager.getCountryFromCode("ZZ"));
    }
}
