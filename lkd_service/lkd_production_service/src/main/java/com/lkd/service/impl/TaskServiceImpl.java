package com.lkd.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lkd.dao.TaskDao;
import com.lkd.entity.TaskEntity;
import com.lkd.service.TaskService;
import com.lkd.vo.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class TaskServiceImpl extends ServiceImpl<TaskDao, TaskEntity> implements TaskService {
    /**
     * 工单列表
     * @param pageIndex
     * @param pageSize
     * @param isRepair
     * @return
     */
    @Override
    public Pager<TaskEntity> search(Long pageIndex, Long pageSize, Boolean isRepair) {
        //1.检验参数
        if (pageIndex == null || pageSize == null) return null;


        //2.创建分页构造器
        Page<TaskEntity> page = new Page<>(pageIndex, pageSize);

        //3.判断工单类型,若为维修工单
        if (isRepair){
            this.page(page, Wrappers.<TaskEntity>lambdaQuery().eq(TaskEntity::getProductTypeId,3));
            return Pager.build(page);
        }

        //4.非维修工单
        this.page(page,Wrappers.<TaskEntity>lambdaQuery().notIn(TaskEntity::getProductTypeId,3));


        return Pager.build(page);
    }

    /**
     * 工单详情
     */
    @Override
    public TaskEntity taskInfo(Integer taskId) {
        //判断参数
        if (taskId == null) return null;

        return this.getOne(Wrappers.<TaskEntity>lambdaQuery().eq(TaskEntity::getTaskId,taskId));
    }
}
