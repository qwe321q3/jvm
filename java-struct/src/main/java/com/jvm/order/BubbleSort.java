package com.jvm.order;


import java.util.Arrays;

/**
 * 冒泡排序
 *
 *
 */
public class BubbleSort {


    public static void main(String[] args) {

        int[] arr = DataChecker.randomArray(100000);
        long startTime = System.currentTimeMillis();
        System.out.println("排序前: "+ Arrays.toString(arr));

        bubbleSort(arr);

        long endTime  = System.currentTimeMillis();
        System.out.println("排序是否正确："+DataChecker.check(arr));
        System.out.println("耗费时间为："+(endTime-startTime));
        System.out.println("排序之后："+Arrays.toString(arr));


    }

    /**
     * 冒泡排序  从小到大
     * 比较每个数据大小，当前数据比大于后面数据交换位置
     * @param arr
     */
    public static void bubbleSort(int[] arr) {

        for (int i = 0; i < arr.length; i++) {

            for(int j = i+1; j <arr.length;j++){
                if(arr[i]>arr[j]){
                    //按位异或交换律
                    arr[i]= arr[i]^arr[j];
                    arr[j]=arr[i]^arr[j];
                    arr[i] = arr[i]^arr[j];
                }
            }

        }


    }

}
