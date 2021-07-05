package com.example.guava.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.guava.account.entity.Account;
import com.example.guava.account.service.AccountService;
import com.example.guava.account.mapper.AccountMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
implements AccountService{

}




