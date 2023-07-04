package com.ys.oa.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ys.oa.mapper.ProcessTemplateMapper;
import com.ys.oa.mapper.ProcessTypeMapper;
import com.ys.oa.model.process.Process;
import com.ys.oa.model.process.ProcessTemplate;
import com.ys.oa.model.process.ProcessType;
import com.ys.oa.service.ProcessService;
import com.ys.oa.service.ProcessTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 审批模板 服务实现类
 * </p>
 *
 * @author 李健
 * @since 2023-06-01
 */
@Service
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplateMapper, ProcessTemplate> implements ProcessTemplateService {


    @Autowired
    private ProcessTypeMapper processTypeMapper;

    @Autowired
    private ProcessService processService;

    @Override
    public IPage<ProcessTemplate> selectPage(Page<ProcessTemplate> pageParam) {
        Page<ProcessTemplate> page = this.baseMapper.selectPage(pageParam,null);
        // 获取所有数据
        List<ProcessTemplate> templateList = page.getRecords();
        // 获取所有类型
        List<ProcessType> typeList = processTypeMapper.selectList(null);

        // 类型装入数据
        for (ProcessTemplate processTemplate:templateList){
            for (ProcessType type:typeList){
                if (processTemplate.getProcessTypeId().equals(type.getId())){
                    processTemplate.setProcessTypeName(type.getName());
                    break;
                }
            }
        }
        return page;
    }

    @Transactional
    @Override
    public void publish(Long id) {
        ProcessTemplate processTemplate = baseMapper.selectById(id);
        // 为1代表已发布
        processTemplate.setStatus(1);
        baseMapper.updateById(processTemplate);
        //优先发布在线流程设计
        if(!StringUtils.isEmpty(processTemplate.getProcessDefinitionPath())) {
           processService.deployByZip(processTemplate.getProcessDefinitionPath());
        }
    }
}
