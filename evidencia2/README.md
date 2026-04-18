# Unit Tests with Mockito

## Objective
Implement complete unit tests for:

- `BinaryTree`
- `CircleLinkedList`
- `DynamicArray`

The suite covers normal flows, boundary conditions, error paths, and branch-heavy cases.
Mockito is used to verify interactions in traversal output and iterator consumers.

## Test Cases by Class

### 1) `CircleLinkedListTest`
File: `src/test/java/utest/evidencia1/clases/CircleLinkedListTest.java`

Covered cases:

1. Constructor initial state (`getSize` and string representation).
2. Append multiple values and keep insertion order.
3. Append `null` must throw `NullPointerException`.
4. Remove middle position (normal case).
5. Remove first position.
6. Remove last position and tail update behavior.
7. Remove with negative position (error case).
8. Remove with position greater than size (error case).
9. Remove on empty list at index `0` (current implementation throws `NullPointerException`).
10. Remove at index equal to size (documented current behavior in implementation).

### 2) `DynamicArrayTest`
File: `src/test/java/utest/evidencia1/clases/DynamicArrayTest.java`

Covered cases:

1. `add/get/put` normal flow.
2. `remove(index)` normal flow and left-shift validation.
3. `isEmpty` before/after insert.
4. `toString` ignores null slots.
5. `stream()` iteration and aggregation.
6. Iterator `hasNext/next` sequence.
7. Iterator `forEachRemaining` with Mockito `Consumer` verification.
8. Iterator `forEachRemaining(null)` throws `NullPointerException`.
9. Iterator behavior at end of sequence (documented current implementation path to `NoSuchElementException`).
10. Iterator `remove()` behavior (current implementation semantics).
11. Iterator double `remove()` throws `IllegalStateException`.
12. Internal shrink scenario after many removals.
13. `forEachRemaining` on empty array does not call consumer (Mockito `never`).
14. Forced concurrent modification branch using reflection to trigger `ConcurrentModificationException`.

### 3) `BinaryTreeTest`
File: `src/test/java/utest/evidencia1/clases/BinaryTreeTest.java`

Covered cases:

1. Default constructor creates empty tree.
2. Parameterized constructor sets root.
3. `put + find` for existing value.
4. `find` for missing value returns expected parent.
5. `find` on empty tree returns `null`.
6. `remove` leaf node.
7. `remove` missing value returns `false`.
8. `remove` on empty tree (current implementation throws `NullPointerException`).
9. `remove` root when it is a leaf.
10. `remove` non-root leaf and parent unlink.
11. `remove` root with only right child.
12. `remove` root with only left child.
13. `remove` non-root with only right child.
14. `remove` non-root with only left child.
15. `remove` root with 2 children (successor replacement).
16. `remove` root with 2 children when successor is direct right child.
17. `remove` root with 2 children when successor has right child.
18. `remove` non-root with 2 children.
19. `findSuccessor` normal case (minimum on right subtree).
20. `findSuccessor` when node has no right child.
21. `inOrder` traversal output order verified with Mockito (`PrintStream`).
22. `preOrder` traversal output order verified with Mockito.
23. `postOrder` traversal output order verified with Mockito.
24. `bfs` traversal output order verified with Mockito (according to current implementation).
25. Traversals with `null` root produce no output (`verifyNoInteractions`).

## Command to Run Tests
Run in project root (`evidencia2`):

```bash
mvn test
```

## Evidence Image
Place test execution screenshot at:

`docs/img/test-run.png`

Reference:

```markdown
![Test run result](docs/img/test-run.png)
```

Example:

![Test run result](docs/img/test-run.png)
