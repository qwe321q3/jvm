package com.example.guava.employees.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codahale.metrics.Meter;
import com.example.guava.bloom.BloomFilterCase;
import com.example.guava.employees.entity.Employees;
import com.example.guava.employees.service.IEmployeesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 员工记录表 前端控制器
 * </p>
 *
 * @author tianshuo
 * @since 2021-06-28
 */
@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private IEmployeesService employeesService;

    @Autowired
    private Meter requestMeter;




    @ResponseBody
    @GetMapping("/getEmployees")
    public Employees getEmployees(Integer id) {
        // tps统计
        requestMeter.mark();
        // 经过布隆过滤器校验，过滤掉不存在的id
        if (!BloomFilterCase.boomFilter.mightContain(id)) {
            log.info("布隆过滤器拦截的id ， {}",id);
            return null;
        }

        Employees employees = employeesService.getById(id);

        return employees;
    }

    @ResponseBody
    @GetMapping("/page")
    public IPage<Employees> page(Integer currentPage,Integer pageSize) {
        IPage<Employees> employeesIPage = employeesService.queryEmployees(currentPage,pageSize);
        return employeesIPage;
    }

    @ResponseBody
    @GetMapping("/xmlPage")
    public IPage<Employees> xmlPage(Integer currentPage,Integer pageSize) {
        IPage<Employees> employeesIPage = employeesService.queryXmlEmployees(currentPage,pageSize);
        return employeesIPage;
    }


    @ResponseBody
    @GetMapping("/add")
    public String add(@RequestBody Employees employees) {
        employeesService.save(employees);
        return "ok";
    }



    @ResponseBody
    @GetMapping("/test")
    public String test() {
        return "test";
    }

}

