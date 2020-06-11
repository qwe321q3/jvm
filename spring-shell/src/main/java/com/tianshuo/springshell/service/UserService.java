package com.tianshuo.springshell.service;

import org.springframework.stereotype.Component;

@Component
public class UserService {

    public void show(int a) {
        System.out.println("调用到测试服务: "+a);
    }
}
