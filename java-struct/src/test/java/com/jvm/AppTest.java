package com.jvm;

import com.jvm.linetable.List;
import com.jvm.linetable.MyArrayList;
import com.jvm.linetable.MyLinkedList;
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
    
}
