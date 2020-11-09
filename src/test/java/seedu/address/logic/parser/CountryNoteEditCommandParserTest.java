package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CountryNoteEditCommand;
import seedu.address.model.country.Country;
import seedu.address.model.note.CountryNote;
import seedu.address.model.note.TagNoteMap;
import seedu.address.model.tag.Tag;

public class CountryNoteEditCommandParserTest {

    private static final String INVALID_COMMAND_ERROR = "Invalid command format! \n"
            + "country note edit: "
            + "Edits the country note at the given index in the last viewed country note list panel.\n"
            + "Parameters: COUNTRY_NOTE_INDEX (nt/NOTE_STRING ) (t/TAG)...\n"
            + "Example: country note edit 1 nt/better government stability in recent months";
    private static final String INVALID_INDEX_ERROR = "Index is not a non-zero unsigned integer.";
    private static final String INVALID_TAG_ERROR = Tag.MESSAGE_CONSTRAINTS;
    private static final String INVALID_NOTE_ERROR = "Notes should not be blank";
    private final TagNoteMap tagNoteMap = new TagNoteMap();
    private final CountryNoteEditCommandParser parser = new CountryNoteEditCommandParser(tagNoteMap);


    @Test
    public void parse_withIndexWithNoteWithTag_returnsExpected() {
        CountryNote c = new CountryNote("abc", Country.NULL_COUNTRY);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("a"));
        c.setTags(tags);
        CountryNoteEditCommand expected = new CountryNoteEditCommand(Index.fromOneBased(1), c);

        assertParseSuccess(parser, " 1 nt/abc t/a", expected);
    }

    @Test
    public void parse_noIndexWithNoteNoTag_throwsParseException() {
        assertParseFailure(parser, " nt/abc", INVALID_COMMAND_ERROR);
        assertParseFailure(parser, " nt/y", INVALID_COMMAND_ERROR);
        assertParseFailure(parser, " abc nt/abc", INVALID_INDEX_ERROR);
        assertParseFailure(parser, " a 1 a nt/abc", INVALID_INDEX_ERROR);
    }

    @Test
    public void parse_withIndexNoNoteNoTag_throwsParseException() {
        assertParseFailure(parser, " 1  ", INVALID_COMMAND_ERROR);
        assertParseFailure(parser, " 1 t/", INVALID_TAG_ERROR);
        assertParseFailure(parser, " 1 nt/", INVALID_NOTE_ERROR);
        assertParseFailure(parser, " 1 nt/ t/", INVALID_TAG_ERROR);
    }

}
