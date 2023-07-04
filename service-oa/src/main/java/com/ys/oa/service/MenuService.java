package com.ys.oa.service;

import com.ys.oa.model.wechat.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ys.oa.vo.wechat.MenuVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author 李健
 * @since 2023-06-19
 */
@Service
public interface MenuService extends IService<Menu> {

    List<MenuVo> findMenuInfo();

    void syncMenu();

    void removeMenu();
}
