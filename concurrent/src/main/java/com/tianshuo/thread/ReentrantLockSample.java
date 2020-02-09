package com.tianshuo.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 实例
 * ReentrantLock 使用起来比较另外，可以尝试锁定，synchronized不行
 * ReentrantLock 必须手动解锁，为了防止业务异常,一般都会在finally块中解锁，并且可以设置公平锁或者公平锁
 * synchronized则是由Jvm来的自动解锁的，另外synchronized在碰到异常异常是会主动释放锁
 * tryLock 可以尝试获得锁，不管能不能获得到都会向下执行，如果获取都了会返回true，没获取到返回false
 * tryLock 可以设置尝试时间 实例中的 在2秒钟尝试获取获得锁
 */
public class ReentrantLockSample {

    private static int count = 10;
    ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockSample reentrantLockSample = new ReentrantLockSample();
        new Thread(() -> {
            reentrantLockSample.reentrantLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 000");

                int b = count - 5;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count = b;
            } finally {
                reentrantLockSample.reentrantLock.unlock();
            }
        }).start();

        new Thread(() -> {
//            synchronized (ReentrantLockSample.class) {
//            reentrantLockSample.reentrantLock.lock();
            boolean flag = false;
            try {

                flag = reentrantLockSample.reentrantLock.tryLock(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                if (flag) {
                    System.out.println(Thread.currentThread().getName());
                    int b = count + 5;

                    count = b;
                }
            } finally {
                if (flag) {
                    reentrantLockSample.reentrantLock.unlock();
                }

            }
//            }

        }).start();
        Thread.sleep(5000);
        System.out.println("count value : " + reentrantLockSample.count);
    }
}
