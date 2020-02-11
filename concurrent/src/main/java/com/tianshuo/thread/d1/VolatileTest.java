package com.tianshuo.thread.d1;

/**
 * 测试变量隔离
 *
 * volatile使线程之间变量可见了
 * 变量stop会被放到cpu的高速缓存中，当不使用volatile时候，设置的stop的true值时，内存中的stop虽然已经是true值，
 * 但是cpu，高速缓存中的还是false，当变量使用了volatile的时候，变量值变化了之后，会同时更新cpu中的高速缓存中的值，
 * 所以线程才会停止。
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
                //线程在空闲期间也是会更新CPU的高速缓冲区的变量值，但是这个什么时候空闲，这个软件无法控制，要看cpu如果实现的
                //增加此段打印，不使用volatile关键字，线程也是会被停止的
//                System.out.println("123123131");
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
