package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TestUtil.basicEqualsTests;
import static seedu.address.testutil.TypicalClients.getTypicalTbmManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.country.Country;
import seedu.address.model.note.CountryNote;

public class CountryNoteDeleteCommandTest {

    private final CountryNote genericCountryNote = new CountryNote("generic note", new Country("SG"));
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTbmManager(), new UserPrefs());
        model.setCountryNotesListPanelIsVisible(true);
    }

    @Test
    public void equals_basicTests() {
        CountryNoteDeleteCommand countryNoteDeleteCommand = new CountryNoteDeleteCommand(Index.fromOneBased(1));
        // basic equals tests
        basicEqualsTests(countryNoteDeleteCommand);
    }

    @Test
    public void equals_sameIndex_returnsTrue() {
        Index index = Index.fromOneBased(1);
        CountryNoteDeleteCommand first = new CountryNoteDeleteCommand(index);
        CountryNoteDeleteCommand second = new CountryNoteDeleteCommand(index);
        assertEquals(first, second);
    }

    @Test
    public void equals_diffIndex_returnsFalse() {
        Index index = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);
        CountryNoteDeleteCommand first = new CountryNoteDeleteCommand(index);
        CountryNoteDeleteCommand second = new CountryNoteDeleteCommand(index2);
        assertNotEquals(first, second);
    }

    @Test
    public void execute_invalidCountryNoteIndex_throwsCommandException() {
        Index invalidCountryNoteIndex = Index.fromOneBased(model.getSortedFilteredCountryNoteList().size() + 1);
        CountryNoteDeleteCommand countryNoteDeleteCommand = new CountryNoteDeleteCommand(invalidCountryNoteIndex);
        assertCommandFailure(countryNoteDeleteCommand, model, Messages.MESSAGE_INVALID_COUNTRY_NOTE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        CountryNoteDeleteCommand countryNoteDeleteCommand = new CountryNoteDeleteCommand(Index.fromOneBased(1));
        assertThrows(NullPointerException.class, () -> countryNoteDeleteCommand.execute(null));
    }

    @Test
    public void execute_validCountryNoteIndex_successfullyDeletesNote() {
        model.addCountryNote(genericCountryNote);
        Index index = Index.fromOneBased(1);
        CountryNoteDeleteCommand countryNoteDeleteCommand = new CountryNoteDeleteCommand(index);

        Model expectedModel = new ModelManager(getTypicalTbmManager(), new UserPrefs());
        String expectedMessage = String.format(CountryNoteDeleteCommand.MESSAGE_SUCCESS, index.getOneBased(),
                genericCountryNote);

        assertCommandSuccess(countryNoteDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_countryNotesPanelNotVisible_throwsCommandException() {
        model.setCountryNotesListPanelIsVisible(false);
        model.addCountryNote(genericCountryNote);
        Index index = Index.fromOneBased(1);
        CountryNoteDeleteCommand countryNoteDeleteCommand = new CountryNoteDeleteCommand(index);
        assertCommandFailure(countryNoteDeleteCommand, model,
                CountryNoteDeleteCommand.MESSAGE_COUNTRY_NOTES_NOT_VISIBLE);
    }

}
