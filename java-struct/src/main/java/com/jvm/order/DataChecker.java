package com.jvm.order;

import java.util.Arrays;
import java.util.Random;

/**
 * 对数器
 * 用于测试算法正确性
 */
public class DataChecker {

    /**
     * 随机生成10000长度的数组
     * 数组的数据随机在10000以内
     * @return
     */
    public static int[] randomArray(){
        Random random = new Random();
        int[] arrs = new int[10000];
        for (int i = 0; i < 10000; i++) {
            arrs[i] = random.nextInt(10000);
        }
        return arrs;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(randomArray()));


    }
}
