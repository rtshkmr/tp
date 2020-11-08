package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.model.note.Note;

/**
 * The UI component that displays information of a {@code Note}.
 */
public class NoteListCard extends UiPart<Region> {

    private static final String FXML = "NoteListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Address Book level 4</a>
     */

    public final Note note;

    @FXML
    private VBox cardPane;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
    @FXML
    private Text noteContent;

    /**
     * Creates a {@code NoteListCard} with the given {@code Note} and index to display.
     */
    public NoteListCard(Note note, int displayedIndex) {
        super(FXML);
        requireNonNull(note);
        VBox.setVgrow(cardPane, Priority.ALWAYS);
        this.note = note;
        id.setText("#" + displayedIndex);
        note.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        noteContent.setText(note.getNoteContent());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteListCard)) {
            return false;
        }

        // state check
        NoteListCard card = (NoteListCard) other;
        return id.getText().equals(card.id.getText())
                && note.equals(card.note);
    }
}
