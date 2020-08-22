package com.tianshuo.main;

import com.tianshuo.config.AppConfig;
import com.tianshuo.model.Order;
import com.tianshuo.model.User;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName : ApplicationContextTest
 * @Description : applicationContext测试类
 * @Author : tianshuo
 * @Date: 2020-08-17 15:26
 */
public class ApplicationContextTest {

    @Test
    public void annotationApplicationContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfig.class);
        applicationContext.refresh();

        User user = applicationContext.getBean(User.class);
//        System.out.println(user);
//        Order order = applicationContext.getBean(Order.class);

    }
}

