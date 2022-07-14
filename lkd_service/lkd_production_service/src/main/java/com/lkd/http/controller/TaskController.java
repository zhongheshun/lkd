package com.lkd.http.controller;

import com.lkd.dtos.TaskSearchDTO;
import com.lkd.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/task")
public class TaskController {

    /**
     * 9005
     */
    @Autowired
    TaskService tbTaskService;

    /**
     * 创建工单
     */
    @PostMapping("/create")
    public boolean createTbTask(@RequestBody TaskSearchDTO taskDto){
        try {
            tbTaskService.createTbTask(taskDto);
        }catch (Exception e){
            return false;
        }

        return true;
    }
}
