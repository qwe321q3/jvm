package com.jvm.controller;

import com.jvm.annotation.AutoWired;
import com.jvm.model.UserService;

/**
 * @author tianshuo
 */
public class UserController {

    @AutoWired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
