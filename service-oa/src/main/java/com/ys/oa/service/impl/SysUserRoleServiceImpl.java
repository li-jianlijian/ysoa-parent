package com.ys.oa.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ys.oa.mapper.SysUserRoleMapper;
import com.ys.oa.model.system.SysRole;
import com.ys.oa.model.system.SysUserRole;
import com.ys.oa.service.SysRoleService;
import com.ys.oa.service.SysUserRoleService;
import com.ys.oa.vo.system.AssignRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author 李健
 * @since 2023-05-12
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public Map<String, Object> findRoleByUserID(Long userId) {
        //所有角色
        List<SysRole> roleList = sysRoleService.list();

        LambdaQueryWrapper<SysUserRole> userRoleQuery = new LambdaQueryWrapper<>();
        userRoleQuery.eq(SysUserRole::getUserId,userId);
        //获取用户的所有角色
        List<SysUserRole> userRoleList = this.list(userRoleQuery);
        //获取用户的所有角色ID
        List<Long> userRoleIds = new ArrayList<>();
        for (SysUserRole sysUserRole : userRoleList) {
            userRoleIds.add(sysUserRole.getRoleId());
        }
        //获取用户的所有角色的详细信息
        ArrayList<SysRole> userRoleDetailsList = new ArrayList<>();
        for (SysRole sysRole : roleList) {
            if (userRoleIds.contains(sysRole.getId())){
                userRoleDetailsList.add(sysRole);
            }
        }
        //将用户角色详情信息和所有角色封装返回
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("roleList",roleList);
        resultMap.put("userRoleList",userRoleDetailsList);

        return resultMap;
    }

    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {
        //删除用户所有角色
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId,assignRoleVo.getUserId());
        this.remove(queryWrapper);
        //重新分配用户角色
        ArrayList<SysUserRole> userRoleList = new ArrayList<>();
        for (Long roleID : assignRoleVo.getRoleIdList()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(assignRoleVo.getUserId());
            sysUserRole.setRoleId(roleID);
            userRoleList.add(sysUserRole);
        }
        //批量添加
        this.saveBatch(userRoleList);
    }
}
