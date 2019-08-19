package com.jvm.binarytree;

/**
 * 链表实现的二叉树
 * @param <E>
 */
public class LinkedBinaryTree<E> implements BinaryTree<E>{

    /**
     * 根节点
     */
    Node<E> root ;

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
     * @param node
     */
    private void preOrderTraverse(Node<E> node){
        if(node != null){

            System.out.print(node.value+" ");
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

    private void inOrderTraverse(Node<E> node){
        if(node != null){
            inOrderTraverse(node.leftNode);
            System.out.print(node.value+" ");
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

    private void postOrderTraverse(Node<E>node){
        if(node != null){

            postOrderTraverse(node.leftNode);
            postOrderTraverse(node.rightNode);
            System.out.print(node.value+" ");

        }
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
    public int size() {
        return 0;
    }

    @Override
    public int height() {
        return height(root);
    }

    /**
     * 1、如果根节点为空，返回0
     * 2、先遍历左子树，在遍历右子树
     * 3、比较左子树和右子树的高度，谁大，就用谁+1  获取整个二叉树的高度
     * @param node
     * @return
     */
    private int height(Node<E> node){
        if(node==null){
            return 0;
        }else {
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
