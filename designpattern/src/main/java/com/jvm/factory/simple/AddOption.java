package com.jvm.factory.simple;

/**
 * 加法操作
 */
public class AddOption extends Option {
    @Override
    public long result(int num1, int num2) {
        return num1+num2;
    }
}
