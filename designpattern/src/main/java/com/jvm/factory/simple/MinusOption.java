package com.jvm.factory.simple;

/**
 * 减法操作
 */
public class MinusOption extends Option {


    @Override
    public long result(int num1, int num2) {
        return num1 - num2;
    }
}
