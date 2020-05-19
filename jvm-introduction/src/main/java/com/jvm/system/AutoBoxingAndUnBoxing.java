package com.jvm.system;

/**
 * 关键点
 * 1、==操作，在不遇到运算的时候，不会自动拆箱或者装箱
 * 2、equals()不会处理数据转型的关系
 * 3、包装类型最好用equals()方法比较
 *
 */
public class AutoBoxingAndUnBoxing {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;

        System.out.println(c==d); //true
        /**
         * Integer的cache中只有-128 ~ 127
         * 超过的话会新new对象，这时候比较的对象内存地址
         *
         * 所以当Integer的值不在-128到127的时候使用==方法判断是否相等就会出错
         *
         */
        System.out.println(e==f); //false
        System.out.println(c==(a+b));//true
        System.out.println(c.equals(a+b));//true
        System.out.println(g==(a+b)); //true
        System.out.println(g.equals(a+b));//false

        System.out.println(e.equals(f)); //true

    }
}
