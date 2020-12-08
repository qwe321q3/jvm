package com.tianshuo.thread.threadpool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName : CtlTest
 * @Description : 测试ThreadPoolExecutor的线程数量变化
 * @Author : tianshuo
 * @Date: 2020-08-04 14:48
 */
public class CtlTest {

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    // Packing and unpacking ctl
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }


    public static void main(String[] args) {


        System.out.println("RUNNING:"+RUNNING);
        System.out.println("SHUTDOWN:" + SHUTDOWN);
        System.out.println("STOP: " + STOP);
        System.out.println("TIDYING: " + TIDYING);
        System.out.println("TERMINATED:" + TERMINATED);
/*
        默认状态:-536870912
        默认状态：11100000000000000000000000000000
        默认线程池线程数量：0
        默认线程池数量二进制：0
        _1线程池线程数量：1
        _1线程池数量二进制：1
        _1状态:-536870912
        _1状态：11100000000000000000000000000000
        _2线程池线程数量：2
        _2线程池数量二进制：10
        _2状态:-536870912
        _2状态：11100000000000000000000000000000*/
        CtlTest ctlTest = new CtlTest();

        int def = ctlTest.ctl.get();
        System.out.println("默认状态:"+runStateOf(def));
        System.out.println("默认状态：" + Integer.toBinaryString(def));
        int defWorkerCount = workerCountOf(def);
        System.out.println("默认线程池线程数量："+defWorkerCount);
        System.out.println("默认线程池数量二进制："+Integer.toBinaryString(defWorkerCount));

        int _1 = ctlTest.ctl.incrementAndGet();
        int _workerCount = workerCountOf(_1);

        System.out.println("_1线程池线程数量："+_workerCount);
        System.out.println("_1线程池数量二进制："+Integer.toBinaryString(_workerCount));
        System.out.println("_1状态:"+runStateOf(def));
        System.out.println("_1状态：" + Integer.toBinaryString(def));

        int _2 = ctlTest.ctl.incrementAndGet();
        int _workerCount2 = workerCountOf(_2);

        System.out.println("_2线程池线程数量："+_workerCount2);
        System.out.println("_2线程池数量二进制："+Integer.toBinaryString(_workerCount2));
        System.out.println("_2状态:"+runStateOf(def));
        System.out.println("_2状态：" + Integer.toBinaryString(def));


        int ws = ctlOf(_2, _workerCount2);

        System.out.println("ws 二进制值："+Integer.toBinaryString(ws));



    }


}

