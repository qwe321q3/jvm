package com.jvm.map;

import java.util.HashMap;
import java.util.Map;

public class MyHashMapAnalysis {
    public static void main(String[] args) {


        Map<Integer, String> map = new HashMap<>(13);
        map.put(1, "aa");
        map.put(2, "bb");
        map.get(1);
        System.out.println(map.size());
        System.out.println(8^0);

        System.out.println(MyHashMapAnalysis.hash(92));
    }

    static final int hash(Object key) {
        int h  = key.hashCode();
        System.out.println("hashCode: "+h);
        int h1 = h >>> 16;
        System.out.println(">>>16："+h1);
        return (key == null) ? 0 : (h) ^ (h1);
    }
}
