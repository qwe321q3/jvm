package com.tianshuo.thread;

/**
 * volatile实例
 * volatile关键字可以变量在线程之间可见
 * 真实原因是变量值会被放置在cpu的高速缓冲区中，如果内存中的变量值变化了，cpu高速缓冲区很可能没有来得及刷新，会导致其他线程获取到的变量值
 * 是旧值，从而影响业务操作，使用了volatile关键字之后，变量值更新之后，会通知cpu更新高速缓冲区
 *
 *
 * 补充：cpu在空闲的时候，也是会更新cpu的高速缓冲区的，但是具体什么时候空闲，这个软件无法决定，这个是由CPU的硬件厂商决定的
 *
 */
public class VolatileThread {
   //volatile可以使cpu更新高速缓冲区，本例子如果不加volatile结果有可能对，也有可能错的
   volatile Person person = new Person();

    public void changeName(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(person.name);
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileThread volatileThread = new VolatileThread();

        new Thread(volatileThread::changeName).start();

        new Thread(()->{
            volatileThread.person.name = "wang";
        }).start();
    }
}
class Person{
    String name = "zhangsan";
}

