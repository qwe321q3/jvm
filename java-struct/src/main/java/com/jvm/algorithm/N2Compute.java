package com.jvm.algorithm;

/**
 * 判断一个数是否是2的N次方
 */
public class N2Compute {
    public static void main(String[] args) {


        int[] arr = {6, 8, 10, 11, 16};


        for (int i = 0; i < arr.length -1; i++) {

            if ((arr[i]&(arr[i]-1))==0){
                System.out.println(arr[i]);
            }

        }


    }
}
