package com.example.shardingsphere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingsphere.entity.Employees;
import com.example.shardingsphere.service.EmployeesService;
import com.example.shardingsphere.mapper.EmployeesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tianshuo
 * @description 针对表【employees(员工记录表)】的数据库操作Service实现
 * @createDate 2022-03-26 11:45:59
 */
@Service
public class EmployeesServiceImpl extends ServiceImpl<EmployeesMapper, Employees>
        implements EmployeesService {

    @Autowired
    private EmployeesMapper employeesMapper;

    @Override
    public IPage<Employees> queryEmployees(Integer currentPage, Integer pageSize) {

        QueryWrapper<Employees> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .eq(Employees::getPosition,"dev");
        Page<Employees> page = new Page<>(currentPage,pageSize);
        IPage<Employees> employeesIPage = employeesMapper.selectPage(page,queryWrapper);

        return employeesIPage;
    }

    @Override
    public IPage<Employees> queryXmlEmployees(Integer currentPage, Integer pageSize) {
        Page<Employees> page = new Page<>(currentPage,pageSize);
        Employees employees = new Employees();

        IPage<Employees> employeesIPage = employeesMapper.xmlPage(page, employees);
        return employeesIPage;
    }


}




