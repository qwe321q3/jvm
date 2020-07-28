package com.tianshuo.thread.lock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName : CustomReenLock
 * @Description : 模拟一个可重入的锁，回顾ReentrantLock知识
 * 模拟公平锁
 * 1、如果先尝试加锁，如果加锁失败，入队。
 * @Author : tianshuo
 * @Date: 2020-07-27 15:00
 */
public class CustomFairLock {

    /**
     * 标识加锁次数
     * 为0标识锁可用，可用加锁
     */
    private volatile int state;


    /**
     * 记录偏移量用来的处理
     */
    private static long stateOffset;


    /**
     * 记录持有锁的线程
     */
    private Thread exclusiveThreadHolder;


    /**
     * 未获取到锁的线程放入队列中
     */
    private ConcurrentLinkedQueue<Thread> threadConcurrentLinkedQueue = new ConcurrentLinkedQueue<>();


    /**
     * 1、判断锁状态是否可以用
     * 2、判断队列是否初始化，如果未初始化，可以尝试获取锁 获取成功返回true
     * 3、如果已初始化，判断当前线程是否是获得锁的线程如果是的话，锁state + arg
     *
     * @param arg
     * @return
     */
    private boolean acquire(int arg) {
        int c = getState();
        Thread currentThread = Thread.currentThread();
//        System.out.println(" --线程: " + currentThread + "尝试获取锁！");
        if (c == 0) {
            if (!hashInitialQueue() && compareAndSweepState(c, arg)) {
//                System.out.println(" --线程: " + currentThread + "获取到锁！");

                setExclusiveThreadHolder(currentThread);
                return true;
            }
        } else if (currentThread == exclusiveThreadHolder) {
            c = getState() + arg;
            setState(c);
            return true;
        }

        return false;

    }

    /**
     * 判断队列是否初始化，如果未初始化，初始化队列同时返回false，
     * 已初始化队列返回true
     *
     * @return
     */
    private boolean hashInitialQueue() {
        if (threadConcurrentLinkedQueue.size()<=0) {
            return false;
        }
        return true;
    }

    /**
     * 获取锁，判断当前线程是否是队列中取出第一个数据，
     * 如果是尝试获取锁，如果不是阻塞
     *
     * @param arg
     * @return
     */
    private boolean tryAcquire(int arg) {
        if (!acquire(arg) && enqueue()) {
//            System.out.println("tryAcquire队列数据:" + threadConcurrentLinkedQueue);
            //循环获取锁
            for (; ; ) {
                Thread thread = threadConcurrentLinkedQueue.peek();

                Thread currentThread = Thread.currentThread();


//                System.out.println(" thread: " + thread + "  尝试获取锁!" +" 当前线程： "+currentThread);

                if (thread == currentThread) {
//                    System.out.println(" thread: " + thread + "  尝试获取锁!");
                    //判断再次判断是否可以获得锁。如果获取不到锁就park
                    if (compareAndSweepState(0, arg)) {

                        setExclusiveThreadHolder(currentThread);
                        threadConcurrentLinkedQueue.remove(currentThread);

                        return true;
                    }
                } else {
                    LockSupport.park();
                }
            }
        }

        return false;
    }

    /**
     * 解锁
     * 如果解锁失败，并且队列中还有线程，唤醒第一个线程。
     */
    public void unlock() {
        try {
            if (releaseLock(1)) {

                if (threadConcurrentLinkedQueue.size() > 0) {
//                    System.out.println("唤醒队列的下一个线程");
                    Thread t = threadConcurrentLinkedQueue.peek();
                    if (t != null) {
                        LockSupport.unpark(t);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放锁
     *
     * @param release
     * @return
     * @throws Exception
     */
    private boolean releaseLock(int release) throws Exception {
        Thread currentThread = Thread.currentThread();

//        System.out.println("当前线程: " + currentThread + "  正在解锁");
//        System.out.println("解锁时队列: " + threadConcurrentLinkedQueue);

        if (currentThread != getExclusiveThreadHolder()) {
            throw new Exception("不是同一个加锁的线程");
        }

        int c = getState() - release;
        if (c == 0) {
//            System.out.println("线程：" + currentThread.getName() + " 释放锁成功！");
            setExclusiveThreadHolder(null);
            setState(c);
            return true;
        }

        setState(c);
        return false;
    }

    /**
     * 获取锁失败，当前线程入队列
     */
    private boolean enqueue() {

        threadConcurrentLinkedQueue.offer(Thread.currentThread());
        return true;

    }


    /**
     * 加锁
     */
    public void lock() {
        tryAcquire(1);
    }


    private boolean compareAndSweepState(int expect, int value) {
        return getUnsafe().compareAndSwapInt(this, stateOffset, expect, value);
    }



    static {

        try {
            stateOffset = getUnsafe().objectFieldOffset(CustomFairLock.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }


    public static Unsafe getUnsafe() {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = null;
        try {
            unsafe = (Unsafe) unsafeField.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return unsafe;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Thread getExclusiveThreadHolder() {
        return exclusiveThreadHolder;
    }

    public void setExclusiveThreadHolder(Thread exclusiveThreadHolder) {
        this.exclusiveThreadHolder = exclusiveThreadHolder;
    }
}

