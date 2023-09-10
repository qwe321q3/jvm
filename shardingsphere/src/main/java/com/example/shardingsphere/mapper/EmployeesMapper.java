package com.example.shardingsphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shardingsphere.entity.Employees;

/**
* @author tianshuo
* @description 针对表【employees(员工记录表)】的数据库操作Mapper
* @createDate 2022-03-26 11:45:59
* @Entity com.example.shardingsphere.entity.Employees
*/
public interface EmployeesMapper extends BaseMapper<Employees> {

    IPage<Employees> xmlPage(Page<Employees> page, Employees employees);
}




