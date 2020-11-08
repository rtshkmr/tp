package seedu.address.model.widget;

import seedu.address.model.client.Client;

/**
 * This is the API for interacting with the View Box widget in the TBM application.
 */
public interface WidgetModel {

    /**
     * Static factory.
     *
     * @return {@code WidgetModel} implemented by {@code WidgetModelManager}.
     */
    static WidgetModel initWidget() {
        return WidgetModelManager.initWidget();
    }

    /**
     * Sets the client to be displayed in the widget view box.
     *
     * @param client To be set in the widget.
     */
    void setWidgetClient(Client client);

    /**
     * Returns the widget content object.
     *
     * @return The client displayed for the widget.
     */
    Client getWidgetClient();
}
