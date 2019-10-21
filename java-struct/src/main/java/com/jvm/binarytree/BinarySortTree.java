package com.jvm.binarytree;

/**
 * 二叉查找树
 * 重点：
 * 1、左子树都小于根节点
 * 2、右子树都大于根节点
 */
public class BinarySortTree {

    TreeNode root;

    public void create(int ix) {
        create(ix, root);
    }

    /**
     * 创建二叉查找树
     * <p>
     * 1、先判断root是否为空，如果为空，则生成root结点，
     * 2、如果root不为空的，判断当前结点的左结点是否为空，如果设置当前值为左结点，如果不为空对比当前结点和左结点的大小
     * 3、如果当前值大于左结点，并且右节点为空，则当前为右节点，如果不为空的则继续循环2，3步对比左右结点的大小值
     *
     * @param
     * @return
     */
    public void create(int ix, TreeNode currTreeNode) {


        if (root == null) {
            root = new TreeNode(ix, null, null);
        } else if (currTreeNode.data > ix) {
            if (currTreeNode.left == null) {
                TreeNode treeNode = new TreeNode(ix, null, null);
                currTreeNode.left = treeNode;
            } else {
                create(ix, currTreeNode.left);
            }
        } else {

            if (currTreeNode.right == null) {
                TreeNode treeNode = new TreeNode(ix, null, null);
                currTreeNode.right = treeNode;
            } else {
                create(ix, currTreeNode.right);

            }
        }


    }

    public TreeNode tree() {
        return root;
    }

    private static class TreeNode {

        int data;

        TreeNode left;

        TreeNode right;

        public TreeNode(int data, TreeNode left, TreeNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "data=" + data +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    public static void main(String[] args) {

        BinarySortTree binarySortTree = new BinarySortTree();

        int[] arr = {4, 2, 5, 7, 1, 6, 9};

        for (int i1 : arr) {
            System.out.print(i1 + " ,");
            binarySortTree.create(i1);
        }
        System.out.println();
        System.out.println(binarySortTree.root);


    }


}
