package com.example.guava.employees.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.guava.employees.entity.Employees;
import com.example.guava.employees.mapper.EmployeesMapper;
import com.example.guava.employees.service.IEmployeesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private EmployeesMapper employeesMapper;

    @Override
    public IPage<Employees> queryEmployees() {

        QueryWrapper<Employees> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Employees::getPosition,"dev");

        Page<Employees> page = new Page<>(1,10);
        IPage<Employees> employeesIPage = employeesMapper.selectPage(page,queryWrapper);

        return employeesIPage;
    }
}
