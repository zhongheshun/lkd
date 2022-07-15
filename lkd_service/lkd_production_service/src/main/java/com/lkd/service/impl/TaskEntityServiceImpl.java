package com.lkd.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lkd.common.VMSystem;
import com.lkd.dao.TaskDao;
import com.lkd.dtos.TaskSearchDTO;
import com.lkd.entity.TaskEntity;
import com.lkd.feign.VMService;
import com.lkd.service.TaskEntityService;
import com.lkd.service.TaskInitService;
import com.lkd.vo.VmVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskEntityServiceImpl extends ServiceImpl<TaskDao, TaskEntity> implements TaskEntityService {

    @Autowired
    private VMService vendingMachineClient;

    @Autowired
    private TaskInitService taskInitService;

    /**
     * 自动创建运维工单
     *
     * @param innerCode
     */
    @Override
    public void add(String innerCode) {
        if (StringUtils.isNotBlank(innerCode)) {
            //根据机器编码查询机器地址
            VmVO byInnerCode = vendingMachineClient.getVMInfo(innerCode);
            //封装数据
            TaskEntity task = new TaskEntity();
            if (byInnerCode != null) {
                task.setAddr(byInnerCode.getNodeAddr());
                task.setTaskStatus(VMSystem.TASK_STATUS_CREATE);
                task.setCreateType(0);
                task.setInnerCode(innerCode);
                task.setProductTypeId(VMSystem.TASK_TYPE_REPAIR);
                task.setRegionId(byInnerCode.getRegionId());

                TaskSearchDTO taskSearchDTO = new TaskSearchDTO();

                BeanUtils.copyProperties(taskSearchDTO, task);

                //添加任务
                taskInitService.taskAllocation(taskSearchDTO);
            }
        }
    }
}
