package com.example.guava.account.controller;

import com.example.guava.account.entity.Account;
import com.example.guava.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName : AccountController
 * @Description : 账户Controller
 * @Author : tianshuo
 * @Date: 2021-07-05 22:45
 */
@RequestMapping("/account")
@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/get")
    @ResponseBody
    public Account get(Integer id) {
        return accountService.getById(id);
    }
}

