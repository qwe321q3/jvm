package com.jvm.methodarea;

/**
 * 类加载的时候，会把类信息存放到的方法区中
 * 方法区中存放类信息（方法，字段），常量池，字节码等
 * <p>
 * 方法区在jdk1.8中可以理解为MetaSpace 元空间
 * 元空间是一块堆外的直接内存
 * 元空间大小如果没有设置的话，默认和系统内存有关，虚拟机会耗光所有的系统内存。
 * 设置方式 -XX:MetaspaceSize 初始元空间大小 和  -XX:MaxMetaspaceSize  最大元空间大小
 *
 * 实例：　-XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=20M
 * <p>
 * <p>
 * 方法区在jdk1.6,1.7可以理解为永久代  permanent  用参数-XX:PermSize 和-XX:MaxPermSize（默认64M）设置
 */
public class MetaSpace {

    //因为元空间是要存放对象的字段字面量，方法，常量池，字节码
    //相同的类存放的信息之后保存一份，如果要测试的话，需要有很多不同类的信息来测试
    //暂未找到测试测试元空间的方法，暂时就不增加的实例了。

    public static void main(String[] args) {
        System.out.println("111");
    }

}
