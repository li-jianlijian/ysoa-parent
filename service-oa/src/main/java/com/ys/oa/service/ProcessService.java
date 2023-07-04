package com.ys.oa.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ys.oa.vo.process.ApprovalVo;
import com.ys.oa.vo.process.ProcessFormVo;
import com.ys.oa.vo.process.ProcessQueryVo;
import com.ys.oa.vo.process.ProcessVo;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import com.ys.oa.model.process.Process;
/**
 * <p>
 * 审批类型 服务类
 * </p>
 *
 * @author 李健
 * @since 2023-06-18
 */
public interface ProcessService extends IService<Process> {
    public IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo);

    public void deployByZip(String deployPath);

    public void startUp(ProcessFormVo processFormVo);

    IPage<ProcessVo> findPending(Page<Process> pageParam);

    Map<String, Object> show(Long id);

    void approve(ApprovalVo approvalVo);

    IPage<ProcessVo> findProcessed(Page<Process> pageParam);

    IPage<ProcessVo> findStarted(Page<ProcessVo> pageParam);
}
