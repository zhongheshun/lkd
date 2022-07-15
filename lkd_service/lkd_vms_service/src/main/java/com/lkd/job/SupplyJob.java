package com.lkd.job;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lkd.common.VMSystem;
import com.lkd.entity.VendingMachineEntity;
import com.lkd.service.VendingMachineService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.util.ShardingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SupplyJob {
    @Autowired
    private VendingMachineService vendingMachineService;

    @XxlJob(value = "shardingJobHandler")
    public ReturnT<String> execute(String param) throws Exception {

        //分片
        ShardingUtil.ShardingVO shardingVo = ShardingUtil.getShardingVo();
        int shardTotal = shardingVo.getTotal();
        int shardIndex = shardingVo.getIndex();

        //查询所有运营中的售货机
        LambdaQueryWrapper<VendingMachineEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VendingMachineEntity::getVmStatus, VMSystem.VM_STATUS_RUNNING);
        List<VendingMachineEntity> list = vendingMachineService.list(wrapper);

        for (VendingMachineEntity vendingMachineEntity : list) {
            String innerCode = vendingMachineEntity.getInnerCode();
            if (Integer.valueOf(innerCode) % shardTotal == shardIndex) {
                vendingMachineService.sendSupplyInfo(innerCode);
            }
        }
        return ReturnT.SUCCESS;
    }
}

