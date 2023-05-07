package com.ys.oa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ys.oa.model.system.SysRole;

public interface SysRoleService extends IService<SysRole> {
    Page<SysRole> selectByNameLikePage(long page, long limit, String roleName);
}
