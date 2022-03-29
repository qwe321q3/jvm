package com.example.shardingsphere;

import com.example.shardingsphere.entity.Order;
import com.example.shardingsphere.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class ShardingsphereApplicationTests {


    @Autowired
    private OrderMapper orderMapper;

    @Test
    void contextLoads() {

        for (int i = 1; i <= 20; i++) {

            Order order = new Order();
            order.setOrderId(Long.valueOf(i));
            order.setCreateTime(new Date());
            orderMapper.insert(order);
        }





    }

}
