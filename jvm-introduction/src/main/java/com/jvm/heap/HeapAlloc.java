package com.jvm.heap;

/**
 * 实例参数配置：-Xms5m -Xmx20m -XX:+PrintCommandLineFlags -XX:+PrintGCDetails
 * <p>
 * 堆空间分配
 * -Xms18m 设置初始堆空间大小  -Xmx 设置最大堆空间大小
 * 一般来时jvm会尽量维持在初始的堆空间，但是如果初始化堆空间耗尽，jvm会对堆空间进行拓展，
 * 其拓展上限为设置的最大堆空间 -Xmx20m。
 * <p>
 * -XX:+PrintCommandLineFlags 打印jvm显式和隐式指定的jvm参数
 * -XX:+UseSerialGC 指定使用连续GC
 * GC前->GC前(总的堆大小) 执行时间       GC前整个堆的大小->GC后整个堆的大小(整个堆的小时)
 * [GC (Allocation Failure) [DefNew: 1413K->191K(1856K), 0.0008752 secs] 1413K->385K(5952K), 0.0008932 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 */
public class HeapAlloc {
    public static void main(String[] args) {

        System.out.println("初始化的内存");
        System.out.print("maxMemory=");
        System.out.println(Runtime.getRuntime().maxMemory() + "byte");
        System.out.print("freeMemory=");
        System.out.println(Runtime.getRuntime().freeMemory() + "byte");
        System.out.print("totalMemory=");
        System.out.println(Runtime.getRuntime().totalMemory() + "byte");

        byte[] bytes1m = new byte[1 * 1024 * 1024];
        System.out.println("分配1m内存给数组");

        System.out.print("maxMemory=");
        System.out.println(Runtime.getRuntime().maxMemory() + "byte");
        System.out.print("freeMemory=");
        System.out.println(Runtime.getRuntime().freeMemory() + "byte");
        System.out.print("totalMemory=");
        System.out.println(Runtime.getRuntime().totalMemory() + "byte");

        byte[] bytes4m = new byte[4 * 1024 * 1024];
        System.out.println("分配4m内存给数组");

        System.out.print("maxMemory=");
        System.out.println(Runtime.getRuntime().maxMemory() + "byte");
        System.out.print("freeMemory=");
        System.out.println(Runtime.getRuntime().freeMemory() + "byte");
        System.out.print("totalMemory=");
        System.out.println(Runtime.getRuntime().totalMemory() + "byte");


    }

}
