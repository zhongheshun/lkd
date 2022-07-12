package com.lkd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lkd.vo.PayVO;
import com.lkd.entity.OrderEntity;
import com.lkd.vo.OrderVO;
import com.lkd.vo.Pager;

import java.util.List;

public interface OrderService extends IService<OrderEntity> {


    /**
     * 通过订单编号获取订单实体
     * @param orderNo
     * @return
     */
    OrderEntity getByOrderNo(String orderNo);







}
