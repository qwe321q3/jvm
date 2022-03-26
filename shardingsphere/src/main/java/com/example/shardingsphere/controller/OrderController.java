package com.example.shardingsphere.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.shardingsphere.entity.Order;
import com.example.shardingsphere.entity.User;
import com.example.shardingsphere.service.OrderService;
import com.example.shardingsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName : UserController
 * @Description : 用户controller
 * @Author : tianshuo
 * @Date: 2022-03-26 19:11
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @ResponseBody
    @RequestMapping("detail/{id}")
    public Order detailById(@PathVariable("id") Integer id) {
        return orderService.getById(id);
    }

    @ResponseBody
    @RequestMapping("/save")
    public String save(Order order) {

        orderService.save(order);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/page")
    public IPage<Order> page(Integer currentPage, Integer pageSize, Order order) {
        return orderService.page(currentPage, pageSize, order);
    }
}

