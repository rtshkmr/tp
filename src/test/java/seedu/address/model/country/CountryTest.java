package seedu.address.model.country;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.note.Note;

public class CountryTest {

    private static final String[] COUNTRY_CODES = Locale.getISOCountries();

    @Test
    public void getCountryNotes_addValidNote_throwsUnsupportedOperationException() {
        for (String countryCode : COUNTRY_CODES) {
            Country country = new Country(countryCode);
            Set<Note> countryNotes = country.getCountryNotes();
            NoteStub genericNote = new NoteStub("something");
            assertThrows(UnsupportedOperationException.class, () -> countryNotes.add(genericNote));
        }
    }

    @Test
    public void getCountryNotes_deleteNote_throwsUnsupportedOperationException() {
        for (String countryCode : COUNTRY_CODES) {
            Country country = new Country(countryCode);
            NoteStub genericNote = new NoteStub("something");
            country.addCountryNote(genericNote);
            Set<Note> countryNotes = country.getCountryNotes();
            assertThrows(UnsupportedOperationException.class, () -> countryNotes.remove(genericNote));
        }
    }

    @Test
    public void addCountryNotes_addValidNote_updatesCountryNotesList() {
        for (String countryCode : COUNTRY_CODES) {
            Country country = new Country(countryCode);
            assertEquals(0, country.getCountryNotes().size());
            NoteStub genericNote = new NoteStub("something");
            country.addCountryNote(genericNote);
            assertEquals(1, country.getCountryNotes().size());
        }
    }

    @Test
    public void addCountryNotes_nullNote_throwsNullPointerException() {
        for (String countryCode : COUNTRY_CODES) {
            Country country = new Country(countryCode);
            assertThrows(NullPointerException.class, () -> {
                NoteStub genericNote = null;
                country.addCountryNote(genericNote);
            });
        }
    }

    @Test
    public void hasCountryNote_noteExists_returnTrue() {
        for (String countryCode : COUNTRY_CODES) {
            Country country = new Country(countryCode);
            NoteStub genericNote = new NoteStub("generic note");
            country.addCountryNote(genericNote);
            assertTrue(country.hasCountryNote(genericNote));
        }
    }

    @Test
    public void hasCountryNote_noteNotExists_returnFalse() {
        for (String countryCode : COUNTRY_CODES) {
            Country country = new Country(countryCode);
            NoteStub genericNote = new NoteStub("generic note");
            country.addCountryNote(genericNote);
            NoteStub otherNote = new NoteStub("other note");
            assertFalse(country.hasCountryNote(otherNote));
        }
    }

    @Test
    public void equals_sameObj_returnTrue() {
        for (String countryCode : COUNTRY_CODES) {
            Country countryFirst = new Country(countryCode);
            Country countrySecond = new Country(countryCode);
            assertEquals(countryFirst, countrySecond);
        }
    }

    @Test
    public void equals_notACountry_returnFalse() {
        for (String countryCode : COUNTRY_CODES) {
            Country countryFirst = new Country(countryCode);
            Object obj = new Object();
            assertNotEquals(countryFirst, obj);
        }
    }

    @Test
    public void equals_diffCountry_returnFalse() {
        for (int i = 0; i < COUNTRY_CODES.length - 1; i++) {
            Country countryFirst = new Country(COUNTRY_CODES[i]);
            Country countrySecond = new Country(COUNTRY_CODES[i + 1]);
            assertNotEquals(countryFirst, countrySecond);
            assertNotEquals(countrySecond, countryFirst);
        }
    }

    @Test
    public void equals_sameCountry_returnTrue() {
        for (String countryCode : COUNTRY_CODES) {
            Country countryFirst = new Country(countryCode);
            Country countrySecond = new Country(countryCode);
            assertEquals(countryFirst, countrySecond);
            assertEquals(countrySecond, countryFirst);
        }
    }
}
