package com.tianshuo.thread.d1;

import java.util.LinkedList;

/**
 * 生产者消费者实例
 * <p>
 * 写一个固定同步容器拥有get ，put，getCount方法，容器中最大只能存放10个对象
 * 能够支撑2个生产者，10个消费者线程的阻塞调用
 * wait，notify/notifyAll实现
 * <p>
 * 另外99%的情况，wait都是配合while一起使用的。
 */
public class ProducerConsumerSample<T> {

    private final LinkedList<T> list = new LinkedList<>();

    private final int MAX_COUNT = 10;

    private static int count = 0;

    private synchronized void get() {
//        synchronized (this) {
            //为什么用while而不用if
            //1.如果用if的话，线程醒来之后会继续向下走，不会再判断当前数组是否空了，还有会出现并发问题
            //2.当使用while的时候，线程醒来之后，会首先判断数组是否是空的，然后再看是否是要wait还是继续向下走
            while (getCount() == 0) {
                try {
                    System.out.println(Thread.currentThread().getName() +"消费者 wait");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Integer b = (Integer) list.removeFirst();

            System.out.println(Thread.currentThread().getName() + "\t获取一个元素:" + b + " 剩余" + getCount());
//            count--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.notifyAll();
//        }
    }

    private synchronized void put(T t) {
        while (getCount() == MAX_COUNT) {
            try {
                System.out.println("生产者："+Thread.currentThread().getName()+"\t wait ");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
//        count++;
        System.out.println(Thread.currentThread().getName() + "\t添加一个元素：" + t + " 剩余" + getCount());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.notifyAll();
    }

    private int getCount() {
//        return count;
        return list.size();
    }


    static int a = 1;

    public static void main(String[] args) {

        ProducerConsumerSample<Integer> producerConsumerSample = new ProducerConsumerSample<>();


        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    producerConsumerSample.put(a);
                    a++;
                }

            },"p"+i).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    producerConsumerSample.get();
                }
            },"c"+i).start();
        }

    }


}
