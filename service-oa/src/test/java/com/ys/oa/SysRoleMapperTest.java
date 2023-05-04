package com.ys.oa;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ys.oa.mapper.SysRoleMapper;
import com.ys.oa.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysRoleMapperTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    public void getall(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        System.out.println(sysRoles);
    }

    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("超级管理员");
        sysRole.setRoleCode("admin");
        sysRole.setDescription("这是一个超级管理员角色");
        // 返回的insert表示修改成功的行数
        int insert = sysRoleMapper.insert(sysRole);
        System.out.println(insert);
    }


    @Test
    public void queryWrapper(){

        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        //根据真实数据库字段进行查询
        queryWrapper.eq("role_name","系统管理员");

        List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);

        System.out.println(sysRoles);
    }

    @Test
    public void lambdaqueryWrapper(){
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getRoleName,"系统管理员");
        List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);
        System.out.println(sysRoles);
    }

}
