package com.jvm.vmstack;

/**
 * 本实例用来演示局部变量回收的实例
 * main方法中增加-XX:+PrintGCDetails 用来监控gc日志打印。
 *
 *    [GC (System.gc())  （GC前堆空间）14039K->(GC后堆空间) 6744K (502784K)（堆空间总和）, 0.0037792 secs]
 *    [Full GC (System.gc())  6744K->6534K(502784K), 0.0045643 secs]
 * 
 */
public class LocalVariableGC {

    /**
     * new byte申请的6M的对空间不会被回收
     * 因为变量bytes还在引用用这个6m的空间所有这个空间不会被回收。
     * 所以即使我们执行了gc方法，触发fullgc但是堆空间也不会被回收
     *
     * 6m数组被直接放到的老年代、多大的对象会被直接放置到的老年代？？？
     * 1、是否数组会被jvm直接放入到的老年代？
     *  // TODO: 2020/1/25  暂未找到答案后续再看
     */
    public void localVar1() {
        byte[] bytes = new byte[6 * 1024 * 1024];
        System.gc();
    }

    /**
     * bytes变量的引用被设置为null
     * 所以在执行gc方法时，堆空间会被回收
     */
    public void localVar2() {
        byte[] bytes = new byte[6 * 1024 * 1024];
        bytes = null;
        System.gc();

    }

    /**
     * 堆空间未释放
     *
     * 局部代码块执行完成之后，bytes变量已经失效，但是bytes变量，还存在
     * 局部变量表中，所以在执行了gc方法之后，6m的空间还在
     */
    public void localVar3() {
        {
            //局部代码块限定方法中变量的生命周期，方法执行完成就释放变量
            byte[] bytes = new byte[6 * 1024 * 1024];
        }
        System.gc();
    }

    /**
     * 堆空间会释放掉
     *
     * 局部代码块执行完成之后，bytes变量失效，bytes还在存在的局部变量表中，
     * 但是c变量会复用bytes变量的slot槽位，此时执行了GC方法之后，
     * 6m的bytes数组会被回收
     */
    public void localVar4() {
        {
            byte[] bytes = new byte[6 * 1024 * 1024];
        }
        int c = 30;
        System.gc();

    }

    /**
     * 堆空间会被回收
     *
     * 原因：localVar5方法调用了localVar1 ，当localVar1方式执行完成之后，localVar1对应的
     * 栈桢会被销毁，所以此时localVar1里边的局部变量也就随之销毁了，所以执行了gc之后，堆空间就
     * 释放了
     */
    public void localVar5() {
        localVar1();
        System.gc();
    }

    public static void main(String[] args) {
        //3947K var1
        // var2 3947K
        LocalVariableGC localVariableGC = new LocalVariableGC();
        localVariableGC.localVar1();
    }


}
