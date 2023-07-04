package com.ys.oa.mapper;

import com.ys.oa.model.process.Process;

import com.ys.oa.vo.process.ProcessQueryVo;
import com.ys.oa.vo.process.ProcessVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 审批类型 Mapper 接口
 * </p>
 *
 * @author 李健
 * @since 2023-06-18
 */
@Mapper
public interface ProcessMapper extends BaseMapper<Process> {
    public IPage<ProcessVo> selectPage(Page<ProcessVo> page, @Param("vo") ProcessQueryVo processQueryVo);


}
