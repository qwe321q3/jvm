package com.tianshuo.thread.d1;


/**
 * A，B，C 3个线程，交替执行5次
 * <p>
 * 实现方式：
 * synchronized 条件锁
 * wait 等待  会释放锁
 * notify  通知
 */
public class RoundThread2 {


    private static final int MAX_COUNT = 5;

    public synchronized void showA(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"--");
        this.notify();
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public synchronized void showB(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"----");
        this.notify();
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public synchronized void showC(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"------");
        this.notify();
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {

        RoundThread2 roundThread = new RoundThread2();
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
