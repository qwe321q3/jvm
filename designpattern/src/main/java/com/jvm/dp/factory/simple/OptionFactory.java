package com.jvm.dp.factory.simple;

public class OptionFactory {

    public static Option optionInstance(String option) {
        switch (option) {
            case "+":
                return new AddOption();
            case "-":
                return new MinusOption();
            case "*":
                return new MultiplyOption();
            case "/":
                return new DivisionOption();
            default:
                return new AddOption();
        }
    }
}
