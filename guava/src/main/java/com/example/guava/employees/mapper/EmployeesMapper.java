package com.example.guava.employees.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.guava.employees.entity.Employees;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 员工记录表 Mapper 接口
 * </p>
 *
 * @author tianshuo
 * @since 2021-06-28
 */
public interface EmployeesMapper extends BaseMapper<Employees> {

    /**
     * 当传递对象时，必须使用@Param
     * @param page
     * @param employees
     * @return
     */
    IPage<Employees> xmlPage(Page<Employees> page,@Param("employees")Employees employees);
}
