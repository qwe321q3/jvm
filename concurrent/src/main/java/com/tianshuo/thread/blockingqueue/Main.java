package com.tianshuo.thread.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {


        int BOUND = 10;

        int N_PRODUCERS = 16;

        /**
         * 获取的CPU逻辑核心数 4
         */
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
        //poisonPill int类型的最大值
        int poisonPill = Integer.MAX_VALUE;

        // 4/16 = 0
        int poisonPillPerProducer = N_CONSUMERS / N_PRODUCERS;

        //4%16 = 4
        int mod = N_CONSUMERS % N_PRODUCERS;

        //链表实现的阻塞队列 ，队列长度为10
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(BOUND);

        //潘金莲给武大郎熬药 16
        for (int i = 1; i < N_PRODUCERS; i++) {
            new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer)).start();
        }

        //武大郎开始喝药 4
        for (int j = 0; j < N_CONSUMERS; j++) {
            new Thread(new NumbersConsumer(queue, poisonPill)).start();
        }
        //潘金莲开始投毒，武大郎喝完毒药GG 0+4
        new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer + mod)).start();

    }


}