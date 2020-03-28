package com.jvm.objectstruct;

import org.openjdk.jol.info.ClassLayout;

/**
 * 使用openJDK 提供的JOL的来打印对象头
 *
 *  对象头= mark word(固定 8字节) + class pointer(指针) 只想方法区的类元数据
 *
 *  对象的大小，刚好要被8整除，如果不够8整除，jvm会把填充的到能够被8整除
 *
 */
public class ObjectHeader {

    public static void main(String[] args) {
        Object object = new Object();
        String layout = ClassLayout.parseInstance(object).toPrintable();

        System.out.println(layout);
        synchronized (object) {
            System.out.println(layout);
        }

        for (int i = 0; i <1000_1000 ; i++) {

        }

    }
}
