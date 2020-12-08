package com.tianshuo.thread.lock;

import java.util.concurrent.locks.ReentrantLock;
/**
 * @ClassName : LockTest
 * @Description : ReentrantLock锁测试
 * @Author : tianshuo
 * @Date: 2020-07-21 17:05
 */
public class LockTest {

    public static volatile int a = 0;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    reentrantLock.lock();
                    try {
                        for (int j=0 ;j<1000;j++) {
                            a++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        reentrantLock.unlock();
                    }
                }
            }, "Thread" + i);
            thread.start();

        }

        Thread.sleep(1000);

        System.out.println(a);



    }

}

