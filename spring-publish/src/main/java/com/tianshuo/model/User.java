package com.tianshuo.model;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @ClassName : User
 * @Description : User测试类  增加@Lazy注解之后，Spring会在getBean时，实例化此对象，未加@Lazy注解时，在容器启动的过程中，其实也就是
 * bean的生命周期中的就会实例化对象
 * @Author : tianshuo
 * @Date: 2020-08-17 15:24
 */
@Component
@Lazy
public class User {
    public User() {
        System.out.println("user初始化。。");
    }
}

