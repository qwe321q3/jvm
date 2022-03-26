package com.example.shardingsphere.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.shardingsphere.entity.User;
import com.example.shardingsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;

/**
 * @ClassName : UserController
 * @Description : 用户controller
 * @Author : tianshuo
 * @Date: 2022-03-26 19:11
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping("detail/{id}")
    public User detailById(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    @ResponseBody
    @RequestMapping("/save")
    public String save(User user) {

        userService.save(user);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/page")
    public IPage<User> page(Integer currentPage, Integer pageSize, User user) {
        return userService.page(currentPage, pageSize, user);
    }
}

