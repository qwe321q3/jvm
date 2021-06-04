package com.jvm.binarytree;

/**
 * 二叉树接口
 */
public interface BinaryTree<E>{


    int size();

    int height();

    boolean isEmpty();

    /**
     * 先序遍历  NLR 递归实现 （ 根，左，右）
     */
    void preOrderTraverse();

    /**
     * 中序遍历  LNR 递归实现（ 左，根，右）
     */
    void inOrderTraverse();

    /**
     * 后序遍历 LRN  递归实现 归实现（ 左，右，根）
     */
    void postOrderTraverse();

    /**
     * 层次遍历  队列实现
     */
    void levelOrderTraverse();

    /**
     * 中序遍历   栈实现
     */
    void inOrderTraverseByStack();

    /**
     * 查询对应的Node节点
     * @param e
     * @return
     */
    E findKey(E e);

    /**
     * 元素是否存在树中
     * @param e
     * @return
     */
    boolean contain(E e);
}
