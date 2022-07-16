package com.lkd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lkd.entity.TaskEntity;
import com.lkd.vo.Pager;

public interface TaskService extends IService<TaskEntity> {
    Pager<TaskEntity> search(Long pageIndex, Long pageSize, Boolean isRepair);

    TaskEntity taskInfo(Integer taskId);
}
