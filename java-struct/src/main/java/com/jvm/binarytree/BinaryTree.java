package com.jvm.binarytree;

/**
 * 二叉树接口
 * 1、数组实现
 * 2、链表实现
 */
public interface BinaryTree<E> {

    boolean isEmpty();

    int size();

    int height();

    LinkedBinaryTree.Node<E> findKey(E e);

    /**
     * 先序遍历
     */
    void nlr();

    /**
     * 中序遍历
     */
    void lnr();

    /**
     * 右序遍历
     */
    void rln();

    /**
     * 层次遍历（非递归） 队列
     */
    void levelOrderTraverse();


    /**
     * 中序遍历(非递归) 栈
     */
    void inOrderTraverse();



}


