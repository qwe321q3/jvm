package com.tianshuo.thread.d2;

/**
 * @ClassName : InterrupterTest
 * @Description : 测试线程打断方法
 * @Author : tianshuo
 * @Date: 2020-08-08 17:00
 */
public class InterrupterTest {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("t1线程被打断！！");
                e.printStackTrace();
            }
        });

        Thread t2  =new Thread(()->{
            while(!Thread.interrupted()) {
                System.out.println(Thread.interrupted());
                System.out.println("T2 线程被打断！！");
                break;
            }

        });

        t1.start();

        System.out.println("开始打断T1线程！");

        t1.interrupt();

        System.out.println("开始打断t2线程！");
        t2.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();

        System.out.println("结束");



    }
}

