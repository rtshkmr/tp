package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.country.Country;
import seedu.address.model.country.NoteStub;

public class CountryNoteCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_duplicateCountryNote_throwsCommandException() {
        Country country = new Country("SG");
        NoteStub genericNote = new NoteStub("generic note");
        model.addCountryNote(country, genericNote);
        CountryNoteCommand countryNoteCommand = new CountryNoteCommand(country, genericNote);
        assertThrows(CommandException.class, () -> countryNoteCommand.execute(model));
    }

    @Test
    public void execute_notDuplicateCountryNote_successfullyAddsCountryNote() {
        try {
            Country country = new Country("SG");
            NoteStub genericNote = new NoteStub("generic note");
            assertFalse(model.hasCountryNote(country, genericNote));
            CountryNoteCommand countryNoteCommand = new CountryNoteCommand(country, genericNote);
            countryNoteCommand.execute(model);
            assertTrue(model.hasCountryNote(country, genericNote));
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void equals_sameObj_returnTrue() {
        CountryNoteCommand countryNoteCommand = new CountryNoteCommand(new Country("SG"),
                new NoteStub("generic"));
        assertTrue(countryNoteCommand.equals(countryNoteCommand));
    }

    @Test
    public void equals_notCountryNoteCommand_returnFalse() {
        CountryNoteCommand countryNoteCommand = new CountryNoteCommand(new Country("SG"),
                new NoteStub("generic"));
        Object obj = new Object();
        assertFalse(countryNoteCommand.equals(obj));
    }

    @Test
    public void equals_diffCountryDiffNote_returnFalse() {
        CountryNoteCommand countryNoteCommandFirst = new CountryNoteCommand(new Country("SG"),
                new NoteStub("generic"));
        CountryNoteCommand countryNoteCommandSecond = new CountryNoteCommand(new Country("MY"),
                new NoteStub("generic2"));
        assertFalse(countryNoteCommandFirst.equals(countryNoteCommandSecond));
        assertFalse(countryNoteCommandSecond.equals(countryNoteCommandFirst));
    }

    @Test
    public void equals_diffCountrySameNote_returnFalse() {
        CountryNoteCommand countryNoteCommandFirst = new CountryNoteCommand(new Country("SG"),
                new NoteStub("generic"));
        CountryNoteCommand countryNoteCommandSecond = new CountryNoteCommand(new Country("MY"),
                new NoteStub("generic"));
        assertFalse(countryNoteCommandFirst.equals(countryNoteCommandSecond));
        assertFalse(countryNoteCommandSecond.equals(countryNoteCommandFirst));
    }

    @Test
    public void equals_sameCountryDiffNote_returnFalse() {
        CountryNoteCommand countryNoteCommandFirst = new CountryNoteCommand(new Country("SG"),
                new NoteStub("generic"));
        CountryNoteCommand countryNoteCommandSecond = new CountryNoteCommand(new Country("SG"),
                new NoteStub("generic2"));
        assertFalse(countryNoteCommandFirst.equals(countryNoteCommandSecond));
        assertFalse(countryNoteCommandSecond.equals(countryNoteCommandFirst));
    }

    @Test
    public void equals_sameCountrySameNote_returnTrue() {
        CountryNoteCommand countryNoteCommandFirst = new CountryNoteCommand(new Country("SG"),
                new NoteStub("generic"));
        CountryNoteCommand countryNoteCommandSecond = new CountryNoteCommand(new Country("SG"),
                new NoteStub("generic"));
        assertTrue(countryNoteCommandFirst.equals(countryNoteCommandSecond));
        assertTrue(countryNoteCommandSecond.equals(countryNoteCommandFirst));
    }
}
