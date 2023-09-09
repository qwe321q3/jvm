package com.jvm.cascade;

public class Father {
    public Father() {
        System.out.println("Father 类 无参构造方法");
    }

    public Father(String name) {
        System.out.println(name + " Father有参构造方法");
    }
}
