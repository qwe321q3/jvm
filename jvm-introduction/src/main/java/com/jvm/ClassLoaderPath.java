package com.jvm;

public class ClassLoaderPath {
    public static int a = 0;


    static{
        System.out.println("ClassLoaderPath 初始化");
    }

    public static void main(String[] args) {
        //系统类加载器 BootStrapClassLoader jre/lib
        System.out.println(System.getProperty("sun.boot.class.path"));
        //拓展类加载器 ExtentionClassLoader jre/lib/ext
        System.out.println(System.getProperty("java.ext.dirs"));
        //应用类加载器 AppClassLoader
        System.out.println(System.getProperty("java.class.path"));

        //设置系统类加载器
        System.out.println(System.getProperty("java.system.class.loader"));


        System.out.println("_____________________________________");

        System.out.println(System.getProperty("java.system.class.loader"));


    }
}
