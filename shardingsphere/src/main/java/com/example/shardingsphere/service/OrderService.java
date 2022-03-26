package com.example.shardingsphere.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shardingsphere.entity.Order;

/**
* @author tianshuo
* @description 针对表【t_order0】的数据库操作Service
* @createDate 2022-03-26 20:54:41
*/
public interface OrderService extends IService<Order> {
    IPage<Order> page(Integer currentPage, Integer pageSize, Order order);


}
