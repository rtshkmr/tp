package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.client.Client;

/**
 * The UI component that displays information of a {@code Client}.
 */
public class ClientListCard extends UiPart<Region> {

    private static final String FXML = "ClientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Address Book level 4</a>
     */

    public final Client client;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox clientFields;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label country;
    @FXML
    private Label timezone;

    /**
     * Creates a {@code ClientListCard} with the given {@code Client} and index to display.
     */
    public ClientListCard(Client client, int displayedIndex) {
        super(FXML);
        requireNonNull(client);
        this.client = client;
        id.setText(displayedIndex + ". ");
        name.setText(client.getName().fullName);
        phone.setText(client.getPhone().value);
        address.setText(client.getAddress().value);
        email.setText(client.getEmail().value);
        country.setText(client.getCountry().getCountryName());
        timezone.setText(client.getTimezone().toString());
        if (!client.getContractExpiryDate().isNullDate) {
            Label dateLabel = new Label(client.getContractExpiryDate().displayValue);
            dateLabel.getStyleClass().add("cell_small_label");
            clientFields.getChildren().add(dateLabel);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientListCard)) {
            return false;
        }

        // state check
        ClientListCard card = (ClientListCard) other;
        return id.getText().equals(card.id.getText())
                && client.equals(card.client);
    }
}
