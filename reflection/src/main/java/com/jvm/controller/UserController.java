package com.jvm.controller;

import com.jvm.annotation.AutoWired;
import com.jvm.model.UserService;

public class UserController {

    @AutoWired
    private UserService userService;

}
