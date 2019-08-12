package com.jvm;

public class CustContextClassLoader {

    public static void main(String[] args) {
        System.out.println(Thread.class.getClassLoader());
        System.out.println(Thread.currentThread().getContextClassLoader());

             ClassLoader classLoader = null;
        try {
             classLoader = Thread.currentThread().getContextClassLoader();
            myMethod();
        } finally {
            Thread.currentThread().setContextClassLoader(classLoader);
        }


    }

    private static void myMethod() {

    }
}
