package com.tianshuo.thread.markword;

import org.openjdk.jol.info.ClassLayout;

/**
 * 测试无锁，偏向锁，轻量级锁及重量级锁
 * MarkWord的变化
 * @author tianshuo
 */
public class LockObjectHeader {
    static Object a = new Object();


    public static void main(String[] args) throws InterruptedException {


        String layout = ClassLayout.parseInstance(a).toPrintable();

        System.out.println(layout);

        System.out.println("------------------");

        Thread.sleep(5000);


        String layout2 = ClassLayout.parseInstance(a).toPrintable();

        System.out.println(layout2);

        /**
         * 轻量级锁  000 轻量级锁
         */
            synchronized (a) {
                String lockNonLockMarkWord = ClassLayout.parseInstance(a).toPrintable();
                System.out.println(lockNonLockMarkWord);
            }
    }


}
