package com.tianshuo.service.impl;

import com.tianshuo.model.Order;
import com.tianshuo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ApplicationContext applicationContext;

    public void save(Order order){

        System.out.println("订单保存");
        applicationContext.publishEvent(order);

    }
}
