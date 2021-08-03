package com.example.security.member.controller;

import com.example.security.member.domain.UmsMember;
import com.example.security.member.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    private UmsMemberService umsMemberService;


    @GetMapping("/get/{id}")
    public UmsMember get(@PathVariable("id") Long id) {
        UmsMember umsMember = umsMemberService.getById(id);
        return umsMember;

    }
}
