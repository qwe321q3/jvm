package com.tianshuo.thread.d1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *
 * 有2个线程，1个线程在容器中保存10个元素，另外一个线程，监控容器，发现容器中有5个元素之后，线程结束。
 * 可以使用CountDownLatch
 *
 */
public class Container3 {

    private List<Integer> list = new ArrayList<>();

    CountDownLatch countDownLatch = new CountDownLatch(1);//执行门栓数，为0时，门栓打开，同时释放锁

    public void add(Integer a) {
        list.add(a);
    }

    public int count() {
        return list.size();
    }

    public static void main(String[] args) {
        Container3 container = new Container3();

        new Thread(()->{
                System.out.println("线程2启动监控，等待被唤醒");
            try {
                container.countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程2结束");

        }).start();

        new Thread(() ->{
                for (int i = 0; i < 10; i++) {
                    container.add(i);
                    System.out.println("线程1增加" + i);

                    if (i==4) {
                        System.out.println("countDownLatch-1唤醒线程2");
                        container.countDownLatch.countDown();//计数器-1，同时会释放锁
                    }

                }
        }).start();


    }
}
