import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.Timeout;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.stream.IntStream;
/**
 * Abstract test class for Set implementations.
 *
 * Implementing test classes must override the getIntegerSet method.
 *
 * @author Simon Larsén
 * @author Erik Vanhainen
 * @version 2018-12-16
 */
public abstract class SetTest {
    @Rule public Timeout globalTimeout = Timeout.seconds(5); // 5 seconds max per method tested

    private Set<Integer> set;
    private final int CAPACITY = 20;
    private int[] uniqueSetElements;
    private int[] elementsNotInSet;

    /**
     * Returns an implementation of Set that can hold at least 'minCapacity'
     * Integers.
     *
     * @param minCapacity The least amount of elements the Set must be able to
     * hold.
     * @return An implementation of Set.
     */
    protected abstract Set<Integer> getIntegerSet(int minCapacity);

    @Before
    public void setUp() {
        // Arrange
        set = getIntegerSet(CAPACITY);
        uniqueSetElements =
            new int[] {-234, 32, 443, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, -231};
        elementsNotInSet = Arrays.stream(uniqueSetElements).map(elem -> elem - 2).toArray();

        for (int elem : uniqueSetElements) {
            set.add(elem);
        }
    }

    @Test
    public void containsIsTrueWhenElementIsInSet() {
        // Arrange
        Arrays
            .stream(uniqueSetElements)
            // Act
            .mapToObj(elem -> set.contains(elem))
            // Assert
            .forEach(contained -> assertThat(contained, is(true)));
    }

    @Test
    public void containsIsFalseWhenElementIsNotInSet() {
        for(int elem : elementsNotInSet) {
            boolean contained = set.contains(elem);
            assertThat(contained, is(false));
        }
    }

    @Test
    public void containsIsFalseForRemovedElements() {
        // Arrange
        for (int elem : uniqueSetElements) {
            // Act
            set.remove(elem);
            boolean contained = set.contains(elem);
            // Assert
            assertThat(contained, is(false));
        }
    }

    @Test
    public void addUniqueElementsIncrementsSize() {
        // Arrange
        // Note how the local set variable shadows the field!
        Set<Integer> set = getIntegerSet(CAPACITY);
        int expectedSize = 0;
        for (int elem : uniqueSetElements) {
            expectedSize++;
            // Act
            set.add(elem);
            // Assert
            assertThat(set.size(), equalTo(expectedSize));
        }
    }

    @Test
    public void addDuplicatesDoesNotIncreaseSize() {
        // Arrange
        int expectedSize = uniqueSetElements.length;
        for (int elem : uniqueSetElements) {
            // Act
            set.add(elem);
        }
        // Assert
        assertThat(set.size(), equalTo(expectedSize));
    }

    @Test
    public void removeElementsInSetDecrementsSize() {
        for(int elem : uniqueSetElements) {
            int tempSize = set.size();
            set.remove(elem);
            assertThat(set.size(), is(tempSize - 1));
        }
    }

    @Test
    public void removeElementsNotInSetDoesNotDecrementSize() {
        // Arrange
        int expectedSize = uniqueSetElements.length;
        for (int elem : elementsNotInSet) {
            // Act
            set.remove(elem);
        }
        // Assert
        assertThat(set.size(), equalTo(expectedSize));
    }

    @Test
    public void removeElementsDoesNotDecrementSizeWhenSetIsEmpty() {
        for(int elem : uniqueSetElements) {
            set.remove(elem);
        }
        for(int elem : elementsNotInSet) {
            assertThat(set.size(), equalTo(0));
        }
    }

    @Test
    public void addIsTrueWhenElementNotInSet() {
        // Arrange
        Set<Integer> set = getIntegerSet(CAPACITY);
        Arrays
            .stream(uniqueSetElements)
            // Act
            .mapToObj(elem -> set.add(elem))
            // Assert
            .forEach(wasAdded -> assertThat(wasAdded, is(true)));
    }

    @Test
    public void addIsFalseForDuplicates() {
        // Arrange
        Arrays
            .stream(uniqueSetElements)
            // Act
            .mapToObj(elem -> set.add(elem))
            // Assert
            .forEach(wasAdded -> assertThat(wasAdded, is(false)));
    }

    @Test
    public void removeIsTrueWhenElementIsInSet() {
        // Arrange
        Arrays
            .stream(uniqueSetElements)
            // Act
            .mapToObj(elem -> set.remove(elem))
            // Assert
            .forEach(wasRemoved -> assertThat(wasRemoved, is(true)));
    }

    @Test
    public void removeIsFalseWhenElementIsNotInSet() {
        for (int elem : elementsNotInSet) {
            boolean contained = set.remove(elem);
            assertThat(contained, is(false));
        }
    }

    @Test
    public void removeIsFalseWhenSetIsEmpty() {
        // Arrange
        Set<Integer> emptySet = getIntegerSet(CAPACITY);
        Arrays
            .stream(uniqueSetElements)
            // Act
            .mapToObj(elem -> emptySet.remove(elem))
            // Assert
            .forEach(wasRemoved -> assertThat(wasRemoved, is(false)));
    }
}
