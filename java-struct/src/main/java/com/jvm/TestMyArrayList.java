package com.jvm;

public class TestMyArrayList {

    public static void main(String[] args) {
        MyArrayList<String>myArrayList = new MyArrayList<>();

        myArrayList.add("11");
        myArrayList.add("432");
        myArrayList.add("44");
        myArrayList.add("77");

        myArrayList.add("44");
        myArrayList.add("77");

        myArrayList.add(2,"aaa");

        System.out.println(myArrayList.isEmpty());
        System.out.println(myArrayList.size());
        System.out.println(myArrayList.get(3));
        System.out.println(myArrayList);

    }
}
