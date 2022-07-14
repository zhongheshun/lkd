package com.lkd.entity;

import lombok.Data;

@Data
public class OrderRequest {
    private long pageIndex;
    private long pageSize;
    private String orderNo;
    private String startDate;
    private String endDate;
}
