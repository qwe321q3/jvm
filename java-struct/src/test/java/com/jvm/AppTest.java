package com.jvm;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
//        System.out.println(myLinkedList);
//        myLinkedList.remove(3);

        myLinkedList.removeAll();

        System.out.println(myLinkedList.isEmpty());
        System.out.println(myLinkedList.size());
        System.out.println(myLinkedList.get(3));
        System.out.println(myLinkedList);
        
        
    }
    
}
