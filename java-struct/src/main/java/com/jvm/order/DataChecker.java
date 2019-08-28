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
    public static int[] randomArray(int size){
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size);
        }
        return arr;
    }


    /**
     * 数据校验
     * @param arr
     * @return
     */
    public static boolean check(int[] arr){

        int[] arr2 = Arrays.copyOfRange(arr, 0, arr.length);

        Arrays.sort(arr2);

        for (int i = 0, len = arr.length; i < len; i++) {

            if (arr[i] != arr2[i]) {
                return false;
            }
        }


        return true;
    }


    public static void main(String[] args) {

        int[]arr = randomArray(10);
        System.out.println("排序前："+Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println("排序后："+Arrays.toString(arr));


    }
}
