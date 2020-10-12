package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class UniqueTagSetTest {
    private final UniqueTagSet uniqueTagSet = new UniqueTagSet();

    @Test
    public void contains_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagSet.contains(null));
    }

    @Test
    public void contains_tagNotInSet_returnsFalse() {
        Tag tag = new Tag("meeting");
        assertFalse(uniqueTagSet.contains(tag));
    }

    @Test
    public void contains_tagInSet_returnsTrue() {
        Tag tag = new Tag("meeting");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);
        uniqueTagSet.addAll(tags);
        assertTrue(uniqueTagSet.contains(tag));
    }

    @Test
    public void addAll_nullTagSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagSet.addAll(null));
    }

    @Test
    public void addAll_tagSetWithNull_throwsNullPointerException() {
        Set<Tag> tags = new HashSet<>();
        tags.add(null);
        assertThrows(NullPointerException.class, () -> uniqueTagSet.addAll(tags));
    }

    @Test
    public void getTags_nullTagSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagSet.getTags(null));
    }

    @Test
    public void getTags_tagSetWithNull_throwsNullPointerException() {
        Set<Tag> tags = new HashSet<>();
        tags.add(null);
        assertThrows(NullPointerException.class, () -> uniqueTagSet.getTags(tags));
    }

    @Test
    public void getTags_tagSet_getCorrectUniqueTags() {
        // Add new tags for the very first time
        Tag meeting = new Tag("meeting");
        Tag business = new Tag("business");
        Set<Tag> originalTags = new HashSet<>();
        originalTags.add(meeting);
        originalTags.add(business);
        uniqueTagSet.addAll(originalTags);

        // New set of local tags
        Tag meeting2 = new Tag("meeting");
        Tag business2 = new Tag("business");
        Set<Tag> localTags = new HashSet<>();
        localTags.add(meeting2);
        localTags.add(business2);

        // Get unique tags from the set of local tags, assert that it is equal to both original and local tags
        Set<Tag> uniqueTags = uniqueTagSet.getTags(localTags);
        assertEquals(originalTags, uniqueTags);
        assertEquals(localTags, uniqueTags);

        // Assert that unique tags are same objects as original tags
        for (Tag originalTag : originalTags) {
            for (Tag uniqueTag : uniqueTags) {
                if (uniqueTag.equals(originalTag)) {
                    assertSame(uniqueTag, originalTag);
                }
            }
        }

        // Assert that unique tags are different objects from local tags
        for (Tag localTag : localTags) {
            for (Tag uniqueTag : uniqueTags) {
                if (uniqueTag.equals(localTag)) {
                    assertNotSame(uniqueTag, localTag);
                }
            }
        }
    }

    @Test
    public void setTags_nullTagSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagSet.setTags(null));
    }

    @Test
    public void setTags_tagSetWithNull_throwsNullPointerException() {
        Set<Tag> tags = new HashSet<>();
        tags.add(null);
        assertThrows(NullPointerException.class, () -> uniqueTagSet.setTags(tags));
    }

    @Test
    public void setTags_tagSet_replacesOwnSetWithProvidedTagSet() {
        Tag businessTag = new Tag("business");
        Tag meetingTag = new Tag("meeting");
        Set<Tag> tags = new HashSet<>();
        tags.add(businessTag);
        uniqueTagSet.addAll(tags);

        Set<Tag> newTags = new HashSet<>();
        newTags.add(meetingTag);
        uniqueTagSet.setTags(newTags);
        assertEquals(uniqueTagSet.asUnmodifiableSet(), newTags);
    }

    @Test
    public void asUnmodifiableSet_modifySet_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueTagSet.asUnmodifiableSet().clear());
    }
}
