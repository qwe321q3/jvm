package com.example.security.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.security.member.domain.UmsMember;
import com.example.security.member.mapper.UmsMemberMapper;
import com.example.security.member.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember>
implements UmsMemberService, UserDetailsService {

    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<UmsMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(UmsMember::getUsername, s);


        UmsMember umsMember = umsMemberMapper.selectOne(queryWrapper);

        if (umsMember == null) {
            throw new RuntimeException("用户不存在!");
        }


        // 角色集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
        authorities.add(new SimpleGrantedAuthority("ROLE_Admin"));

        User user = new User(umsMember.getUsername(),umsMember.getPassword(), authorities);

        return user;
    }

    @Override
    public Page<UmsMember> pageList(Long currentPage, Long pageSize) {

        Page<UmsMember> page = new Page<>();
        QueryWrapper<UmsMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                    .eq(UmsMember::getStatus,1);
        return umsMemberMapper.selectPage(page, queryWrapper);
    }
}




