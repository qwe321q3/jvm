package com.example.shardingsphere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingsphere.entity.User;
import com.example.shardingsphere.service.UserService;
import com.example.shardingsphere.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tianshuo
 * @description 针对表【user_1】的数据库操作Service实现
 * @createDate 2022-03-26 19:11:04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<User> page(Integer currentPage, Integer pageSize, User user) {

        Page<User> page = new Page<>(currentPage, pageSize);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        LambdaQueryWrapper<User> lambda = userQueryWrapper.lambda();
//        lambda.like(User::getName, user.getName())
//                .eq(User::getAge, user.getAge());
        return userMapper.selectPage(page, userQueryWrapper);
    }
}




