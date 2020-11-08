package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.country.Country;
import seedu.address.model.note.CountryNote;

public class CountryNoteListPanelTest extends GuiUnitTest {
    private static final ObservableList<CountryNote> COUNTRY_NOTE_OBSERVABLE_LIST = FXCollections.observableArrayList();
    private static final long CARD_CREATION_TIMEOUT = 2500;

    /**
     * Verifies that creating and deleting large number of notes in {@code CountryNoteListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        createBackingList(220);
        CountryNoteListPanel countryNoteListPanel = new CountryNoteListPanel(COUNTRY_NOTE_OBSERVABLE_LIST);
        uiPartExtension.setUiPart(countryNoteListPanel);
        assertTimeoutPreemptively(ofMillis(CARD_CREATION_TIMEOUT), () -> {
            CountryNote countryNote = new CountryNote("final test", new Country("SG"));
            guiRobot.interact(() -> COUNTRY_NOTE_OBSERVABLE_LIST.add(countryNote));
        });
    }

    private void createBackingList(int numCards) {
        for (int i = 0; i < numCards; i++) {
            CountryNote countryNote = new CountryNote("#" + i, new Country("SG"));
            COUNTRY_NOTE_OBSERVABLE_LIST.add(countryNote);
        }
    }
}
