package utest.evidencia1.clases;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircleLinkedListTest {

    @Test
    void constructorShouldStartEmpty() {
        CircleLinkedList<Integer> list = new CircleLinkedList<>();
        assertEquals(0, list.getSize());
        assertEquals("[  ]", list.toString());
    }

    @Test
    void appendShouldIncreaseSizeAndKeepOrder() {
        CircleLinkedList<Integer> list = new CircleLinkedList<>();

        list.append(10);
        list.append(20);
        list.append(30);

        assertEquals(3, list.getSize());
        assertEquals("[ 10 , 20 , 30 ]", list.toString());
    }

    @Test
    void appendNullShouldThrowException() {
        CircleLinkedList<String> list = new CircleLinkedList<>();

        assertThrows(NullPointerException.class, () -> list.append(null));
    }

    @Test
    void removeShouldReturnDeletedElementAndUpdateSize() {
        CircleLinkedList<String> list = new CircleLinkedList<>();
        list.append("A");
        list.append("B");
        list.append("C");

        String removed = list.remove(1);

        assertEquals("B", removed);
        assertEquals(2, list.getSize());
        assertEquals("[ A , C ]", list.toString());
    }

    @Test
    void removeWithInvalidPositionShouldThrowException() {
        CircleLinkedList<Integer> list = new CircleLinkedList<>();

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    void removeFirstElementShouldUpdateList() {
        CircleLinkedList<String> list = new CircleLinkedList<>();
        list.append("A");
        list.append("B");
        list.append("C");

        String removed = list.remove(0);

        assertEquals("A", removed);
        assertEquals(2, list.getSize());
        assertEquals("[ B , C ]", list.toString());
    }

    @Test
    void removeLastElementShouldUpdateTailAndList() {
        CircleLinkedList<Integer> list = new CircleLinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);

        Integer removed = list.remove(2);

        assertEquals(3, removed);
        assertEquals(2, list.getSize());
        assertEquals("[ 1 , 2 ]", list.toString());
    }

    @Test
    void removeOnEmptyListAtZeroShouldThrowNullPointerExceptionByCurrentImplementation() {
        CircleLinkedList<Integer> list = new CircleLinkedList<>();

        assertThrows(NullPointerException.class, () -> list.remove(0));
    }

    @Test
    void removeAtExactSizeReturnsNullAndDecrementsSizeByCurrentImplementation() {
        CircleLinkedList<Integer> list = new CircleLinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);

        Integer removed = list.remove(3);

        assertNull(removed);
        assertEquals(2, list.getSize());
        assertEquals("[ 1 , 2 ]", list.toString());
    }
}
