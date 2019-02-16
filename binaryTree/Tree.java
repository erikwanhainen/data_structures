import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Class for a tree data structure.
 * @author Erik Vanhainen
 * @version 11-02-2019
 */
class Tree<T extends Comparable<T>> {
    private Node<T> root;
    private int size;

    /**
     * Class for a Nodes in tree.
     */
    public class Node<T> {
        public T data;
        public Node<T> LC;
        public Node<T> RC;

        /**
         * Node constructor.
         * @param data the value that the node stores.
         **/
        public Node(T data) {
            this.data = data;
            LC = null;
            RC = null;
        }
    }
    /**
     * Tree constructor.
     **/
    public Tree() {
        root = null;
        size = 0;
    }

    /**
     * Searches for a element by value iterative.
     * @param elem the value of the element.
     * @return true if element is in tree, false otherwise.
     **/
    public boolean search(T elem) {
        Node<T> current  = root;
        while(current != null) {
            if(current.data.compareTo(elem) == 0) {
                return true;
            } else if(current.data.compareTo(elem) > 0) {
                current = current.LC;
            } else {
                current = current.RC;
            }
        }
        return false;
    }

    /**
     * Inserts a element in correct order iterative.
     * @param elem the value of the element.
     * @return treu if no such element already exists, false othewise.
     **/
    public boolean insert(T elem) {
        if(root == null) {
            root = new Node<>(elem);
            size++;
            return true;
        }
        Node<T> current  = root;
        if(!search(elem)) {
            while(current != null) {
                if(current.data.compareTo(elem) > 0) {
                    if(current.LC == null) {
                        current.LC = new Node<>(elem);
                        size++;
                        return true;
                    }
                    current = current.LC;
                } else {
                    if(current.RC == null) {
                        current.RC = new Node<>(elem);
                        size++;
                        return true;
                    }
                    current = current.RC;
                }
            }
        }
        return false;
    }

    /**
     * Size of the tree.
     * @return number of elements.
     **/
    public int size() {
        return size;
    }

    /**
     * Height of the tree.
     * @return maximum number of "layers" in the tree.
     **/
    public int height() {
        return heightCount(root);
    }

    /**
     * Helper method for counting the height recursivly.
     * @param node the node that the height is counted from.
     * @return maximum number of "layers" from the node.
     **/
    private int heightCount(Node<T> node) {
        if(node == null) {
            return 0;
        }
        if(node.LC == null && node.RC == null) {
            return 0;
        }
        int left = heightCount(node.LC);
        int right = heightCount(node.RC);
        return(Math.max(left, right) + 1);
    }

    /**
     * Couting number of elements at the bottom of the tree (leaves).
     * @return number of leaves.
     **/
    public int leaves() {
        return leavesCount(root);
    }

    /**
     * Helper method for counting number of leaves recursivly.
     * @param node the node that the count starts from.
     * @return number of leaves below the node.
     **/
    private int leavesCount(Node<T> node) {
        if(node == null) {
            return 0;
        }
        if (node.LC == null && node.RC == null) {
            return 1;
        }
        return leavesCount(node.LC) + leavesCount(node.RC);
    }

    /**
     * Computes a string representation of the tree.
     * @return string representation of the tree.
     **/
    public String toString() {
        if(size == 0) {
            return "[]";
        }
        ArrayList<T> stringArr = new ArrayList<>();
        return Arrays.toString(toStringHelper(root, stringArr).toArray());
    }

    /**
     * Helper method for representing the tree recursivly.
     * @param node the node that the representation starts from.
     **/
    private ArrayList<T> toStringHelper(Node<T> node, ArrayList<T> list) {
        if(node.LC != null) {
            toStringHelper(node.LC, list);
        }
        list.add(node.data);
        if(node.RC != null) {
            toStringHelper(node.RC, list);
        }
        return list;
    }
}
