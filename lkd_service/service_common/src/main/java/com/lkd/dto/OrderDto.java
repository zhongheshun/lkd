package com.lkd.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {

    //售货机编号
    private String innerCode;

    //小程序端jscode
    private String jsCode;

    //商品id
    private String skuld;

    //当前页码
    private String pageIndex;

    //当前页数据
    private String pageSize;

    //订单编号
    private String orderNo;

    //微信用户openId
    private String openId;

    //开始日期
    private LocalDateTime startDate;

    //结束日期
    private LocalDateTime endDate;
}
