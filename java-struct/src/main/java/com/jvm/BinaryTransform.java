package com.jvm;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 十进制转进制工具类
 */
public class BinaryTransform {

    public static void main(String[] args) {

        transformBinary(15);

    }


    public static void transformBinary(int a){
        Deque deque = new LinkedList();
        int t = a;
        while(t!=0){
            deque.push(t%2);
            t = t/2;
            System.out.println(t);
        }

        while(!deque.isEmpty()){
            System.out.print(deque.poll());
        }
    }
}
