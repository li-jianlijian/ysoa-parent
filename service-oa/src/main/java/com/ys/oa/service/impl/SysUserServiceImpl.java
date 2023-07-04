package com.ys.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ys.oa.custom.CustomUser;
import com.ys.oa.mapper.SysUserMapper;
import com.ys.oa.model.system.SysRole;
import com.ys.oa.model.system.SysUser;
import com.ys.oa.service.SysUserService;
import com.ys.oa.util.LoginUserInfoHelper;
import com.ys.oa.vo.system.SysUserQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 李健
 * @since 2023-05-11
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

//    @Autowired
//    private SysDeptService sysDeptService;
//
//    @Autowired
//    private SysPostService sysPostService;

    @Override
    public Page<SysUser> selectByNameLikePage(Long page, Long limit, SysUserQueryVo sysUserQueryVo) {
        // 分页条件
        Page<SysUser> pageParam = new Page<>(page, limit);

        //查询条件
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();
        // like 模糊查询 ge 大于等于 le 小于等于
        if (!StringUtils.isEmpty(username)){
            wrapper.like(SysUser::getName,username);
        }
        if (!StringUtils.isEmpty(createTimeBegin)){
            wrapper.ge(SysUser::getCreateTime,createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)){
            wrapper.le(SysUser::getCreateTime,createTimeEnd);
        }

        // 分页和查询条件组合
        return this.page(pageParam, wrapper);
    }

    @Override
    public void updateUserStatus(Long userID, Integer status) {
        SysUser sysUser = this.getById(userID);
        sysUser.setStatus(status);
        sysUser.setId(userID);
        this.updateById(sysUser);
    }

    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername,username);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Map<String, Object> getCurrentUser() {
        SysUser sysUser = baseMapper.selectById(LoginUserInfoHelper.getUserId());
        //SysDept sysDept = sysDeptService.getById(sysUser.getDeptId());
        //SysPost sysPost = sysPostService.getById(sysUser.getPostId());
        Map<String, Object> map = new HashMap<>();
        map.put("name", sysUser.getName());
        map.put("phone", sysUser.getPhone());
        //map.put("deptName", sysDept.getName());
        //map.put("postName", sysPost.getName());
        return map;
    }
}
