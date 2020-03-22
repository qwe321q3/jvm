package com.jvm.dp.factory.simple;

/**
 * 除法
 *
 */
public class DivisionOption extends Option {
    @Override
    public long result(int num1, int num2) {
        return num1 / num2;
    }
}
