package com.jvm.lock;

/**
 * 测试变量隔离
 *
 */
public class VolatileTest {
    public static class MyThread implements Runnable {

        private volatile boolean stop = false;//如果不加volatile 无法成功修改的stop变量


        public void stopMe() {
            stop = true;
        }
        @Override
        public void run() {
            int i  = 0;
            while (!stop) {
                i++;
            }

            System.out.println("stop the Thread");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread my = new MyThread();
        Thread myThread = new Thread(my);
        myThread.start();
        Thread.sleep(1000);
        my.stopMe();
        Thread.sleep(1000);


    }
}
