package com.jvm.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 * 使用重入锁 实现线程安全
 */
public class MultiThreadLock {

    private static int amount = 300;

    ReentrantLock reentrantLock = new ReentrantLock();

    public class Thread1 extends Thread {
        @Override
        public void run() {

            try {
                reentrantLock.lock();
                int yl = MultiThreadLock.amount - 100;
                System.out.println("1.1");
                Thread.sleep(1000);
                System.out.println("1.2");
                MultiThreadLock.amount = yl;
                System.out.println(Thread.currentThread().getName() + " amount: " + MultiThreadLock.amount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally{
                reentrantLock.unlock();
            }

        }
    }

    public class Thread2 extends Thread {
        @Override
        public void run(){
            try {
                reentrantLock.lock();
                System.out.println(222);
                int yl = MultiThreadLock.amount + 500;

                MultiThreadLock.amount = yl;
                System.out.println(Thread.currentThread().getName()+" amount: "+ MultiThreadLock.amount);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }
    }



    public static void main(String[] args) throws InterruptedException {
        MultiThreadLock multiThread = new MultiThreadLock();

        MultiThreadLock.Thread1 thread1 = multiThread.new Thread1();
        thread1.start();

        MultiThreadLock.Thread2 thread2 = multiThread.new Thread2();
        thread2.start();

        Thread.sleep(5000);

        System.out.println("余额: "+ MultiThreadLock.amount);
    }


}
