package com.jvm.lock;

/**
 * 对于long的赋值和读取，因为long类型长度为64位，无法一次性操作，
 * 一次操作不是原子的，在并发环境下，可能会出现一个无法想象的错误
 *
 * volatile
 */
public class MultiThreadVolatileLong {

    public static volatile long t = 0 ; //如果没有加volatile，long类型的值很有可能不能完整输出

    public static class ChangeT implements Runnable {
        private long to;

        public ChangeT(long l) {
            this.to = l;
        }

        @Override
        public void run() {
            while (true) {
                MultiThreadVolatileLong.t = to;
                Thread.yield();
            }
        }
    }

    public static class ReadT implements Runnable {
        @Override
        public void run() {

            while (true) {
                long tmp = MultiThreadVolatileLong.t;

                if (tmp != 1L && tmp != 3L && tmp != 44L && tmp != 55L) {
                    System.out.println("意料之外的值："+tmp);
                    Thread.yield();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ChangeT(1L)).start();
        new Thread(new ChangeT(3L)).start();
        new Thread(new ChangeT(44L)).start();
        new Thread(new ChangeT(55L)).start();

        new Thread(new ReadT()).start();

    }

}
