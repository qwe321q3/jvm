package com.jvm.model;

import java.io.Serializable;

/**
 * @ClassName : User
 * @Description : 用户类用来做的netty的传输测试。
 * @Author : tianshuo
 * @Date: 2021-01-21 15:13
 */
public class User implements Serializable {
    private String id;
    private String name;


    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

