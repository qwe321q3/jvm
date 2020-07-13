package com.jvm.objectstruct;

import org.openjdk.jol.info.ClassLayout;

/**
 * 使用openJDK 提供的JOL的来打印对象头
 *
 *  对象头= mark word(固定 8字节) + class pointer(指针)未开启指针压缩的情况下占8个字节，开启的情况下占4个字节 <指针压缩默认开启>
 *  指向方法区的类元数据
 *
 *  对象的大小，刚好要被8整除，如果不够8整除，jvm会把填充的到能够被8整除
 *
 *  对象的大小 = mark word (8） + klass pointer(8/4) + 数组大小 + 实例数据大小 + 数据填充
 *
 *  -- 开启压缩
 *  以ObjectHeader空对象来说  8(markword) + 4(klasspointer) + 0(数组大小) + 0(实例数据大小) + 4(数据填充) = 16
 *  -- 未开启压缩
 *  以ObjectHeader空对象来说  8(markword) + 8(klasspointer) + 0(数组大小) + 0(实例数据大小) + 0(数据填充) = 16
 *
 *  //当给ObjectHeader增加int a,b属性
 *
 *  -- 开启压缩 -XX:+UseCompressedOops
 * 以ObjectHeader空对象来说  8(markword) + 4(klasspointer) + 0(数组大小) + 4+4(实例数据大小，int类型占4个字节) + 4(数据填充) = 24
 * -- 未开启压缩 -XX:-UseCompressedOops
 * 以ObjectHeader空对象来说  8(markword) + 8(klasspointer) + 0(数组大小) + 4+4(实例数据大小) + 0(数据填充) = 16
 *
 *
 */
public class ObjectHeader {

    int a = 11;
    int b = 23;

    int[] array = {1, 3, 5};

    public static void main(String[] args) {

        //8+4 +(4+4)+(4*3)+ 0 = 32；
        ObjectHeader object = new ObjectHeader();
        String layout = ClassLayout.parseInstance(object).toPrintable();

        System.out.println(layout);
//        synchronized (object) {
//            System.out.println(layout);
//        }
//
//        for (int i = 0; i <1000_1000 ; i++) {
//
//        }

    }
}
