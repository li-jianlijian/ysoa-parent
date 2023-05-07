package com.ys.oa.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ys.oa.mapper.SysRoleMapper;
import com.ys.oa.model.system.SysRole;
import com.ys.oa.service.SysRoleService;
import com.ys.oa.stringUtil.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Override
    public Page<SysRole> selectByNameLikePage(long page, long limit, String roleName) {
        // 分页条件
        Page<SysRole> pageParam = new Page<>(page, limit);

        //查询条件
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isEmpty(roleName)){
            wrapper.like(SysRole::getRoleName,roleName);
        }

        // 分页和查询条件组合
        return this.page(pageParam, wrapper);
    }
}
