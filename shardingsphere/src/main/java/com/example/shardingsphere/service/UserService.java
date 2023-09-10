package com.example.shardingsphere.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shardingsphere.entity.User;

/**
 * @author tianshuo
 * @description 针对表【user_1】的数据库操作Service
 * @createDate 2022-03-26 19:11:04
 */
public interface UserService extends IService<User> {

    IPage<User> page(Integer currentPage, Integer pageSize, User user);

}
