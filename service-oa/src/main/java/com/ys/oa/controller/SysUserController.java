package com.ys.oa.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ys.oa.model.system.SysUser;
import com.ys.oa.password.MD5;
import com.ys.oa.result.Result;
import com.ys.oa.service.SysUserRoleService;
import com.ys.oa.service.SysUserService;
import com.ys.oa.vo.system.AssignRoleVo;
import com.ys.oa.vo.system.SysUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 李健
 * @since 2023-05-11
 */
@RestController
@Api(tags = "用户模块")
@RequestMapping("/admin/system/sysUser")
@CrossOrigin  //跨域
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation(value = "更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        sysUserService.updateUserStatus(id, status);
        return Result.ok();
    }


    // 获取用户角色信息 和 所有角色信息
    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId) {
        Map<String, Object> roleMap = sysUserRoleService.findRoleByUserID(userId);
        return Result.ok(roleMap);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.assignRole')")
    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignRoleVo assginRoleVo) {
        sysUserRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }


    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation(value = "条件分页查询")
    @GetMapping("/{page}/{limit}")
    public Result pageQueryRole(@PathVariable("page") Long page,
                                @PathVariable("limit") Long limit,
                                SysUserQueryVo sysUserQueryVo){

        Page<SysUser> pageResult = sysUserService.selectByNameLikePage(
                page,
                limit,
                sysUserQueryVo);

        return Result.ok(pageResult);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.add')")
    @ApiOperation(value = "添加用户")
    @PostMapping("/save")
    // RequestBody 通过json格式传输
    public Result save(@RequestBody SysUser sysUser){
        sysUser.setPassword(MD5.encrypt(sysUser.getPassword()));
        boolean is_success = sysUserService.save(sysUser);
        if (is_success){
            return Result.ok("用户添加成功");
        }

        return Result.fail("用户添加失败");
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation(value = "获取用户通过id")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id){
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation(value = "修改用户")
    @PutMapping("/update")
    // RequestBody 通过json格式传输
    public Result update(@RequestBody SysUser sysUser){

        boolean is_success = sysUserService.updateById(sysUser);
        if (is_success){
            return Result.ok("用户修改成功");
        }

        return Result.fail("用户修改失败");
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation(value = "删除用户根据ID")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id){

        boolean is_success = sysUserService.removeById(id);
        if (is_success){
            return Result.ok("用户删除成功");
        }

        return Result.fail("用户删除失败");
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation(value = "批量删除用户根据ID")
    @DeleteMapping("/batchRemove")
    // RequestBody 通过json格式传输
    public Result batchRemove(@RequestBody List<Long> idList){

        boolean is_success = sysUserService.removeByIds(idList);
        if (is_success){
            return Result.ok("用户批量删除成功");
        }

        return Result.fail("用户批量删除失败");
    }
    
}

