package com.jvm.banarycode;

/**
 * 现代的jvm，通常都会将解释执行和编译执行结合起来
 * 解释执行，就是通过解释器解释字节码，遇到相应指令就去执行
 * 编译执行，就是通过JIT(Just In Time)即时编译器将字节码转换为机器码去执行，现代的JVM会通过代码热点
 * 来生成相应的本地机器码
 *
 * <clinit> 方法静态成员和静态块合并构成的
 * initial
 * <p>
 * 如果类中不存在静态成员赋值或者静态块，那么的jvm不会给类生成<clinit> 方法
 *
 * 调用类中的静态变量也会触发类的静态块  如Mytest8.a  触发static{}
 */
public class MyTest8 {

    public MyTest8() {
        System.out.println(" construct function  mytest8!");
    }

    public MyTest8(int dd) {
        this.dd = dd;
    }

    static{
        System.out.println("MyTest8 static ");
    }

    public static int a = 45;
//
//    private static String a = "a";
//    private static int b;
//    会生成<clinit>
//    static {
//        b = 2;
//    }

    //不会生成<clinit>方法
    private static final String b = "d";
    private static final int cc = 50;
    private int dd =  22;


}
