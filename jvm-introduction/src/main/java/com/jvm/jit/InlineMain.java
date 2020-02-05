package com.jvm.jit;

/**
 * 测试方法内联
 * spend : 51ms
 *
 * -XX:+Inline  //方法内联默认是打开的
 *
 * 关闭方法内联之后 -XX:-Inline
 *
 * spend : 1607ms
 */
public class InlineMain {
    static int i = 0;

    public static void increment() {
        i ++ ;
    }

    public static void main(String[] args) {
        long b = System.currentTimeMillis();

        for (int j = 0; j < 1000000000; j++) {
            increment();
        }

        long e = System.currentTimeMillis();

        System.out.println("spend : " + (e - b)+"ms");
    }
}
