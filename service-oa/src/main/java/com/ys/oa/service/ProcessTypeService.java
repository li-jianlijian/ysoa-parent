package com.ys.oa.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ys.oa.model.process.ProcessType;

import java.util.List;

/**
 * <p>
 * 审批类型 服务类
 * </p>
 *
 * @author 李健
 * @since 2023-06-01
 */
public interface ProcessTypeService extends IService<ProcessType> {

    List<ProcessType> findProcessType();
}
