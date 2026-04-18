package utest.evidencia1.clases;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DynamicArrayTest {

    @Test
    void addGetAndPutShouldWork() {
        DynamicArray<String> array = new DynamicArray<>(2);
        array.add("uno");
        array.add("dos");
        array.add("tres");

        array.put(1, "DOS");

        assertEquals(3, array.getSize());
        assertEquals("uno", array.get(0));
        assertEquals("DOS", array.get(1));
        assertEquals("tres", array.get(2));
    }

    @Test
    void removeShouldReturnElementAndDecreaseSize() {
        DynamicArray<Integer> array = new DynamicArray<>();
        array.add(100);
        array.add(200);
        array.add(300);

        Integer removed = array.remove(1);

        assertEquals(200, removed);
        assertEquals(2, array.getSize());
        assertEquals(300, array.get(1));
    }

    @Test
    void isEmptyShouldReflectContent() {
        DynamicArray<String> array = new DynamicArray<>();
        assertTrue(array.isEmpty());
        array.add("x");
        assertFalse(array.isEmpty());
    }

    @SuppressWarnings("unchecked")
    @Test
    void iteratorForEachRemainingShouldCallConsumerForEachElement() {
        DynamicArray<String> array = new DynamicArray<>();
        array.add("A");
        array.add("B");
        array.add("C");
        Consumer<String> consumer = mock(Consumer.class);

        Iterator<String> iterator = array.iterator();
        iterator.forEachRemaining(consumer);

        verify(consumer, times(1)).accept("A");
        verify(consumer, times(1)).accept("B");
        verify(consumer, times(1)).accept("C");
    }

    @Test
    void toStringShouldSkipNullValues() {
        DynamicArray<String> array = new DynamicArray<>(4);
        array.put(0, "X");
        array.put(2, "Y");

        assertEquals("[X, Y]", array.toString());
    }

    @Test
    void streamShouldIterateInsertedValues() {
        DynamicArray<Integer> array = new DynamicArray<>();
        array.add(1);
        array.add(2);
        array.add(3);

        int sum = array.stream().mapToInt(Integer::intValue).sum();

        assertEquals(6, sum);
    }

    @Test
    void iteratorForEachRemainingNullConsumerShouldThrow() {
        DynamicArray<String> array = new DynamicArray<>();
        array.add("A");
        Iterator<String> iterator = array.iterator();

        assertThrows(NullPointerException.class, () -> iterator.forEachRemaining(null));
    }

    @Test
    void iteratorHasNextAndNextShouldAdvanceCursor() {
        DynamicArray<String> array = new DynamicArray<>();
        array.add("one");
        array.add("two");
        Iterator<String> iterator = array.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("one", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("two", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorNextShouldThrowNoSuchElementExceptionOnThirdCallWithSingleValueByCurrentImplementation() {
        DynamicArray<String> array = new DynamicArray<>();
        array.add("one");
        Iterator<String> iterator = array.iterator();

        assertEquals("one", iterator.next());
        // Current implementation returns null in second call.
        assertEquals(null, iterator.next());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorRemoveShouldRemoveCurrentIndexByCurrentImplementation() {
        DynamicArray<String> array = new DynamicArray<>();
        array.add("A");
        array.add("B");
        array.add("C");
        Iterator<String> iterator = array.iterator();

        iterator.remove();

        assertEquals(2, array.getSize());
        assertEquals("B", array.get(0));
        assertEquals("C", array.get(1));
    }

    @Test
    void iteratorDoubleRemoveShouldThrowIllegalStateException() {
        DynamicArray<String> array = new DynamicArray<>();
        array.add("A");
        Iterator<String> iterator = array.iterator();

        iterator.remove();
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    void removeShouldShrinkInternallyAfterManyDeletions() {
        DynamicArray<Integer> array = new DynamicArray<>(32);
        for (int i = 0; i < 20; i++) {
            array.add(i);
        }
        for (int i = 0; i < 14; i++) {
            array.remove(0);
        }

        assertEquals(6, array.getSize());
        assertEquals(14, array.get(0));
        assertEquals(19, array.get(5));
    }

    @SuppressWarnings("unchecked")
    @Test
    void iteratorForEachRemainingOnEmptyArrayShouldNotInvokeConsumer() {
        DynamicArray<String> array = new DynamicArray<>();
        Consumer<String> consumer = mock(Consumer.class);
        Iterator<String> iterator = array.iterator();

        iterator.forEachRemaining(consumer);

        verify(consumer, never()).accept(anyString());
    }

    @Test
    void iteratorNextShouldThrowConcurrentModificationWhenCursorAboveElementsLength() throws Exception {
        DynamicArray<String> array = new DynamicArray<>(2);
        array.add("A");
        Iterator<String> iterator = array.iterator();

        Field cursorField = iterator.getClass().getDeclaredField("cursor");
        cursorField.setAccessible(true);
        cursorField.setInt(iterator, 5);

        Field sizeField = DynamicArray.class.getDeclaredField("size");
        sizeField.setAccessible(true);
        sizeField.setInt(array, 10);

        assertThrows(java.util.ConcurrentModificationException.class, iterator::next);
    }
}
