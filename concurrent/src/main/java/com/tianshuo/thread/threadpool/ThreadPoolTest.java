package com.tianshuo.thread.threadpool;

import java.util.concurrent.*;

/**
 * @Classname ThreadPoolTest
 * @Description TODO
 * @Date 2020/8/1 0001 22:42
 * @Created by Administrator
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService executorService =new ThreadPoolExecutor(5, 7,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        for (int i = 0; i < 100; i++) {
            executorService.execute(new Thread1());
        }

        executorService.shutdown();

    }
}
