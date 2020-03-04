package com.tianshuo.thread.d2;

import java.util.Vector;

/**
 * 2个售票厅，来放票
 * 10个客户端来取票
 * <p>
 * 结论：出现线程安全问题，索引越界
 * 问题1：arrayList不是线程安全的容器 使用vector
 * 问题2：使用vector之后 ，虽然add和remove方式是同步的，但是多个通过方法之后的操作的不是原子的，所有就导致就算使用了同步容器，还是有
 * 线程同步问题
 */
public class UnsafeArray {

    //    static final List<Integer> ticketList = new ArrayList<>();
    static final Vector<Integer> ticketList = new Vector<>();
    static {
        for (int j = 0; j < 10000; j++) {
            ticketList.add(j);
        }
    }

    public static void main(String[] args) {


        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
//                synchronized(ticketList) { //粗粒度锁
                while (ticketList.size() > 0) {
                    System.out.println(Thread.currentThread().getName() + "\t获取票据" + "\t 票据剩余:  " + ticketList.size());
                    ticketList.remove(0);
                }
//                }
            }).start();
        }


    }

}
