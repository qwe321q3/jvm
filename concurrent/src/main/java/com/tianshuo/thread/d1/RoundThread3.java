package com.tianshuo.thread.d1;

import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName : RoundThread3
 * @Description : 2个线程交替执行 abc,123
 * @Author : tianshuo
 * @Date: 2020-08-10 11:37
 */
public class RoundThread3 {
    static Thread t1, t2;


    public static void main(String[] args) throws InterruptedException {
        String str = "abcasfsafsdfsdfsfsfwefrwerwerwerewrwerewrewrwerew";
//        String str = "abc";

//        String str2 = "123";
        String str2 = "123131231232498789237492372342342341231231213212";

        t1 = new Thread(() -> {
            char[] char1 = str.toCharArray();
            for (int i = 0; i < char1.length; i++) {
                System.out.println(Thread.currentThread().getName() + " -- " + char1[i]);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        }, "t1");

        t2 = new Thread(() -> {
            char[] char1 = str2.toCharArray();
            for (int i = 0; i < char1.length; i++) {
                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + " -- " + char1[i]);
                LockSupport.unpark(t1);
            }
        }, "t2");

        t2.start();
        t1.start();
//        t2.join();
//        t1.join();


    }

}

