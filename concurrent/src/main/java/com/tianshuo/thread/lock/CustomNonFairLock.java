package com.tianshuo.thread.lock;


import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * 自定义锁  非公平
 * <p>
 * 模拟AQS
 * <p>
 * 使用CAS来保证的锁状态信号量
 */
public class CustomNonFairLock {


    /**
     * 锁状态  0未获取到锁
     */
    private volatile int state;

    /**
     * state字段的内存偏移量
     */
    private static long stateOffset;


    /**
     * 锁持有人线程
     */
    private Thread threadHolder;


    /**
     * 保存未获取到锁的线程的队列
     */
    private ConcurrentLinkedQueue<Thread> threadConcurrentLinkedQueue = new ConcurrentLinkedQueue<>();


    static {

        try {
            stateOffset = getUnsafe().objectFieldOffset(CustomNonFairLock.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    /**
     * 尝试获取锁 ，由0，改成1
     *
     * @return
     */
    public boolean tryAcquire() {
        int state = getState();
        if (state == 0) {
            threadHolder = Thread.currentThread();
            return compareAndSweep(state, 1);
        }
        return false;
    }


    /**
     * 加锁
     */
    public void lock() {
        //1、使用cas改变锁状态字段，改变成功，代表拿到锁

        System.out.println("lock before: " + this);

        if (tryAcquire()) {
            System.out.println("lock after: " + this);
            System.out.println("线程: " + threadHolder.getName() + " 拿到了锁!");

            return;
        }

        //2、未拿到锁的线程的使用自旋空轮询，继续尝试使用Cas修改锁状态，如果还是没成功，线程等待，同时加入到队列中
        Thread currentThread = Thread.currentThread();
        threadConcurrentLinkedQueue.add(currentThread);
        for (; ; ) {
            System.out.println("lock before n: " + this);

            if (tryAcquire()) {
                System.out.println("线程: " + threadHolder.getName() + " 拿到了锁!");
                threadConcurrentLinkedQueue.poll();
                return;
            }


            System.out.println("lock afler z: " + this);

            LockSupport.park(currentThread);
        }

    }


    /**
     * 释放锁
     */
    public void unlock() {
        // 如果锁持有的对象 == 当前对象，使用cas操作，改变锁状态为0，然后把持有锁的线程设置为空
        Thread currentThread = Thread.currentThread();
        if (currentThread == threadHolder) {
            int state = getState();
            if (compareAndSweep(state, 0)) {
                threadHolder = null;

                Thread head = threadConcurrentLinkedQueue.peek();
                if (head != null) {
                    LockSupport.unpark(head);
                }

                System.out.println("释放锁成功！");
            }
        }

    }


    /**
     * 使用JDK类的UnSafe类，做cas操作
     *
     * @param oldValue
     * @param newValue
     * @return
     */
    public boolean compareAndSweep(int oldValue, int newValue) {
        try {
            return getUnsafe().compareAndSwapInt(this, stateOffset, oldValue, newValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Unsafe getUnsafe() throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        return unsafe;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return "CustomNonFairLock{" +
                "state=" + state +
                ", threadHolder=" + threadHolder +
                ", threadConcurrentLinkedQueue=" + threadConcurrentLinkedQueue +
                '}';
    }
}
