package com.example.guava.employees.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.guava.employees.entity.Employees;
import com.example.guava.employees.mapper.EmployeesMapper;
import com.example.guava.employees.service.IEmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private EmployeesMapper employeesMapper;

    @Override
    public IPage<Employees> queryEmployees(Integer currentPage,Integer pageSize) {

        QueryWrapper<Employees> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .eq(Employees::getPosition,"dev");
        Page<Employees> page = new Page<>(currentPage,pageSize);
        IPage<Employees> employeesIPage = employeesMapper.selectPage(page,queryWrapper);

        return employeesIPage;
    }

    /**
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<Employees> queryXmlEmployees(Integer currentPage , Integer pageSize) {
        Page<Employees> page = new Page<>(currentPage,pageSize);
        Employees employees = new Employees();
        employees.setStatus(0);

        IPage<Employees> employeesIPage = employeesMapper.xmlPage(page, employees);
        return employeesIPage;
    }


    /**
     * 更新
     * @param employees
     */
    @Override
    public void updateEmployees(Employees employees) {
        UpdateWrapper<Employees> tWrapper = new UpdateWrapper<>();
        tWrapper.lambda()
                .set(Employees::getAge, employees.getAge())
                .set(Employees::getName,employees.getName())
                .eq(Employees::getId, employees.getId());

        employeesMapper.update(employees,tWrapper);
    }




}
