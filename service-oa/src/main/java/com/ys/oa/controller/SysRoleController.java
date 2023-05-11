package com.ys.oa.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ys.oa.model.system.SysRole;
import com.ys.oa.result.Result;
import com.ys.oa.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "角色模块")
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    //@ApiImplicitParam(name = "name",value = "姓名",required = true)
    @ApiOperation(value = "获取所有角色")
    @GetMapping("/findAll")
    public Result findAll(){
        return Result.ok(sysRoleService.list());
    }

    @ApiOperation(value = "条件分页查询")
    @GetMapping("/{page}/{limit}")
    public Result pageQueryRole(@PathVariable("page") Long page,
                                @PathVariable("limit") Long limit,
                                SysRole sysRole){

        Page<SysRole> pageResult = sysRoleService.selectByNameLikePage(
                page,
                limit,
                sysRole.getRoleName());

        return Result.ok(pageResult);
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/save")
    // RequestBody 通过json格式传输
    public Result save(@RequestBody SysRole sysRole){

        boolean is_success = sysRoleService.save(sysRole);
        if (is_success){
            return Result.ok("角色添加成功");
        }

        return Result.fail("角色添加失败");
    }

    @ApiOperation(value = "获取角色通过id")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("/update")
    // RequestBody 通过json格式传输
    public Result update(@RequestBody SysRole sysRole){

        boolean is_success = sysRoleService.updateById(sysRole);
        if (is_success){
            return Result.ok("角色修改成功");
        }

        return Result.fail("角色修改失败");
    }

    @ApiOperation(value = "删除角色根据ID")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id){

        boolean is_success = sysRoleService.removeById(id);
        if (is_success){
            return Result.ok("角色删除成功");
        }

        return Result.fail("角色删除失败");
    }

    @ApiOperation(value = "批量删除角色根据ID")
    @DeleteMapping("/batchRemove")
    // RequestBody 通过json格式传输
    public Result batchRemove(@RequestBody List<Long> idList){

        boolean is_success = sysRoleService.removeByIds(idList);
        if (is_success){
            return Result.ok("角色批量删除成功");
        }

        return Result.fail("角色批量删除失败");
    }

}
