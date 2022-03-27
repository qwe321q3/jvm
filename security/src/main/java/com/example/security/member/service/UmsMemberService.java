package com.example.security.member.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.security.member.domain.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UmsMemberService extends IService<UmsMember> {

    Page<UmsMember> pageList(Long currentPage,Long pageSize);

}
