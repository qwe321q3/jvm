package com.tianshuo.thread;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者实例
 * <p>
 * 写一个固定同步容器拥有get ，put，getCount方法，容器中最大只能存放10个对象
 * 能够支撑2个生产者，10个消费者线程的阻塞调用
 * 使用ReentrantLock 配合signalAll和 await ，以及Lock接口下的Condition，实现精准控制消费者和生产者
 * 效率上要比notifyAll要高（因为notifyAll会把消费者和生产者一起唤醒）
 * <p>
 */
public class ProducerConsumerSample2<T> {

    private final LinkedList<T> list = new LinkedList<>();

    private final int MAX_COUNT = 10;

    private Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();


    private void get() {
        lock.lock();
        try {
            while (list.size() == 0) {
                System.out.println(Thread.currentThread().getName() + "消费者 wait");
                consumer.await();//消费者等待
            }
            Integer b = (Integer) list.removeFirst();

            System.out.println(Thread.currentThread().getName() + "\t获取一个元素:" + b + " 剩余" + getCount());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //给所有的生产者发信号
            producer.signalAll();//signal，sigalAll类似 notify 、notifyAll
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void put(T t) {
        lock.lock();
        try {
            while (list.size() == MAX_COUNT) {
                System.out.println("生产者：" + Thread.currentThread().getName() + "\t wait ");
                producer.await();//生产者等待
            }
            list.add(t);
            System.out.println(Thread.currentThread().getName() + "\t添加一个元素：" + t + " 剩余" + getCount());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            consumer.signalAll();//给所有的消费者发信号
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    private int getCount() {
        return list.size();
    }


    static int a = 1;

    public static void main(String[] args) {

        ProducerConsumerSample2<Integer> producerConsumerSample = new ProducerConsumerSample2<>();


        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    producerConsumerSample.put(a);
                    a++;
                }

            }, "p" + i).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    producerConsumerSample.get();
                }
            }, "c" + i).start();
        }

    }


}
