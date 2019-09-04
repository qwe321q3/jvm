package com.jvm.leetcode;

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


        System.out.println(addTwoNumbers(n2,n5));

    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;

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
