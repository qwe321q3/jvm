package com.jvm.lock;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 同步优化
 * 锁优化：
 * 减少锁的持有时间，提高效率
 * 减少锁力度，缩小锁定对象的范围，从而减少锁冲突的可能性，从而提供系统的并发性能  参考ConcurrentHashMap实现
 * 锁分离 LinkedBlockingQueue take 和 put 分为2把锁
 */
public class LessSynchronizedLock implements Runnable{

    private void method1(){
        for (int i = 0; i <1000000 ; i++) {

        }
        System.out.println("method1 end");
    }

    private void method2(){
        for (int i = 0; i <1000000 ; i++) {

        }
        System.out.println("method2 end");
    }

   // private synchronized void method3(){  //方法锁

        private  void method3(){
        method1();
        synchronized(this) { //减少线程持有锁的时间
            for (int i = 0; i < 500000000; i++) {
//            System.out.println("3");
            }
        }
        method2();
    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        ThreadPoolExecutor pool=new ThreadPoolExecutor(5,10,200, TimeUnit.MILLISECONDS,
                                                        new ArrayBlockingQueue<Runnable>(5));

        for(int i=0;i<15;i++) {
            LessSynchronizedLock lessSynchronizedLock  = new LessSynchronizedLock();
            pool.execute(lessSynchronizedLock);
            System.out.println("线程池中线程数目："+pool.getPoolSize()+"，队列中等待执行的任务数目："+
                    pool.getQueue().size()+"，已执行玩别的任务数目："+pool.getCompletedTaskCount());
        }
        pool.shutdown();

        long endTime = System.currentTimeMillis();

        System.out.println("花费时间："+(endTime-startTime)+"ms");


    }


    @Override
    public void run() {
        method3();
    }
}
