package utest.evidencia1.clases;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

class BinaryTreeTest {

    @Test
    void defaultConstructorShouldCreateEmptyTree() {
        BinaryTree tree = new BinaryTree();
        assertNull(tree.getRoot());
    }

    @Test
    void parameterizedConstructorShouldUseProvidedRoot() {
        BinaryTree.Node root = new BinaryTree.Node(99);
        BinaryTree tree = new BinaryTree(root);
        assertEquals(99, tree.getRoot().data);
    }

    @Test
    void putAndFindShouldLocateValuesAndParents() {
        BinaryTree tree = createSampleTree();

        BinaryTree.Node found = tree.find(12);
        BinaryTree.Node parentForMissing = tree.find(13);

        assertEquals(12, found.data);
        assertEquals(12, parentForMissing.data);
    }

    @Test
    void findShouldReturnNullWhenTreeIsEmpty() {
        BinaryTree tree = new BinaryTree();
        assertNull(tree.find(10));
    }

    @Test
    void removeShouldHandleLeafAndValueNotPresent() {
        BinaryTree tree = createSampleTree();

        assertTrue(tree.remove(3));
        assertFalse(tree.remove(999));
    }

    @Test
    void removeOnEmptyTreeShouldThrowNullPointerExceptionByCurrentImplementation() {
        BinaryTree tree = new BinaryTree();
        assertThrows(NullPointerException.class, () -> tree.remove(1));
    }

    @Test
    void removeRootLeafShouldEmptyTree() {
        BinaryTree tree = new BinaryTree();
        tree.put(1);

        boolean removed = tree.remove(1);

        assertTrue(removed);
        assertNull(tree.getRoot());
    }

    @Test
    void removeNonRootLeafShouldDetachFromParent() {
        BinaryTree tree = new BinaryTree();
        tree.put(10);
        tree.put(5);
        tree.put(15);
        tree.put(12);

        boolean removed = tree.remove(12);

        assertTrue(removed);
        assertNull(tree.find(15).left);
    }

    @Test
    void removeRootWithOnlyRightChildShouldPromoteChild() {
        BinaryTree tree = new BinaryTree();
        tree.put(10);
        tree.put(15);

        boolean removed = tree.remove(10);

        assertTrue(removed);
        assertEquals(15, tree.getRoot().data);
    }

    @Test
    void removeRootWithOnlyLeftChildShouldPromoteChild() {
        BinaryTree tree = new BinaryTree();
        tree.put(10);
        tree.put(5);

        boolean removed = tree.remove(10);

        assertTrue(removed);
        assertEquals(5, tree.getRoot().data);
    }

    @Test
    void removeNonRootWithOnlyRightChildShouldRelinkParent() {
        BinaryTree tree = new BinaryTree();
        tree.put(10);
        tree.put(5);
        tree.put(7);

        boolean removed = tree.remove(5);

        assertTrue(removed);
        BinaryTree.Node root = tree.getRoot();
        assertEquals(7, root.left.data);
        assertEquals(10, root.left.parent.data);
    }

    @Test
    void removeNonRootWithOnlyLeftChildShouldRelinkParent() {
        BinaryTree tree = new BinaryTree();
        tree.put(10);
        tree.put(15);
        tree.put(12);

        boolean removed = tree.remove(15);

        assertTrue(removed);
        BinaryTree.Node root = tree.getRoot();
        assertEquals(12, root.right.data);
        assertEquals(10, root.right.parent.data);
    }

    @Test
    void removeRootWithTwoChildrenShouldReplaceRoot() {
        BinaryTree tree = createSampleTree();

        boolean removed = tree.remove(10);

        assertTrue(removed);
        assertEquals(12, tree.getRoot().data);
        assertNull(tree.getRoot().parent);
    }

    @Test
    void removeRootWithTwoChildrenWhenSuccessorIsDirectRightChild() {
        BinaryTree tree = new BinaryTree();
        tree.put(10);
        tree.put(5);
        tree.put(15);

        boolean removed = tree.remove(10);

        assertTrue(removed);
        assertEquals(15, tree.getRoot().data);
        assertEquals(5, tree.getRoot().left.data);
    }

    @Test
    void removeRootWithTwoChildrenWhenSuccessorHasRightChild() {
        BinaryTree tree = new BinaryTree();
        tree.put(50);
        tree.put(30);
        tree.put(70);
        tree.put(60);
        tree.put(65);

        boolean removed = tree.remove(50);

        assertTrue(removed);
        assertEquals(60, tree.getRoot().data);
        assertEquals(65, tree.getRoot().right.left.data);
    }

    @Test
    void removeNonRootWithTwoChildrenShouldUpdateParentReference() {
        BinaryTree tree = new BinaryTree();
        tree.put(40);
        tree.put(20);
        tree.put(60);
        tree.put(10);
        tree.put(30);
        tree.put(25);

        boolean removed = tree.remove(20);

        assertTrue(removed);
        assertEquals(25, tree.getRoot().left.data);
        assertEquals(40, tree.getRoot().left.parent.data);
    }

    @Test
    void findSuccessorShouldReturnMinOfRightSubtree() {
        BinaryTree tree = createSampleTree();
        BinaryTree.Node successor = tree.findSuccessor(tree.find(10));

        assertEquals(12, successor.data);
    }

    @Test
    void findSuccessorWithoutRightChildShouldReturnSameNode() {
        BinaryTree tree = createSampleTree();
        BinaryTree.Node node = tree.find(7);
        BinaryTree.Node successor = tree.findSuccessor(node);
        assertEquals(7, successor.data);
    }

    @Test
    void inOrderShouldPrintValuesInAscendingOrder() {
        BinaryTree tree = createSampleTree();
        PrintStream originalOut = System.out;
        PrintStream mockedOut = mock(PrintStream.class);
        System.setOut(mockedOut);

        try {
            tree.inOrder(tree.getRoot());

            InOrder order = inOrder(mockedOut);
            order.verify(mockedOut).print("3 ");
            order.verify(mockedOut).print("5 ");
            order.verify(mockedOut).print("7 ");
            order.verify(mockedOut).print("10 ");
            order.verify(mockedOut).print("12 ");
            order.verify(mockedOut).print("15 ");
            order.verify(mockedOut).print("20 ");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void preOrderShouldPrintRootLeftRight() {
        BinaryTree tree = createSampleTree();
        PrintStream originalOut = System.out;
        PrintStream mockedOut = mock(PrintStream.class);
        System.setOut(mockedOut);

        try {
            tree.preOrder(tree.getRoot());

            InOrder order = inOrder(mockedOut);
            order.verify(mockedOut).print("10 ");
            order.verify(mockedOut).print("5 ");
            order.verify(mockedOut).print("3 ");
            order.verify(mockedOut).print("7 ");
            order.verify(mockedOut).print("15 ");
            order.verify(mockedOut).print("12 ");
            order.verify(mockedOut).print("20 ");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void postOrderShouldPrintLeftRightRoot() {
        BinaryTree tree = createSampleTree();
        PrintStream originalOut = System.out;
        PrintStream mockedOut = mock(PrintStream.class);
        System.setOut(mockedOut);

        try {
            tree.postOrder(tree.getRoot());

            InOrder order = inOrder(mockedOut);
            order.verify(mockedOut).print("3 ");
            order.verify(mockedOut).print("7 ");
            order.verify(mockedOut).print("5 ");
            order.verify(mockedOut).print("12 ");
            order.verify(mockedOut).print("20 ");
            order.verify(mockedOut).print("15 ");
            order.verify(mockedOut).print("10 ");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void bfsShouldPrintRootRightLeftByCurrentImplementation() {
        BinaryTree tree = createSampleTree();
        PrintStream originalOut = System.out;
        PrintStream mockedOut = mock(PrintStream.class);
        System.setOut(mockedOut);

        try {
            tree.bfs(tree.getRoot());

            InOrder order = inOrder(mockedOut);
            order.verify(mockedOut).print("10 ");
            order.verify(mockedOut).print("15 ");
            order.verify(mockedOut).print("5 ");
            order.verify(mockedOut).print("20 ");
            order.verify(mockedOut).print("12 ");
            order.verify(mockedOut).print("7 ");
            order.verify(mockedOut).print("3 ");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void traversalsWithNullRootShouldNotPrintAnything() {
        BinaryTree tree = new BinaryTree();
        PrintStream originalOut = System.out;
        PrintStream mockedOut = mock(PrintStream.class);
        System.setOut(mockedOut);

        try {
            tree.inOrder(null);
            tree.preOrder(null);
            tree.postOrder(null);
            tree.bfs(null);
            verifyNoInteractions(mockedOut);
        } finally {
            System.setOut(originalOut);
        }
    }

    private BinaryTree createSampleTree() {
        BinaryTree tree = new BinaryTree();
        tree.put(10);
        tree.put(5);
        tree.put(15);
        tree.put(3);
        tree.put(7);
        tree.put(12);
        tree.put(20);
        return tree;
    }
}
