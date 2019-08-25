package com.jvm.order;


/**
 * 折半算法 / 2分算法
 * 找到某个值得索引位置。
 */
public class HalfOrder {


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
