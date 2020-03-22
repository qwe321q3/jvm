package com.jvm.dp.factory.simple;

/**
 * 乘法
 */
public class MultiplyOption extends Option {
    @Override
    public long result(int num1, int num2) {
        return num1 * num2;
    }
}
