import java.util.Iterator;
import java.util.Stack;

/**
 * Class that implements a binary search tree which implements the MyMap
 * interface.
 * @author Tinklyn Xiang jx2553
 * @version 1.1.1 October 23, 2024
 */
public class BSTreeMap<K extends Comparable<K>, V> implements MyMap<K, V> {
    public static final int PREORDER = 1, INORDER = 2, POSTORDER = 3;
    protected Node<K, V> root;
    protected int size;

    /**
     * Creates an empty binary search tree map.
     */
    public BSTreeMap() { }

    /**
     * Creates a binary search tree map of the given key-value pairs.
     * @param elements an array of key-value pairs
     */
    public BSTreeMap(Pair<K, V>[] elements) {
        insertElements(elements);
    }

    /**
     * Creates a binary search tree map of the given key-value pairs. If
     * sorted is true, a balanced tree will be created. If sorted is false,
     * the pairs will be inserted in the order they are received.
     * @param elements an array of key-value pairs
     */
    public BSTreeMap(Pair<K, V>[] elements, boolean sorted) {
        if (!sorted) {
            insertElements(elements);
        } else {
            root = createBST(elements, 0, elements.length - 1);
        }
    }

    /**
     * Recursively constructs a balanced binary search tree by inserting the
     * elements via a divide-snd-conquer approach. The middle element in the
     * array becomes the root. The middle of the left half becomes the root's
     * left child. The middle element of the right half becomes the root's right
     * child. This process continues until low > high, at which point the
     * method returns a null Node.
     * @param pairs an array of <K, V> pairs sorted by key
     * @param low   the low index of the array of elements
     * @param high  the high index of the array of elements
     * @return      the root of the balanced tree of pairs
     */
    protected Node<K, V> createBST(Pair<K, V>[] pairs, int low, int high) {
        int counter = 0;
        if (low > high) {
            return null;
        }
        int mid = low + (high - low) / 2;
        Node<K, V> n = new Node<>(pairs[mid].key, pairs[mid].value);
        n.setLeft(createBST(pairs, low, mid - 1));
        if (n.getLeft() != null) {
            n.getLeft().setParent(n);
            }
        n.setRight(createBST(pairs, mid + 1, high));
        if (n.getRight() != null) {
            n.getRight().setParent(n);
        }
        size++;
        return n;
    }

    /**
     * Inserts the pairs into the tree in the order they appear in the given
     * array.
     * @param pairs the array of <K, V> pairs to insert
     */
    protected void insertElements(Pair<K, V>[] pairs) {
        for (Pair<K, V> pair : pairs) {
            put(pair);
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this map contains no key-value mappings.
     * @return true if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns a String of the key-value pairs visited with a preorder
     * traversal. Uses a StringBuilder for efficiency.
     * @return a String of the key-value pairs visited with a preorder
     *         traversal
     */
    public String preorder() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        preorder(root, builder, 0);
        builder.append("]");
        return builder.toString();
    }

    /**
     * Visits the Nodes of the tree in a preorder traversal. Each Node's
     * toString() return value should be appended to the StringBuilder. A ", "
     * must appear between each Node's data in the final String.
     * @param n            the current Node
     * @param builder      the StringBuilder used to build up the output
     * @param nodesVisited the number of nodes visited so far. Useful for
     *                     determining when to append ", ".
     * @return the number of nodes visited after each recursive call
     */
    private int preorder(Node<K, V> n, StringBuilder builder,
                         int nodesVisited) {
        if (n == null) {
            return nodesVisited;
        }
        if (nodesVisited > 0) {
            builder.append(", ");
        }
        builder.append(n.toString());
        nodesVisited++;
        nodesVisited = preorder(n.getLeft(), builder, nodesVisited);
        nodesVisited = preorder(n.getRight(), builder, nodesVisited);
        return nodesVisited;
    }

    /**
     * Returns a String of the key-value pairs visited with an inorder
     * traversal. Uses a StringBuilder for efficiency.
     * @return a String of the key-value pairs visited with an inorder
     *         traversal
     */
    public String inorder() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        inorder(root, builder, 0);
        builder.append("]");
        return builder.toString();
    }

    /**
     * Visits the Nodes of the tree in an inorder traversal. Each Node's
     * toString() return value should be appended to the StringBuilder. A ", "
     * must appear between each Node's data in the final String.
     * @param n            the current Node
     * @param builder      the StringBuilder used to build up the output
     * @param nodesVisited the number of nodes visited so far. Useful for
     *                     determining when to append ", ".
     * @return the number of nodes visited after each recursive call
     */
    private int inorder(Node<K, V> n, StringBuilder builder,
                        int nodesVisited) {
        if (n == null) {
            return nodesVisited;
        }
        nodesVisited = inorder(n.getLeft(), builder, nodesVisited);
        if (nodesVisited > 0) {
            builder.append(", ");
        }
        builder.append(n.toString());
        nodesVisited++;
        nodesVisited = inorder(n.getRight(), builder, nodesVisited);
        return nodesVisited;
    }

    /**
     * Returns a String of the key-value pairs visited with a postorder
     * traversal. Uses a StringBuilder for efficiency.
     * @return a String of the key-value pairs visited with a postorder
     *         traversal
     */
    public String postorder() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        postorder(root, builder, 0);
        builder.append("]");
        return builder.toString();
    }

    /**
     * Visits the Nodes of the tree in a postorder traversal. Each Node's
     * toString() return value should be appended to the StringBuilder. A ", "
     * must appear between each Node's data in the final String.
     * @param n            the current Node
     * @param builder      the StringBuilder used to build up the output
     * @param nodesVisited the number of nodes visited so far. Useful for
     *                     determining when to append ", ".
     * @return the number of nodes visited after each recursive call
     */
    private int postorder(Node<K, V> n, StringBuilder builder,
                          int nodesVisited) {
        if (n == null) {
            return nodesVisited;
        }
        nodesVisited = postorder(n.getLeft(), builder, nodesVisited);
        nodesVisited = postorder(n.getRight(), builder, nodesVisited);
        if (nodesVisited > 0) {
            builder.append(", ");
        }
        builder.append(n.toString());
        nodesVisited++;
        return nodesVisited;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     * @param  key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this
     *         map contains no mapping for the key
     */
    public V get(K key) {
        Node<K, V> x = iterativeSearch(key);
        return x != null ? x.value : null;
    }

    /**
     * Determines if the supplied key is found in the tree. If so, it returns a
     * reference to the Node containing the key. Otherwise, null is returned.
     * @param key key whose mapping is to be removed from the map
     * @return a reference to the Node containing the specified key
     */
    private Node<K, V> iterativeSearch(K key) {
        Node<K, V> current = root;
        if (current == null){
            return null;
        }
        while (current != null && key != current.key ) {
            if (key.compareTo(current.key) < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return current;
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is replaced
     * by the specified value.
     * @param pair  the key-value mapping to insert into the tree
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V put(Pair<K, V> pair) {
        return put(pair.key, pair.value);
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is replaced
     * by the specified value.
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V put(K key, V value) {
            Node<K, V> parent = null;
            Node<K, V> current = root;
            Node<K, V> newNode = new Node<>(key, value);
            while (current != null){
                parent = current;
                if (newNode.key.compareTo(current.key) < 0) {
                    current = current.getLeft();
                }else if (newNode.key.compareTo(current.key) > 0){
                    current = current.getRight();
                }else{
                    V hold = current.value;
                    current.value = newNode.value;
                    return hold;
                }
            }

//            current=newNode;
        newNode.setParent(parent);

            if (parent == null){
                root = newNode;
//                size++;
            } else if (newNode.key.compareTo(parent.key) < 0){
//                Node <K, V> oldValue = parent.getLeft();
                parent.setLeft(newNode);
//                size++;
//                return (V) oldValue;
            }else {
//                Node <K, V> oldValue = parent.getRight();
                parent.setRight(newNode);
//                size++;
//                return (V) oldValue;
            }
            size++;
            return null;
        }

    /**
     * Removes the mapping for a key from this map if it is present.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V remove(K key) {
        Node<K, V> current = root;
        Node<K, V> parent = null;

        while (current != null && !current.key.equals(key)) {
            parent = current;
            if (key.compareTo(current.key) < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        if (current == null) {
            return null;
        }

        V removedValue = current.value;

        if (current.getLeft() == null) {
            transplant(parent, current, current.getRight());
        }

        else if (current.getRight() == null) {
            transplant(parent, current, current.getLeft());
        }
        else {
            Node<K, V> past = treeMinimum(current.getRight());
            if (past.getParent() != current) {
                transplant(past.getParent(), past, past.getRight());
                past.setRight(current.getRight());
                past.getRight().setParent(past);
            }
            transplant(parent, current, past);
            past.setLeft(current.getLeft());
            past.getLeft().setParent(past);
        }
        size--;
        return removedValue;
    }

    private void transplant(Node<K, V> parent, Node<K, V> current, Node<K, V> newNode) {
        if (parent == null) {
            root = newNode;
        } else if (current == parent.getLeft()) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
        if (newNode != null) {
            newNode.setParent(parent);
        }
    }

    /**
     * Returns a reference to the Node whose key value is the minimum key in the
     * tree.
     * @param x the Node at which to start the traversal
     * @return a reference to the Node whose key value is the minimum key in the
     *         tree
     */
    protected Node<K, V> treeMinimum(Node<K, V> x) {
        while (x.getLeft() != null) {
            x = x.getLeft();
        }
        return x;
    }

    /**
     * Returns a String representation of the tree, where the Nodes are visited
     * with an inorder traversal.
     * @return a String representation of the tree
     */
    public String toString() {
        return inorder();
    }

    /**
     * Returns an ASCII drawing of the tree.
     * @return an ASCII drawing of the tree
     */
    public String toAsciiDrawing() {
        BinarySearchTreePrinter<K, V> printer =
                new BinarySearchTreePrinter<>();
        printer.createAsciiTree(root);
        return printer.toString();
    }

    public void printTraversal(int type) {
        switch (type) {
            case PREORDER -> {
                System.out.print("Preorder traversal:       ");
                System.out.println(preorder());
            }
            case INORDER -> {
                System.out.print("Inorder traversal:        ");
                System.out.println(inorder());
            }
            case POSTORDER -> {
                System.out.print("Postorder traversal:      ");
                System.out.println(postorder());
            }
        }
    }

    /**
     * Returns an iterator over the Entries in this map in the order
     * in which they appear.
     * @return an iterator over the Entries in this map
     */
    public Iterator<Entry<K, V>> iterator() {
        return new BinaryTreeItr();
    }

    private class BinaryTreeItr implements Iterator<Entry<K, V>> {
        private Node<K, V> current;
        private final Stack<Node<K, V>> parentStack = new Stack<>();

        BinaryTreeItr() {
            current = root;
        }

        @Override
        public boolean hasNext() {
            return !parentStack.isEmpty() || current != null;
        }

        @Override
        public Entry<K, V> next() {
            while (hasNext()) {
                if (current != null) {
                    parentStack.push(current);
                    current = current.getLeft();
                } else {
                    Node<K, V> toReturn = parentStack.pop();
                    current = toReturn.getRight();
                    return toReturn;
                }
            }
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns the height of the tree. If the tree is null, the height is -1.
     * @return the height of the tree
     */
    public int height() {
        return height(root) ;
    }

    protected int height(Node<K, V> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    /**
     * Returns the number of null references in the tree. Uses a recursive
     * helper method to count the null references.
     * @return the number of null references in the tree
     */
    public int nullCount() {
        return nullCount(root);
    }

    private int nullCount(Node<K, V> node) {
        if (node == null) {
            return 1;
        }
        return nullCount(node.getLeft()) + nullCount(node.getRight());
    }

    /**
     * Returns the sum of the levels of each non-null node in the tree starting
     * at the root.
     * For example, the tree
     *   5 <- level 0
     *  / \
     * 2   8 <- level 1
     *      \
     *      10 <- level 2
     * has sum 0 + 2(1) + 2 = 4.
     * @return the sum of the levels of each non-null node in the tree starting
     *         at the root
     */
    public int sumLevels() {
        return sumLevels(root, 0);
    }

    private int sumLevels(Node<K, V> node, int level) {
        if (node == null) {
            return 0;
        }
        int leftSum =  sumLevels(node.getLeft(), level + 1);
        int rightSum = sumLevels(node.getRight(), level + 1);
        return level + leftSum + rightSum;
    }

    /**
     * Returns the sum of the levels of each null node in the tree starting at
     * the root.
     * For example, the tree
     *    5 <- level 0
     *   / \
     *  2   8 <- level 1
     * / \ / \
     * * * * 10 <- level 2
     *       / \
     *       * * <- level 3
     * has sum 3(2) + 2(3) = 12.
     * @return the sum of the levels of each null node in the tree starting at
     *         the root
     *
     */
    public int sumNullLevels() {
        return sumNullLevels(root, 0);
    }

    private int sumNullLevels(Node<K, V> node, int level) {
        if (node == null) {
            return level;
        }

        int leftSum = sumNullLevels(node.getLeft(), level + 1);
        int rightSum = sumNullLevels(node.getRight(), level + 1);
        return leftSum + rightSum;
    }


    public double successfulSearchCost() {
        return size == 0 ? 0 : 1 + (double)sumLevels() / size;
    }

    public double unsuccessfulSearchCost() {
        return (double)sumNullLevels() / nullCount();
    }
}
