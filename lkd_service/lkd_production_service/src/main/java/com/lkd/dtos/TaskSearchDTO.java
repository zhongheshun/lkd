package com.lkd.dtos;


import com.lkd.entity.TaskDetailsEntity;
import com.lkd.entity.TaskEntity;
import lombok.Data;

@Data
public class TaskSearchDTO extends TaskEntity {
    Object[] details;

    //TaskDetailsEntity details;

}
