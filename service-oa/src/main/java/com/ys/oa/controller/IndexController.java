package com.ys.oa.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ys.oa.jwt.JwtHelper;
import com.ys.oa.model.system.SysMenu;
import com.ys.oa.model.system.SysUser;
import com.ys.oa.password.MD5;
import com.ys.oa.result.Result;
import com.ys.oa.service.SysMenuService;
import com.ys.oa.service.SysUserService;
import com.ys.oa.vo.system.LoginVo;
import com.ys.oa.vo.system.RouterVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
@CrossOrigin  //跨域
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 登录
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginUser) {
        //获取用户
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername,loginUser.getUsername());
        SysUser user = sysUserService.getOne(queryWrapper);

        if (user == null){
            return Result.fail("用户名不存在");
        }

        if (!user.getPassword().equals(MD5.encrypt(loginUser.getPassword()))){
            return Result.fail("用户密码错误");
        }
        // 用户状态 0禁用 1可用
        if (user.getStatus() == 0){
            return Result.fail("用户被禁用");
        }

        Map<String, Object> map = new HashMap<>();
        String token = JwtHelper.createToken(user.getId(), user.getUsername());
        map.put("token",token);

        return Result.ok(map);
    }
    /**
     * 获取用户信息
     */
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
        // 获取前端传递的token
        String token = request.getHeader("token");
        // 解析token获取用户id
        Long userId = JwtHelper.getUserId(token);
        // 根据用户id获取用户菜单列表
        List<RouterVo> routeList = sysMenuService.getUserMenuListByUserId(userId);
        // 根据用户id获取用户按钮权限
        List<String> btnList = sysMenuService.getUserPermsByUserId(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name",JwtHelper.getUsername(token));
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        map.put("routers", routeList);
        map.put("buttons", btnList);
        return Result.ok(map);
    }
    /**
     * 退出
     */
    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }

}
