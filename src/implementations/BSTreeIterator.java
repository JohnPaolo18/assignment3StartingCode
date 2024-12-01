package implementations;

import utilities.Iterator;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Iterator for traversing a Binary Search Tree (BST) in various orders.
 *
 * @param <E> The type of elements stored in the BST.
 */
public class BSTreeIterator<E extends Comparable<? super E>> implements Iterator<E> {
    private final Queue<E> traversalQueue;

    /**
     * Constructs an iterator for the specified traversal order.
     *
     * @param root  The root node of the BST.
     * @param order The traversal order ("inorder", "preorder", "postorder").
     */
    public BSTreeIterator(BSTreeNode<E> root, String order) {
        traversalQueue = new LinkedList<>();
        if (root != null) {
            switch (order.toLowerCase()) {
                case "inorder":
                    inorderTraversal(root);
                    break;
                case "preorder":
                    preorderTraversal(root);
                    break;
                case "postorder":
                    postorderTraversal(root);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid traversal order: " + order);
            }
        }
    }

    /**
     * Performs an in-order traversal and populates the queue.
     *
     * @param node The current node.
     */
    private void inorderTraversal(BSTreeNode<E> node) {
        if (node == null) return;
        inorderTraversal(node.getLeft());
        traversalQueue.offer(node.getElement());
        inorderTraversal(node.getRight());
    }

    /**
     * Performs a pre-order traversal and populates the queue.
     *
     * @param node The current node.
     */
    private void preorderTraversal(BSTreeNode<E> node) {
        if (node == null) return;
        traversalQueue.offer(node.getElement());
        preorderTraversal(node.getLeft());
        preorderTraversal(node.getRight());
    }

    /**
     * Performs a post-order traversal and populates the queue.
     *
     * @param node The current node.
     */
    private void postorderTraversal(BSTreeNode<E> node) {
        if (node == null) return;
        postorderTraversal(node.getLeft());
        postorderTraversal(node.getRight());
        traversalQueue.offer(node.getElement());
    }

    @Override
    public boolean hasNext() {
        return !traversalQueue.isEmpty();
    }

    @Override
    public E next() throws NoSuchElementException {
        if (!hasNext()) throw new NoSuchElementException("No more elements in the iteration.");
        return traversalQueue.poll();
    }
}
