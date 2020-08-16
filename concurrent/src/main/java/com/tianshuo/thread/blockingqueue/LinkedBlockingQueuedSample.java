package com.tianshuo.thread.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Classname LinkedBlockingQueuedSample
 * @Description TODO
 * @Date 2020/8/16 0016 17:45
 * @Created by Administrator
 */
public class LinkedBlockingQueuedSample {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

        for (int i = 0; i < 10; i++) {
            blockingQueue.offer("a" + i);
        }

        System.out.println("-----------------");

        for (int i = 0; i < 10; i++) {
            System.out.println(blockingQueue.take());
        }


    }
}
