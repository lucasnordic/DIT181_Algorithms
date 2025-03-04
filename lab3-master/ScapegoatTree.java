import java.util.ArrayList;

/**
 *  The {@code ScapegoatTree} class represents an ordered map of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>size</em> and <em>isEmpty</em> methods.
 *  It does not support <em>delete</em>, however.
 *  It also supports <em>min</em> and <em>max</em> for finding the
 *  smallest and largest keys in the map.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  <p>
 *  This implementation uses a scapegoat tree. It requires that
 *  the key type implements the {@code Comparable} interface and calls the
 *  {@code compareTo()} and method to compare two keys. It does not call either
 *  {@code equals()} or {@code hashCode()}.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Nick Smallbone
 *  @author Christian Sattler
 *  @author Lucas Nordgren
 */
public class ScapegoatTree<Key extends Comparable<Key>, Value> {

    // How unbalanced the tree may become (must be at least 1).
    final double alpha = 2;

    // In addition to being a binary search tree,
    // a scapegoat tree satisfies the following invariant every node:
    //   height - 1 <= alpha * log_2(size).

    private Node root;             // root of BST
    private int rebuilds;          // number of times rebuild() was called, for statistics

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree
        private int height;        // height of subtree

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.left = null;
            this.right = null;
            this.size = 1;
            this.height = 1;
        }
    }

    /**
     * Initializes an empty map.
     */
    public ScapegoatTree() {
    }

    /**
     * Returns true if this map is empty.
     * @return {@code true} if this map is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the number of key-value pairs in this map.
     * @return the number of key-value pairs in this map
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null)
            return 0;
        return x.size;
    }

    /**
     * Does this map contain the given key?
     *
     * @param  key the key
     * @return {@code true} if this map contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null)
            throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the map
     *         and {@code null} if the key is not in the map
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (key == null)
            throw new IllegalArgumentException("calls get() with a null key");
        if (x == null)
            return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return get(x.left, key);
        if (cmp > 0)
            return get(x.right, key);
        return x.val;
    }

    /**
     * Inserts the specified key-value pair into the map, overwriting the old
     * value with the new value if the map already contains the specified key.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key, val);
        assert check();
    }

    private Node put(Node node, Key key, Value val) {
        if (node == null)
            return new Node(key, val);

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, val);
        } else if (cmp > 0) {
            node.right = put(node.right, key, val);
        } else {
            node.val = val;
        }
        node.size = 1 + size(node.left) + size(node.right);
        node.height = 1 + Math.max(height(node.left), height(node.right));
        if (!(height(node) - 1 <= alpha * log2(size(node)))) {
            node = rebuild(node);
        }

        return node;
    }

    // Rebuild a tree to make it perfectly balanced.
    // You do not need to change this method, but need to define 'inorder'
    // and 'balanceNodes'.
    //
    // Important: this method *returns* the rebuilt tree!
    // So after you call this method, make sure to use the return value,
    // not the node that you passed in as a parameter.
    private Node rebuild(Node node) {
        rebuilds++; // update statistics

        ArrayList<Node> nodes = new ArrayList<Node>(size(node));
        inorder(node, nodes);
        return balanceNodes(nodes, 0, nodes.size()-1);
    }

    // Perform an inorder traversal of the subtree rooted at 'node',
    // storing its nodes into the ArrayList 'nodes'.
    private void inorder(Node node, ArrayList<Node> nodes) {
        if (node == null) {
            return;
        }

        inorder(node.left, nodes);
        nodes.add(node);
        inorder(node.right,nodes);
    }

    // Given an array of nodes, and two indexes 'lo' and 'hi',
    // return a balanced BST containing all nodes in the subarray
    // nodes[lo]..nodes[hi].
    private Node balanceNodes(ArrayList<Node> nodes, int lo, int hi) {
        // Base case: empty subarray.
        if (lo > hi)
            return null;

        // Midpoint of subarray.
        int mid = (lo+hi)/2;

        //2.  New node is mid node.
        Node node = nodes.get(mid);

        node.left = balanceNodes(nodes, lo, mid-1);
        node.right = balanceNodes(nodes, mid+1, hi);

        // Set height and size.
        node.size = 1 + size(node.left) + size(node.right);
        node.height = 1 + Math.max(height(node.left), height(node.right));

        return node;
    }

    // Returns the binary logarithm of a number.
    private static double log2(int n) {
        return Math.log(n) / Math.log(2);
    }

    /**
     * Returns all keys in the map as an {@code Iterable}.
     * To iterate over all of the keys in the map named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the map
     */
    public Iterable<Key> keys() {
        ArrayList<Key> queue = new ArrayList<Key>();
        keys(root, queue);
        return queue;
    }

    private void keys(Node x, ArrayList<Key> queue) {
        if (x == null)
            return;

        keys(x.left, queue);
        queue.add(x.key);
        keys(x.right, queue);
    }

    /**
     * Returns the height of the binary tree.
     *
     * @return the height of the binary tree (an empty tree has height 0)
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null)
            return 0;
        return x.height;
    }

    /**
     * Returns the number of times the rebuild method has been called
     * (for debugging only).
     * @return the number of times the rebuild method has been called
     */
    public int rebuilds() {
        return rebuilds;
    }

    /**
     * Returns some statistics about the BST (for debugging).
     *
     * @return information about the BST.
     */
    public String statistics() {
        return String.format("scapegoat tree | size:%-7d | height:%-4d | rebuilds:%-4d", size(), height(), rebuilds());
    }

  /*************************************************************************
    *  Check integrity of scapegoat data structure.
    ***************************************************************************/
    private boolean check() {
        if (!isBST())              System.out.println("Not in symmetric order");
        if (!isSizeConsistent())   System.out.println("Subtree counts not consistent");
        if (!isHeightConsistent()) System.out.println("Heights not consistent");
        if (!isBalanced())         System.out.println("Not balanced");
        return isBST() && isSizeConsistent() && isHeightConsistent() && isBalanced();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        return x.size == size(x.left) + size(x.right) + 1
            && isSizeConsistent(x.left)
            && isSizeConsistent(x.right);
    }

    // are the height fields correct?
    private boolean isHeightConsistent() { return isHeightConsistent(root); }
    private boolean isHeightConsistent(Node x) {
        if (x == null) return true;
        return x.height == 1 + Math.max(height(x.left), height(x.right))
            && isHeightConsistent(x.left)
            && isHeightConsistent(x.right);
    }

    // is the tree sufficiently balanced?
    private boolean isBalanced() { return isBalanced(root); }
    private boolean isBalanced(Node x) {
        if (x == null) return true;
        return x.height - 1 <= alpha * log2(x.size)
            && isBalanced(x.left)
            && isBalanced(x.right);
    }

    public static void main(String[] args) {
        ScapegoatTree<String, Integer> test = new ScapegoatTree();

        test.put("d",1);
        test.put("d",4);
        test.put("d",1);
        test.put("d",1);
        test.put("d",2);

        test.put("a",1);
        test.put("b",1);
        test.put("c",1);
        test.put("c",1);

        test.put("e",1);
        test.put("j",1);
        test.put("k",1);
        test.put("l",1);

        test.put("i",1);
        test.put("o",1);
        test.put("n",1);
        test.put("m",1);

        test.put("x",1);
        test.put("v",1);
        test.put("u",1);
        test.put("t",1);
        test.put("s",1); // It failed here, why?

        test.put("r",1);
        test.put("q",1);
        test.put("p",1);

        test.put("y",1);
        test.put("z",1);
        test.put("å",1);
        test.put("ä",1);

        test.put("ö",1);
        test.put(".",1);
        test.put(",",1);
        test.put("4",1);

        for (String string: test.keys()) {
            System.out.println(string);
        }
    }
}
