package com.jvm.leetcode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Add Two Numbers
 * <p>
 * Example:
 * <p>
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 */
public class AddTwoNumbers {

    public static void main(String[] args) {

        ListNode n2 = new ListNode(2);
        ListNode n4 = new ListNode(4);
        ListNode n3 = new ListNode(3);
        n2.next = n4;
        n4.next = n3;


        ListNode n5 = new ListNode(5);
        ListNode n6 = new ListNode(6);
        ListNode n41 = new ListNode(4);
        n5.next = n6;
        n6.next = n41;


        addTwoNumbers(n2,n5);

    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ArrayList<Integer>b1 = new ArrayList();
        ArrayList<Integer> b2 = new ArrayList();
        while(l1!=null){
            b1.add(l1.val);
            l1 = l1.next;
        }

        while(l2!=null){
            b2.add(l2.val);
            l2 = l2.next;
        }

        System.out.println(b1);
        System.out.println(b2);
        for (int i = 0, len = b1.size(); i < len / 2; i++) {
            int temp = b1.get(i);
            b1.set(i,b1.get(len-1-i));
            b1.set((len - 1-i), temp);
        }

        System.out.println("翻转之后的数据为："+b1);

        System.out.println(b1);
        System.out.println(b2);
        return null;


    }


   static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

       @Override
       public String toString() {
           return "ListNode{" +
                   "val=" + val +
                   ", next=" + next +
                   '}';
       }
   }
}
