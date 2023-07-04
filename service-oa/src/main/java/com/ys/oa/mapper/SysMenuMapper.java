package com.ys.oa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ys.oa.model.system.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author 李健
 * @since 2023-05-12
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findRouterListByUserId(Long userId);

    List<SysMenu> findPermsListByUserId(Long userId);
}
