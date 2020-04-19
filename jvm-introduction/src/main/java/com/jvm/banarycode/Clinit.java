package com.jvm.banarycode;

/**
 * 类中存在static 方法块 或者 静态变量，JVM就会生产 <clinit>块
 *
 * 成员变量是<init>块中被赋值的，也就是说是是再构造方法中被赋值的
 */
public class Clinit {

//    int b = 0;

    static int  a  = 0;

    static {
        System.out.println("Static function !");
    }
}
