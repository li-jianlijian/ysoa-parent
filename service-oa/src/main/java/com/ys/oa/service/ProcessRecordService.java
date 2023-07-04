package com.ys.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ys.oa.model.process.ProcessRecord;

/**
 * <p>
 * 审批记录 服务类
 * </p>
 *
 * @author 李健
 * @since 2023-06-19
 */
public interface ProcessRecordService extends IService<ProcessRecord> {
    void record(Long processId, Integer status, String description);
}
