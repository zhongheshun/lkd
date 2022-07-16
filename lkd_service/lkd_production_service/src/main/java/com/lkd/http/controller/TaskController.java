package com.lkd.http.controller;

import com.lkd.entity.TaskEntity;
import com.lkd.entity.TaskStatusTypeEntity;
import com.lkd.http.vo.TaskViewModel;
import com.lkd.service.TaskService;
import com.lkd.service.TaskStatusTypeService;
import com.lkd.vo.Pager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    @Autowired
    private TaskStatusTypeService statusTypeService;
    @Autowired
    private TaskService taskService;

    /**
     * 工单状态列表
     *
     * @return
     */
    @GetMapping("/allTaskStatus")
    public List<TaskStatusTypeEntity> allTaskStatus() {
        return statusTypeService.allStatus();
    }

    /**
     * 工单列表页
     *  @param pageIndex
     * @param pageSize
     * @param isRepair
     */
    @GetMapping("/search")
    public Pager<TaskEntity> search(@RequestParam(value = "pageIndex", required = false) Long pageIndex,
                                    @RequestParam(value = "pageSize", required = false) Long pageSize,
                                    @RequestParam(value = "isRepair", required = false) Boolean isRepair) {
        return taskService.search(pageIndex, pageSize, isRepair);
    }
    @GetMapping("/taskInfo/{taskId}")
    public TaskEntity taskInfo(@PathVariable("taskId") Integer taskId){
        return taskService.taskInfo(taskId);
    }
}
