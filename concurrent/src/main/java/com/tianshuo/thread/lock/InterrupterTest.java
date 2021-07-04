package com.tianshuo.thread.lock;

import lombok.SneakyThrows;

/**
 * @ClassName : InterrupterTest
 * @Description : interrupt 对使用了synchronized方法或者代码块不起作用
 * @Author : tianshuo
 * @Date: 2021-07-04 20:20
 */
public class InterrupterTest {

    public static void main(String[] args) {


        Thread t1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (this) {
                    // Thread.sleep(10000);
                    for (int i = 0; i < 24494222; i++) {
                        System.out.println("ssss");
                    }
                }
            }
        });

        t1.start();
        Thread t = new Thread(() ->
                t1.interrupt());
        t.start();
        System.out.println("打断T1");
    }
}

