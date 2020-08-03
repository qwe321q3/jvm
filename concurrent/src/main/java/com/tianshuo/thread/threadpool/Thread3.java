package com.tianshuo.thread.threadpool;

import java.util.concurrent.Callable;

/**
 * @Classname Thread1
 * @Description TODO
 * @Date 2020/8/1 0001 22:06
 * @Created by Administrator
 */
public class Thread3 implements Callable<String> {
    @Override
    public String call() throws Exception {
        return Thread.currentThread().getName()+" this is Thread3 implements Callable<String>!";
    }
}
