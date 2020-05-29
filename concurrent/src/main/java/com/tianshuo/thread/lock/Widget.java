package com.tianshuo.thread.lock;

/**
 * 测试内置锁是否可以重入，结论：可重入
 * synchronized 对调用来说，是互斥的，对线程来说是可重入的
 * @author tianshuo
 */
public class Widget {

    public synchronized void doSomething(){

    }


    public static void main(String[] args) {
        LoggingWidget loggingWidget = new LoggingWidget();
        loggingWidget.doSomething();
    }

}

class LoggingWidget extends Widget {

    @Override
    public synchronized void doSomething() {
        System.out.println("sing");
        super.doSomething();
    }
}
