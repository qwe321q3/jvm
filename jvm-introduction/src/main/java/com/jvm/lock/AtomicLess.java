package com.jvm.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 原子性的
 * Cas无锁算法实例
 *
 * @author Amos
 */
public class AtomicLess {
    /**
     * 最大线程数
     */
    private static final int MAX_THREADS = 3;

    /**
     * 最大任务数
     */
    private static final int MAX_TASKS = 3;

    /**
     * 目标总数
     */
    private static final int TARGET_COUNT = 10000000;

    private long count = 0;
    private AtomicLong atomicLong = new AtomicLong(0);
    private LongAdder longAdder = new LongAdder();

    static CountDownLatch countSynDownLatch = new CountDownLatch(MAX_TASKS);
    static CountDownLatch atomicLatch = new CountDownLatch(MAX_TASKS);
    static CountDownLatch longAdderLatch = new CountDownLatch(MAX_TASKS);



    protected synchronized long inc(){
        return ++count;
    }

    protected synchronized long getCount() {
        return count;
    }

    public void clear() {
        count = 0;
    }

    public class SynchronizedThread implements Runnable {

        protected String name;
        protected long startTime;

        AtomicLess out;
        public SynchronizedThread(AtomicLess o,long startTime) {
            out = o;
            this.startTime = startTime;

        }
        @Override
        public void run() {

            long v = out.getCount();
            while (v < TARGET_COUNT) {
                v = out.inc();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Synchronized spend : "+(endTime- startTime)+"ms"+" v"+v);

            countSynDownLatch.countDown();
        }
    }

    public void testSynchronized() throws InterruptedException {
        ExecutorService exe = Executors.newFixedThreadPool(MAX_THREADS);
        long startTime = System.currentTimeMillis();

        SynchronizedThread synchronizedThread = new SynchronizedThread(this, startTime);
        for (int i = 0; i < MAX_TASKS; i++) {
            exe.submit(synchronizedThread);
        }
        countSynDownLatch.await();
        exe.shutdown();
    }

    public class AtomicThread implements Runnable {


        private long startTime;

        public AtomicThread(long startTime) {
            this.startTime = startTime;
        }

        @Override
        public void run() {
            long v = atomicLong.get();
            while (v < TARGET_COUNT) {
                v = atomicLong.incrementAndGet();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("AtomicThread spend : "+(endTime- startTime)+"ms"+" v"+v);
            atomicLatch.countDown();
        }
    }

    public void testAtomic() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
        long startTime = System.currentTimeMillis();
        AtomicThread atomicThread = new AtomicThread(startTime);
        for (int i = 0; i < MAX_TASKS; i++) {
            executorService.submit(atomicThread);
        }
        atomicLatch.await();
        executorService.shutdown();


    }

    public class AtomicLongThread implements Runnable {


        private long startTime;

        public AtomicLongThread(long startTime) {
            this.startTime = startTime;
        }

        @Override
        public void run() {
            long v = longAdder.sum();
            while (v < TARGET_COUNT) {
                longAdder.increment();
                v = longAdder.sum();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("longAdder spend : "+(endTime- startTime)+"ms"+" v"+v);
            longAdderLatch.countDown();
        }
    }

    public void testLongAdder() throws InterruptedException {
        ExecutorService exe = Executors.newFixedThreadPool(MAX_THREADS);
        long startTime = System.currentTimeMillis();
        AtomicLongThread atomicLongThread = new AtomicLongThread(startTime);
        for (int i = 0; i < MAX_TASKS; i++) {
            exe.submit(atomicLongThread);

        }
        longAdderLatch.await();
        exe.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicLess atomicLess = new AtomicLess();
        atomicLess.testAtomic();
        atomicLess.testSynchronized();
        atomicLess.testLongAdder();
    }

}
