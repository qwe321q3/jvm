package com.example.security.member.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.security.member.domain.UmsMember;
import com.example.security.member.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {

    @Autowired
    private UmsMemberService umsMemberService;


    @GetMapping("/get/{id}")
    public UmsMember get(@PathVariable("id") Long id) {
        UmsMember umsMember = umsMemberService.getById(id);
        return umsMember;

    }

    @GetMapping("/pageList")
    public Page<UmsMember> pageList(Long currentPage, Long pageSize) {
        return umsMemberService.pageList(currentPage, pageSize);
    }

    @PostMapping("/add")
    public String add(@RequestBody UmsMember umsMember) {
        umsMemberService.save(umsMember);
        return "ok";
    }
}
