package com.jvm.lock;

/**
 * synchronized 关键字模拟
 * sleep方法并不会放弃锁
 * <p>
 * public synchronized void show
 * 相当于
 * synchronized (this)
 * <p>
 * public synchronized static show
 * 相当于
 * synchronized (xxx.class)  锁的字节码
 */
public class MultiThreadSynchronized {

    private static int amount = 300;
    //锁住的是堆中new出来的AtomicLess实例
    AtomicLess atomicLess = new AtomicLess();


    public class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (atomicLess) {
                int yl = MultiThreadSynchronized.amount - 100;
                try {
                    System.out.println("1.1");
                    Thread.sleep(1000);
                    System.out.println("1.2");
                    MultiThreadSynchronized.amount = yl;
                    System.out.println(Thread.currentThread().getName() + " amount: " + MultiThreadSynchronized.amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (atomicLess) {
                System.out.println(222);
                int yl = MultiThreadSynchronized.amount + 500;

                MultiThreadSynchronized.amount = yl;
                System.out.println(Thread.currentThread().getName() + " amount: " + MultiThreadSynchronized.amount);
            }
        }
    }

    public void show() {
        System.out.println("show");
    }

    public static void main(String[] args) throws InterruptedException {
        MultiThreadSynchronized multiThread = new MultiThreadSynchronized();

        MultiThreadSynchronized.Thread1 thread1 = multiThread.new Thread1();
        thread1.start();

        MultiThreadSynchronized.Thread2 thread2 = multiThread.new Thread2();
        thread2.start();
        Thread.sleep(5000);

        System.out.println("余额: " + MultiThreadSynchronized.amount);

    }


}
