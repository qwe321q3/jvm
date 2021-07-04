package com.example.guava.bloom;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.guava.bloom.BloomFilterCase;
import com.example.guava.employees.entity.Employees;
import com.example.guava.employees.service.IEmployeesService;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 单例bean实例化之后执行
 */
@Slf4j
@Component
public class BloomInit implements SmartInitializingSingleton {

    @Autowired
    IEmployeesService employeesService;

    @Override
    public void afterSingletonsInstantiated() {
        log.info("----  --  布隆过滤器数据初始化开始 ");
        IPage<Employees> employeesIPage = employeesService.queryEmployees(1,20);
        List<Employees> records = employeesIPage.getRecords();
        // 构建布隆过滤器设置误差为千分之1
        BloomFilterCase.boomFilter = BloomFilter.create(Funnels.integerFunnel(), 10000, 0.0001);
        for (Employees employees : records) {
            // 设置bloom的值
            BloomFilterCase.boomFilter.put(employees.getId());

        }

        log.info("----  --  布隆过滤器数据初始化结束  ");
    }
}
