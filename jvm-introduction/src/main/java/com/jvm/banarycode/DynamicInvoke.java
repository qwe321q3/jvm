package com.jvm.banarycode;

/**
 * 动态分派
 *
 * 方法的动态分派涉及到的一个重要的概念：方法接受者
 *
 * invokeviratual  字节码指令的多态查找流程
 *
 * 通过比较方法重载和方法重写发现：
 * 1、方法重载时静态的行为； 方法重写是多态的行为；
 * 2、方法重载在编译期的行为； 方法重写是运行期的行为；
 */
public class DynamicInvoke {
    public static void main(String[] args) {
        Fruit apple  = new Apple();
        Fruit orange = new Orange();

        apple.test();
        orange.test();

        apple = new Orange();

        apple.test();
    }

}

class Fruit{

    public void test(){
        System.out.println("fruit !!");
    }

}


class Apple extends Fruit{

    @Override
    public void test() {
        System.out.println("Apple !!");
    }
}

class Orange extends Fruit{
    @Override
    public void test() {
        System.out.println(" Orange !!");
    }
}
