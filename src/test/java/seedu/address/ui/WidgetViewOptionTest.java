package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.country.Country;

public class WidgetViewOptionTest {

    @Test
    public void generateClientWidgetOption_isClientTrue() {
        WidgetViewOption widgetViewOption = WidgetViewOption.generateClientWidgetOption();
        assertTrue(widgetViewOption.isClientView());
        assertFalse(widgetViewOption.isCountryNoteView());
        assertEquals(Country.NULL_COUNTRY, widgetViewOption.getCountry());
    }

    @Test
    public void generateCountryNoteWidgetOption_isCountryNoteTrue() {
        WidgetViewOption widgetViewOption = WidgetViewOption.generateCountryNoteWidgetOption(Country.NULL_COUNTRY);
        assertFalse(widgetViewOption.isClientView());
        assertTrue(widgetViewOption.isCountryNoteView());
        assertEquals(Country.NULL_COUNTRY, widgetViewOption.getCountry());
        widgetViewOption = WidgetViewOption.generateCountryNoteWidgetOption(new Country("SG"));
        assertFalse(widgetViewOption.isClientView());
        assertTrue(widgetViewOption.isCountryNoteView());
        assertEquals(new Country("SG"), widgetViewOption.getCountry());
    }

    @Test
    public void generateNullWidgetOption_allFalse() {
        WidgetViewOption widgetViewOption = WidgetViewOption.generateNullWidgetOption();
        assertFalse(widgetViewOption.isClientView());
        assertFalse(widgetViewOption.isCountryNoteView());
        assertEquals(Country.NULL_COUNTRY, widgetViewOption.getCountry());
    }

}
