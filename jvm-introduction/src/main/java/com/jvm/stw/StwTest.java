package com.jvm.stw;

import java.util.HashMap;
import java.util.Map;

/**
 * STW : Stop The World
 * <p>
 * GC期间应用会产生一定时间停顿，这个停顿就叫做stw
 * 这个停顿为了在这个时间点上不产生新的垃圾，同时让系统在一定时间点保持一致性，有利于垃圾回收
 * <p>
 * 但是STW对应用程序来说，会让系统卡死，没有任何响应，这个卡死的时间有可能是灾难性的，所有要尽量减少stw的时间，以及出现。
 * <p>
 * 实例：模拟stw出现
 */
public class StwTest {

    public static class MyThread extends Thread {

        Map map = new HashMap();
        @Override
        public void run() {
            try {
                while (true) {
                    if(map.size()*512/1024/1024>=500){
                        map.clear();
                        System.out.println("map is clean");
                    }
                    byte[]b = null;
                    for (int i = 0; i <100 ; i++) {
                        b = new byte[512];
                        map.put(System.nanoTime(), b);
                    }
                    Thread.sleep(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class PrintThread extends Thread {
        public static final long START_TIME = System.currentTimeMillis();

        @Override
        public void run() {
            try {
                while (true) {
                    long t = System.currentTimeMillis() - START_TIME;
                    System.out.println(t/1000+"."+t%1000);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
        PrintThread printThread = new PrintThread();
        printThread.start();
    }

}
