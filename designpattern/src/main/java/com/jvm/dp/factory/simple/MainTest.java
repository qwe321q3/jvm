package com.jvm.dp.factory.simple;


public class MainTest {
    public static void main(String[] args) {
        Option option = OptionFactory.optionInstance("+");

        System.out.println(option.result(5,6));
    }
}
