package com.example.guava;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.guava.*.mapper")
@SpringBootApplication
public class GuavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuavaApplication.class, args);
    }
}
