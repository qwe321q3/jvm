package com.jvm.heap;

/**
 * 新生代的类实例
 * <p>
 * E区 和Survivor  from 和 to区大小计算
 * <p>
 * -Xmn5m 设置新生代为5M
 * -XX:SurvivorRatio=2    Eden区/from = Eden区/to = 2    from/to（幸存区）和Eden区的比例
 * x+2y = 5m
 * x/y = 2
 * x为eden区  ，y为from /to 区           4y = 5*1024       y = 5*1024/4 = 1280   x =2560
 * <p>
 * <p>
 * -XX:NewRatio=2  设置老年代与新生代的比例为2  表达式:  老年代/新生代=2
 */
public class NewGenerationHeap {
    public static void main(String[] args) {
        byte[] b = null;

        for (int i = 0; i < 10; i++) {
            b = new byte[1 * 1024 * 1024];
        }
    }
}
