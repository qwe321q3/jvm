package com.jvm.order;

import java.util.Arrays;

/**
 * 选择排序
 * 1.外层循环每次都是数组长度n次，
 * 2.内层循环每次会找到一个最小的数，第一次循环会找到第0位置上的最小数，第二次循环会找到第一个位置上的数
 * 所以内层循环每次都是要从外层索引+1的地方开始循环判断，减少循环次数。
 *
 *
 */
public class SelectionSort {

    public static void main(String[] args) {

        int [] arr = DataChecker.randomArray(100000);

        long startTime = System.currentTimeMillis();
        selectionSort(arr);
        long endTime = System.currentTimeMillis();
        System.out.println("耗费时间：" + (endTime - startTime));
        System.out.println("排序完成："+Arrays.toString(arr));
        System.out.println("排序结果"+DataChecker.check(arr));
    }


    /**
     * 选择排序
     * @param arr
     */
    private static void selectionSort(int[]arr){
      //  System.out.println("排序前: "+Arrays.toString(arr));

        for (int x = 0 ; x <arr.length; x++) {
            //定义变量保存最小的数据索引
            int minPosition = x;
            //遍历数组，让数组的每个数都和这个minPosition这个索引的数据做比较，如果这个数据让他在数组的第一个位置
            for (int i = x+1; i < arr.length; i++) {
                if (arr[i] < arr[minPosition]) {
                    //如果当前数小于，这个minPosition索引对应的数，那么minPosition就设置为当前为的数的索引
                    //然后使用按位异或的交换律来做的数据交换

                    arr[i] = arr[i] ^ arr[minPosition];
                    arr[minPosition] = arr[i] ^ arr[minPosition];
                    arr[i] = arr[i] ^ arr[minPosition];

                }
            }

           // System.out.println("第"+x+"排序后：" + Arrays.toString(arr));
        }

    }
}
