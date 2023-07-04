package com.ys.oa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ys.oa.model.system.SysUser;
import com.ys.oa.vo.system.SysUserQueryVo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 李健
 * @since 2023-05-11
 */
public interface SysUserService extends IService<SysUser> {

    Page<SysUser> selectByNameLikePage(Long page, Long limit, SysUserQueryVo sysUserQueryVo);

    void updateUserStatus(Long userID, Integer status);


    SysUser getByUsername(String username);

    Map<String, Object> getCurrentUser();
}
