package com.tianshuo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName : AppConfig
 * @Description : 基于Spring的annotation配置类
 * @Author : tianshuo
 * @Date: 2020-08-17 15:20
 */
@Configuration
@ComponentScans(value={@ComponentScan(value = "com.tianshuo")})
public class AppConfig {
}

