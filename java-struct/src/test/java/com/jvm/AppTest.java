package com.jvm;

import com.jvm.binarytree.BinaryTree;
import com.jvm.binarytree.LinkedBinaryTree;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    
    @Test
    public void TestMyLinkedList(){
        MyLinkedList<String>myLinkedList = new MyLinkedList<>();

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

        System.out.println(myLinkedList.isEmpty());
        System.out.println(myLinkedList.size());
        System.out.println(myLinkedList.get(3));
        System.out.println(myLinkedList);

        
    }

    @Test
    public void testArrayList(){
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
    public void testLinkedBinaryTree(){


        LinkedBinaryTree.Node node5 = new LinkedBinaryTree.Node(5,null,null);
        LinkedBinaryTree.Node node4 = new LinkedBinaryTree.Node(4,null,node5);
        LinkedBinaryTree.Node node7 = new LinkedBinaryTree.Node(7,null,null);
        LinkedBinaryTree.Node node6 = new LinkedBinaryTree.Node(6,null,node7);
        LinkedBinaryTree.Node node3 = new LinkedBinaryTree.Node(3,null,null);
        LinkedBinaryTree.Node node2 = new LinkedBinaryTree.Node(2,node3,node6);
        LinkedBinaryTree.Node node1 = new LinkedBinaryTree.Node(1,node4,node2);

        BinaryTree<Integer> binaryTree = new LinkedBinaryTree<>(node1);


        System.out.println(binaryTree);
        System.out.println("二叉树高度："+ binaryTree.height());

    }
    
}
