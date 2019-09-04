package com.jvm.leetcode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个二叉树，返回它的中序 遍历。
 *
 * 示例:
 *
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [1,3,2]
 */
public class inorderTraversal {

    public static void main(String[] args) {



        TreeNode n3 = new TreeNode(3);
        TreeNode n2 = new TreeNode(2);
        n2.left = n3;
        TreeNode n1 = new TreeNode(1);
        n1.right = n2;

        System.out.println(inorderTraversal(n1));

    }

    //使用队列，现在放入根节点，然后放入左侧节点，然后右侧
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();

        Deque<TreeNode> deque = new LinkedList();
        TreeNode  current = root;

        while(current!=null || deque.size()>0){
            while(current!=null){
                deque.push(current);
                current = current.left;
            }
            if(!deque.isEmpty()){

                TreeNode node = deque.poll();
                list.add(node.val);
                current = node.right;
            }
        }

        return list;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
