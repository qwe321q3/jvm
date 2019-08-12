package com.jvm.banarycode;

/**
 * Jvm 栈桢  非常重要
 *
 * stack Frame
 *
 * stack 栈 先进后出
 *
 * 栈桢是一种jvm进行方法执行和方法调用的数据结构
 *
 *
 * 本例子为静态解析   invokestatic
 *
 * 静态解析包括invokestatic，invoke
 */
public class StackFrame {

    public static void test(){
        System.out.println("acidg");
    }


    public static void main(String[] args) {
        test();
    }


}
