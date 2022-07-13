package com.lkd.service.impl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lkd.dao.TaskStatusTypeDao;
import com.lkd.entity.TaskStatusTypeEntity;
import com.lkd.service.TaskStatusTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusTypeServiceImpl extends ServiceImpl<TaskStatusTypeDao,TaskStatusTypeEntity> implements TaskStatusTypeService{
    @Override
    public List<TaskStatusTypeEntity> allStatus() {
        return this.list();

    }
}