package com.jvm.leetcode;

import java.util.Arrays;

/**
 * 给定一个int类型的数组，在一个目标数9
 * 返回数组中相加得9的，2个数的索引
 */
public class TwoSum {
    public static void main(String[] args) {

        int []nums = new int[]{2,5,5,11};
        int target = 10;
        System.out.println(Arrays.toString(twoSum(nums,target)));
    }


    /**
     * 返回数组中2个数据相加=目标数，索引数组
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        int []index = new int[2];
        for (int i = 0; i < nums.length ; i++) {
            for(int j = i+1; j<nums.length; j++){
                if(nums[i]+nums[j]==target){
                    index[0]=i;
                    index[1]=j;
                    return index;
                }
            }
        }


        return null;
    }
}
