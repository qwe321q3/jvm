package com.jvm.map;

/**
 *
 * 1、分析HashMap的容量大小为什么要是2的次幂
 * 2、分析为什么HashMap可以使用的 按位&操作来代替 %操作
 * @author tianshuo
 */
public class Compute {

    public static void main(String[] args) {

        /**
         * 为什么HashMap的容量要是2次幂，
         * 1、为了使用&操作来替代%运算提高效率
         * 8%4 为了可以使用 8&(4-1) 替代
         *                         1000
         * 8%4=0   = 8&(4-1)   --> 0100
         *                         0000    0
         * 2、为什么要被&的数要减去一
         *  减一刚好能去掉的4的二进制高位的1，能限制结果一定小于4
         *
         *  如果不减一时   4&4=4     如果在这种情况下会出现索引越界。
         *
         *  & 操作上下为1，则为1。
         *
         */
        System.out.println(8% 4);
        System.out.println(8&(4-1));
    }
}
