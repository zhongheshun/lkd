package com.lkd.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lkd.entity.ChannelEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChannelDao extends BaseMapper<ChannelEntity> {

    //sku 商品  channel 货道  innerCode 售货机设备编号
    @Select("select * from tb_channel where inner_code=#{innerCode}")
    @Results(id="channelMap",value = {
            @Result(property = "channelId",column = "channel_id"),
            @Result(property = "skuId",column = "sku_id"),
            @Result(property = "sku",column = "sku_id",one = @One(select = "com.lkd.dao.SkuDao.getById"))
    })
    List<ChannelEntity> getChannelsByInnerCode(String innerCode);

}
