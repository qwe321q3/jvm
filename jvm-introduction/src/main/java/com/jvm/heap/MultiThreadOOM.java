package com.jvm.heap;

/**
 * 多线程导致的OOM
 * 线程会占用一部分堆外内存
 * 如果内存系统内存很大的话，很难内存溢出
 */
public class MultiThreadOOM {
    public static void main(String[]
                                    args) {
        for (int i = 0; i < 200000000 ; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(100000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("Thread "+i+" created ");

        }
    }
}
