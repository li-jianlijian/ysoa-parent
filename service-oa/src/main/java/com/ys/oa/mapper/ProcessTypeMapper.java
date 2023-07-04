package com.ys.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ys.oa.model.process.ProcessType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 审批类型 Mapper 接口
 * </p>
 *
 * @author 李健
 * @since 2023-06-01
 */
@Mapper
public interface ProcessTypeMapper extends BaseMapper<ProcessType> {

}
