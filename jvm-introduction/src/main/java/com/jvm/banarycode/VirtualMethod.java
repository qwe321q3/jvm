package com.jvm.banarycode;

import java.util.Date;

/**
 * 虚方法表
 * 针对动态分派的过程，虚拟机会在方法区建立一个虚方法表的数据结构（virtual method table）vtable 便于动态分派的查找
 * 针对于invokeinterface来说，虚拟机会建立一个接口表的数据结构（interface method table） itable
 *
 */
public class VirtualMethod {

    public static void  main(String[] args) {

        Animal animal = new Animal();
        Animal dog = new Dog();
        animal.test(new Date());
        dog.test(new Date());

    }
}

class Animal {


    public void test(){
        System.out.println("ainamal test!!");
    }

    public void test(Date date){
        System.out.println("ainamal date!!");
    }

}

class Dog extends Animal{
    @Override
    public void test() {
        System.out.println("Dog test !!!");
    }

    @Override
    public void test(Date date) {
        System.out.println("Dog date!!");
    }
}
