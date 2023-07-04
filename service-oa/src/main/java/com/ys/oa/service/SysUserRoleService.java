package com.ys.oa.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ys.oa.model.system.SysUserRole;
import com.ys.oa.vo.system.AssignRoleVo;

import java.util.Map;

/**
 * <p>
 * 用户角色 服务类
 * </p>
 *
 * @author 李健
 * @since 2023-05-12
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    // 获取用户角色信息 和 所有角色信息
    Map<String, Object> findRoleByUserID(Long userId);

    // 为用户做角色分配
    void doAssign(AssignRoleVo assginRoleVo);
}
