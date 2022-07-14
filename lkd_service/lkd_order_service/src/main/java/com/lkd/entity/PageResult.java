package com.lkd.entity;

import lombok.Data;

@Data
public class PageResult {
    private String pageIndex;
    private String pageSize;
    private String totalPage;
    private String totalCount;
    private Object currentPageRecords;
}
