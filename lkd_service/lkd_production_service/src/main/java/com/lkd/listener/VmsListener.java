package com.lkd.listener;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lkd.business.MsgHandler;
import com.lkd.config.TopicConfig;
import com.lkd.contract.StatusInfo;
import com.lkd.contract.VmStatusContract;
import com.lkd.emq.Topic;
import com.lkd.service.TaskEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Topic("/server/vmtest")
public class VmsListener implements MsgHandler {

    @Autowired
    TaskEntityService taskEntityService;

    @Override
    public void process(String jsonMsg) throws IOException {
        //参数校验
        if (StringUtils.isNotBlank(jsonMsg)) {
            //解析参数
            VmStatusContract vmStatusContract = JSON.parseObject(jsonMsg, VmStatusContract.class);
            //获取机器码
            String innerCode = vmStatusContract.getInnerCode();
            //获取零件
            List<StatusInfo> statusInfoList = vmStatusContract.getStatusInfo();
            statusInfoList.stream().forEach(statusInfo -> {
                if (!statusInfo.isStatus()) {
                    //创建工单
                    taskEntityService.add(innerCode);
                }
            });

        }
    }
}
