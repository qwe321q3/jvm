package com.tianshuo.thread.lock;

/**
 * 线程重排序实验
 * <p>
 * 重排序是为了刚好的使用多核CPU，CPU会在对代码语义没有影响的情况下，对代码进行重排序。  使代码在多线程环境中，使代码执行在多线程环境
 * 中看起来像是有序一下。  as-if-serial
 */
public class ReOrder {
    static int a = 0, b = 0;
    static int x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException {

        int i = 0;
        while (true ) {

            Thread t1 = new Thread(() -> {
                //由于线程one先启动，下面这句话让它等一等线程two. 可根据自己电脑的实际性能适当调整等待时间.
//                shortWait(10000);
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
            System.out.println("第" + i + "次 : x="+x+" ,y="+y);

            if (x == 0 && y == 0) {
                System.out.println("第" + i + "次 : x=0 ,y=0");
                break;
            }


        }

    }

    public static void shortWait(long interval){
        long start = System.nanoTime();
        long end;
        do{
            end = System.nanoTime();
        }while(start + interval >= end);
    }
}
