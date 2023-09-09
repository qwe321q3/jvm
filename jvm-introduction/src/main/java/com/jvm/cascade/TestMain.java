package com.jvm.cascade;

/**
 * 1、父类，子类都有无参构造方法时
 * 实际测试结果，子类实例化的时候，总是会调用父类的构造方法
 * 子类的构造方法是中默认有一个super();  不论是有参构造方法还是无参构造方法
 */
public class TestMain {

    public static void main(String[] args) {

//        Father 类 无参构造方法
//        Child 类 无参构造方法
        Child child = new Child();

//        Father 类 无参构造方法
//        333 Child有参构造方法
        Child child1 = new Child("333");




    }
}
