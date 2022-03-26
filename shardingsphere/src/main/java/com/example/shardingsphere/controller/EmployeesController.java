package com.example.shardingsphere.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.shardingsphere.entity.Employees;
import com.example.shardingsphere.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName : EmployeeController
 * @Description : 用户客户端
 * @Author : tianshuo
 * @Date: 2022-03-26 11:47
 */
@Component
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @RequestMapping("/page")
    @ResponseBody
    public IPage<Employees> page(Integer currentPage, Integer pageSize) {
        return employeesService.queryEmployees(currentPage,pageSize);
    }

    @RequestMapping("/xmlPage")
    @ResponseBody
    public IPage<Employees> xmlPage(Integer currentPage, Integer pageSize) {
        return employeesService.queryXmlEmployees(currentPage,pageSize);
    }
}

