package com.jvm.vmstack;

/**
 * Virtual Manchine Stack --> stack frame --> 局部变量表
 *  stack frame 对应类中的方法
 *  用于存放栈帧中的局部变量
 *
 *  虚拟机栈可以用-Xss150k 参数，来设置栈的大小
 *
 *  此类用来设置局部变量是和Virtual Machine Stack相关的，测试有参数，有局部变量及无参数方法
 *  在相同栈大小的情况下，调用深度不一样。
 *
 *  结论 ： 栈帧中的局部变量是在virtual machine stack中的，会占用stack内存，局部变量越多占用的stack
 *  内存越大，方法调用深度越前浅
 */
public class LocalVarTable {

    private static int count = 0;

    private static void alloc(int a,int b){
        int e,f,g,y,u,i,o=2;
        count++;
        alloc(1,2);
    }

    private static void alloc1(){
        count++;
        alloc1();
    }

    public static void main(String[] args) {
//        try {
//            alloc(1,2);
//
//        }catch (Throwable e){ //310次
//            System.out.println("有参数方法调用次数: "+count);
//            e.printStackTrace();
//        }

        try {
            alloc1();

        }catch (Throwable e){
            e.printStackTrace(); //546次
            System.out.println("无参数方法调用次数: "+count);
        }
    }




}
