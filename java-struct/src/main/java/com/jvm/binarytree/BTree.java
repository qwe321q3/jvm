package com.jvm.binarytree;

/**
 * 二叉树实现。
 */
public class BTree<E> implements BinaryTree<E> {

    private Node<E> root;

    public BTree(Node<E> root) {
        this.root = root;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void preOrderTraverse() {

    }

    @Override
    public void inOrderTraverse() {

    }

    @Override
    public void postOrderTraverse() {

    }

    @Override
    public void levelOrderTraverse() {

    }

    @Override
    public void inOrderTraverseByStack() {

    }

    @Override
    public E findKey(E e) {
        return null;
    }

    @Override
    public boolean contain(E e) {
        return false;
    }

    @Override
    public String toString() {
        return "BTree{" +
                "root=" + root +
                '}';
    }

    static class Node<E>{

        E value;

        Node<E> leftChild;

        Node<E> rightChild;

        public Node(E value, Node<E> leftChild, Node<E> rightChild) {
            this.value = value;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public Node() {
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", leftChild=" + leftChild +
                    ", rightChild=" + rightChild +
                    '}';
        }
    }



}
