package com.example.guava.employees.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.guava.employees.entity.Employees;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 员工记录表 服务类
 * </p>
 *
 * @author tianshuo
 * @since 2021-06-28
 */
public interface IEmployeesService extends IService<Employees> {

    IPage<Employees> queryEmployees();

}