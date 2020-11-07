package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.model.client.Client;

/**
 * Provides a handler to the widget view box.
 */
public class WidgetViewBoxHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String COUNTRY_FIELD_ID = "#country";
    private static final String CONTRACT_EXPIRY_DATE_FIELD_ID = "#contractExpiryDate";
    private static final String NOTE_LIST_VIEW_ID = "#clientNoteListView";
    private final Text nameLabel;
    private final Text phoneLabel;
    private final Text emailLabel;
    private final Text addressLabel;
    private final Text countryLabel;
    private final Text contractExpiryDateLabel;
    private final VBox noteListView;

    /**
     * Constructor for handler.
     */
    public WidgetViewBoxHandle(Node widgetNode) {
        super(widgetNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        countryLabel = getChildNode(COUNTRY_FIELD_ID);
        contractExpiryDateLabel = getChildNode(CONTRACT_EXPIRY_DATE_FIELD_ID);
        noteListView = getChildNode(NOTE_LIST_VIEW_ID);
    }

    /**
     * Returns true if this handle contains a {@code client}.
     */
    public boolean equals(Client client) {
        boolean notesEqual = noteListView.getChildren().size() == client.getClientNotesAsObservableList().size();
        for (int i = 0; i < noteListView.getChildren().size(); i++) {
            VBox noteListCard = (VBox) noteListView.getChildren().get(i);
            TextFlow noteTextFlow = (TextFlow) noteListCard.getChildren().get(1);
            Text noteText = (Text) noteTextFlow.getChildren().get(0);
            String noteContent = client.getClientNotesAsObservableList().get(i).getNoteContent();
            if (!noteText.getText().equals(noteContent)) {
                notesEqual = false;
                break;
            }
        }
        return nameLabel.getText().equals(client.getName().toString())
                && phoneLabel.getText().equals("Phone: " + client.getPhone().toString())
                && emailLabel.getText().equals("Email: " + client.getEmail().toString())
                && addressLabel.getText().equals("Address: " + client.getAddress().toString())
                && countryLabel.getText()
                        .equals(client.getCountry().getCountryName() + " (" + client.getTimezone().toString() + ")")
                && contractExpiryDateLabel.getText().equals("Expiry: " + client.getContractExpiryDate().displayValue)
                && notesEqual;
    }

}
