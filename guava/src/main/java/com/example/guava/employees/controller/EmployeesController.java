package com.example.guava.employees.controller;


import com.example.guava.employees.entity.Employees;
import com.example.guava.employees.service.IEmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 员工记录表 前端控制器
 * </p>
 *
 * @author tianshuo
 * @since 2021-06-28
 */
@RestController
@RequestMapping("/employees/employees")
public class EmployeesController {

    @Autowired
    private IEmployeesService employeesService;


    @ResponseBody
    @GetMapping("/getEmployees")
    public Employees getEmployees(Integer id) {
        Employees employees = employeesService.getById(id);
        return employees;
    }

    @ResponseBody
    @GetMapping("/test")
    public String test() {
        return "test";
    }

}

