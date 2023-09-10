package com.jvm.system;

import java.util.Arrays;
import java.util.List;

/**
 * 自动装箱，拆箱和增强for循环
 * <p>
 * 反编译之后：
 * List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
 * int sum = 0;
 * <p>
 * int a;
 * for(Iterator var3 = list.iterator(); var3.hasNext(); sum += a) {
 * a = (Integer)var3.next();
 * }
 * <p>
 * System.out.println(sum);
 */
public class AutoBoxing {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);


        int sum = 0;
        for (int a : list) {
            sum += a;
        }
        System.out.println(sum);
    }
}
