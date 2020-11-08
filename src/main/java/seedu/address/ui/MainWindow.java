package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ClientListPanel clientListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private WidgetViewBox widgetViewBox;
    private CountryNoteListPanel countryNoteListPanel;
    @FXML
    private StackPane widgetPlaceholder;
    @FXML
    private StackPane commandBoxPlaceholder;
    @FXML
    private MenuItem helpMenuItem;
    @FXML
    private StackPane clientListPanelPlaceholder;
    @FXML
    private StackPane resultDisplayPlaceholder;
    @FXML
    private StackPane statusBarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic, MainApp mainApp) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow(mainApp);
    }

    /**
     * Obtains the stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator.
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        widgetViewBox = new WidgetViewBox(logic.getFilteredClientList());
        countryNoteListPanel = new CountryNoteListPanel(logic.getSortedFilteredCountryNoteList());
        widgetPlaceholder.getChildren().add(widgetViewBox.getRoot());

        clientListPanel = new ClientListPanel(logic.getFilteredClientList());
        clientListPanelPlaceholder.getChildren().add(clientListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTbmManagerFilePath());
        statusBarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}, truncating the width, height, and position of the window
     * to some bounded values to ensure that the display of our application does not get messed up.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double minY = 0;
        double maxY = screenHeight / 2;
        double minX = 0;
        double maxX = screenWidth / 2;
        double truncatedHeight = Math.min(guiSettings.getWindowHeight(), screenHeight);
        double truncatedWidth = Math.min(guiSettings.getWindowWidth(), screenWidth);
        primaryStage.setHeight(truncatedHeight);
        primaryStage.setWidth(truncatedWidth);
        logger.info(String.format("Window width set to: %.2f", truncatedWidth));
        logger.info(String.format("Window height set to: %.2f", truncatedHeight));
        if (guiSettings.getWindowCoordinates() != null) {
            double truncatedX = truncateDouble(guiSettings.getWindowCoordinates().getX(), minX, maxX);
            double truncatedY = truncateDouble(guiSettings.getWindowCoordinates().getY(), minY, maxY);
            primaryStage.setX(truncatedX);
            primaryStage.setY(truncatedY);
            logger.info(String.format("Window x-position set to: %.2f", truncatedX));
            logger.info(String.format("Window y-position set to: %.2f", truncatedY));
        }
    }

    /**
     * Truncates the given double value to be at least the lower bound and at most the upper bound.
     */
    private double truncateDouble(double value, double lowerBound, double upperBound) {
        double truncatedValue = value;
        if (value < lowerBound) {
            truncatedValue = lowerBound;
        } else if (value > upperBound) {
            truncatedValue = upperBound;
        }
        return truncatedValue;
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public ClientListPanel getClientListPanel() {
        return clientListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            logger.info("Widget View Option: " + commandResult.getWidgetViewOptionAsString());

            if (commandResult.shouldDisplayClientView()) {
                logger.info("Toggling client view");
                widgetPlaceholder.getChildren().clear();
                widgetPlaceholder.getChildren().add(widgetViewBox.getRoot());
                logger.info(logic.getWidgetClient().getName().fullName);
                widgetViewBox.updateClientDisplay(logic.getWidgetClient());
            } else if (commandResult.shouldDisplayCountryNoteView()) {
                logger.info("Toggling country notes view");
                widgetPlaceholder.getChildren().clear();
                countryNoteListPanel.setHeader(commandResult.getCountry());
                widgetPlaceholder.getChildren().add(countryNoteListPanel.getRoot());
            }

            if (commandResult.shouldResetWidget()) {
                widgetPlaceholder.getChildren().clear();
                widgetPlaceholder.getChildren().add(widgetViewBox.getRoot());
                widgetViewBox.setToDefaultView();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            logic.setCountryNotesListPanelIsVisible(widgetPlaceholder.getChildren()
                    .contains(countryNoteListPanel.getRoot()));

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
