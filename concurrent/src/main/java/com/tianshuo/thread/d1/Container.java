package com.tianshuo.thread.d1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 有2个线程，1个线程在容器中保存10个元素，另外一个线程，监控容器，发现容器中有5个元素之后，线程结束。
 *
 * volatile 关键字实现，但是效率比较低，而且需要无线循环。
 */
public class Container {

    private volatile List<Integer> list = new ArrayList<>();


    public void add(Integer a) {
        list.add(a);
    }

    public int count() {
        return list.size();
    }

    public static void main(String[] args) {
        Container container = new Container();

        new Thread(() ->{
            for (int i = 0; i < 10; i++) {
                container.add(i);
                System.out.println("线程1增加" + i);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(()->{
            System.out.println("线程2启动");
            while (true) {
                if (container.count() == 5) {
                    System.out.println("线程2结束");
                    break;
                }
            }
        }).start();


    }
}
