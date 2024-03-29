package com.example.guava;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan("com.example.guava.*.mapper")
@SpringBootApplication
public class GuavaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(GuavaApplication.class, args);
//        ConsoleReporter reporter = run.getBean(ConsoleReporter.class);
//        reporter.start(1, TimeUnit.SECONDS);
    }
}
