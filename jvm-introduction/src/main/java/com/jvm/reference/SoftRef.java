package com.jvm.reference;

import java.lang.ref.SoftReference;

/**
 * 测试软引用
 * 前提设置最大堆空间 -Xmx20m
 */
public class SoftRef {

    public static void main(String[] args) throws InterruptedException {

        SoftReference<byte[]> softReference = new SoftReference<>(new byte[1024 * 1024 * 10]);
        System.out.println(softReference.get());


        System.gc();
        Thread.sleep(900);


        System.out.println(softReference.get());

        byte[] b = new byte[1024 * 1024 * 15];

        System.out.println(softReference.get());
    }
}
