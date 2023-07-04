package com.ys.oa.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ys.oa.model.process.Process;
import com.ys.oa.model.process.ProcessTemplate;
import com.ys.oa.result.Result;
import com.ys.oa.service.ProcessService;
import com.ys.oa.service.ProcessTemplateService;
import com.ys.oa.service.ProcessTypeService;
import com.ys.oa.vo.process.ProcessFormVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "审批流管理")
@RestController
@RequestMapping(value="/admin/process")
@CrossOrigin  //跨域
public class EmployeeProcessController {

    @Autowired
    private ProcessTypeService processTypeService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private ProcessTemplateService processTemplateService;

    @ApiOperation(value = "获取全部审批分类及模板")
    @GetMapping("findProcessType")
    public Result findProcessType() {
        return Result.ok(processTypeService.findProcessType());
    }



    @ApiOperation(value = "获取审批模板")
    @GetMapping("getProcessTemplate/{processTemplateId}")
    public Result get(@PathVariable Long processTemplateId) {
        ProcessTemplate processTemplate = processTemplateService.getById(processTemplateId);
        return Result.ok(processTemplate);
    }

    @ApiOperation(value = "启动流程")
    @PostMapping("/startUp")
    public Result start(@RequestBody ProcessFormVo processFormVo) {
        processService.startUp(processFormVo);
        return Result.ok();
    }

    @ApiOperation(value = "待处理")
    @GetMapping("/findPending/{page}/{limit}")
    public Result findPending(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<Process> pageParam = new Page<>(page, limit);
        return Result.ok(processService.findPending(pageParam));
    }
}
