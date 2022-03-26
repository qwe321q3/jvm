package com.example.shardingsphere.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.shardingsphere.entity.Employees;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author tianshuo
* @description 针对表【employees(员工记录表)】的数据库操作Service
* @createDate 2022-03-26 11:45:59
*/
public interface EmployeesService extends IService<Employees> {

    /**
     * 分页查询实例
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<Employees> queryEmployees(Integer currentPage , Integer pageSize);


    /**
     * xml分页查询实例
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<Employees> queryXmlEmployees(Integer currentPage ,Integer pageSize);

}
