package com.tianshuo.service;

import com.tianshuo.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestOrderServiceImpl {

    @Autowired
    private OrderService orderService;

    @Test
    public void testOrder(){
        Order order = new Order("888");
        orderService.save(order);
    }

}
