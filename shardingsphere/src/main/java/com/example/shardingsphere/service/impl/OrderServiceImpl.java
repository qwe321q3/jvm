package com.example.shardingsphere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingsphere.entity.Order;
import com.example.shardingsphere.mapper.OrderMapper;
import com.example.shardingsphere.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author tianshuo
* @description 针对表【t_order0】的数据库操作Service实现
* @createDate 2022-03-26 20:54:41
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public IPage<Order> page(Integer currentPage, Integer pageSize, Order order) {

        Page<Order> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
//        LambdaQueryWrapper<User> lambda = userQueryWrapper.lambda();
//        lambda.like(User::getName, user.getName())
//                .eq(User::getAge, user.getAge());
        return orderMapper.selectPage(page, null);
    }

}




