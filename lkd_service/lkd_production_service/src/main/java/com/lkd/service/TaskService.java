package com.lkd.service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lkd.dtos.TaskSearchDTO;
import com.lkd.entity.TaskEntity;
import com.lkd.entity.TaskProductionDto;
import com.lkd.http.vo.CancelTaskViewModel;
import com.lkd.vo.Pager;


public interface TaskService extends IService<TaskEntity> {
    /**
     * 运营APP端待办工单列表查询
     * @param dto
     * @return
     */
    Pager pageList(TaskProductionDto dto);

    /**
     * 接受工单
     * @param taskId
     * @return
     */
    boolean accept(Long taskId);

    /**
     * 完成工单
     * @param taskId
     * @return
     */
    boolean completeTask(Long taskId);

    /**
     * 拒绝/取消工单
     * @param taskId
     * @param cancelTaskViewModel
     * @return
     */
    boolean cancelTask(Long taskId, CancelTaskViewModel cancelTaskViewModel);
    /**
     * 创建工单
     *
     * @return
     */
    void createTbTask(TaskSearchDTO tbTaskDto);
}
