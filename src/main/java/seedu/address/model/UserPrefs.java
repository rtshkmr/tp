package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path tbmManagerFilePath = Paths.get("data" , "tbmManager.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setTbmManagerFilePath(newUserPrefs.getTbmManagerFilePath());
    }

    /**
     * Returns the {@code guiSettings}.
     */
    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    /**
     * Sets the {@code guiSettings}.
     */
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    /**
     * Returns the {@code tbmManagerFilePath}.
     */
    public Path getTbmManagerFilePath() {
        return tbmManagerFilePath;
    }

    /**
     * Sets the {@code tbmManagerFilePath}.
     */
    public void setTbmManagerFilePath(Path tbmManagerFilePath) {
        requireNonNull(tbmManagerFilePath);
        this.tbmManagerFilePath = tbmManagerFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && tbmManagerFilePath.equals(o.tbmManagerFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, tbmManagerFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + tbmManagerFilePath);
        return sb.toString();
    }

}
