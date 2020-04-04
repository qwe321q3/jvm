package com.jvm.heap;


/**
 * 增加jvm判断的对象是否是垃圾对象的方法
 *
 * 测试GC，判断对象是否可用时，是否使用的引用计数法
 *
 * 结果：对象循环依赖，但是还会被GC给回收了，说明jvm并未使用引用计数法来的判断哪些对象是垃圾对象
 *
 * JVM的如何判断对象是否可用？
 * 使用的了根可达性算法。
 * 通过GC Roots根 ，来向下找对象是否可用
 *
 * GC Roots根是什么？
 * 1、可以是局部变量表中的对象引用
 * 2、可以是方法区静态对象引用
 * 3、可以是方法区常量对象引用
 *
 * 如何判断对象是否是垃圾对象?
 * 1、对象的不可触及(通过GC Roots根，无法找到对象)
 * 2、对象的finalize()已经被执行
 *
 *
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCApplicationConcurrentTime -XX:+PrintHeapAtGC
 */
public class ReferenceCountingTest {

    public static void main(String[] args) {


        ResObject resObjectA = new ResObject();

        ResObject resObjectB = new ResObject();

        resObjectA.instance = resObjectB;

        resObjectB.instance = resObjectA;



        resObjectA = null;
        resObjectB = null;

        System.gc();

    }

}

class ResObject {
    byte[] arg = new byte[2 * 1024 * 1024];
    ResObject instance;
}
