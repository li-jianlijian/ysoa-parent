package com.ys.oa.util;

import com.ys.oa.model.system.SysMenu;
import com.ys.oa.vo.system.MetaVo;
import com.ys.oa.vo.system.RouterVo;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuUtil {

    // 构建路由结构
    public static List<RouterVo> buildRouter(List<SysMenu> menuList) {

        // 先建构成为树形结构
        List<SysMenu> sysMenuTree = buildTree(menuList);

        return startBuildRouter(sysMenuTree);
    }

    private static List<RouterVo> startBuildRouter(List<SysMenu> menuList) {
        // 最终返回的集合
        ArrayList<RouterVo> routers = new ArrayList<>();

        for (SysMenu sysMenu : menuList) {
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setAlwaysShow(false);
            // 不是顶层不加/ 是顶层才加/
            router.setPath(sysMenu.getParentId()==0?"/"+sysMenu.getPath():sysMenu.getPath());
            router.setComponent(sysMenu.getComponent());
            router.setMeta(new MetaVo(sysMenu.getName(), sysMenu.getIcon()));
            // 判断是否为隐藏路由 此时获取的全为路由 可若是路由的perm不为空 则代表该路由通常需要条件触发 也代表此为隐藏路由
            if (!StringUtils.isEmpty(sysMenu.getPath())&&!StringUtils.isEmpty(sysMenu.getPerms())){
                router.setHidden(true);
            }
            router.setChildren(startBuildRouter(sysMenu.getChildren()));
            routers.add(router);
        }

        return routers;
    }

    // 构建为树形结构
    public static List<SysMenu> buildTree(List<SysMenu> menuList) {
        // 对数据格式进行处理 防止最底部的子节点Children为null 因此先将所有节点的child设为[]
        for (SysMenu sysMenu : menuList) {
            sysMenu.setChildren(new ArrayList<>());
        }
        return startBuildTree(null,menuList);
    }

    private static List<SysMenu> startBuildTree(SysMenu sysMenu, List<SysMenu> menuList) {
        List<SysMenu> sysMenus = new ArrayList<>();
        for (SysMenu sysMenuFor : menuList) {

            // 如果是根节点
            if (sysMenuFor.getParentId() == 0 && sysMenu == null) {
                MenuUtil.startBuildTree(sysMenuFor, menuList);
                sysMenus.add(sysMenuFor);
                // 非根节点
            } else if (sysMenu != null && Objects.equals(sysMenu.getId(), sysMenuFor.getParentId())) {
                sysMenus.add(sysMenuFor);
                MenuUtil.startBuildTree(sysMenuFor, menuList);
                sysMenu.setChildren(sysMenus);
            }
        }
        return sysMenus;

    }
}
