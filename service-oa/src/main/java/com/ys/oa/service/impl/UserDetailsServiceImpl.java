package com.ys.oa.service.impl;


import com.ys.oa.custom.CustomUser;
import com.ys.oa.model.system.SysUser;
import com.ys.oa.service.SysMenuService;
import com.ys.oa.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if(null == sysUser) {
            // 这里抛出的异常信息没有用 可以不写
            throw new UsernameNotFoundException("");
        }


        // 根据用户id获取用户权限
        List<String> userPermsList = sysMenuService.getUserPermsByUserId(sysUser.getId());
        // 封装权限数据
        ArrayList<SimpleGrantedAuthority> authList = new ArrayList<>();
        for (String perm : userPermsList) {
            authList.add(new SimpleGrantedAuthority(perm.trim()));
        }
        return new CustomUser(sysUser, authList);
    }
}
