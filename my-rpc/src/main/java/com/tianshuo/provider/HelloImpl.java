package com.tianshuo.provider;

/**
 * @author tianshuo
 */
public class HelloImpl implements IHello {
    @Override
    public String sayHello(String msg) {
        System.out.println("xiao"+msg);
        return "response: " + msg;
    }
}
