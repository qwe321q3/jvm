package com.jvm.system;

import java.util.HashMap;
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

    }
}
