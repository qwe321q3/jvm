package com.tianshuo.thread.threadpool;

import java.util.concurrent.*;

/**
 * @author tianshuo
 * @Classname ThreadPoolTest
 * @Description 测试ThreadPoolExecutor
 * @Date 2020/8/1 0001 22:42
 */
public class ThreadPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService =new ThreadPoolExecutor(5, 7,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        for (int i = 0; i < 100; i++) {
            executorService.execute(new Thread1());
//            Future<String> str = executorService.submit(new Thread3());
//            System.out.println(str.get());
        }

        executorService.shutdown();

    }
}
