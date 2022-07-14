package com.lkd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lkd.dtos.TaskSearchDTO;
import com.lkd.entity.TaskEntity;


public interface TaskService extends IService<TaskEntity> {

    /**
     * 创建工单
     *
     * @return
     */
    void createTbTask(TaskSearchDTO tbTaskDto);

}
