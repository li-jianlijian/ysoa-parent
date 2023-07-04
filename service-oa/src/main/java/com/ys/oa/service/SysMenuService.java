package com.ys.oa.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ys.oa.model.system.SysMenu;
import com.ys.oa.vo.system.AssignMenuVo;
import com.ys.oa.vo.system.RouterVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author 李健
 * @since 2023-05-12
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();

    void removeMenuById(Long id);

    List<SysMenu> findSysMenuByRoleId(Long roleId);

    void doAssign(AssignMenuVo assignMenuVo);

    List<RouterVo> getUserMenuListByUserId(Long userId);

    List<String> getUserPermsByUserId(Long userId);
}
