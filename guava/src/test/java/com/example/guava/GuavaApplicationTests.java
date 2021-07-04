package com.example.guava;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.guava.employees.entity.Employees;
import com.example.guava.employees.service.IEmployeesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class GuavaApplicationTests {

    @Autowired
    IEmployeesService employeesService;

    @Test
    void insert() {
        Employees employees = new Employees();
        employees.setName("cc");
        employees.setAge(8);
        employees.setId(null);
        employees.setPosition("dd");
        employees.setStatus(0);
        employeesService.save(employees);
        // 插入成功自动填充主键字段值
        System.out.println(employees);
    }

    @Test
    void modify() {
        UpdateWrapper<Employees> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(Employees::getPosition, "ccc")
                .set(Employees::getAge, "80")
                .set(Employees::getStatus, 1)
                .eq(Employees::getId, 100001);
        employeesService.update(updateWrapper);
    }

    @Test
    void remove() {
        QueryWrapper<Employees> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Employees::getId, 100001);
        employeesService.remove(queryWrapper);
    }

    /**
     * 逻辑删除
     */
    @Test
    void logicRemove() {
        QueryWrapper<Employees> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
        .eq(Employees::getId, 100000);
        employeesService.remove(queryWrapper);
    }

    @Test
    void query() {
        QueryWrapper<Employees> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(Employees::getId, Employees::getName)
                .eq(Employees::getId, 100000);
        List<Employees> list = employeesService.list(queryWrapper);
        System.out.println(list);
    }


}
