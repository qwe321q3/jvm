package com.jvm.cascade;

public class Child extends Father{
    public Child() {
        System.out.println("Child 类 无参构造方法");
    }

    public Child(String name) {
        System.out.println(name + " Child有参构造方法");
    }
}