package com.tianshuo.thread.markword;

import org.openjdk.jol.info.ClassLayout;

/**
 * 测试无锁，偏向锁，轻量级锁及重量级锁
 * MarkWord的变化
 * @author tianshuo
 */
public class LockObjectHeader {

    public static void main(String[] args) throws InterruptedException {

        Object a = new Object();
        /**
         * 轻量级锁  000 轻量级锁
         */
            synchronized (a) {
                String lockNonLockMarkWord = ClassLayout.parseInstance(a).toPrintable();
                System.out.println(lockNonLockMarkWord);
            }
    }


}
