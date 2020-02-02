package com.jvm.lock;

import java.util.List;
import java.util.Vector;


/**
 * ArrayList 不是线程安全的,所以在多线程环境下，是可能会发生索引越界的情况
 *
 * 使用Vector 线程安全的
 * -XX:+UseBiasedLocking
 * -XX:BiasedLockingStartupDelay=0 //jvm启动延迟0秒启动　默认延迟4秒启动
 *
 *　偏向锁：如果一个线程获得了锁，便会进入偏向模式，线程再次获得锁的时候，就不需要做相关的同步操作，从而节省了操作时间
 * 但是如果在此之间进行了锁请求，则锁就取消偏向模式，针对与线程少的时候，竞争不激烈的时候，开启偏向模式确实可以优化效率大概30％，但是
 * 如果线程比较多的时候，竞争激烈的时候，则因为很难保持偏向模式，反而因为频繁的切换模式，效率反而会低
 * 偏向锁开启之前　2个线程
 * 214
 * 216
 *
 * 偏向锁开启之后　2个线程
 * 144
 * 174
 *
 */
public class ThreadUnsafe {
    
    //static List<Integer> list = new ArrayList<>();  //不是线程安全的
    static List<Integer> list = new Vector(); //线程安全的
    public static class AddToList implements Runnable{

        int startNum = 0;

        public AddToList(int startNumber){
            this.startNum = startNumber;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            int count = 0;
            while (count < 1000000) {
                list.add(startNum);
                count++;
                startNum += 2;
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime-startTime);
        }
    }

    public static void main(String[] args) {
            new Thread(new AddToList(0)).start();
            new Thread(new AddToList(1)).start();
    }
}
