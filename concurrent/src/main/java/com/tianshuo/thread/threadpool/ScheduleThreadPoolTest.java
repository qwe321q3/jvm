package com.tianshuo.thread.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : ScheduleThreadPoolTest
 * @Description : 延时线程池测试
 * @Author : tianshuo
 * @Date: 2021-04-13 22:28
 */
public class ScheduleThreadPoolTest {

    public static void main(String[] args) {

        // 延迟3秒之后执行
        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        System.out.println("开始任务");
        //延时3秒执行
        service.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行任务");
                // 循环延迟
                // service.schedule(this, 3, TimeUnit.SECONDS);
            }
        }, 3, TimeUnit.SECONDS);


        ScheduledExecutorService service1 = Executors.newScheduledThreadPool(3);
        System.out.println("开始任务");
        //延时3秒执行,每1秒执行一次
        service1.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行任务");
            }
        }, 3, 1, TimeUnit.SECONDS);

    }


}

