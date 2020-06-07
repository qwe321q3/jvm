package com.jvm.dp.singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * double check singleton 双重校验单例模式　多线程线程安全
 * 懒汉式　　　因为使用了懒加载
 *
 */
public class DclSingleton {

    private DclSingleton() {

    }

    /**
     * 增加volatile 方式的DclSingleton对象初始化的时候，会出现指令重排序
     * 对象初始化的步骤：
     * 1.分配内存空间
     * 2.初始化对象
     * 3.对象的引用指向内存空间地址
     *
     * 如果不增加[volatile]关键字，由于指令重排序的存在，有可能出现的2,1,3的执行顺序
     *
     * 当线程a,执行到了new DclSingleton()步骤时，先初始化了对象，线程b进来了之后，发现对象不为空
     * 返回对象实例，这时候很有可能对象的里边的属性值，没有全部赋值完成，有可能本来a=10的，这个时候获取到
     * a却等于0
     *
     */
    static volatile DclSingleton dclSingleton = null;


    /**
     * 关于双重判断
     *
     * 线程a和b同时进入了0处，但是a先获取到了锁，a执行完成之后，b获得了锁
     * 然后执行，这个时候如果没有判断，会重新执行new操作
     * @return
     */
    public static DclSingleton getDclSingleton() {
        if (dclSingleton == null) {
            //0
            synchronized (DclSingleton.class) {
                if (dclSingleton == null) {
                    dclSingleton = new DclSingleton();
                    return dclSingleton;
                }
            }
        }

        return dclSingleton;

    }

    public static void main(String[] args) throws InterruptedException {
        List<DclSingleton> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(DclSingleton.getDclSingleton());
            }).start();
        }


        Thread.sleep(2000);

        for (DclSingleton dclSingleton: list) {
            System.out.println(dclSingleton.hashCode());

        }

    }

}
