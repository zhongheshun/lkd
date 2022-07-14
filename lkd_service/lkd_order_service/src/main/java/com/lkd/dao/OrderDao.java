package com.lkd.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lkd.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    List getList(@Param("pageIndex") long pageIndex, @Param("pageSize") long pageSize, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
