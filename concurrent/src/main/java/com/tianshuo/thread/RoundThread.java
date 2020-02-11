package com.tianshuo.thread;


import sun.jvm.hotspot.opto.Block;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A，B，C 3个线程，交替执行5次
 * <p>
 * 实现方式：
 * Condition 条件锁
 * await 等待  会释放锁
 * signal  信号  相当于notify
 */
public class RoundThread {

    Lock lock = new ReentrantLock();
    Condition conditionA = lock.newCondition();
    Condition conditionB = lock.newCondition();
    Condition conditionC = lock.newCondition();

    private static final int MAX_COUNT = 5;

    public void showA(){
        lock.lock();
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"--");
            conditionB.signal();
            conditionA.await();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void showB(){
        lock.lock();
        try {
            Thread.sleep(1000);

            System.out.println(Thread.currentThread().getName()+"----");
            conditionC.signal();
            conditionB.await();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void showC(){
        lock.lock();
        try {
            Thread.sleep(1000);

            System.out.println(Thread.currentThread().getName()+"------");
            conditionA.signal();
            conditionC.await();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }



    public static void main(String[] args) {

        RoundThread roundThread = new RoundThread();
        new Thread(() -> {
            for (int i = 0; i < MAX_COUNT; i++) {
                roundThread.showA();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < MAX_COUNT; i++) {
                roundThread.showB();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < MAX_COUNT; i++) {
                roundThread.showC();
            }
        }).start();


    }


}
