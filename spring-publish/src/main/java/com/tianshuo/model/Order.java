package com.tianshuo.model;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Order {


    private final String id = UUID.randomUUID().toString();

    private String name ;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order(String name) {
        this.name = name;
    }

    public Order() {
        System.out.println("order construct ..");
    }


    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
