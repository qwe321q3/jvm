package com.jvm.order;

import java.util.Arrays;

/**
 * 快速排序
 * 冒泡 + 分治 + 递归
 * 东拆西补 或者 西拆东补 ，一边拆一边补
 * 1、先找随便找一个基准值（一般都是找第一个）
 * 2、拿剩下的数据和这个基准数来比较，左边放小于基准数的数，右边放大于基准数的数（设置2个指针变量一个指向在数组上头端索引，一个指向在尾端索引，
 * 每次比较时都会找出一个大于或者小于基数的值，来占据空缺的索引位置，直到这2个指针指向相同的索引值时，说明左右2边的数据已经区分完成，
 * 此时基数落入这个索引的地方）
 * 3、然后递归分别对左右两边的数，来做重复第一个基数的操作
 */
public class MyQuickSort {


    /**
     * 排序方法
     * @param arrs
     * @param low
     * @param height
     */
    private static void quickSort(int[]arrs,int low ,int height){
        if(low<height) {
            //设置基准数
            int x = arrs[low];
            //设置i 和 j的值
            int i = low;
            int j = height;
            //分区 获取基准值的索引位置
            int index = partition(arrs, i, j, x);
            //循环左分区的数据
            quickSort(arrs, i, index-1);
            //循环右分区的数据
            quickSort(arrs, index+1, j);

        }

    }

    /**
     * 跳出循环之后，会找到的i == j； 基准数会被放到合适位置。
     * @param arrs
     * @param i
     * @param j
     * @param x
     * @return
     */
    private static int partition(int[] arrs, int i, int j,int x) {

        while(i<j){
//            从尾部依次循环找到第一个小于基准数x的数，然后把这个数放到的基准数的索引位置，
//            然后头部循环位置在从基准数位置+1，开始找大于基准数的位置，然后这个大于基准数值，
//            放入到到上一次尾部索引位置

            //从右边指针处找小于基准数的值,没找到的话，挪动右边指针的索引值。(控制i的值要小于j的值，否则数据会混乱)
            while(arrs[j]>=x && i<j){
               j--;
            }
            //找到小于基准数的值之后，把这个值放到左边的坑儿中。（i不能大于j）
            if (i < j) {
                arrs[i] = arrs[j];
                i++;
            }
            //从左边找小于基准数的值，如果没找到就挪动左指针的索引位置 ，i<j
            while (x > arrs[i] && i<j) {
                i++;
            }

            //如果左边的数小于基准数，把这个数，放到有右边的坑中 ； i < j
            if (i < j) {
                arrs[j] = arrs[i];
                j--;
            }

        }
        //i == j时，把基准数放到此位置上
        arrs[i] = x;
        // 返回此时的索引位置值
        return i;
    }


    public static void quickSort(int[] arrs){
        long startTime = System.currentTimeMillis();

        int low = 0;

        int height = arrs.length-1;

        quickSort(arrs,low,height);

        long endTime = System.currentTimeMillis();

        System.out.println("耗费时间为："+(endTime-startTime));

    }

    public static void main(String[] args) {

//        int[] arrs = new int[]{23,65,3,63,89,33,12,90,45};
        int[] arrs = DataChecker.randomArray();
        System.out.println("快速排序前："+Arrays.toString(arrs));
        quickSort(arrs);
        System.out.println("快速排序后："+Arrays.toString(arrs));

    }
}
