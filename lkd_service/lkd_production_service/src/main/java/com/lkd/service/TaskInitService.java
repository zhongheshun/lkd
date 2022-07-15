package com.lkd.service;

import com.alibaba.fastjson.JSON;
import com.lkd.common.VMSystem;
import com.lkd.config.TopicConfig;
import com.lkd.dtos.TaskSearchDTO;
import com.lkd.emq.Topic;
import com.lkd.feign.UserService;
import com.lkd.feign.VMService;
import com.lkd.vo.UserVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Set;

import static com.lkd.utils.UserRoleUtils.isOperator;
import static com.lkd.utils.UserRoleUtils.isRepair;

public interface TaskInitService {
    HttpStatus workCountInitJobHandler(String param);

    /**
     * 任务分配给最少任务的工人
     */
    void taskAllocation(TaskSearchDTO taskSearchDTO);
}
