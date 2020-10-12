package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

/**
 * A set of tags that not allow nulls.
 * A tag is considered unique by comparing using {@code Tag#equals(Object)}, which compares tagNames.
 *
 * Supports some set operations.
 */
public class UniqueTagSet implements Iterable<Tag> {

    private final ObservableSet<Tag> internalSet = FXCollections.observableSet();
    private final Set<Tag> internalUnmodifiableSet = FXCollections.unmodifiableObservableSet(internalSet);

    /**
     * Returns true if the set contains an equivalent tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return internalSet.contains(toCheck);
    }

    /**
     * Union with another tag set.
     */
    public void addAll(Set<Tag> toAdd) {
        requireAllNonNull(toAdd);
        internalSet.addAll(toAdd);
    }

    /**
     * Returns an unmodifiable set of unique tag objects from the backing set that match the set of tags passed in.
     *
     * @param toGet The set of tag objects to get
     * @return An unmodifiable set containing the unique tag objects
     */
    public Set<Tag> getTags(Set<Tag> toGet) {
        requireAllNonNull(toGet);
        return internalSet.stream().filter(toGet::contains).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Replaces the contents of this set with {@code tags}.
     */
    public void setTags(Set<Tag> tags) {
        requireAllNonNull(tags);
        internalSet.clear();
        internalSet.addAll(tags);
    }

    /**
     * Returns the backing set as an unmodifiable {@code Set}.
     */
    public Set<Tag> asUnmodifiableSet() {
        return internalUnmodifiableSet;
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalSet.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTagSet // instanceof handles nulls
                && internalSet.equals(((UniqueTagSet) other).internalSet));
    }

    @Override
    public int hashCode() {
        return internalSet.hashCode();
    }
}
