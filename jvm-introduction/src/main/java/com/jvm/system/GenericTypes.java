package com.jvm.system;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericTypes {


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        String a = map.get("a");
        String b = map.get("b");
        String c = map.get("c");


        Father f = new Father();
        Child child = new Child();

        List<Father> list = new ArrayList<>();
        list.add(f);
        list.add(child);

        List<? super Father> list1 = new ArrayList<>();

//        list1.add(f);
        list1.add(child);


    }

    static class Father {

    }

    static class Child extends Father {
    }

}


