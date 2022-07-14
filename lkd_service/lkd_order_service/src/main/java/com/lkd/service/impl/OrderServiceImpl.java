package com.lkd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lkd.dao.OrderDao;
import com.lkd.entity.OrderEntity;
import com.lkd.entity.OrderRequest;
import com.lkd.entity.PageResult;
import com.lkd.service.OrderService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Override
    public OrderEntity getByOrderNo(String orderNo) {
        QueryWrapper<OrderEntity> qw = new QueryWrapper<>();
        qw.lambda()
                .eq(OrderEntity::getOrderNo, orderNo);
        return this.getOne(qw);
    }


    @Override
    public PageResult getOrder(OrderRequest orderRequest) {
        long pageSize = orderRequest.getPageSize();
        long pageIndex = orderRequest.getPageIndex();
        String endDate = orderRequest.getEndDate();
        String startDate = orderRequest.getStartDate();
        List list = orderDao.getList(pageIndex, pageSize, startDate, endDate);

        PageResult pageResult = new PageResult();
        pageResult.setCurrentPageRecords(list);
        pageResult.setPageIndex(pageIndex + "");
        pageResult.setPageSize(pageSize + "");
        pageResult.setTotalCount(list.size() + "");
        pageResult.setTotalPage(list.size() / pageSize + "");
        return pageResult;
    }


}
