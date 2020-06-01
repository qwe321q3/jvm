package com.tianshuo.thread.lock;

/**
 * ReentrantLock
 * 使用重入锁 实现线程安全
 */
public class MultiThreadLockTest {

    private static int amount = 300;

//    ReentrantLock reentrantLock = new ReentrantLock();

    CustomNonFairLock customLock  = new CustomNonFairLock();

    public class Thread1 extends Thread {
        @Override
        public void run() {

            try {
                customLock.lock();
                int yl = MultiThreadLockTest.amount - 100;
                System.out.println("1.1");
                Thread.sleep(1000);
                System.out.println("1.2");
                MultiThreadLockTest.amount = yl;
                System.out.println(Thread.currentThread().getName() + " amount: " + MultiThreadLockTest.amount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally{
                 customLock.unlock();
            }

        }
    }

    public class Thread2 extends Thread {
        @Override
        public void run(){
            try {
                customLock.lock();
                System.out.println(222);
                int yl = MultiThreadLockTest.amount + 500;

                MultiThreadLockTest.amount = yl;
                System.out.println(Thread.currentThread().getName()+" amount: "+ MultiThreadLockTest.amount);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                customLock.unlock();
            }
        }
    }



    public static void main(String[] args) throws InterruptedException {
        MultiThreadLockTest multiThread = new MultiThreadLockTest();

        MultiThreadLockTest.Thread1 thread1 = multiThread.new Thread1();
        thread1.start();

        MultiThreadLockTest.Thread2 thread2 = multiThread.new Thread2();
        thread2.start();

        Thread.sleep(5000);

        System.out.println("余额: "+ MultiThreadLockTest.amount);
    }


}
