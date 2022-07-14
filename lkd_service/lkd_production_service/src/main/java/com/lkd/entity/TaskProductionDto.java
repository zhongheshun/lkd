package com.lkd.entity;

import lombok.Data;

@Data
public class TaskProductionDto extends TaskEntity {

    private Long pageIndex;
    private Long pageSize;
    private Integer userId;
    private Integer status;
    private boolean isRepair;

}
