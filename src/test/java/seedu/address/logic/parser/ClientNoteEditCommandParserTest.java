package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClientNoteEditCommand;
import seedu.address.model.note.Note;
import seedu.address.model.note.TagNoteMap;
import seedu.address.model.tag.Tag;

class ClientNoteEditCommandParserTest {

    private static final String SPACE = " ";
    private static final String CLIENT_INDEX_STRING = "1";
    private static final Index CLIENT_INDEX = Index.fromOneBased(Integer.parseInt(CLIENT_INDEX_STRING));
    private static final String NOTE_INDEX_STRING = "1";
    private static final Index NOTE_INDEX = Index.fromOneBased(Integer.parseInt(NOTE_INDEX_STRING));
    private static final String NOTE_STRING = "yay this be a note";
    private static final String NO_CLIENT_INDEX_NO_NOTE_INDEX_NO_NOTE = "just an empty string here";
    private static final String HAS_CLIENT_INDEX_HAS_NOTE_INDEX_NO_NOTE = CLIENT_INDEX_STRING + SPACE
            + NOTE_INDEX_STRING + SPACE + " nope no note here";
    private static final String HAS_CLIENT_INDEX_HAS_NOTE_INDEX_HAS_NOTE = CLIENT_INDEX_STRING + SPACE
            + NOTE_INDEX_STRING + SPACE + PREFIX_NOTE + NOTE_STRING;
    private static final String EXPECTED_PARSE_FAILURE_MESSAGE = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ClientNoteEditCommand.MESSAGE_USAGE);

    private final TagNoteMap tagNoteMap = new TagNoteMap();
    private final ClientNoteEditCommandParser parser = new ClientNoteEditCommandParser(tagNoteMap);
    private final Tag testTag = new Tag("testTag");
    private Set<Tag> untaggedTags;
    private Set<Tag> tags;

    @BeforeEach
    public void setUp() {
        tags = new HashSet<>();
        tags.add(testTag);
        untaggedTags = new HashSet<>();
        untaggedTags.add(Tag.UNTAGGED);
    }

    @Test
    public void constructor() {
        assertThrows(NullPointerException.class, () -> new ClientNoteEditCommandParser(null));
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_passingOnlyASingleIndex_parseFailure() {
        assertParseFailure(parser, CLIENT_INDEX_STRING + SPACE + PREFIX_NOTE
                + SPACE + NOTE_STRING, EXPECTED_PARSE_FAILURE_MESSAGE);
    }

    @Test
    public void parse_emptyString_parseFailure() {
        assertParseFailure(parser, "", EXPECTED_PARSE_FAILURE_MESSAGE);
    }

    @Test
    public void parse_noClientIndexNoNoteIndexNoNote_parseFailure() {
        assertParseFailure(parser, NO_CLIENT_INDEX_NO_NOTE_INDEX_NO_NOTE, EXPECTED_PARSE_FAILURE_MESSAGE);
    }

    @Test
    public void parse_hasClientIndexHasNoteIndexNoNote_parseFailure() {
        assertParseFailure(parser, HAS_CLIENT_INDEX_HAS_NOTE_INDEX_NO_NOTE, EXPECTED_PARSE_FAILURE_MESSAGE);
    }

    @Test
    public void parse_hasClientIndexHasNoteIndexHasNote_equalsExpected() {
        Note expectedClientNote = new Note(NOTE_STRING);
        expectedClientNote.setTags(untaggedTags);
        ClientNoteEditCommand expected = new ClientNoteEditCommand(CLIENT_INDEX, NOTE_INDEX, expectedClientNote);
        assertParseSuccess(parser, HAS_CLIENT_INDEX_HAS_NOTE_INDEX_HAS_NOTE, expected);
    }

    @Test
    public void parse_hasClientIndexHasNoteIndexOnlyTags_equalsExpected() {
        Note expectedClientNote = new Note("");
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(testTag);
        expectedClientNote.setTags(expectedTags);
        ClientNoteEditCommand expectedCommand = new ClientNoteEditCommand(CLIENT_INDEX, NOTE_INDEX, expectedClientNote);
        String userInput = CLIENT_INDEX_STRING + SPACE + NOTE_INDEX_STRING + SPACE + PREFIX_TAG + testTag.tagName;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validUntaggedNote_parseSuccess() {
        Note expectedClientNote = new Note(NOTE_STRING);
        Set<Tag> untaggedSet = new HashSet<>();
        untaggedSet.add(Tag.UNTAGGED);
        expectedClientNote.setTags(untaggedSet);
        ClientNoteEditCommand expectedCommand = new ClientNoteEditCommand(CLIENT_INDEX, NOTE_INDEX, expectedClientNote);
        String userInput = CLIENT_INDEX_STRING + SPACE + NOTE_INDEX_STRING + SPACE + PREFIX_NOTE + NOTE_STRING;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validFormatTaggedNote_parseSuccess() {
        Note expectedClientNote = new Note(NOTE_STRING);
        expectedClientNote.setTags(tags);
        ClientNoteEditCommand expectedCommand = new ClientNoteEditCommand(CLIENT_INDEX, NOTE_INDEX, expectedClientNote);
        String userInput = CLIENT_INDEX_STRING + SPACE + NOTE_INDEX_STRING + SPACE + PREFIX_TAG
                + SPACE + testTag.tagName + SPACE + PREFIX_NOTE + NOTE_STRING;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /* todo: future tests:
     *  1. exception throwing tests
     *
     */

}
