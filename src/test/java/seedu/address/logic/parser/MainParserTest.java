package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUGGEST;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClientAddCommand;
import seedu.address.logic.commands.ClientDeleteCommand;
import seedu.address.logic.commands.ClientEditCommand;
import seedu.address.logic.commands.ClientEditCommand.EditClientDescriptor;
import seedu.address.logic.commands.ClientFindCommand;
import seedu.address.logic.commands.ClientListCommand;
import seedu.address.logic.commands.ClientNoteAddCommand;
import seedu.address.logic.commands.ClientNoteDeleteCommand;
import seedu.address.logic.commands.ClientNoteEditCommand;
import seedu.address.logic.commands.ClientSuggestCommand;
import seedu.address.logic.commands.ClientViewCommand;
import seedu.address.logic.commands.CountryFilterCommand;
import seedu.address.logic.commands.CountryNoteAddCommand;
import seedu.address.logic.commands.CountryNoteDeleteCommand;
import seedu.address.logic.commands.CountryNoteEditCommand;
import seedu.address.logic.commands.CountryNoteViewCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientCountryMatchesInputCountryPredicate;
import seedu.address.model.client.ClientSuggestionType;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.model.country.Country;
import seedu.address.model.note.CountryNote;
import seedu.address.model.note.Note;
import seedu.address.model.note.TagNoteMap;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.ClientUtil;
import seedu.address.testutil.EditClientDescriptorBuilder;

public class MainParserTest {

    private final TagNoteMap tagNoteMap = new TagNoteMap();
    private final MainParser parser = new MainParser(tagNoteMap);

    @Test
    public void parseClientCommands_addClient() throws Exception {
        Client client = new ClientBuilder().build();
        final String commandString = ClientUtil.getAddCommand(client);

        ClientAddCommand command = (ClientAddCommand) parser.parseCommand(commandString);
        assertEquals(new ClientAddCommand(client), command);
    }

    @Test
    public void parseClientCommands_editClient() throws Exception {
        Client client = new ClientBuilder().build();
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder(client).build();
        final String commandString = ClientEditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_CLIENT.getOneBased() + " " + ClientUtil.getEditClientDescriptorDetails(descriptor);

        ClientEditCommand command = (ClientEditCommand) parser.parseCommand(commandString);
        assertEquals(new ClientEditCommand(INDEX_FIRST_CLIENT, descriptor), command);
    }

    @Test
    public void parseClientCommands_deleteClient() throws Exception {
        final String commandString = ClientDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased();

        ClientDeleteCommand command = (ClientDeleteCommand) parser.parseCommand(commandString);
        assertEquals(new ClientDeleteCommand(INDEX_FIRST_CLIENT), command);
    }

    @Test
    public void parseClientCommands_findClient() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        final String commandString = ClientFindCommand.COMMAND_WORD + " "
                + String.join(" ", keywords);

        ClientFindCommand command = (ClientFindCommand) parser.parseCommand(commandString);
        assertEquals(new ClientFindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseClientCommands_viewClient() throws Exception {
        final String commandString = ClientViewCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased();
        ClientViewCommand command = (ClientViewCommand) parser.parseCommand(commandString);
        assertEquals(new ClientViewCommand(INDEX_FIRST_CLIENT), command);
    }

    @Test
    public void parseClientCommands_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("client"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("client "));
    }

    @Test
    public void parseClientCommands_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("client unknownCommand"));
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("client unknownCommand "));
    }

    @Test
    public void parseCountryCommands_countryFilter() throws Exception {
        final String countryString = "SG";
        final String commandString = CountryFilterCommand.COMMAND_WORD + " " + PREFIX_COUNTRY + countryString;

        CountryFilterCommand command = (CountryFilterCommand) parser.parseCommand(commandString);
        final ClientCountryMatchesInputCountryPredicate predicate =
                new ClientCountryMatchesInputCountryPredicate(new Country(countryString));
        assertEquals(new CountryFilterCommand(predicate), command);
    }

    @Test
    public void parseCountryCommands_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("country"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("country "));
    }

    @Test
    public void parseCountryCommands_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("country unknownCommand"));
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("country unknownCommand "));
    }

    @Test
    public void parseCountryNoteCommands_addCountryNote() throws Exception {
        final String countryString = "SG";
        final String noteString = "is hot";
        final String commandString = CountryNoteAddCommand.COMMAND_WORD + " " + PREFIX_COUNTRY + countryString
                + " " + PREFIX_NOTE + noteString;
        Set<Tag> tags = new HashSet<>();
        tags.add(Tag.UNTAGGED);
        CountryNote expected = new CountryNote(noteString, new Country(countryString));
        expected.setTags(tags);
        CountryNoteAddCommand command = (CountryNoteAddCommand) parser.parseCommand(commandString);
        assertEquals(new CountryNoteAddCommand(expected), command);
    }

    @Test
    public void parseCountryNoteCommands_countryNoteView() throws Exception {
        final String countryString = "SG";
        final String commandString = CountryNoteViewCommand.COMMAND_WORD + " " + PREFIX_COUNTRY + countryString;

        CountryNoteViewCommand command = (CountryNoteViewCommand) parser.parseCommand(commandString);
        assertEquals(new CountryNoteViewCommand(new Country(countryString)), command);
    }

    @Test
    public void parseCountryNoteCommands_countryNoteDelete() throws Exception {
        final String commandString = CountryNoteDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased();

        CountryNoteDeleteCommand command = (CountryNoteDeleteCommand) parser.parseCommand(commandString);
        assertEquals(new CountryNoteDeleteCommand(INDEX_FIRST_CLIENT), command);
    }

    @Test
    public void parseCountryNoteCommands_countryNoteEdit() throws Exception {
        final String noteString = "is hot";
        final String commandString = CountryNoteEditCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased()
                + " " + PREFIX_NOTE + noteString;
        CountryNote expected = new CountryNote(noteString, Country.NULL_COUNTRY);
        CountryNoteEditCommand command = (CountryNoteEditCommand) parser.parseCommand(commandString);
        CountryNoteEditCommand expectedCmd = new CountryNoteEditCommand(INDEX_FIRST_CLIENT, expected);
        assertEquals(expectedCmd, command);
    }

    @Test
    public void parseCountryNoteCommands_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("country note"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("country note "));
    }

    @Test
    public void parseCountryNoteCommands_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("country note unknownCommand"));
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("country note unknownCommand "));
    }

    //TODO: add tests when country commands are finalized

    @Test
    public void parseClientNoteCommands_addValidClientNote() throws Exception {
        final String noteString = "likes cats";
        final String commandString = ClientNoteAddCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased()
                + " " + PREFIX_NOTE + noteString;
        TagNoteMap tagNoteMap = new TagNoteMap();
        Set<Tag> tags = tagNoteMap.getUniqueTags(Collections.emptyList());
        final Note note = new Note(noteString);
        note.setTags(tags);
        ClientNoteAddCommand command = (ClientNoteAddCommand) parser.parseCommand(commandString);
        assertEquals(new ClientNoteAddCommand(INDEX_FIRST_CLIENT, note), command);
    }

    @Test
    public void parseClientNoteCommands_editValidClientNote() throws Exception {
        final String noteString = "likes cats";
        final String commandString = ClientNoteEditCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased()
                + " 1" + " " + PREFIX_NOTE + noteString;
        TagNoteMap tagNoteMap = new TagNoteMap();
        Set<Tag> tags = tagNoteMap.getUniqueTags(Collections.emptyList());
        final Note note = new Note(noteString);
        note.setTags(tags);

        ClientNoteEditCommand command = (ClientNoteEditCommand) parser.parseCommand(commandString);
        assertEquals(new ClientNoteEditCommand(INDEX_FIRST_CLIENT, Index.fromOneBased(1), note), command);
    }

    @Test
    public void parseClientNoteCommands_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("client note"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("client note "));
    }

    @Test
    public void parseClientNoteCommands_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("client note unknownCommand"));
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("client note unknownCommand "));
    }

    @Test
    public void parseClientNoteCommands_deleteClientNote_returnsTrue() throws Exception {
        Index validClientIndex = Index.fromOneBased(1);
        Index validClientNoteIndex = Index.fromOneBased(12);
        final String commandString = ClientNoteDeleteCommand.COMMAND_WORD + " " + validClientIndex.getOneBased()
                + " " + validClientNoteIndex.getOneBased();

        ClientNoteDeleteCommand expectedCommand = new ClientNoteDeleteCommand(validClientIndex, validClientNoteIndex);
        ClientNoteDeleteCommand actualCommand = (ClientNoteDeleteCommand) parser.parseCommand(commandString);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parseCommand_clientSuggest() throws Exception {
        final String commandString = ClientSuggestCommand.COMMAND_WORD + " " + PREFIX_SUGGEST
                + ClientSuggestionType.BY_AVAILABLE;
        Set<ClientSuggestionType> clientSuggestionTypeSet = new LinkedHashSet<>();
        clientSuggestionTypeSet.add(new ClientSuggestionType(ClientSuggestionType.BY_AVAILABLE));

        ClientSuggestCommand command = (ClientSuggestCommand) parser.parseCommand(commandString);
        assertEquals(new ClientSuggestCommand(clientSuggestionTypeSet), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_clientList() throws Exception {
        assertTrue(parser.parseCommand(ClientListCommand.COMMAND_WORD) instanceof ClientListCommand);
        assertTrue(parser.parseCommand(ClientListCommand.COMMAND_WORD + " 3") instanceof ClientListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

}
