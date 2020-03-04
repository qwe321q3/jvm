package com.tianshuo.thread.d2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 10000张票
 * 10个客户端来取票
 *
 * 结论：出现线程安全问题，索引越界
 * 解决方案：使用同步容器或者使用原子操作，或者方法加锁
 *
 *
 */
public class SafeContainer {

    //static final List<Integer> ticketList = new ArrayList<>();
//    static final Vector<Integer> ticketList = new Vector<>();
    static Queue<Integer> ticketList = new ConcurrentLinkedQueue<>();


    static{
        for (int j = 0; j < 10000; j++) {
            ticketList.add(j);
        }
    }

    public static void main(String[] args) {


        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while(true){
                    Integer ix = ticketList.poll();
                    if(ix!=null)
                    System.out.println(Thread.currentThread().getName()+"\t获取票据"+"\t 票据剩余:  "+ticketList.size());
                    else break;
                }
            }).start();
        }


    }

}
