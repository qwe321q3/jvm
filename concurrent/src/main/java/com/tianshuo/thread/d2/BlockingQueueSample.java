package com.tianshuo.thread.d2;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueSample {
    public static void main(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    queue.put("aa"+i);
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            while(true){
                try {
                    String take = queue.take();
                    if(take!=null){
                        System.out.println(take);
                    }else{
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
        System.out.println("ppppp");


    }


}
