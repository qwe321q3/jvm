package com.jvm;

import com.jvm.binarytree.BinaryTree;
import com.jvm.binarytree.LinkedBinaryTree;
import com.jvm.linetable.List;
import com.jvm.linetable.MyArrayList;
import com.jvm.linetable.MyLinkedList;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void TestMyLinkedList() {
        MyLinkedList<String> myLinkedList = new MyLinkedList<>();

        myLinkedList.add("11");
        myLinkedList.add("432");
        myLinkedList.add("44");
        myLinkedList.add("77");

        myLinkedList.add("44");
        myLinkedList.add("77");

//        myLinkedList.add(2,"aaa");
        System.out.println(myLinkedList);
//        myLinkedList.remove(3);

//        myLinkedList.removeAll();
        String tr = "11";
        System.out.println(tr+": 是否存在："+myLinkedList.contain(tr));

        System.out.println(myLinkedList.isEmpty());
        System.out.println(myLinkedList.size());
        System.out.println(myLinkedList.get(3));
        System.out.println(myLinkedList);


    }

    @Test
    public void testArrayList() {
        List<String> arrayList = new MyArrayList<>();

        arrayList.add("aaa");
        arrayList.add("bbb");
        arrayList.add("ccc");
        arrayList.add("ddd");
        arrayList.add("eee");

        System.out.println(arrayList);
        System.out.println(arrayList.size());

        arrayList.remove(0);

        System.out.println(arrayList);
        System.out.println(arrayList.size());

    }

    @Test
    public void testLinkedBinaryTree() {


        LinkedBinaryTree.Node node5 = new LinkedBinaryTree.Node(5, null, null);
        LinkedBinaryTree.Node node4 = new LinkedBinaryTree.Node(4, null, node5);
        LinkedBinaryTree.Node node7 = new LinkedBinaryTree.Node(7, null, null);
        LinkedBinaryTree.Node node6 = new LinkedBinaryTree.Node(6, null, node7);
        LinkedBinaryTree.Node node3 = new LinkedBinaryTree.Node(3, null, null);
        LinkedBinaryTree.Node node2 = new LinkedBinaryTree.Node(2, node3, node6);
        LinkedBinaryTree.Node node1 = new LinkedBinaryTree.Node(1, node4, node2);

        BinaryTree<Integer> binaryTree = new LinkedBinaryTree<>(node1);


        System.out.println(binaryTree);
        System.out.println("二叉树高度：" + binaryTree.height());
        binaryTree.preOrderTraverse();
        binaryTree.inOrderTraverse();
        binaryTree.postOrderTraverse();
        binaryTree.levelOrderTraverse();

        System.out.println("节点个数：" + binaryTree.size());

        binaryTree.inOrderTraverseByStack();

        binaryTree.findKey(2);

        System.out.println("否是存在："+binaryTree.contain(8));

    }

    @Test
    public void testBit() {

        System.out.println(Integer.highestOneBit(15));
        System.out.println(tableSizeFor(15));

        // 7
        // 0000 0000 0000 0000 0000 0000 0000 1111
        // 0000 0000 0000 0000 0000 0000 0000 0111  >>1
        // 0000 0000 0000 0000 0000 0000 0000 1111  i|
        // 0000 0000 0000 0000 0000 0000 0000 0001  >> 2
        // 0000 0000 0000 0000 0000 0000 0000 0111
        // 0000 0000 0000 0000 0000 0000 0000 0000  >>4
        // 0000 0000 0000 0000 0000 0000 0000 0111


    }
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {

        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /**
     *
     * // 0000 0000 0000 0000 0000 0000 0000 1111 15
     * // 0000 0000 0000 0000 0000 0000 0000 0111  >>1
     * // 0000 0000 0000 0000 0000 0000 0000 1111  i|
     * // 0000 0000 0000 0000 0000 0000 0000 0001  >> 2
     * // 0000 0000 0000 0000 0000 0000 0000 0111
     * // 0000 0000 0000 0000 0000 0000 0000 0000  >>4
     * // 0000 0000 0000 0000 0000 0000 0000 0111
     *
     *
     * 此方法目的获取输入值的二进制最高位的2次幂值，如：15 的高位2次幂的值为8
     * 首先找到规律，2的次幂的数据，在二进制里边，所有位中只有一个1存在 如：1： 0001  为2^0  或者2：10 为 2^1  或者2 ：100 为2^2
     *
     * 算法业务：
     *          1、把输入的int类型的，二进制最高位以下的所有值都变为1 ，如：1111
     *          2、然后用在用1111-(1111右移1位，及1110)，就获得了到了1000，即输入的int值的二进制的最高的的值，即>=i 的一个2次幂值
     *          最终目的是为了得到 (1111- 0111)=1000
     *
     * 关键点：
     *        1.按位或| ，一个数如果和自己按位或，结果还是这数据本身
     *        2.一个数|0  结果也还是这个数据本身
     *
     * 为什么第一次 右移1位，第二次右移2位，然后位接着是4位，8位，16位
     * 1、输入的值为int类型，int类型占4个字节，32位即0-31 ，考虑到输入的这个值有可能是int的最大值，所以极限情况下需要移动31位。
     * 2、第一次右移之后，会获取的把，高位之后的第一个二进制也变成1
     * 3、因为已经确定了，搞位的前2位是1了，那么第二次右移的时候，可以为了的提高效率，直接移2位，把3，4位变为1，如果这个数只有2位的，
     * 4、因为已经确定前4位都是1了，那么下次可以直接移动4位
     * 5、然后8，16类推
     * 6、如果这个高位之后，比移为的数要少，那么|操作之后，还是这个数
     * 如 ： 5   101
     *         i |= (i >>  1);     111
     *         i |= (i >>  2);     111   011   111
     *         i |= (i >>  4);     111   000   111
     *         i |= (i >>  8);     111   000   111
     *         i |= (i >> 16);   如：111 - 011 = 100  = 4
     *
     *  算法目的是把输入的值的而二进制的最高为之后全部变为1位
     * @param i
     * @return
     */

    public static int highestOneBit(int i) {
        // HD, Figure 3-1
        i |= (i >>  1);
        i |= (i >>  2);
        i |= (i >>  4);
        i |= (i >>  8);
        i |= (i >> 16);
        return i - (i >>> 1);
    }


}
