package com.example.guava.employees.service.impl;

import com.example.guava.employees.entity.Employees;
import com.example.guava.employees.mapper.EmployeesMapper;
import com.example.guava.employees.service.IEmployeesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工记录表 服务实现类
 * </p>
 *
 * @author tianshuo
 * @since 2021-06-28
 */
@Primary
@Service
public class EmployeesServiceImpl extends ServiceImpl<EmployeesMapper, Employees> implements IEmployeesService {

}
