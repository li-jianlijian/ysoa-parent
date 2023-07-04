package com.ys.oa.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ys.oa.config.exception.OaException;
import com.ys.oa.mapper.SysMenuMapper;
import com.ys.oa.model.system.SysMenu;
import com.ys.oa.model.system.SysRoleMenu;
import com.ys.oa.result.ResultCodeEnum;
import com.ys.oa.service.SysMenuService;
import com.ys.oa.service.SysRoleMenuService;
import com.ys.oa.util.MenuUtil;
import com.ys.oa.vo.system.AssignMenuVo;
import com.ys.oa.vo.system.RouterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author 李健
 * @since 2023-05-12
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Override
    public List<SysMenu> findNodes() {

        List<SysMenu> list = this.list();

        //防止最后的子节点的Children为null
        for (int i = 0;i<list.size();i++){
            list.get(i).setChildren(new ArrayList<>());
        }

        return MenuUtil.buildTree(list);
    }

    @Override
    public void removeMenuById(Long id) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId,id);

        if (this.count(queryWrapper)>0){
            throw new OaException(ResultCodeEnum.CUSTOM_ERROR.getCode(),"有子菜单，不能删除");
        }
        this.removeById(id);
    }

    //根据角色获取菜单 获取角色菜单和所有菜单
    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //获取角色的权限ID
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(queryWrapper);
        ArrayList<Long> menuIdList = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : sysRoleMenuList) {
            menuIdList.add(sysRoleMenu.getMenuId());
        }
        //获取所有菜单权限 状态0禁用 1可用
        List<SysMenu> sysMenuList = this.list(
                new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus,1));
        //获取角色的权限
        for (SysMenu sysMenu : sysMenuList) {
            sysMenu.setSelect(menuIdList.contains(sysMenu.getId()));
        }

        // 构建成为树形结构返回
        return MenuUtil.buildTree(sysMenuList);
    }

    @Override
    public void doAssign(AssignMenuVo assignMenuVo) {
        //先删除该角色下的所有菜单权限
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId,assignMenuVo.getRoleId());
        sysRoleMenuService.remove(queryWrapper);
        //为角色分配权限
        ArrayList<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        for (Long aLong : assignMenuVo.getMenuIdList()) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(aLong);
            sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
            sysRoleMenus.add(sysRoleMenu);
        }
        sysRoleMenuService.saveBatch(sysRoleMenus);
    }

    @Override
    public List<RouterVo> getUserMenuListByUserId(Long userId) {
        // 根据id查询路由
        List<SysMenu> sysMenuList = baseMapper.findRouterListByUserId(userId);
        // 构建路由并返回
        return MenuUtil.buildRouter(sysMenuList);
    }

    @Override
    public List<String> getUserPermsByUserId(Long userId) {
        ArrayList<String> result = new ArrayList<>();
        // 根据id查询按钮级权限
        List<SysMenu> permsList = baseMapper.findPermsListByUserId(userId);
        for (SysMenu sysMenu : permsList) {
            result.add(sysMenu.getPerms());
        }
        return result;
    }
}
