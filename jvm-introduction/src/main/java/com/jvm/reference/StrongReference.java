package com.jvm.reference;

/**
 * 强引用实例
 *
 * StrongReference
 *
 * 1、强引用可以直接访问目标对象
 * 2、任何时候系统都不会主动回收强引用，jvm宁愿发生oom也不会回收强引用指向的目标对象
 * 3、强引用有可能会发生内存泄漏
 *
 */
public class StrongReference {

    public static void main(String[] args) {

        //sb就是StringBuilder的强引用
        //sb可以直接操作的这个实例
        StringBuilder sb = new StringBuilder("ddd");


    }
}
