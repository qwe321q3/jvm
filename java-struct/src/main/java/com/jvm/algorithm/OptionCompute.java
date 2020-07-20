package com.jvm.algorithm;

import com.jvm.linetable.MyStack;

/**
 * @ClassName : OptionCompute
 * @Description : 用栈数据结构，实现加减乘除运算  8+3*4-7 = 13
 * @Author : tianshuo
 * @Date: 2020-07-20 16:15
 */
public class OptionCompute {

    private MyStack flagStack;

    private MyStack<Integer> numberStack;



    public OptionCompute() {
        this.flagStack = new MyStack<>(10);
        this.numberStack = new MyStack<>(10);
    }

    public int option(String string) {
        char[] chars = string.toCharArray();
        int xindex = 0;
        boolean flag = false;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i]=='0' || chars[i]=='1' ||chars[i]=='2' ||chars[i]=='3' ||chars[i]=='4' ||chars[i]=='5' ||chars[i]=='6' ||
                    chars[i]=='7' ||chars[i]=='8' ||chars[i]=='9'){
                numberStack.push( Integer.valueOf(String.valueOf(chars[i])));
            }else{
                if (String.valueOf(chars[i]).equals("*") || String.valueOf(chars[i]).equals("/")) {
                    xindex = i+1;
                    flag = true;
                }
                flagStack.push(chars[i]);
            }

            /**
             * 栈底元素为左边的操作数
             */
            if (flag&&i == xindex) {
                int num1 = numberStack.pop();
                char c = (char) flagStack.pop();
                int num2 = numberStack.pop();

                if (String.valueOf(c).equals("*") ) {
                    numberStack.push(num2*num1);
                }else if(String.valueOf(c).equals("/")){
                    numberStack.push(num2 / num1);
                }

            }
        }

        while (!flagStack.isEmpty()) {
            int num1 = numberStack.pop();
            char c = (char) flagStack.pop();
            int num2 = numberStack.pop();

            if (String.valueOf(c).equals("+") ) {
                numberStack.push(num2+num1);
            }else if(String.valueOf(c).equals("-")){
                numberStack.push(num2-num1);
            }
        }



        return numberStack.pop();
    }


    public static void main(String[] args) {
        OptionCompute optionCompute = new OptionCompute();
        System.out.println(optionCompute.option("8/2"));
    }

}

