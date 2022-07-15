package com.lkd.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lkd.common.VMSystem;
import com.lkd.dao.TaskDao;
import com.lkd.entity.TaskEntity;
import org.springframework.stereotype.Service;

@Service
public interface TaskEntityService extends IService<TaskEntity> {

    public void add(String innerCode);
}
