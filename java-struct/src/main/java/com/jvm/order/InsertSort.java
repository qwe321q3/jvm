package com.jvm.order;

import java.util.Arrays;

/**
 * 插入排序
 * 从1开始，和前面的索引位置数比较大小，然后如果当前是位置，小于前面位置的数据就交换位置
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] arr = DataChecker.randomArray(100000);
        System.out.println("排序前："+Arrays.toString(arr));

        long startTime = System.currentTimeMillis();

        insertSort(arr);
        long endTime = System.currentTimeMillis();
        System.out.println("耗费时间：" + (endTime - startTime));


        System.out.println("排序完成：" + Arrays.toString(arr));
        System.out.println(DataChecker.check(arr));
    }

    /**
     * 插入排序
     * @param arr
     */
    public static void insertSort(int[] arr) {
        for (int x = 1; x < arr.length; x++) {
//            int minPosition = 0;
            for (int i = x; i > 0&& arr[i]<arr[i-1]; i--) {
//                if(arr[i]<arr[i-1]){
//                    minPosition = i-1;
                arr[i] = arr[i] ^ arr[i-1];
                arr[i-1] = arr[i] ^ arr[i-1];
                arr[i] = arr[i] ^ arr[i-1];
                }

            }
//            if(minPosition!=x) {

//            }
        }


    }
