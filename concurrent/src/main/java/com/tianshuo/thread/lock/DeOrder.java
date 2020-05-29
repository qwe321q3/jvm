package com.tianshuo.thread.lock;

/**
 * 线程重排序实验
 * <p>
 * 重排序是为了刚好的使用多核CPU，CPU会在对代码语义没有影响的情况下，对代码进行重排序。  使代码在多线程环境中，使代码执行在多线程环境
 * 中看起来像是有序一下。  as-ifself-serial
 */
public class DeOrder {
    static int a = 0, b = 0;
    static int x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException {

        int i = 0;
        for (; ; ) {

            Thread t1 = new Thread(() -> {
                a = 1;
                x = b;
            }, "t1");

            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
            }, "t2");
            t1.start();
            t2.start();

            t1.join();
            t2.join();
            i++;

            if (x == 0 && y == 0) {
                System.out.println("第" + i + "次 : x=0 ,y=0");
                break;
            }


        }

    }
}
