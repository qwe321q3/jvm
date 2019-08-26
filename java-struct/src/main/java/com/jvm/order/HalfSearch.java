package com.jvm.order;


/**
 * 折半算法 / 2分算法
 * 找到某个值得索引位置。
 * 思路：
 * 1、定义头指针，定义尾指针
 * 2、计算中间值 mid = (头指针+尾指针)/2
 * 3、要查找的值和mid索引对应的值做比较，如果大于mid索引出值，则舍弃左侧值，修改头指针索引位mid+1; 如果小于mid索引处值，则修改的尾部
 * 指针为mid-1;
 * 4、然后只要头部指针小于或者等于尾部指针就继续按照1-3步继续查找。
 */
public class HalfSearch {


    private static final int [] arrs = new int[]{1,10,13,18,31,40,58,73,90};


    public static void main(String[] args) {

        int score = 40;
        System.out.println(HalfOrder(arrs,score));
    }


    public static int HalfOrder(int[] arrgs,int a){

        int low = 0;

        int heigt = arrs.length;

        while(low<=heigt){

            int mid = (heigt+low)/2;
            if(arrgs[mid]<a){
                low = mid + 1;
            }else if(arrgs[mid] >a){
                heigt = mid - 1;
            }else{
                return mid;
            }
            System.out.println("aaaaa");


        }

        return -1 ;
    }



}
