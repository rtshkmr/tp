package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.model.client.Client;
import seedu.address.model.client.ContractExpiryDate;
import seedu.address.model.client.Timezone;
import seedu.address.model.note.Note;

/**
 * The UI component that displays the information of {@code Client}.
 */
public class WidgetViewBox extends UiPart<Region> {

    private static final String FXML = "WidgetViewBox.fxml";

    private final ObservableList<Client> clientObservableList;

    private TextClock textClock;
    // Default values indicate that there is no client currently being viewed.
    private int displayedClientIndex = -1;
    private int clientListSize = -1;
    private Client displayedClient = null;

    @FXML
    private ScrollPane widgetScrollPane;
    @FXML
    private VBox viewBox;
    @FXML
    private VBox tbmLogoContainer;
    @FXML
    private ImageView tbmLogo;
    @FXML
    private Text name;
    @FXML
    private Text timer;
    @FXML
    private Text country;
    @FXML
    private Text phone;
    @FXML
    private Text email;
    @FXML
    private Text address;
    @FXML
    private Text contractExpiryDate;
    @FXML
    private Text noteTitle;
    @FXML
    private VBox clientNoteListView;

    /**
     * Creates a {@code WidgetViewBox}.
     */
    public WidgetViewBox(ObservableList<Client> clientObservableList) {
        super(FXML);
        this.clientObservableList = clientObservableList;
        clientListSize = clientObservableList.size();
        clientObservableList.addListener((ListChangeListener<Client>) c -> {
            if (c.next()) {
                if (clientObservableList.size() == clientListSize && !c.wasPermutated()) {
                    // List size remains the same and ordering did not change
                    if (displayedClientIndex != -1) {
                        updateClientDisplay(clientObservableList.get(displayedClientIndex));
                    }
                } else {
                    for (int i = 0; i < clientObservableList.size(); i++) {
                        Client client = clientObservableList.get(i);
                        if (client.isSameClient(displayedClient)) {
                            displayedClient = client;
                            displayedClientIndex = i;
                            updateClientDisplay(displayedClient);
                            return;
                        }
                    }
                    setToDefaultView();
                }
            }
        });
        setToDefaultView();
    }

    /**
     * Updates the current client of the widget view box to the given client.
     *
     * @param client The new client to display.
     */
    public void updateClientDisplay(Client client) {
        widgetScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        widgetScrollPane.setVmax(1.0);
        tbmLogoContainer.getChildren().clear();
        tbmLogoContainer.setStyle("-fx-padding: 0 0 0 0; -fx-spacing: 0;");
        displayedClient = client;
        for (int i = 0; i < clientObservableList.size(); i++) {
            if (clientObservableList.get(i).isSameClient(client)) {
                displayedClient = clientObservableList.get(i);
                displayedClientIndex = i;
            }
        }
        textClock.pause();
        textClock = new TextClock(timer, client.getTimezone().getJavaTimeZone());
        textClock.play();
        country.setText(client.getCountry().getCountryName() + " (" + client.getTimezone().toString() + ")");
        name.setText(client.getName().toString());
        phone.setText("Phone: " + client.getPhone().toString());
        email.setText("Email: " + client.getEmail().toString());
        address.setText("Address: " + client.getAddress().toString());
        if (!client.getContractExpiryDate().equals(ContractExpiryDate.NULL_DATE)) {
            contractExpiryDate.setText("Expiry: " + client.getContractExpiryDate().displayValue);
        }
        noteTitle.setText("Notes");
        updateClientNotesDisplay(client.getClientNotesAsUnmodifiableList());
        drawPaneBorder();
    }

    /**
     * Static factory for use in GUI testing.
     *
     * @return WidgetViewBox
     */
    public static WidgetViewBox init() {
        return new WidgetViewBox(FXCollections.emptyObservableList());
    }

    /**
     * Sets the display to the default view for the view box.
     */
    public void setToDefaultView() {
        //TODO: Some better information
        widgetScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        widgetScrollPane.setVmax(0.0);
        tbmLogoContainer.getChildren().clear();
        tbmLogoContainer.getChildren().add(tbmLogo);
        tbmLogoContainer.setStyle("-fx-padding: 20 0 25 0; -fx-spacing: 10");
        if (textClock != null) {
            textClock.pause();
        }
        textClock = new TextClock(timer, TimeZone.getDefault());
        textClock.play();
        name.setText("");
        phone.setText("");
        email.setText("");
        address.setText("");
        String offset = ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now()).toString();
        String offsetString = Timezone.UTC_STRING + offset;
        country.setText(Locale.getDefault().getDisplayCountry() + " (" + offsetString + ")");
        contractExpiryDate.setText("");
        noteTitle.setText("");
        clientNoteListView.getChildren().clear();
        clearPaneBorder();
        displayedClient = null;
        displayedClientIndex = -1;
    }

    /**
     * Updates the displayed notes.
     *
     * @param clientNotes The client notes to display.
     */
    private void updateClientNotesDisplay(List<Note> clientNotes) {
        clientNoteListView.getChildren().clear();
        int noteIdx = 1;
        for (Note note : clientNotes) {
            NoteListCard noteListCard = new NoteListCard(note, noteIdx++);
            clientNoteListView.getChildren().add(noteListCard.getRoot());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WidgetViewBox)) {
            return false;
        }

        // state check
        Client other1 = ((WidgetViewBox) other).displayedClient;
        return displayedClient.equals(other1);
    }

    private void drawPaneBorder() {
        clientNoteListView.setStyle("-fx-border-color: #FF3333; -fx-border-radius: 10; -fx-border-width: 2;"
                + " -fx-padding: 10");
    }

    private void clearPaneBorder() {
        clientNoteListView.setStyle("-fx-border-color: transparent; -fx-border-width: 0; -fx-padding: 0");
    }

}
