package com.jvm.system;

import java.util.Map;
import java.util.Properties;

/**
 * System.getEnv 获取的是操作系统的环境变量
 *
 * System.getProperties 获取所有的jvm参数配置
 * System.getProperty获取执行java参数配置
 *
 * 如果需要自定义java参数配置使用
 *
 *  如下：-D参数名称=参数值
 *  -Dage=1
 */
public class SystemEnv {

    public static void main(String[] args) {

        Map<String, String> getenv = System.getenv();
        System.out.println("系统的环境变量");
        for (Map.Entry<String, String> m : getenv.entrySet()) {
            System.out.println(m.getKey() + " - - - - - " + m.getValue());
        }
        System.out.println("-----------------");


        System.out.println("获取JVM的参数配置");
        Properties properties = System.getProperties();

        for (Map.Entry a : properties.entrySet()) {
            System.out.println(a.getKey() + " - - - - - " + a.getValue());

        }

        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty("age"));//自定义jvm参数
    }
}
