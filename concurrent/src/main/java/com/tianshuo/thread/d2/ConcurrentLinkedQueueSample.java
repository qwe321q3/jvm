package com.tianshuo.thread.d2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * ConcurrentLinkedQueue
 */
public class ConcurrentLinkedQueueSample {

    public static void main(String[] args) {
        Queue<String> queue = new ConcurrentLinkedDeque<>();

        queue.offer("key");

        System.out.println(queue.size());

        System.out.println(queue.peek());  // 会从队列取出数据
        System.out.println(queue.size());

        System.out.println(queue.poll()); //poll会从队列末尾取出数据，同时从队列中移除这个数据
        System.out.println(queue.size());

        List<String> list = new ArrayList<>();
        List<String> syncList = Collections.synchronizedList(list); //构建同步list
        syncList.add("dd");
    }


}
