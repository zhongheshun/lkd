package com.lkd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lkd.dao.TaskDetailsDao;
import com.lkd.entity.TaskDetailsEntity;
import com.lkd.service.TaskDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 工单详情，只有补货工单才有 服务实现类
 * </p>
 *
 * @author young
 */
@Slf4j
@Service
@Transactional
public class TaskDetailsServiceImpl extends ServiceImpl<TaskDetailsDao, TaskDetailsEntity> implements TaskDetailsService {

}
