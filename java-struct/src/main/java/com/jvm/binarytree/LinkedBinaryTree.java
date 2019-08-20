package com.jvm.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 链表实现的二叉树
 *
 * @param <E>
 */
public class LinkedBinaryTree<E> implements BinaryTree<E> {

    /**
     * 根节点
     */
    Node<E> root;

    public LinkedBinaryTree(Node<E> root) {
        this.root = root;
    }


    @Override
    public String toString() {
        return "LinkedBinaryTree{" +
                "root=" + root +
                '}';
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void preOrderTraverse() {
        System.out.print("先序遍历: ");
        preOrderTraverse(root);
        System.out.println();
    }

    /**
     * 先序遍历
     * 1、从根开始遍历
     * 2、先左子树，然后右子树遍历
     *
     * @param node
     */
    private void preOrderTraverse(Node<E> node) {
        if (node != null) {

            System.out.print(node.value + " ");
            preOrderTraverse(node.leftNode);
            preOrderTraverse(node.rightNode);
        }

    }

    /**
     * 中序排列：
     * 1、先左子树，再右子树
     * 2、然后在根
     */
    @Override
    public void inOrderTraverse() {
        System.out.print("中序遍历：");
        inOrderTraverse(root);
        System.out.println();

    }

    private void inOrderTraverse(Node<E> node) {
        if (node != null) {
            inOrderTraverse(node.leftNode);
            System.out.print(node.value + " ");
            inOrderTraverse(node.rightNode);
        }

    }

    /**
     * 1、先左子树
     * 2、右子树
     * 3、根节点
     */
    @Override
    public void postOrderTraverse() {
        System.out.print("后序遍历：");
        postOrderTraverse(root);
        System.out.println();
    }

    private void postOrderTraverse(Node<E> node) {
        if (node != null) {

            postOrderTraverse(node.leftNode);
            postOrderTraverse(node.rightNode);
            System.out.print(node.value + " ");

        }
    }

    /**
     * 1、先把跟结点放入到队列中
     * 2、判断队列是否为空，如果为空的话，循环中队列中的数据
     * 3、循环取出队列中的数据，同时把队列中的数据的左子树和右子树放入的到队列中，循环以上操作。
     */
    @Override
    public void levelOrderTraverse() {
        System.out.print("层次遍历：");
        levelOrderTraverse(root);
        System.out.println();
    }

    private void levelOrderTraverse(Node<E> node) {
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(node);
        while (!nodeQueue.isEmpty()) {
            //     for(int i =0 ;i<nodeQueue.size();i++){

            Node<E> eNode = nodeQueue.poll();
            if (eNode != null) {
                System.out.print(eNode.value + " ");
                nodeQueue.add(eNode.leftNode);
                nodeQueue.add(eNode.rightNode);
            }
//            }
        }


    }

    /**
     * 使用栈来做中序循环
     */
    @Override
    public void inOrderTraverseByStack() {
        System.out.print("中序遍历(栈操作):");
    }

    private void inOrderTraverseByStack(Node<E> node){




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
    public int size() {
        return size(root);
    }

    private int size(Node<E> node){
        if (node == null) {
            return 0;
        } else {
            int nl = size(node.leftNode);

            int nr = size(node.rightNode);


            return nl + nr + 1;
        }
    }

    @Override
    public int height() {
        return height(root);
    }

    /**
     * 1、如果根节点为空，返回0
     * 2、先遍历左子树，在遍历右子树
     * 3、比较左子树和右子树的高度，谁大，就用谁+1  获取整个二叉树的高度
     *
     * @param node
     * @return
     */
    private int height(Node<E> node) {
        if (node == null) {
            return 0;
        } else {
            int nl = height(node.leftNode);

            int nr = height(node.rightNode);


            return nl > nr ? nl + 1 : nr + 1;
        }
    }

    public static class Node<E> {
        /**
         * 结点值
         */
        E value;
        /**
         * 左子树的引用
         */
        Node<E> leftNode;
        /**
         * 右子树引用
         */
        Node<E> rightNode;

        public Node() {
        }

        public Node(Node<E> leftNode, Node<E> rightNode) {
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

        public Node(E value, Node<E> leftNode, Node<E> rightNode) {
            this.value = value;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", leftNode=" + leftNode +
                    ", rightNode=" + rightNode +
                    '}';
        }
    }
}
