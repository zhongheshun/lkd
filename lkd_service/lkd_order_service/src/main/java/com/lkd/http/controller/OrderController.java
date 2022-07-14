package com.lkd.http.controller;

import com.lkd.entity.OrderRequest;
import com.lkd.entity.PageResult;
import com.lkd.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单搜索
     */
//    @GetMapping("/search/{pageIndex}/{pageSize}/{orderNo}/{startDate}/{endDate}")
//    public PageResult getOrder(@PathVariable long pageIndex, @PathVariable long pageSize, @PathVariable String orderNo,
//                               @PathVariable String startDate, @PathVariable String endDate) {
////        return orderService.foundOrder(pageIndex, pageSize, orderNo, startDate, endDate);
//    }
    @GetMapping("/search")
    public PageResult get(OrderRequest orderRequest) {
        return orderService.getOrder(orderRequest);
    }
}
