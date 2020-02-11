package com.tianshuo.thread.d1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 有2个线程，1个线程在容器中保存10个元素，另外一个线程，监控容器，发现容器中有5个元素之后，线程结束。
 * 使用synchronized 关键字配合
 * wait 线程等待，同时会释放锁。
 * notify  线程通知时，不会释放锁   ---->  重点
 *
 *
 * 重点 多线程过程中，异常一定要处理，否则虚拟机会自动帮你释放锁。
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
//         如果object引用变了之后，就相当于2个object对象，各是各的的锁，原子性就会被破坏，所以锁是所在堆的空间的new Object对象上。
//        container.object = new Object();


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
