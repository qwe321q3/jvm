package com.tianshuo.thread.lock;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * @ClassName : SemaphoreSample
 * @Description : 测试的共享锁，Semaphore/'sɛməfɔr/ 信号标, 可以用来在限流场景下
 * @Author : tianshuo
 * @Date: 2020-07-28 15:37
 */
public class SemaphoreSample implements Runnable{


    Semaphore semaphore;

    public SemaphoreSample(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {

        try {
            semaphore.acquire();

            System.out.println(Thread.currentThread().getName() + "  --  " + new Date());

            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }

    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < 100; i++) {
            new Thread(new SemaphoreSample(semaphore),"thread"+i).start();
        }

    }
}

