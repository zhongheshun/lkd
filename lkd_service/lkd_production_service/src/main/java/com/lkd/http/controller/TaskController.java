package com.lkd.http.controller;

import com.lkd.entity.TaskEntity;
import com.lkd.entity.TaskProductionDto;
import com.lkd.exception.LogicException;
import com.lkd.http.vo.CancelTaskViewModel;
import com.lkd.service.TaskService;
import com.lkd.vo.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/search")
    public Pager list(TaskProductionDto dto) {

        return taskService.pageList(dto);
    }

    /**
     * 接受工单
     *
     * @param taskId
     * @return
     */
    @GetMapping("/accept/{taskId}")
    public boolean accept(@PathVariable("taskId") String taskId) {

        Long id = Long.valueOf(taskId);
        //判断工单是否是当前登录用户
        TaskEntity task = taskService.getById(id);
        if (task.getAssignorId().intValue() != getUserId().intValue()) {
            throw new LogicException("非法操作");
        }
        return taskService.accept(id);


    }

    /**
     * 完成工单
     *
     * @param taskId
     * @return
     */
    @GetMapping("/complete/{taskId}")
    public boolean complete(@PathVariable("taskId") Long taskId) {
        return taskService.completeTask(taskId);
    }

    /**
     * 拒绝/取消工单
     *
     * @param taskId
     * @param cancelTaskViewModel
     * @return
     */
    @PostMapping("/cancel/{taskId}")
    public boolean cancelTask(@PathVariable("taskId") String taskId, @RequestBody CancelTaskViewModel cancelTaskViewModel) {
        Long id = Long.valueOf(taskId);
        //判断工单是否是当前登录用户
        TaskEntity task = taskService.getById(id);
        if (task.getAssignorId().intValue() != getUserId().intValue()) {
            throw new LogicException("非法操作");
        }

        return taskService.cancelTask(id, cancelTaskViewModel);
    }

}
