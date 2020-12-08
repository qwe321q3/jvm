package com.jvm.classloader;


import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * 自定义类加载器
 * @author tianshuo
 */
public class MyClassLoader extends ClassLoader {


    private byte[] loadByteDate(String name) {
        String path  = "/Users/tianshuo/Downloads/"+name.replaceAll("\\.","/")+".class";
        System.out.println(path);
        byte[] bt ;

        try{
            FileInputStream fi = new FileInputStream(path);
            bt = new byte[fi.available()];
            DataInputStream dataInputStream = new DataInputStream(fi);
            dataInputStream.read(bt);
            return bt;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //根据类的二进制名称获取类的二进制字节数组
        byte[] bytes = loadByteDate(name);

        /**
         * jdk提供的方法，传递二进制字节数据，生成方法区的需要的数据结构
         *
         */
        return defineClass(name, bytes, 0, bytes.length);
    }


    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> aClass = myClassLoader.loadClass("com.jvm.test.A");
        System.out.println(aClass.getClassLoader());
        ClassLoader parent = myClassLoader.getParent();
        System.out.println(parent);
    }
}
