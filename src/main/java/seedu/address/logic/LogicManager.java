package seedu.address.logic;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.MainParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTbmManager;
import seedu.address.model.client.Client;
import seedu.address.model.note.CountryNote;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final MainParser mainParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        requireAllNonNull(model, storage);
        this.model = model;
        this.storage = storage;
        mainParser = new MainParser(model.getTagNoteMap());
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = mainParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveTbmManager(model.getTbmManager());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public void setCountryNotesListPanelIsVisible(boolean isVisible) {
        model.setCountryNotesListPanelIsVisible(isVisible);
    }

    @Override
    public ReadOnlyTbmManager getTbmManager() {
        return model.getTbmManager();
    }

    @Override
    public Client getWidgetClient() {
        return model.getWidgetClient();
    }

    @Override
    public ObservableList<Client> getFilteredClientList() {
        return model.getSortedFilteredClientList();
    }

    @Override
    public ObservableList<CountryNote> getSortedFilteredCountryNoteList() {
        return model.getSortedFilteredCountryNoteList();
    }

    @Override
    public Path getTbmManagerFilePath() {
        return model.getTbmManagerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
