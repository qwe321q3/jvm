package com.tianshuo.thread;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 有2个线程，1个线程在容器中保存10个元素，另外一个线程，监控容器，发现容器中有5个元素之后，线程结束。
 * 使用synchronized 关键字配合
 * wait 线程等待，同时会释放锁。
 * notify  线程通知时，不会释放锁   ---->  重点
 */
public class Container2 {

    private List<Integer> list = new ArrayList<>();
    Object object = new Object();


    public void add(Integer a) {
        list.add(a);
    }

    public int count() {
        return list.size();
    }

    public static void main(String[] args) {
        Container2 container = new Container2();

        new Thread(()->{
            System.out.println("线程2启动监控，等待被唤醒");
            synchronized (container.object) {
                try {
                    container.object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程2结束");

                container.object.notify();

            }


        }).start();

        new Thread(() ->{
            synchronized (container.object) {
                for (int i = 0; i < 10; i++) {
                    container.add(i);
                    System.out.println("线程1增加" + i);


                    if (i==4) {
                        System.out.println("唤醒线程2");
                        container.object.notify();

                        try {
                            System.out.println("线程1释放锁,并等待");
                            container.object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

        }).start();


    }
}
