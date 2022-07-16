package com.lkd.feign;

import com.lkd.dto.OrderDto;
import com.lkd.vo.Pager;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "order-service")
public interface OrderClient {

    /**
     * 小程序端查询订单列表
     * @param dto
     * @return
     */
    @GetMapping("/order/getOrderList")
    public Pager getOrderList(OrderDto dto);
}
