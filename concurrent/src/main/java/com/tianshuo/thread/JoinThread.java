package com.tianshuo.thread;

/**
 * join关键字的使用
 *
 */
public class JoinThread {


    public static void main(String[] args) throws InterruptedException {
     Thread thread =   new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"线程启动");
            }
        });
     thread.start();

        for (int i = 0; i < 5; i++) {
            System.out.println("main method");
        }
        thread.join();

    }
}
