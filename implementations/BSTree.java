package implementations;

import utilities.BSTreeADT;
import utilities.Iterator;


/**
 * Binary Search Tree implementation.
 *
 * @param <E> The type of elements this tree stores, which must implement Comparable.
 */
@SuppressWarnings("serial")
public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E> {
    private BSTreeNode<E> root;
    private int size;

    public BSTree() {
        root = null;
        size = 0;
    }

    @Override
    public BSTreeNode<E> getRoot() throws NullPointerException {
        if (root == null) throw new NullPointerException();
        return root;
    }

    @Override
    public int getHeight() {
        return calculateHeight(root);
    }

    private int calculateHeight(BSTreeNode<E> node) {
        if (node == null) return 0;
        if (node.getLeft() == null && node.getRight() == null) return 1;
        return 1 + Math.max(calculateHeight(node.getLeft()), calculateHeight(node.getRight()));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean contains(E entry) throws NullPointerException {
        if (entry == null) throw new NullPointerException();
        return search(entry) != null;
    }

    @Override
    public BSTreeNode<E> search(E entry) throws NullPointerException {
        if (entry == null) throw new NullPointerException();
        return searchRecursive(root, entry);
    }

    private BSTreeNode<E> searchRecursive(BSTreeNode<E> node, E entry) {
        if (node == null) return null;
        int comparison = entry.compareTo(node.getElement());
        if (comparison == 0) return node;
        return comparison < 0 ? searchRecursive(node.getLeft(), entry) : searchRecursive(node.getRight(), entry);
    }

    @Override
    public boolean add(E newEntry) throws NullPointerException {
        if (newEntry == null) throw new NullPointerException();
        if (root == null) {
            root = new BSTreeNode<>(newEntry);
            size++;
            return true;
        }
        return addRecursive(root, newEntry);
    }

    private boolean addRecursive(BSTreeNode<E> node, E newEntry) {
        int comparison = newEntry.compareTo(node.getElement());
        if (comparison == 0) return false; // Duplicate not allowed
        if (comparison < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTreeNode<>(newEntry));
                size++;
                return true;
            }
            return addRecursive(node.getLeft(), newEntry);
        } else {
            if (node.getRight() == null) {
                node.setRight(new BSTreeNode<>(newEntry));
                size++;
                return true;
            }
            return addRecursive(node.getRight(), newEntry);
        }
    }

    @Override
    public BSTreeNode<E> removeMin() {
        if (isEmpty()) return null;
        BSTreeNode<E> parent = null;
        BSTreeNode<E> current = root;

        while (current.getLeft() != null) {
            parent = current;
            current = current.getLeft();
        }

        if (parent == null) root = current.getRight(); // Root is the smallest node
        else parent.setLeft(current.getRight());
        size--;
        return current;
    }

    @Override
    public BSTreeNode<E> removeMax() {
        if (isEmpty()) return null;
        BSTreeNode<E> parent = null;
        BSTreeNode<E> current = root;

        while (current.getRight() != null) {
            parent = current;
            current = current.getRight();
        }

        if (parent == null) root = current.getLeft(); // Root is the largest node
        else parent.setRight(current.getLeft());
        size--;
        return current;
    }

    @Override
    public Iterator<E> inorderIterator() {
        return new BSTreeIterator<>(root, "inorder");
    }

    @Override
    public Iterator<E> preorderIterator() {
        return new BSTreeIterator<>(root, "preorder");
    }

    @Override
    public Iterator<E> postorderIterator() {
        return new BSTreeIterator<>(root, "postorder");
    }
}
