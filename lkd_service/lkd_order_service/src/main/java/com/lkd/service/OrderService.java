package com.lkd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lkd.entity.OrderEntity;
import com.lkd.entity.OrderRequest;
import com.lkd.entity.PageResult;

public interface OrderService extends IService<OrderEntity> {


    /**
     * 通过订单编号获取订单实体
     * @param orderNo
     * @return
     */
    OrderEntity getByOrderNo(String orderNo);


    PageResult getOrder(OrderRequest orderRequest);
}
