package com.lkd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lkd.entity.TaskStatusTypeEntity;

import java.util.List;

public interface TaskStatusTypeService extends IService<TaskStatusTypeEntity> {
    List<TaskStatusTypeEntity> allStatus();


}
