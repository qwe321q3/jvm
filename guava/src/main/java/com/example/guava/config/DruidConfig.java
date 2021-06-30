package com.example.guava.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**druid的配置类
 * @author sy
 * @date 2019/3/14 17:35
 */

@Configuration
public class DruidConfig {

    @Value("${spring.druid.slowSqlMillis}")
    private Long slowSqlMillis;

    //会扫描application.properties文件的以spring.druid开头的数据注入
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean(initMethod = "init",destroyMethod = "close") //跟着spring一块启动，一块关闭
    public DruidDataSource dataSource(){

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setProxyFilters(Lists.newArrayList(statFilter()));//Lists是guava包
        return dataSource;
    }

    //alibaba的监听器，打印慢sql
    @Bean
    public Filter statFilter(){

        StatFilter filter = new StatFilter();
        filter.setSlowSqlMillis(slowSqlMillis);
        filter.setLogSlowSql(true);
        filter.setMergeSql(true);
        return filter;

    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");

    }


}