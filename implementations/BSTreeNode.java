package implementations;

/**
 * Represents a single node in the Binary Search Tree.
 *
 * @param <E> The type of elements stored in the node.
 */
public class BSTreeNode<E> {
    private E element;
    private BSTreeNode<E> left;
    private BSTreeNode<E> right;

    /**
     * Constructor to create a node with the specified element.
     *
     * @param element The element to be stored in this node.
     */
    public BSTreeNode(E data) {
        this.element = data;
        this.left = null;
        this.right = null;
    }

    /**
     * Gets the element stored in this node.
     *
     * @return The data in the node.
     */
    public E getElement() {
        return element;
    }

    /**
     * Sets the element for this node.
     *
     * @param data The data to set.
     */
    public void setElement(E data) {
        this.element = data;
    }

    /**
     * Gets the left child of this node.
     *
     * @return The left child node, or null if none exists.
     */
    public BSTreeNode<E> getLeft() {
        return left;
    }

    /**
     * Sets the left child of this node.
     *
     * @param left The left child to set.
     */
    public void setLeft(BSTreeNode<E> left) {
        this.left = left;
    }

    /**
     * Gets the right child of this node.
     *
     * @return The right child node, or null if none exists.
     */
    public BSTreeNode<E> getRight() {
        return right;
    }

    /**
     * Sets the right child of this node.
     *
     * @param right The right child to set.
     */
    public void setRight(BSTreeNode<E> right) {
        this.right = right;
    }
}
