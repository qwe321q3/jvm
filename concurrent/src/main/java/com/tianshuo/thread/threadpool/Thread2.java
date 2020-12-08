package com.tianshuo.thread.threadpool;

/**
 * @Classname Thread1
 * @Description TODO
 * @Date 2020/8/1 0001 22:06
 * @Created by Administrator
 */
public class Thread2 implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" this is thread2 implements Runnable");
    }
}