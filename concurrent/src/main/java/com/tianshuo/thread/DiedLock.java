package com.tianshuo.thread;

/**
 * 死锁模拟
 * 定义2个对象，对象A，对象B，用来做加锁使用
 * 线程A，把锁加到对象A上，同时获取对象B上的锁，在获取对象B的锁的时候，停顿1秒，方式线程执行速度过快导致线程b还未拿到对象B的对象.
 * 线程B ,把锁加载对象B上,同时获取对象A上的锁
 * 此时就造成了死锁
 */
public class DiedLock {
    Object objectA = new Object();
    Object objectB = new Object();

    public  void showA() {
        synchronized (objectA) {
            System.out.println("showA 获取objectA的锁");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (objectB) {
                System.out.println("showA 获取objectB的锁");
            }
        }
    }


    public  void showB() {
        synchronized (objectB) {
            System.out.println("showB 获取objectB的锁");
            synchronized (objectA) {
                System.out.println("showB 获取objectA的锁");
            }
        }
    }

    public static void main(String[] args) {
        DiedLock diedLock = new DiedLock();
        new Thread(diedLock::showA).start();
        new Thread(diedLock::showB).start();
    }


}
