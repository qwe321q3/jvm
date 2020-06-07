package com.jvm.dp.singleton;

/**
 * 饱汉式单例模式
 */
public class Singleton {

    private  Singleton() {
    }

    private static Singleton singleton = new Singleton();


    public static Singleton getInstance() {
        return singleton;
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();

        System.out.println(singleton == singleton2);

        System.out.println(singleton.hashCode());
        System.out.println(singleton2.hashCode());
    }
}
